package ingsw.group1.findmyphone;




import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;

import ingsw.group1.findmyphone.activity.AlarmAndLocateResponseActivity;

/**
 * Class called when a message from the app is received.
 *
 * @author Pardeep Kumar
 */
public class ServiceManager extends SMSReceivedServiceListener {
    /**
     * Callback for when a sms message sent from this library is received, also analyzes the content of the message.
     *
     * @param message the received message.
     */
    @Override
    public void onMessageReceived(SMSMessage message) {
        Manager manager = new Manager(getApplicationContext());
        manager.activeResponse(message, AlarmAndLocateResponseActivity.class);
        String receivedTextMessage = message.getData();
        SMSPeer receivedMessageAddress = message.getPeer();
        manager.analyzeRequest(receivedTextMessage, receivedMessageAddress.getAddress());
    }

}
