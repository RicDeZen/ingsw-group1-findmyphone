package ingsw.group1.findmyphone.random;

import java.util.Random;

import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.msglibrary.RandomSMSPeerGenerator;

/**
 * Class meant to generate some {@link SMSContact}.
 */
public class RandomSMSContactGenerator {

    private static final int USERNAME_LENGTH = 16;

    private static Random random = new Random();
    private static RandomSMSPeerGenerator randomPeer = new RandomSMSPeerGenerator();

    /**
     * Method generating a contact via a valid {@link ingsw.group1.msglibrary.SMSPeer} and a
     * random username.
     *
     * @return A pseudo-random Contact.
     */
    public SMSContact getRandomContact() {
        return new SMSContact(
                randomPeer.generateValidPeer(),
                getRandomUsername()
        );
    }

    /**
     * Method generating a random String for the contact's username.
     *
     * @return A pseudo-random String made up of ASCII characters between 48 and 122 (inclusive).
     */
    private String getRandomUsername() {
        StringBuilder result = new StringBuilder();
        while (result.length() < USERNAME_LENGTH) {
            result.append((char) random.nextInt(75) + 48);
        }
        return result.toString();
    }
}
