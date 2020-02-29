package ingsw.group1.findmyphone.cryptography;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Class used to generate random strings and int value within the bounds.
 *
 * @author Pardeep Kumar
 */
class RandomStringGeneratorUtils {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARACTERS = "|!£$%&/()=?^'ìè+ùòà,.-;:_ç°*§<>[]@#{}";
    private static final int MINIMAL_STRING_LENGTH = 1;
    //The maximum length for a message is 160 but with hex it's the half.
    private static final int MAXIMUM_STRING_LENGTH = 80;


    private static final String DATA_FOR_RANDOM_STRING =
            CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARACTERS;
    private static SecureRandom random = new SecureRandom();

    /**
     * Generates a random string with random length.
     *
     * @return The random string.
     */
    public static String generateRandomString() {

        int length = generateRandomIntIntRange(MINIMAL_STRING_LENGTH, MAXIMUM_STRING_LENGTH);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }

    /**
     * Returns a value within the minimum and maximum range
     *
     * @param min The minimum value
     * @param max The maximum value
     * @return The random value
     */
    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
