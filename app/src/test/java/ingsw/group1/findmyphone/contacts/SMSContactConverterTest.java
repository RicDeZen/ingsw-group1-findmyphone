package ingsw.group1.findmyphone.contacts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ingsw.group1.msglibrary.SMSPeer;

/**
 * Test for {@link SMSContactConverter}
 *
 * @author Giorgia Bortoletti
 */
public class SMSContactConverterTest {

    private static final String EX_VALID_ADDRESS = "+393478989890"; //for contact and peer
    private static final String CONTACT_VALID_NAME = "NewContact";
    private static final String CONTACT_EMPTY_NAME = "";

    private SMSContact contactWithoutName;
    private SMSContact contactWithName; //a contact may or may not have a name
    private SMSPeer peerTest;

    @Before
    public void createPeerAndContact() {
        contactWithoutName = new SMSContact(EX_VALID_ADDRESS, CONTACT_EMPTY_NAME);
        contactWithName = new SMSContact(EX_VALID_ADDRESS, CONTACT_VALID_NAME);
        peerTest = new SMSPeer(EX_VALID_ADDRESS);
    }

    /**
     * Test {@link SMSContactConverter#contactFromSMSPeer(SMSPeer)}
     * <p>
     * Conversion from a SMSPeer to a Contact without a name
     */
    @Test
    public void contactFromSMSPeerWithEmptyName() {
        Assert.assertEquals(contactWithoutName, SMSContactConverter.contactFromSMSPeer(peerTest));
    }

    /**
     * Test {@link SMSContactConverter#contactFromSMSPeer(SMSPeer, String)}
     * <p>
     * Conversion from a SMSPeer to a Contact with a name
     */
    @Test
    public void contactFromSMSPeerWithValidName() {
        Assert.assertEquals(contactWithName, SMSContactConverter.contactFromSMSPeer(peerTest,
                CONTACT_VALID_NAME));
    }

    /**
     * Test {@link SMSContactConverter#peerFromContact(SMSContact)}
     */
    @Test
    public void peerFromContact() {
        Assert.assertEquals(peerTest, SMSContactConverter.peerFromContact(contactWithoutName));
        Assert.assertEquals(peerTest, SMSContactConverter.peerFromContact(contactWithName));
    }

}