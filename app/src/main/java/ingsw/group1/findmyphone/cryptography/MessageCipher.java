package ingsw.group1.findmyphone.cryptography;

import ingsw.group1.msglibrary.Message;

/**
 * @param <M>
 * @author Pardeep Kumar
 */

public interface MessageCipher<M extends Message> {

    M cipherMessage(M messageToCipher);

    M decipherMessage(M messageToDecipher);

    void setPassword(String password);
}
