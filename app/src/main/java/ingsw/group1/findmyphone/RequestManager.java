package ingsw.group1.findmyphone;

import android.content.Context;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;

import ingsw.group1.findmyphone.cryptography.SMSCipher;


/**
 * This is the main manager.
 *
 * @author Pardeep Kumar
 * @author Giorgia Bortoletti (refactoring)
 */
public class RequestManager {

    private static RequestManager instance;

    private SMSManager smsManager = SMSManager.getInstance();
    private MessageParser messageParser = new MessageParser();

    /**
     * Default constructor, declared private to enforce singleton usage.
     */
    private RequestManager() {
    }

    public static RequestManager getInstance() {
        if (instance != null) return instance;
        instance = new RequestManager();
        return instance;
    }

    //---------------------------- LISTENERS ----------------------------

    /**
     * Removes the receivedListener.
     *
     * @param context The calling Context.
     */
    public void removeReceiveListener(Context context) {
        smsManager.removeReceivedListener(context);
    }

    /**
     * Saves in memory the service class name to wake up. It doesn't need an
     * instance of the class, it just saves the name and instantiates it when needed.
     *
     * @param context                   The calling Context.
     * @param receivedListenerClassName the listener called on message received.
     * @param <T>                       the class type that extends
     *                                  {@link SMSReceivedServiceListener} to be called.
     */
    public <T extends SMSReceivedServiceListener> void setReceiveListener(
            Context context,
            Class<T> receivedListenerClassName) {
        smsManager.setReceivedListener(receivedListenerClassName, context);
    }

    //---------------------------- SEND REQUEST ----------------------------

    /**
     * Send a message to the peer for a location request.
     *
     * @param destination the peer to which  send sms Location request.
     * @author Turcato
     */
    public void sendLocationRequest(SMSPeer destination, String password) {
        SMSCipher cipher = new SMSCipher(password);
        smsManager.sendMessage(cipher.cipherMessage(messageParser.getLocationRequest(destination)));
    }

    /**
     * Sends a message to the peer for an Alarm request.
     *
     * @param destination the peer to which  send sms Location & alarm request.
     * @author Turcato
     */
    public void sendAlarmRequest(SMSPeer destination, String password) {
        SMSCipher cipher = new SMSCipher(password);
        smsManager.sendMessage(cipher.cipherMessage(messageParser.getRingRequest(destination)));
    }

}