package ingsw.group1.findmyphone.contacts;

import org.junit.Before;
import org.junit.Test;

import ingsw.group1.msglibrary.SMSPeer;

import static org.junit.Assert.*;

/**
 * Test for {@link ContactConverter}
 *
 * @author Giorgia Bortoletti
 */
public class ContactConverterTest {

    private static final String EX_VALID_ADDRESS = "+393465951980";
    private static final String EX_VALID_NAME = "NewContact";
    private static final String EX_EMPTY_NAME = "";

    private Contact contactWithoutName, contactWithName; //a contact may or may not have a name
    private SMSPeer peerTest;

    @Before
    public void createPeerAndContact(){
        contactWithoutName = new Contact(EX_VALID_ADDRESS, EX_EMPTY_NAME);
        contactWithName = new Contact(EX_VALID_ADDRESS, EX_VALID_NAME);
        peerTest = new SMSPeer(EX_VALID_ADDRESS);
    }

    /**
     * Test {@link ContactConverter#contactFromSMSPeer(SMSPeer)}
     *
     * Conversion from a SMSPeer to a Contact without a name
     */
    @Test
    public void contactFromSMSPeer_withEmptyName() {
        assertEquals(contactWithoutName, ContactConverter.contactFromSMSPeer(peerTest));
    }

    /**
     * Test {@link ContactConverter#contactFromSMSPeer(SMSPeer, String)}
     *
     * Conversion from a SMSPeer to a Contact with a name
     */
    @Test
    public void contactFromSMSPeer_withValidName() {
        assertEquals(contactWithName, ContactConverter.contactFromSMSPeer(peerTest, EX_VALID_NAME));
    }

    /**
     * Test {@link ContactConverter#peerFromContact(Contact)}
     */
    @Test
    public void peerFromContact() {
        assertEquals(peerTest, ContactConverter.peerFromContact(contactWithoutName));
        assertEquals(peerTest, ContactConverter.peerFromContact(contactWithName));
    }

    /**
     * Test {@link ContactConverter#nameFromContact(Contact)}
     */
    @Test
    public void nameFromContact() {
        assertEquals(EX_EMPTY_NAME, ContactConverter.nameFromContact(contactWithoutName));
        assertEquals(EX_VALID_NAME, ContactConverter.nameFromContact(contactWithName));
    }

}