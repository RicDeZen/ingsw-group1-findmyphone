package ingsw.group1.findmyphone;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.activity.NavHolderActivity;
import ingsw.group1.findmyphone.contacts.SMSContactManager;
import ingsw.group1.findmyphone.event.EventType;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.location.GeoPosition;
import ingsw.group1.findmyphone.location.LocationManager;
import ingsw.group1.findmyphone.location.LocationResponseCommand;
import ingsw.group1.findmyphone.log.LogManager;
import ingsw.group1.findmyphone.log.items.LogItem;
import ingsw.group1.findmyphone.log.items.LogItemFormatter;

/**
 * Class meant to manage responses to the various incoming messages. This includes starting the
 * appropriate activities/services if necessary.
 *
 * @author Riccardo De Zen.
 */
public class ResponseManager {

    private static ResponseManager instance;

    private LocationManager locationManager = new LocationManager();
    private MessageParser messageParser = new MessageParser();
    private SMSContactManager contacts;
    private SMSLogDatabase logDatabase;
    private LogItemFormatter logItemFormatter;

    /**
     * Default constructor, private in order to be hidden from the user.
     */
    private ResponseManager(Context context) {
        logDatabase = SMSLogDatabase.getInstance(context, LogManager.DEFAULT_LOG_DATABASE);
        contacts = SMSContactManager.getInstance(context);
        logItemFormatter = new LogItemFormatter(context);
    }

    /**
     * Method to return the only available instance of the class.
     *
     * @param context The calling Context.
     * @return The available instance of this class.
     */
    public static ResponseManager getInstance(Context context) {
        if (instance != null) return instance;
        instance = new ResponseManager(context.getApplicationContext());
        return instance;
    }

    /**
     * The message is assumed to have already been decrypted.
     * Performs the appropriate action based on what the type of the message is:
     * - {@link MessageType#RING_REQUEST} The foreground Service is started.
     * - {@link MessageType#RING_RESPONSE} The Ring Event is recorded in the Log Database.
     * - {@link MessageType#LOCATION_REQUEST} The asynchronous location retrieval is started and
     * will send back the result when available.
     * - {@link MessageType#LOCATION_RESPONSE} The Location Event is recorded in the Log Database.
     *
     * @param callingContext The calling context, used if starting a Service or such.
     * @param message        The message to handle.
     */
    public void performActionBasedOnMessage(Context callingContext, @NonNull SMSMessage message) {
        switch (messageParser.getMessageType(message)) {
            case RING_REQUEST:
                Intent ringService = new Intent(callingContext, RingService.class);
                ringService.putExtra(RingService.ADDRESS_KEY, message.getPeer().getAddress());
                callingContext.startService(ringService);
                break;
            case RING_RESPONSE:
                processRingResponse(message);
                break;
            case LOCATION_REQUEST:
                locationManager.getLastLocation(
                        callingContext,
                        new LocationResponseCommand(
                                message.getPeer(),
                                System.currentTimeMillis(),
                                this
                        )
                );
                break;
            case LOCATION_RESPONSE:
                processLocationResponse(message);
                break;
        }
        // UNKNOWN is ignored.
    }

    /**
     * Method used to send a ring response.
     *
     * @param destination The peer to which the response is directed to.
     * @param elapsedTime The elapsed time in milliseconds.
     */
    public void sendRingResponse(@NonNull SMSPeer destination, long startTime, long elapsedTime) {
        SMSManager.getInstance().sendMessage(
                messageParser.getRingResponse(destination, elapsedTime)
        );
        logDatabase.addEvent(new SMSLogEvent(
                EventType.RING_REQUEST_RECEIVED,
                destination.getAddress(),
                startTime,
                String.valueOf(elapsedTime)
        ));
    }

    /**
     * Method used to send a location response.
     *
     * @param destination The peer to which the response is directed to.
     * @param startTime   The time in milliseconds from 1 Jan 1970 when this event took place.
     * @param position    The elapsed time in milliseconds.
     */
    public void sendLocationResponse(@NonNull SMSPeer destination,
                                     @NonNull Long startTime,
                                     @NonNull GeoPosition position) {
        SMSManager.getInstance().sendMessage(
                messageParser.getLocationResponse(destination, position)
        );
        logDatabase.addEvent(new SMSLogEvent(
                EventType.LOCATION_REQUEST_RECEIVED,
                destination.getAddress(),
                startTime,
                String.valueOf(position)
        ));
    }

    /**
     * This method processes a ring response by recording the event in the database and updating
     * {@link SharedData#getLastEvent()}.
     *
     * @param message A message. It is assumed to be of {@link MessageType#RING_RESPONSE} type.
     */
    private void processRingResponse(@NonNull SMSMessage message) {
        Long time = messageParser.getTimeIfRingResponse(message.getData());
        SMSLogEvent correspondingEvent = new SMSLogEvent(
                EventType.RING_REQUEST_SENT,
                message.getPeer().getAddress(),
                System.currentTimeMillis(),
                (time == null) ? null : time.toString()
        );
        logDatabase.addEvent(correspondingEvent);
        MutableLiveData<LogItem> lastEvent = NavHolderActivity.sharedData.getLastEvent();
        // We don't know if the activity is running.
        if (lastEvent == null) return;
        lastEvent.postValue(logItemFormatter.formatItem(correspondingEvent));
    }

    /**
     * This method processes a location response by recording the event in the database and updating
     * {@link SharedData#getLastEvent()}.
     *
     * @param message A message. It is assumed to be of {@link MessageType#RING_RESPONSE} type.
     */
    private void processLocationResponse(@NonNull SMSMessage message) {
        GeoPosition pos = messageParser.getPositionIfLocationResponse(message.getData());
        SMSLogEvent correspondingEvent = new SMSLogEvent(
                EventType.LOCATION_REQUEST_SENT,
                message.getPeer().getAddress(),
                System.currentTimeMillis(),
                (pos == null) ? null : pos.toString()
        );
        logDatabase.addEvent(correspondingEvent);
        MutableLiveData<LogItem> lastEvent = NavHolderActivity.sharedData.getLastEvent();
        // We don't know if the activity is running.
        if (lastEvent == null) return;
        lastEvent.postValue(logItemFormatter.formatItem(correspondingEvent));
    }

}
