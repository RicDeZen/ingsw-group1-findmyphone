package ingsw.group1.findmyphone.cryptography;


import androidx.annotation.NonNull;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import static ingsw.group1.findmyphone.cryptography.SMSCipherUtils.addPadding;
import static ingsw.group1.findmyphone.cryptography.SMSCipherUtils.fromStringToAscii;

/**
 * Class used to cipher and decipher a SMSMessage.
 *
 * @author Pardeep Kumar
 */
public class SMSCipher implements MessageCipher<SMSMessage> {

    private String passwordToCypher;

    /**
     * Constructor used to build a cypher object to encrypt/decrypt with the chosen password.
     *
     * @param password The password used for both encryption and decryption.
     */
    public SMSCipher(@NonNull String password) {
        passwordToCypher = password;
    }


    /**
     * Cyphers the message contained in the SMSMessage object.
     *
     * @param messageToCipher The object that contains the message.
     * @return An encrypted SMSMessage object ready to be sent.
     */
    public SMSMessage cipherMessage(SMSMessage messageToCipher) {
        String messageToEncrypt = messageToCipher.getData();
        SMSPeer peerOfMessage = messageToCipher.getPeer();
        String encryptedMessage = encrypt(messageToEncrypt, passwordToCypher);
        return new SMSMessage(peerOfMessage, encryptedMessage);
    }

    /**
     * Decipher the message contained in the SMSMessage object.
     *
     * @param messageToDecipher The object that contains the message.
     * @return A decrypted SMSMessage object ready to be sent.
     */
    public SMSMessage decipherMessage(SMSMessage messageToDecipher) {
        String messageToDecrypt = messageToDecipher.getData();
        SMSPeer peerOfMessage = messageToDecipher.getPeer();
        String decryptedMessage = decrypt(messageToDecrypt, passwordToCypher);
        return new SMSMessage(peerOfMessage, decryptedMessage);
    }

    /**
     * Encrypts the string using xor encryption with the key.
     *
     * @param stringToCipher The string to be encrypted.
     * @param key            The Key used for encryption.
     * @return The encrypted String.
     */
    public static String encrypt(String stringToCipher, String key) {
        //converts every characters of both string into ASCII Code and saved inside a int array
        int[] strInt = fromStringToAscii(stringToCipher);
        int[] keyInt = fromStringToAscii(key);
        StringBuilder encryptedStr = new StringBuilder();
        for (int i = 0; i < strInt.length; i++) {
            //XOR the pair of characters of index i of the 2 strings and convert the result in hex
            String s = Integer.toHexString(strInt[i] ^ keyInt[i % keyInt.length]);
            //this padding is add because for instance 07 is written 7 inside the string
            String s2 = addPadding(s, 2);
            encryptedStr.append(s2);
        }
        return encryptedStr.toString();
    }

    /**
     * Decrypts the string using xor encryption with the key.
     *
     * @param stringToDecipher The string to be decrypted.
     * @param key              The key used for decryption.
     * @return The Decrypted string.
     */
    public static String decrypt(String stringToDecipher, String key) {
        StringBuilder decrypted = new StringBuilder();
        //converts every characters of the key into ASCII Code and saves inside a int array
        int[] keyInt = fromStringToAscii(key);
        int[] intArr = new int[stringToDecipher.length()];
        String[] strArr = new String[stringToDecipher.length() / 2];
        for (int i = 0; i < strArr.length; i++) {
            //save in strArr[i] every couple of 2 characters because in hex a couple of value is a character 01 23 45 ...
            strArr[i] = stringToDecipher.substring(i * 2, (i * 2) + 2);
        }
        for (int i = 0; i < strArr.length; i++) {
            //converts the strArr (which was in hex) into decimal.
            int decimal = Integer.parseInt(strArr[i], 16);
            intArr[i] = decimal;
            //Decrypt the String and converts it from ascii to a normal String.
            String s = String.valueOf(intArr[i] ^ keyInt[i % keyInt.length]);
            int n = Integer.valueOf(s, 10);
            decrypted.append((char) n);
        }
        return decrypted.toString();
    }


    /**
     * Sets the password of the cypher object.
     *
     * @param password The new set password.
     */
    public void setPassword(String password) {
        passwordToCypher = password;
    }

    /**
     * Returns the password of the cypher object.
     *
     * @return The password of the cypher object.
     */
    public String getPassword() {
        return passwordToCypher;
    }
}


