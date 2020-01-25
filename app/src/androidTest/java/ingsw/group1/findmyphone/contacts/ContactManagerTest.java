package ingsw.group1.findmyphone.contacts;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import ingsw.group1.msglibrary.SMSPeer;
import ingsw.group1.msglibrary.database.SMSDatabase;

import static org.junit.Assert.*;

/**
 * Test for {@link ContactManager}
 *
 * @author Giorgia Bortoletti
 */
public class ContactManagerTest {

    private static final String EX_VALID_ADDRESS = "+393478989890"; //for contact and peer
    private static final String EX_VALID_ADDRESS_2 = "+393478999999"; //for a peer not inserted to check method contains
    private static final String CONTACT_VALID_NAME = "NewContact";

    private ContactManager contactManager;
    private SMSPeer peerTest;

    @Before
    public void createManager() {
        Context context = ApplicationProvider.getApplicationContext();
        contactManager = new ContactManager(context);

        peerTest = new SMSPeer(EX_VALID_ADDRESS);
    }

    //---------------------------- TESTS ----------------------------

    /**
     * Test for {@link ContactManager#addContact(SMSPeer)}
     */
    @Test
    public void addContact() {
        contactManager.addContact(peerTest);

        assertTrue(contactManager.containsPeer(peerTest));
    }

    /**
     * Test for {@link ContactManager#addContact(SMSPeer, String)}
     */
    @Test
    public void addContact1() {
        contactManager.addContact(peerTest, CONTACT_VALID_NAME);

        assertTrue(contactManager.containsPeer(peerTest));
    }

    /**
     * Test for {@link ContactManager#removeContact(SMSPeer)}
     */
    @Test
    public void removeContact() {
        contactManager.addContact(peerTest, CONTACT_VALID_NAME);
        contactManager.removeContact(peerTest);

        assertFalse(contactManager.containsPeer(peerTest));
    }

    /**
     * Test for {@link ContactManager#containsPeer(SMSPeer)}
     */
    @Test
    public void notContainsPeer() {
        SMSPeer peerNotInserted = new SMSPeer(EX_VALID_ADDRESS_2);

        assertFalse(contactManager.containsPeer(peerNotInserted));
    }




}