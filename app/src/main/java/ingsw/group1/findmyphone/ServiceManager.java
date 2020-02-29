package ingsw.group1.findmyphone;


import android.util.Log;

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
    protected final String SERVICE_MANAGER_TAG = "ServiceManagerTag";

    /**
     * Callback for when a sms message sent from this library is received.
     * Decrypt the message and analyzes its content.
     *
     * @param message the received message.
     */
    @Override
    public void onMessageReceived(SMSMessage message) {
        Manager manager = new Manager(getApplicationContext());
        manager.setPasswordToSendMessage(manager.findPassword(message.getPeer()));
        Log.d(SERVICE_MANAGER_TAG, "password set to: " + manager.findPassword(message.getPeer()));
        Log.d(SERVICE_MANAGER_TAG, manager.printEverything());
        String receivedTextMessage = message.getData();
        SMSPeer receivedMessageAddress = message.getPeer();
        manager.analyzeRequest(receivedTextMessage, receivedMessageAddress.getAddress());
        manager.activeResponse(message, AlarmAndLocateResponseActivity.class);

    }

}
