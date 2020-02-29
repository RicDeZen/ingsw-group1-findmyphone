package ingsw.group1.findmyphone;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;

import ingsw.group1.findmyphone.activity.ActivityConstantsUtils;
import ingsw.group1.findmyphone.alarm.AlarmManager;
import ingsw.group1.findmyphone.cryptography.PasswordManager;
import ingsw.group1.findmyphone.cryptography.SMSCipher;
import ingsw.group1.findmyphone.event.EventManager;
import ingsw.group1.findmyphone.event.EventType;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.event.SMSLoggableEvent;
import ingsw.group1.findmyphone.location.CommandResponseLocation;
import ingsw.group1.findmyphone.location.LocationManager;
import ingsw.group1.findmyphone.log.LogManager;


/**
 * This is the main manager.
 *
 * @author Pardeep Kumar
 * @author Giorgia Bortoletti (refactoring)
 */
public class Manager {
    private static final String MANAGER_TAG = "Manager";
    private String passwordToSendSMS;
    private Context currentContext;

    private AlarmManager alarmManager;
    private LocationManager locationManager;
    private SMSManager smsManager;
    private CommandResponseLocation sendResponseSms;
    private PasswordManager passwordManager;
    private EventManager eventManager;
    private long time = System.currentTimeMillis();
    private SMSLogDatabase smsLogDatabase;
    private SMSLogDatabase eventManagerDatabase;

    /**
     * Constructor.
     *
     * @param context application context.
     */
    public Manager(Context context) {
        currentContext = context.getApplicationContext();
        locationManager = new LocationManager();
        alarmManager = new AlarmManager();
        smsManager = SMSManager.getInstance();
        passwordManager = new PasswordManager(context);
        eventManager = EventManager.getInstance(context);
        smsLogDatabase = SMSLogDatabase.getInstance(context, LogManager.DEFAULT_LOG_DATABASE);
        eventManagerDatabase = SMSLogDatabase.getInstance(context, EventManager.EVENT_DATABASE);
    }

    //---------------------------- LISTENERS ----------------------------

    /**
     * Removes the receivedListener.
     */
    public void removeReceiveListener() {
        smsManager.removeReceivedListener(currentContext);
    }

    /**
     * Saves in memory the service class name to wake up. It doesn't need an
     * instance of the class, it just saves the name and instantiates it when needed.
     *
     * @param receivedListenerClassName the listener called on message received.
     * @param <T>                       the class type that extends {@link SMSReceivedServiceListener} to be called.
     */
    public <T extends SMSReceivedServiceListener> void setReceiveListener(Class<T> receivedListenerClassName) {
        smsManager.setReceivedListener(receivedListenerClassName, currentContext);
    }

    //---------------------------- SEND REQUEST ----------------------------

    /**
     * Send a message to the peer for a location request.
     *
     * @param smsPeer the peer to which  send sms Location request.
     * @author Turcato, Kumar
     */
    public void sendLocationRequest(SMSPeer smsPeer) {
        String requestStringMessage = locationManager.getRequestLocationMessage();
        String encryptedRequest = SMSCipher.encrypt(requestStringMessage, passwordToSendSMS);
        SMSMessage smsMessage = new SMSMessage(smsPeer, encryptedRequest);
        smsManager.sendMessage(smsMessage);
        SMSLoggableEvent smsLoggableEvent = new SMSLoggableEvent(EventType.LOCATION_REQUEST_SENT, smsPeer.getAddress(), time,
                EventManager.PASSWORD_TAG + passwordToSendSMS);
        eventManagerDatabase.addEvent(smsLoggableEvent);
        Log.d(MANAGER_TAG, String.valueOf(eventManagerDatabase.addEvent(smsLoggableEvent)));
        Log.d(MANAGER_TAG, eventManager.printEverything());
    }

    /**
     * Sends a message to the peer for an Alarm request.
     *
     * @param smsPeer the peer to which  send sms Location & alarm request.
     * @author Turcato, Kumar
     */
    public void sendAlarmRequest(SMSPeer smsPeer) {
        String requestStringMessage = alarmManager.getAlarmRequestMessage();
        String encryptedRequest = SMSCipher.encrypt(requestStringMessage, passwordToSendSMS);
        SMSMessage smsMessage = new SMSMessage(smsPeer, encryptedRequest);
        smsManager.sendMessage(smsMessage);
        SMSLoggableEvent smsLoggableEvent = new SMSLoggableEvent(EventType.RING_REQUEST_SENT, smsPeer.getAddress(), time, String.valueOf(time));
        eventManagerDatabase.addEvent(smsLoggableEvent);
        smsLogDatabase.addEvent(smsLoggableEvent);
        Log.d(MANAGER_TAG, String.valueOf(eventManagerDatabase.addEvent(smsLoggableEvent)));
        Log.d(MANAGER_TAG, eventManager.printEverything());
    }

