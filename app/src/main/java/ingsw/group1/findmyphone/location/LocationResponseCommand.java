package ingsw.group1.findmyphone.location;

import android.location.Location;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.MessageParser;


/**
 * Action to execute when receiving a Location request
 * Sends back current position
 *
 * @author Turcato
 * @author Giorgia Bortoletti (refactoring)
 */
public class LocationResponseCommand implements Command<Location> {

    private SMSManager smsManager = SMSManager.getInstance();
    private MessageParser messageParser = new MessageParser();

    private SMSPeer destination;

    /**
     * Constructor
     *
     * @param destination receiver's phone number
     */
    public LocationResponseCommand(SMSPeer destination) {
        this.destination = destination;
    }

    /**
     * Sends a sms message to the defined receivingAddress
     * with a text specifically formatted with foundLocation
     *
     * @param foundLocation {@link Location} to forward to given phone number
     */
    public void execute(Location foundLocation) {
        SMSMessage smsMessage = messageParser.getLocationResponse(
                destination,
                new GeoPosition(foundLocation.getLatitude(), foundLocation.getLongitude())
        );
        smsManager.sendMessage(smsMessage);
    }

}
