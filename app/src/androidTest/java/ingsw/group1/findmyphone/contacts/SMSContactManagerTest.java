package ingsw.group1.findmyphone.contacts;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.eis.smslibrary.SMSPeer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Test for {@link SMSContactManager}
 *
 * @author Giorgia Bortoletti
 */
public class SMSContactManagerTest {

    private static final String EX_VALID_ADDRESS = "+393478989890"; //for contact and peer
    private static final String EX_VALID_ADDRESS_2 = "+393478999999"; //for a peer not inserted
    // to check method contains
    private static final String CONTACT_VALID_NAME = "NewContact";

    private SMSContactManager contactManager;
    private SMSPeer peerTest;

    @Before
    public void createManager() {
        Context context = ApplicationProvider.getApplicationContext();
        contactManager = SMSContactManager.getInstance(context);

        peerTest = new SMSPeer(EX_VALID_ADDRESS);
    }

    //---------------------------- TESTS ----------------------------

    /**
     * Test for {@link SMSContactManager#addContact(SMSPeer)}
     */
    @Test
    public void addContact() {
        contactManager.addContact(peerTest);

        Assert.assertTrue(contactManager.containsPeer(peerTest));
    }

    /**
     * Test for {@link SMSContactManager#addContact(SMSPeer, String)}
     */
    @Test
    public void addContact1() {
        contactManager.addContact(peerTest, CONTACT_VALID_NAME);

        Assert.assertTrue(contactManager.containsPeer(peerTest));
    }

    /**
     * Test for {@link SMSContactManager#removeContact(SMSPeer)}
     */
    @Test
    public void removeContact() {
        contactManager.addContact(peerTest, CONTACT_VALID_NAME);
        contactManager.removeContact(peerTest);

        Assert.assertFalse(contactManager.containsPeer(peerTest));
    }

    /**
     * Test for {@link SMSContactManager#containsPeer(SMSPeer)}
     */
    @Test
    public void notContainsPeer() {
        SMSPeer peerNotInserted = new SMSPeer(EX_VALID_ADDRESS_2);

        Assert.assertFalse(contactManager.containsPeer(peerNotInserted));
    }

    /**
     * Test asserting a Contact can be retrieved from its corresponding Peer.
     */
    @Test
    public void canFindContactForAddress() {
        contactManager.addContact(peerTest, CONTACT_VALID_NAME);
        SMSContact contact = contactManager.getContactForPeer(peerTest);
        if (contact == null) fail();
        Assert.assertEquals(CONTACT_VALID_NAME, contact.getName());
    }
}