package ingsw.group1.findmyphone.location;

import android.location.Location;

import androidx.annotation.NonNull;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.MessageParser;
import ingsw.group1.findmyphone.ResponseManager;


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
    private ResponseManager responseManager;

    private SMSPeer destination;
    private Long startTime;

    /**
     * Constructor
     *
     * @param destination receiver's phone number.
     * @param startTime   The time at which this event started.
     */
    public LocationResponseCommand(SMSPeer destination, @NonNull Long startTime,
                                   ResponseManager responseManager) {
        this.destination = destination;
        this.startTime = startTime;
        this.responseManager = responseManager;
    }

    /**
     * Sends a sms message to the defined receivingAddress
     * with a text specifically formatted with foundLocation
     *
     * @param foundLocation {@link Location} to forward to given phone number
     */
    public void execute(Location foundLocation) {
        GeoPosition foundPosition = new GeoPosition(
                foundLocation.getLatitude(),
                foundLocation.getLongitude()
        );
        SMSMessage smsMessage = messageParser.getLocationResponse(
                destination,
                foundPosition
        );
        smsManager.sendMessage(smsMessage);
        responseManager.sendLocationResponse(
                destination,
                startTime,
                foundPosition
        );
    }

}
