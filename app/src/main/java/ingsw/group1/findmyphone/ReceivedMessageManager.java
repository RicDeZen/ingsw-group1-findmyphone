package ingsw.group1.findmyphone;


import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;

import ingsw.group1.findmyphone.cryptography.PasswordManager;
import ingsw.group1.findmyphone.cryptography.SMSCipher;

/**
 * Class called when a message from the app is received.
 *
 * @author Pardeep Kumar
 */
public class ReceivedMessageManager extends SMSReceivedServiceListener {

    private MessageParser messageParser = new MessageParser();

    /**
     * Callback for when a sms message sent from this library is received, also analyzes the
     * content of the message. Assumes that the message may or may not be deciphered since
     * requests are but responses are not.
     *
     * @param message the received message.
     */
    @Override
    public void onMessageReceived(SMSMessage message) {
        if (!messageParser.getMessageType(message).equals(MessageType.UNKNOWN)) {
            ResponseManager.getInstance(this).performActionBasedOnMessage(this, message);
            return;
        }
        SMSCipher cipher = new SMSCipher(new PasswordManager(this).retrievePassword());
        SMSMessage decipheredMessage = cipher.decipherMessage(message);
        if (!messageParser.getMessageType(decipheredMessage).equals(MessageType.UNKNOWN)) {
            ResponseManager.getInstance(this)
                    .performActionBasedOnMessage(this, decipheredMessage);
        }
    }

}
