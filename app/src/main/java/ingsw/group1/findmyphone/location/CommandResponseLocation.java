package ingsw.group1.findmyphone.location;

import android.content.Context;
import android.location.Location;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.cryptography.PasswordManager;
import ingsw.group1.findmyphone.cryptography.SMSCipher;
import ingsw.group1.findmyphone.event.EventType;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.log.LogManager;


/**
 * Action to execute when receiving a Location request
 * Sends back current position
 *
 * @author Turcato
 * @author Giorgia Bortoletti (refactoring)
 */
public class CommandResponseLocation implements Command<Location> {
    private String receivingAddress;
    private SMSManager smsManager;
    private LocationManager locationManager;
    private PasswordManager passwordManager;
    private SMSLogDatabase smsLogDatabase;
    private long time = System.currentTimeMillis();


    /**
     * Constructor
     *
     * @param receiverAddress    receiver's phone number
     * @param applicationContext android application Context
     */
    public CommandResponseLocation(String receiverAddress, Context applicationContext) {
        receivingAddress = receiverAddress;
        smsManager = SMSManager.getInstance();
        locationManager = new LocationManager();
        passwordManager = new PasswordManager(applicationContext);
        smsLogDatabase = SMSLogDatabase.getInstance(applicationContext, LogManager.DEFAULT_LOG_DATABASE);
    }

    /**
     * Sends a sms message to the defined receivingAddress
     * with a text specifically formatted with foundLocation
     *
     * @param foundLocation {@link Location} to forward to given phone number
     */
    public void execute(Location foundLocation) {
        String responseMessage = locationManager.getResponseMessage(foundLocation);
        smsLogDatabase.addEvent(new SMSLogEvent(EventType.LOCATION_REQUEST_SENT, receivingAddress, time,
                LocationMessageHelper.getLatitudeFrom(responseMessage) +
                        GeoPosition.POSITION_SPLIT_SEQUENCE +
                        LocationMessageHelper.getLongitudeFrom(responseMessage)));
        String encryptedResponse = SMSCipher.encrypt(responseMessage, passwordManager.retrievePassword());
        SMSMessage smsMessage = new SMSMessage(new SMSPeer(receivingAddress), encryptedResponse);
        smsManager.sendMessage(smsMessage);
    }

}
