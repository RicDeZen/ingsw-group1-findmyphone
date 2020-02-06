package ingsw.group1.findmyphone.cryptography;

import ingsw.group1.msglibrary.SMSMessage;
import ingsw.group1.msglibrary.SMSPeer;

/**
 * Clas used to cipher and decipher a SMSMessage.
 *
 * @author Pardeep
 */
public class SMSCipher implements MessageCipher<SMSMessage> {

    private static SMSCipher instance;
    private String password;

    private SMSCipher() {
        if (instance != null)
            throw new RuntimeException("This class uses the singleton design pattern. " +
                    "Use getInstance() to get a reference to the single instance of this class");
    }

    /**
     * Gets an SMSCipher object if it's not already created.
     *
     * @return The SMSCipher object
     */
    public static SMSCipher getInstance() {
        if (instance == null) {
            instance = new SMSCipher();
        }
        return instance;
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
        String encryptedMessage = encrypt(messageToEncrypt, password);
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
        String decryptedMessage = decrypt(messageToDecrypt, password);
        return new SMSMessage(peerOfMessage, decryptedMessage);
    }

    /**
     * Encrypts the string using a xor encryption with the key.
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
     * If stringToPad.length is grater or equals to @length than it only converts the intToPadd into a string.
     *
     * @param stringToPad The String that need the padding.
     * @param length      The wanted length for the string.
     * @return String with the correct length.
     */
    private static String addPadding(String stringToPad, int length) {
        final char PARSING_CHARACTER = '0';
        while (stringToPad.length() < length) {
            stringToPad = PARSING_CHARACTER + stringToPad;
        }
        return stringToPad;
    }

    /**
     * Converts every characters of the string into integer of Ascii table and save their values in an array of integer.
     *
     * @param stringToBeConverted The string to be converted
     * @return An array of integer containing in position i the value of the character i of the string
     */
    static int[] fromStringToAscii(String stringToBeConverted) {
        int[] integerArray = new int[stringToBeConverted.length()];
        for (int i = 0; i < stringToBeConverted.length(); i++) {
            integerArray[i] = (int) stringToBeConverted.charAt(i);
        }
        return integerArray;
    }


    /**
     * Sets the password that will be used as a key for encryption and decryption.
     *
     * @param newPassword The password used for encryption and decryption.
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}


