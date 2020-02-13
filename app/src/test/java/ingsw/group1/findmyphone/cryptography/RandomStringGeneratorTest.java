package ingsw.group1.findmyphone.cryptography;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


import ingsw.group1.msglibrary.SMSMessage;
import ingsw.group1.msglibrary.SMSPeer;

/**
 * Tests the encryption and decryption with generating random text to cypher / decipher with a random generated password.
 *
 * @author Pardeep Kumar
 */
@Config(sdk = 28)
@RunWith(ParameterizedRobolectricTestRunner.class)
public class RandomStringGeneratorTest {
    private final int MINIMUM_STRING_LENGTH = 1;
    private final int MAXIMUM_STRING_LENGTH = 60;
    private Context context = ApplicationProvider.getApplicationContext();
    private SMSCipher smsCipher = new SMSCipher(context);
    private String expectedAddress = "+393888624988";
    private SMSPeer smsPeer = new SMSPeer(expectedAddress);

    private static final int NUM_REPEATS = 100;

    @ParameterizedRobolectricTestRunner.Parameters()
    public static Collection<Object[]> data() {
        Collection<Object[]> out = new ArrayList<>();
        for (int i = 0; i < NUM_REPEATS; i++) {
            out.add(new Object[0]);
        }
        return out;
    }

    /**
     * Tests that the randomInt generated it's within the correct range.
     */
    @Test
    public void randomIntegerGeneratorTest() {
        int randomInt = RandomStringGenerator.generateRandomIntIntRange(MINIMUM_STRING_LENGTH, MAXIMUM_STRING_LENGTH);
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
        int randomIntLower = RandomStringGenerator.generateRandomIntIntRange(outOfLowerBound, outOfLowerBoundMaximum);
        int randomIntHigher = RandomStringGenerator.generateRandomIntIntRange(outOfUpperBoundMinimum, outOfUpperBound);
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

        String expectedText = RandomStringGenerator.generateRandomString();
        System.out.println("ExpectedText: " + expectedText);
        String password = RandomStringGenerator.generateRandomString();
        smsCipher.setPassword(password);
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
        String wrongPassword = RandomStringGenerator.generateRandomString();
        String expectedText = RandomStringGenerator.generateRandomString();
        System.out.println("ExpectedText: " + expectedText);
        String password = RandomStringGenerator.generateRandomString();
        System.out.println("Password: " + password);
        smsCipher.setPassword(wrongPassword);
        System.out.println("Wrong password: " + wrongPassword);
        SMSMessage messageToCypher = new SMSMessage(smsPeer, expectedText);
        String encryptedMessage = smsCipher.cipherMessage(messageToCypher).getData();
        System.out.println("EncryptedText: " + encryptedMessage);
        SMSMessage encryptedSmsMessage = new SMSMessage(smsPeer, encryptedMessage);
        smsCipher.setPassword(password);
        SMSMessage decryptedMessage = smsCipher.decipherMessage(encryptedSmsMessage);
        System.out.println("DecryptedText: " + decryptedMessage.getData());
        assertNotEquals(expectedText, decryptedMessage.getData());
    }

}
