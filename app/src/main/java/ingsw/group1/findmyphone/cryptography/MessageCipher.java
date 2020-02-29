package ingsw.group1.findmyphone.cryptography;


import com.eis.communication.Message;

/**
 * Interface defining the behaviour of a class that encrypt and decrypt messages.
 *
 * @param <M> The type of the message
 * @author Pardeep Kumar
 */

public interface MessageCipher<M extends Message> {
    /**
     * Encrypts the message and return the encrypted message.
     *
     * @param messageToCipher The message to Cipher.
     * @return The encrypted message.
     */
    M cipherMessage(M messageToCipher);

    /**
     * Decrypts the message and return the decrypted message.
     *
     * @param messageToDecipher The message to decrypt.
     * @return The decrypted message.
     */
    M decipherMessage(M messageToDecipher);

    /**
     * Sets the password/key used for encryption/decryption
     *
     * @param password The password used to encrypt/decrypt
     */
    void setPassword(String password);
}
