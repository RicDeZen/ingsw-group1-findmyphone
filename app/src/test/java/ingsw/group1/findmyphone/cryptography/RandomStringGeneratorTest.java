package ingsw.group1.findmyphone.cryptography;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import ingsw.group1.msglibrary.SMSMessage;
import ingsw.group1.msglibrary.SMSPeer;

/**
 * Tests the encryption and decryption with generating random text to cypher / decipher with a random generated password.
 *
 * @author Pardeep
 */
@RunWith(Parameterized.class)
public class RandomStringGeneratorTest {
    private final int MINIMUM_STRING_LENGTH = 1;
    private final int MAXIMUM_STRING_LENGTH = 60;

    private static final int NUM_REPEATS = 1000;

    @Parameterized.Parameters()
    public static Collection<Object[]> data() {
        Collection<Object[]> out = new ArrayList<>();
        for (int i = 0; i < NUM_REPEATS; i++) {
            out.add(new Object[0]);
        }
        return out;
    }

    @Test
    public void RandomIntegerGeneratorTest() {
        int randomInt = RandomStringGenerator.generateRandomIntIntRange(MINIMUM_STRING_LENGTH, MAXIMUM_STRING_LENGTH);
        System.out.println("ValidInt: " + randomInt);
        assertTrue(randomInt >= MINIMUM_STRING_LENGTH && randomInt <= MAXIMUM_STRING_LENGTH);
    }

    @Test
    public void RandomIntegerGeneratorTestOutOfRange() {
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

    @Test
    public void testEncryptionDecryptionMethod() {
        SMSCipher smsCipher = SMSCipher.getInstance();
        String expectedAddress = "+393888624988";
        SMSPeer smsPeer = new SMSPeer(expectedAddress);
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

    @Test
    public void testEncryptionDecryptionIncorrectPassword() {
        SMSCipher smsCipher = SMSCipher.getInstance();
        String wrongPassword = RandomStringGenerator.generateRandomString();
        String expectedAddress = "+393888624988";
        SMSPeer smsPeer = new SMSPeer(expectedAddress);
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
