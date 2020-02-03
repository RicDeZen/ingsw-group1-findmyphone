package ingsw.group1.findmyphone.contacts;

import org.junit.Before;
import org.junit.Test;

import ingsw.group1.msglibrary.SMSPeer;

import static org.junit.Assert.*;

/**
 * Test for {@link SMSContactConverter}
 *
 * @author Giorgia Bortoletti
 */
public class ContactConverterTest {

    private static final String EX_VALID_ADDRESS = "+393478989890"; //for contact and peer
    private static final String CONTACT_VALID_NAME = "NewContact";
    private static final String CONTACT_EMPTY_NAME = "";

    private SMSContact contactWithoutName, contactWithName; //a contact may or may not have a name
    private SMSPeer peerTest;

    @Before
    public void createPeerAndContact(){
        contactWithoutName = new SMSContact(EX_VALID_ADDRESS, CONTACT_EMPTY_NAME);
        contactWithName = new SMSContact(EX_VALID_ADDRESS, CONTACT_VALID_NAME);
        peerTest = new SMSPeer(EX_VALID_ADDRESS);
    }

    /**
     * Test {@link SMSContactConverter#contactFromSMSPeer(SMSPeer)}
     *
     * Conversion from a SMSPeer to a Contact without a name
     */
    @Test
    public void contactFromSMSPeer_withEmptyName() {
        assertEquals(contactWithoutName, SMSContactConverter.contactFromSMSPeer(peerTest));
    }

    /**
     * Test {@link SMSContactConverter#contactFromSMSPeer(SMSPeer, String)}
     *
     * Conversion from a SMSPeer to a Contact with a name
     */
    @Test
    public void contactFromSMSPeer_withValidName() {
        assertEquals(contactWithName, SMSContactConverter.contactFromSMSPeer(peerTest, CONTACT_VALID_NAME));
    }

    /**
     * Test {@link SMSContactConverter#peerFromContact(SMSContact)}
     */
    @Test
    public void peerFromContact() {
        assertEquals(peerTest, SMSContactConverter.peerFromContact(contactWithoutName));
        assertEquals(peerTest, SMSContactConverter.peerFromContact(contactWithName));
    }

    /**
     * Test {@link SMSContactConverter#nameFromContact(SMSContact)}
     */
    @Test
    public void nameFromContact() {
        assertEquals(CONTACT_EMPTY_NAME, SMSContactConverter.nameFromContact(contactWithoutName));
        assertEquals(CONTACT_VALID_NAME, SMSContactConverter.nameFromContact(contactWithName));
    }

}