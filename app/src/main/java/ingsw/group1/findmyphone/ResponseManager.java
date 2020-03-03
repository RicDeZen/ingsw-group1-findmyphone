package ingsw.group1.findmyphone;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.event.EventType;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.location.GeoPosition;
import ingsw.group1.findmyphone.location.LocationManager;
import ingsw.group1.findmyphone.location.LocationResponseCommand;
import ingsw.group1.findmyphone.log.LogManager;

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
    private SMSLogDatabase logDatabase;

    /**
     * Default constructor, private in order to be hidden from the user.
     */
    private ResponseManager(Context context) {
        logDatabase = SMSLogDatabase.getInstance(context, LogManager.DEFAULT_LOG_DATABASE);
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
                // TODO register in log
                break;
            case RING_RESPONSE:
                Long time = messageParser.getTimeIfRingResponse(message.getData());
                logDatabase.addEvent(
                        new SMSLogEvent(
                                EventType.RING_REQUEST_SENT,
                                message.getPeer().getAddress(),
                                System.currentTimeMillis(),
                                (time == null) ? null : time.toString()
                        )
                );
                break;
            case LOCATION_REQUEST:
                locationManager.getLastLocation(
                        callingContext,
                        new LocationResponseCommand(message.getPeer())
                );
                // TODO register in log
                break;
            case LOCATION_RESPONSE:
                GeoPosition pos = messageParser.getPositionIfLocationResponse(message.getData());
                logDatabase.addEvent(
                        new SMSLogEvent(
                                EventType.LOCATION_REQUEST_SENT,
                                message.getPeer().getAddress(),
                                System.currentTimeMillis(),
                                (pos == null) ? null : pos.toString()
                        )
                );
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
    public void sendRingResponse(@NonNull SMSPeer destination, long elapsedTime) {
        SMSManager.getInstance().sendMessage(
                messageParser.getRingResponse(destination, elapsedTime)
        );
    }

}
