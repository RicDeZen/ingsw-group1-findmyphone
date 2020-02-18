package ingsw.group1.findmyphone.cryptography;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ingsw.group1.msglibrary.SMSMessage;
import ingsw.group1.msglibrary.SMSPeer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the encryption and decryption with generating random text to cypher / decipher with a random generated password.
 *
 * @author Pardeep Kumar
 */
@RunWith(Parameterized.class)
public class RandomStringGeneratorTest {
    private final int MINIMUM_STRING_LENGTH = 1;
    private final int MAXIMUM_STRING_LENGTH = 60;
    private String expectedAddress = "+393888624988";
    private SMSPeer smsPeer = new SMSPeer(expectedAddress);

    private static final int NUM_REPEATS = 10;

    @Parameterized.Parameters()
    public static Object[][] data() {
        return new Object[NUM_REPEATS][0];
    }


    /**
     * Tests that the randomInt generated it's within the correct range.
     */
    @Test
    public void randomIntegerGeneratorTest() {
        int randomInt =RandomStringGeneratorUtils.generateRandomIntIntRange(MINIMUM_STRING_LENGTH, MAXIMUM_STRING_LENGTH);
        System.out.println("ValidInt: " + randomInt);
        assertTrue(randomInt >= MINIMUM_STRING_LENGTH && randomInt <= MAXIMUM_STRING_LENGTH);
    }

    /**
     * Tests that the randomInt generated it's never within the correct range.
     */
    @Test
    public void randomIntegerGeneratorTestOutOfRange() {
        int outOfLowerBound = -100;
        int outOfLowerBoundMaximum = 0;
        int outOfUpperBoundMinimum = 61;
        int outOfUpperBound = 160;
        int randomIntLower = RandomStringGeneratorUtils.generateRandomIntIntRange(outOfLowerBound, outOfLowerBoundMaximum);
        int randomIntHigher = RandomStringGeneratorUtils.generateRandomIntIntRange(outOfUpperBoundMinimum, outOfUpperBound);
        System.out.println("Not ValidInt: " + randomIntLower);
        System.out.println("Not ValidInt: " + randomIntHigher);
        assertFalse(randomIntLower >= MINIMUM_STRING_LENGTH && randomIntLower <= MAXIMUM_STRING_LENGTH);
        assertFalse(randomIntHigher >= MINIMUM_STRING_LENGTH && randomIntHigher <= MAXIMUM_STRING_LENGTH);
    }

    /**
     * Tests that decrypting a message (encrypted with a password) with the same password returns the original massage.
     */
    @Test
    public void testCypher() {

        String expectedText = RandomStringGeneratorUtils.generateRandomString();
        System.out.println("ExpectedText: " + expectedText);
        String password = RandomStringGeneratorUtils.generateRandomString();
        SMSCipher smsCipher = new SMSCipher(password);
        System.out.println("Password: " + password);
        SMSMessage messageToCypher = new SMSMessage(smsPeer, expectedText);
        String encryptedText = smsCipher.cipherMessage(messageToCypher).getData();
        System.out.println("EncryptedText: " + encryptedText);
        SMSMessage smsMessage = new SMSMessage(smsPeer, encryptedText);
        SMSMessage decryptedMessage = smsCipher.decipherMessage(smsMessage);
        System.out.println("DecryptedText: " + decryptedMessage.getData());
        assertEquals(expectedText, decryptedMessage.getData());
    }

    /**
     * Tests that decrypting a SMSMessage with a wrong password return a different text from the original one.
     */
    @Test
    public void testCypherIncorrectPassword() {
        String wrongPassword = RandomStringGeneratorUtils.generateRandomString();
        String expectedText = RandomStringGeneratorUtils.generateRandomString();
        System.out.println("ExpectedText: " + expectedText);
        String password = RandomStringGeneratorUtils.generateRandomString();
        System.out.println("Password: " + password);
        SMSCipher smsCipher = new SMSCipher(password);
        System.out.println("Wrong password: " + wrongPassword);
        SMSMessage messageToCypher = new SMSMessage(smsPeer, expectedText);
        String encryptedMessage = smsCipher.cipherMessage(messageToCypher).getData();
        System.out.println("EncryptedText: " + encryptedMessage);
        SMSMessage encryptedSmsMessage = new SMSMessage(smsPeer, encryptedMessage);
        smsCipher.setPassword(wrongPassword);
        SMSMessage decryptedMessage = smsCipher.decipherMessage(encryptedSmsMessage);
        System.out.println("DecryptedText: " + decryptedMessage.getData());
        assertNotEquals(expectedText, decryptedMessage.getData());
    }

}
