package ingsw.group1.findmyphone.cryptography;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the encryption and decryption of the string, used the following website to check if it
 * was right.
 * https://convert-tool.com/conversion/xor-encrypt to check the xor encryption.
 * https://www.rapidtables.com/convert/number/ascii-hex-bin-dec-converter.html to check the
 * conversion of a string to ASCII.
 *
 * @author Pardeep Kumar
 */
public class SMSCipherTest {

    private String textToCypher = "Testo di prova da  roba a caso no nso cosa scrivere!$%&/ " +
            "criptare 1923u421312!%&%&";
    private String password = "fasdaw214";
    private String encryptedText =
            "320400100e5756581416131c12005756501446131c06005753115707121c440f18125f470941100b1216124257140805011312131511404e5307131e42455514045355584501440054504055535617171140";
    private String differanceFromExpectedOutput = "anything";


    /**
     * Tests the static method encrypt and assert that the output it's equal to the encrypted text.
     */
    @Test
    public void testEncryptMessage() {
        String output = SMSCipher.encrypt(textToCypher, password);
        assertEquals(encryptedText, output);
    }

    /**
     * Tests the static method encrypt and assert that the wrong output it's not equal to the
     * encrypted text.
     */
    @Test
    public void testEncryptMessageNotEqual() {

        String output = SMSCipher.encrypt(textToCypher, password);
        String wrongOutput = output + differanceFromExpectedOutput;
        assertNotEquals(encryptedText, wrongOutput);
    }

    /**
     * Tests the static decrypt encrypt and assert that the output it's equal to the original
     * string.
     */
    @Test
    public void testDecryptMessage() {
        String output = SMSCipher.decrypt(encryptedText, password);
        assertEquals(textToCypher, output);
    }


    /**
     * Tests the static method decrypt and assert that the wrong output it's not equal to the
     * original text.
     */
    @Test
    public void testDecryptMessageNotEqual() {

        String output = SMSCipher.decrypt(encryptedText, password);
        String wrongOutput = output + differanceFromExpectedOutput;
        assertNotEquals(textToCypher, wrongOutput);
    }


    /**
     * Tests the conversion of the original text into ASCII and assert that it's equal to the
     * expectedText
     */
    @Test
    public void testFromStringToAscii() {
        String textToCypher = "Testo di prova da convertire";
        String expectedText =
                "8410111511611132100105321121141111189732100973299111110118101114116105114101";
        StringBuilder stringBuilder = new StringBuilder();
        int[] intArray = SMSCipherUtils.fromStringToAscii(textToCypher);
        for (int i = 0; i < intArray.length; i++) {
            stringBuilder.append(intArray[i]);
        }
        assertEquals(expectedText, stringBuilder.toString());
    }

    /**
     * Tests the conversion of the original text into ASCII and assert that it's not equal to a
     * wrong text.
     */
    @Test
    public void testFromStringToAsciiNotEquals() {
        String textToCypher = "Testo di prova da convertire";
        String expectedText =
                "8410111511611132100105321121141111189732100973299111110118101114116105114101";
        String wrongExpectedText = expectedText + differanceFromExpectedOutput;
        StringBuilder stringBuilder = new StringBuilder();
        int[] intArray = SMSCipherUtils.fromStringToAscii(textToCypher);
        for (int i = 0; i < intArray.length; i++) {
            stringBuilder.append(intArray[i]);
        }
        assertNotEquals(wrongExpectedText, stringBuilder.toString());
    }


    /**
     * Tests the method to add the padding to a string and assert that it's equal to the
     * expectedString.
     */
    @Test
    public void testAddPadding() {
        int wantedLength = 4;
        String string = "5";
        String expectedString = "0005";
        assertEquals(expectedString, SMSCipherUtils.addPadding(string, wantedLength));
    }

    /**
     * Tests the method to add the padding to a string and assert that it's not equal to the
     * wrongExpectedString.
     */
    @Test
    public void testAddPaddingNotEquals() {
        int wantedLength = 4;
        String string = "5";
        String expectedString = "0005";
        String wrongExpectedString = expectedString + differanceFromExpectedOutput;
        assertNotEquals(wrongExpectedString, SMSCipherUtils.addPadding(string, wantedLength));
    }

    /**
     * Tests the method to set and get the value of the password of the cypher object.
     */
    @Test
    public void testSetGetPassword() {
        String password = "randomPassword123";
        SMSCipher smsCipher = new SMSCipher(password);
        assertEquals(password, smsCipher.getPassword());
        String newPassword = "123asd";
        smsCipher.setPassword(newPassword);
        assertEquals(newPassword, smsCipher.getPassword());
    }

    /**
     * Tests the method to set and get the value of the password of the cypher object.
     */
    @Test
    public void testSetGetPasswordNotEquals() {
        String password = "randomPassword123";
        SMSCipher smsCipher = new SMSCipher(password);
        assertNotEquals(password, smsCipher.getPassword() + differanceFromExpectedOutput);
        String newPassword = "123asd";
        smsCipher.setPassword(newPassword);
        assertNotEquals(newPassword, smsCipher.getPassword() + differanceFromExpectedOutput);
    }


}
