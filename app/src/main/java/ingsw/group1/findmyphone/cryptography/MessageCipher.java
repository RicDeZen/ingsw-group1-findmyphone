package ingsw.group1.findmyphone.cryptography;


import com.eis.communication.Message;

/**
 *
 * @author Pardeep Kumar
 * @param <M>
 */

public interface MessageCipher<M extends Message> {

    M cipherMessage(M messageToCipher);

    M decipherMessage(M messageToDecipher);

    void setPassword(String password);
}
