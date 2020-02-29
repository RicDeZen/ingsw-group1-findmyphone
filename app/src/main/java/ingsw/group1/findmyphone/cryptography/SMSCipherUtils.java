package ingsw.group1.findmyphone.cryptography;

public class SMSCipherUtils {
    private static final char PARSING_CHARACTER = '0';

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
}
