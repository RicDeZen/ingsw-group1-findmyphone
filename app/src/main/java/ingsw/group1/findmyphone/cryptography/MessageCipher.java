package ingsw.group1.findmyphone.cryptography;

import ingsw.group1.msglibrary.Message;

public interface MessageCipher<M extends Message> {
    void setPassword(String newPassword);

    M cipherMessage(M messageToCipher);

    M decipherMessage(M messageToDecipher);
}
