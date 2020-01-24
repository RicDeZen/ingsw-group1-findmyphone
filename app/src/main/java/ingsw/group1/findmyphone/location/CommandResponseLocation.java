package ingsw.group1.findmyphone.location;

import android.content.Context;
import android.location.Location;

import ingsw.group1.msglibrary.SMSManager;
import ingsw.group1.msglibrary.SMSMessage;
import ingsw.group1.msglibrary.SMSPeer;

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
     * @author Turcato
     *
     * Constructor
     *
     * @param receiverAddress    receiver's phone number
     * @param applicationContext android application Context
     */
    public CommandResponseLocation(String receiverAddress, Context applicationContext){
        receivingAddress = receiverAddress;
        smsManager = SMSManager.getInstance(applicationContext);
        locationManager = new LocationManager();
    }

    /**
     * @author Turcato
     *
     * Sends a sms message to the defined receivingAddress
     * with a text specifically formatted with foundLocation
     *
     * @param foundLocation location to forward to given phone number
     */
    public void execute(Location foundLocation) {
        String responseMessage = locationManager.getResponseMessage(foundLocation);
        SMSMessage smsMessage = new SMSMessage(new SMSPeer(receivingAddress), responseMessage);
        smsManager.sendMessage(smsMessage);
    }

}