    //---------------------------- ACTIONS AFTER RECEIVING A REQUEST ----------------------------

    /**
     * Checks the received string and can active alarm or send location based on its content.
     *
     * @param requestMessage the request message that decide which action to do.
     * @param phoneNumber    the number to which send your phone's location or active alarm.
     */
    public void analyzeRequest(String requestMessage, String phoneNumber) {
        String decryptedString = SMSCipher.decrypt(requestMessage, passwordManager.retrievePassword());
        if (locationManager.isLocationRequest(decryptedString)) {
            //Action to execute when device receives a Location request
            sendResponseSms = new CommandResponseLocation(phoneNumber, currentContext.getApplicationContext());
            locationManager.getLastLocation(currentContext.getApplicationContext(), sendResponseSms);
        }
        //User has to close app manually to stop the alarm
        if (alarmManager.isAlarmRequest(decryptedString))
            alarmManager.startAlarm(currentContext.getApplicationContext());
    }

    /**
     * Based on the response this method opens the activityClass or open the default map app.
     *
     * @param messageResponse The message received.
     * @param activityClass   The activity app to be opened.
     */
    public void activeResponse(SMSMessage messageResponse, Class activityClass) {
        String requestMessage = messageResponse.getData();
        if (passwordToSendSMS.equals(EventManager.NO_PASSWORD_FOUND_ERROR))
            setPasswordToSendMessage(passwordManager.retrievePassword());
        String decryptedString = SMSCipher.decrypt(requestMessage, passwordToSendSMS);
        if (locationManager.isLocationRequest(decryptedString)
                || alarmManager.isAlarmRequest(decryptedString)) {
            openRequestsActivity(decryptedString, messageResponse.getPeer().getAddress(), activityClass);
        }

        //The only expected response
        if (locationManager.isLocationResponse(decryptedString)) {
            Double longitude;
            Double latitude;
            try {
                longitude = Double.parseDouble(locationManager.getLongitudeFrom(decryptedString));
                latitude = Double.parseDouble(locationManager.getLatitudeFrom(decryptedString));
                locationManager.openMapsUrl(currentContext, latitude, longitude);
            } catch (Exception e) {
                //Written in log for future users to report
                Log.e(MANAGER_TAG, e.getMessage());
            }
        }
        Log.d(MANAGER_TAG, eventManager.printEverything());
    }

    /**
     * Opens an activityClass, forwarding the receivedMessageText and the receivedMessageReturnAddress.
     *
     * @param activityClass                the activity to be opened.
     * @param receivedMessageText          the text of the request message.
     * @param receivedMessageReturnAddress the return address of the request message.
     * @author Turcato
     */
    private void openRequestsActivity(String receivedMessageText, String receivedMessageReturnAddress, Class activityClass) {
        Log.d(MANAGER_TAG, "OpenRequestsActivity");
        Intent openAlarmAndLocateActivityIntent = new Intent(currentContext.getApplicationContext(), activityClass);
        openAlarmAndLocateActivityIntent.putExtra(ActivityConstantsUtils.RECEIVED_STRING_MESSAGE, receivedMessageText);
        openAlarmAndLocateActivityIntent.putExtra(ActivityConstantsUtils.RECEIVED_STRING_ADDRESS, receivedMessageReturnAddress);
        openAlarmAndLocateActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentContext.getApplicationContext().startActivity(openAlarmAndLocateActivityIntent);
    }

    /**
     * Sets the password used to encrypt message going outside.
     *
     * @param passwordToSendMessage the new password.
     */
    public void setPasswordToSendMessage(String passwordToSendMessage) {
        passwordToSendSMS = passwordToSendMessage;
    }

    /**
     * Finds in the database the password of an event with the same address of smslogEvent.
     *
     * @param smsPeer
     * @return A string containing the password.
     */
    public String findPassword(SMSPeer smsPeer) {
        return eventManager.findPassword(smsPeer);
    }

    /**
     * Print all the relative info about all the events in the eventManagerDatabase.
     *
     * @return String containing all the info about all the events.
     */
    public String printEverything() {
        return eventManager.printEverything();
    }

}