package ingsw.group1.findmyphone.cryptography;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ingsw.group1.msglibrary.SMSMessage;
import ingsw.group1.msglibrary.SMSPeer;

/**
 * Tests the encryption and decryption of the string, used the following website to check if it was right.
 * https://convert-tool.com/conversion/xor-encrypt
 *
 * @author Pardeep
 */
public class SMSCipherTest {

    @Test
    public void testCryptMessage() {
        String textToCypher = "Testo di prova da  roba a caso no nso cosa scrivere!$%&/ criptare 1923u421312!%&%&";
        String password = "fasdaw214";
        String expectedText = "320400100e5756581416131c12005756501446131c06005753115707121c440f18125f470941100b1216124257140805011312131511404e5307131e42455514045355584501440054504055535617171140";
        SMSCipher smsCipher = SMSCipher.getInstance();
        smsCipher.setPassword(password);
        String output = SMSCipher.encrypt(textToCypher, password);
        assertEquals(expectedText, output);
    }

    @Test
    public void testDecipheMessage() {
        String textToCypher = "Testo di prova da  roba a caso no nso cosa scrivere!$%&/ criptare 1923u421312!%&%&";
        String password = "fasdaw214";
        String expectedText = "320400100e5756581416131c12005756501446131c06005753115707121c440f18125f470941100b1216124257140805011312131511404e5307131e42455514045355584501440054504055535617171140";
        SMSCipher smsCipher = SMSCipher.getInstance();
        smsCipher.setPassword(password);
        String output = SMSCipher.decrypt(expectedText, password);
        assertEquals(textToCypher, output);
    }

    @Test
    public void testFromStringToAscii() {
        String textToCypher = "Testo di prova da criptare";
        String expectedtext = "841011151161113210010532112114111118973210097329911410511211697114101";
        StringBuilder stringBuilder = new StringBuilder();
        int[] intArray = SMSCipher.fromStringToAscii(textToCypher);
        for (int i = 0; i < intArray.length; i++) {
            stringBuilder.append(intArray[i]);
        }
        assertEquals(expectedtext, stringBuilder.toString());
    }

    @Test
    public void testCipherMessage() {
        String expectedAddress = "+393888624988";
        String textToCypher = "Chi sei Goku non lo sai";
        String expectedText = "221b0d4b1f081e11725e5914530a04024d1b5e15425308";
        String password = "asdklmw1512";
        SMSPeer smsPeer = new SMSPeer(expectedAddress);
        SMSMessage smsMessage = new SMSMessage(smsPeer, textToCypher);
        SMSCipher smsCipher = SMSCipher.getInstance();
        smsCipher.setPassword(password);
        SMSMessage output = smsCipher.cipherMessage(smsMessage);
        assertEquals(expectedText, output.getData());
    }

    @Test
    public void testDecryptionMethod() {
        String expectedAddress = "+393888624988";
        String expectedText = "Chi sei Goku non lo sai";
        String password = "asdklmw1512";
        SMSPeer smsPeer = new SMSPeer(expectedAddress);
        String encryptedText = "221b0d4b1f081e11725e5914530a04024d1b5e15425308";
        SMSMessage smsMessage = new SMSMessage(smsPeer, encryptedText);
        SMSCipher smsCipher = SMSCipher.getInstance();
        smsCipher.setPassword(password);
        SMSMessage decryptedMessage = smsCipher.decipherMessage(smsMessage);
        assertEquals(expectedText, decryptedMessage.getData());
    }

}
