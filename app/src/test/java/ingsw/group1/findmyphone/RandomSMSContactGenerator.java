package ingsw.group1.findmyphone;

import java.util.Random;

import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.msglibrary.RandomSMSPeerGenerator;

//TODO specs
public class RandomSMSContactGenerator {

    private static final int USERNAME_LENGTH = 16;

    private static Random random = new Random();
    private static RandomSMSPeerGenerator randomPeer = new RandomSMSPeerGenerator();

    public SMSContact getRandomContact() {
        return new SMSContact(
                randomPeer.generateValidPeer(),
                getRandomUsername()
        );
    }

    private String getRandomUsername() {
        StringBuilder result = new StringBuilder();
        while (result.length() < USERNAME_LENGTH) {
            result.append((char) random.nextInt(75) + 48);
        }
        return result.toString();
    }
}
