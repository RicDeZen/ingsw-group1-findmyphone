package ingsw.group1.findmyphone.random;

import com.eis.smslibrary.RandomSMSPeerGenerator;

import java.util.Random;

import ingsw.group1.findmyphone.contacts.SMSContact;

/**
 * Class meant to generate some {@link SMSContact}.
 */
public class RandomSMSContactGenerator {

    private static Random random = new Random();
    private static RandomSMSPeerGenerator randomPeer = new RandomSMSPeerGenerator();

    /**
     * Method generating a contact via a valid {@link com.eis.smslibrary.SMSPeer} and a
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
     * Method generating a random name for the contact's username.
     *
     * @return A random String from the {@link RandomNameUtils#NAMES} array.
     */
    public static String getRandomUsername() {
        return RandomNameUtils.NAMES[random.nextInt(RandomNameUtils.NAMES.length)];
    }
}
