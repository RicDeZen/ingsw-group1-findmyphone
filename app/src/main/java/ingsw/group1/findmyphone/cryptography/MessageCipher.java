package ingsw.group1.findmyphone.cryptography;


import com.eis.communication.Message;

/**
 * Interface defining the behaviour of a SMSCipher object
 *
 * @param <M> The type of the message
 * @author Pardeep Kumar
 */

public interface MessageCipher<M extends Message> {

    M cipherMessage(M messageToCipher);

    M decipherMessage(M messageToDecipher);

    void setPassword(String password);
}
