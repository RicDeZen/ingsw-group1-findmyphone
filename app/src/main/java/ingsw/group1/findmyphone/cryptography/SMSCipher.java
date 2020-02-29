package ingsw.group1.findmyphone.cryptography;


import androidx.annotation.NonNull;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

/**
 * Class used to cipher and decipher a SMSMessage.
 *
 * @author Pardeep Kumar
 */
public class SMSCipher implements MessageCipher<SMSMessage> {

    private static final char PARSING_CHARACTER = '0';
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
        int[] strInt = SMSCipher.fromStringToAscii(stringToCipher);
        int[] keyInt = SMSCipher.fromStringToAscii(key);
        StringBuilder encryptedStr = new StringBuilder();
        for (int i = 0; i < strInt.length; i++) {
            String s = Integer.toHexString(strInt[i] ^ keyInt[i % keyInt.length]);
            String s2 = SMSCipher.addPadding(s, 2);
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
        int[] keyInt = SMSCipher.fromStringToAscii(key);
        int[] intArr = new int[stringToDecipher.length()];
        String[] strArr = new String[stringToDecipher.length() / 2];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = stringToDecipher.substring(i * 2, (i * 2) + 2);
        }
        for (int i = 0; i < strArr.length; i++) {
            int decimal = Integer.parseInt(strArr[i], 16);
            intArr[i] = decimal;
            String s = String.valueOf(intArr[i] ^ keyInt[i % keyInt.length]);
            int n = Integer.valueOf(s, 10);
            decrypted.append((char) n);
        }
        return decrypted.toString();
    }

    /**
     * Converts an integer to a string and adds to it the padding character.
     * If stringToPad.length is grater or equals to @length than it only converts the intToPadd
     * into a string.
     *
     * @param stringToPad The String that need the padding.
     * @param length      The wanted length for the string.
     * @return String with the correct length.
     */
    public static String addPadding(String stringToPad, int length) {
        String paddedString = stringToPad;
        while (paddedString.length() < length) {
            paddedString = PARSING_CHARACTER + paddedString;
        }
        return paddedString;
    }

    /**
     * Converts every characters of the string into integer of Ascii table and save their values
     * in an array of integer.
     *
     * @param stringToBeConverted The string to be converted.
     * @return An array of integer containing in position i the value of the character i of the
     * string.
     */
    public static int[] fromStringToAscii(String stringToBeConverted) {
        int[] integerArray = new int[stringToBeConverted.length()];
        for (int i = 0; i < stringToBeConverted.length(); i++) {
            integerArray[i] = (int) stringToBeConverted.charAt(i);
        }
        return integerArray;
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


