package ingsw.group1.findmyphone.location;

import android.content.Context;
import android.location.Location;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;


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
    }

    /**
     * Sends a sms message to the defined receivingAddress
     * with a text specifically formatted with foundLocation
     *
     * @param foundLocation {@link Location} to forward to given phone number
     */
    public void execute(Location foundLocation) {
        String responseMessage = locationManager.getResponseMessage(foundLocation);
        SMSMessage smsMessage = new SMSMessage(new SMSPeer(receivingAddress), responseMessage);
        smsManager.sendMessage(smsMessage);
    }

}
