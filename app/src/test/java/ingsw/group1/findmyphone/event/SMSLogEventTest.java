package ingsw.group1.findmyphone.event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.msglibrary.RandomSMSPeerGenerator;
import nl.jqno.equalsverifier.EqualsVerifier;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Test class for {@link SMSLogEvent}.
 * This class only evaluates basic pojo features, equation and hashcode.
 * Equation and hashcode are verified through
 * <a href="https://jqno.nl/equalsverifier/">EqualsVerifier</a>
 *
 * @author Riccardo De Zen.
 */
@RunWith(JUnit4.class)
public class SMSLogEventTest {

    private LogEventType eventType = LogEventType.UNKNOWN;
    private SMSContact contact = new SMSContact(
            new RandomSMSPeerGenerator().generateValidPeer(),
            "Contact"
    );
    private Long startTime = 100L;
    private String extra = "extra info";

    private SMSLogEvent testedEvent;

    /**
     * Checking the empty constructor returns an unknown event.
     */
    @Before
    public void defaultConstructorGivesUnknown() {
        assertEquals(LogEventType.UNKNOWN, new SMSLogEvent().getType());
    }

    /**
     * Checking the default constructor passes.
     */
    @Before
    public void doesConstructorPass() {
        testedEvent = new SMSLogEvent(
                eventType,
                contact,
                startTime,
                extra
        );
    }

    /**
     * Checking the constructor can actually fail for an invalid extra.
     * More in depth testing is at {@link SMSLogEventExtraTest}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void doesConstructorFail() {
        new SMSLogEvent(
                LogEventType.RING_REQUEST_RECEIVED,
                contact,
                startTime,
                "I'm not appropriate"
        );
    }

    /**
     * Testing {@link SMSLogEvent#getType()} returns the actual value of the type.
     */
    @Test
    public void getTypeReturnsActual() {
        assertEquals(eventType, testedEvent.getType());
    }

    /**
     * Testing {@link SMSLogEvent#getContact()} returns the actual value of the contact.
     */
    @Test
    public void getContactReturnsActual() {
        assertEquals(contact, testedEvent.getContact());
    }

    /**
     * Testing {@link SMSLogEvent#getTime()} returns the actual value of the start time.
     */
    @Test
    public void getTimeReturnsActual() {
        assertEquals(startTime, testedEvent.getTime());
    }

    /**
     * Testing {@link SMSLogEvent#getExtra()} returns the actual value of the extra.
     */
    @Test
    public void getExtraReturnsActual() {
        assertEquals(extra, testedEvent.getExtra());
    }

    /**
     * Verifying {@link SMSLogEvent#equals(Object)} and hashCode contract.
     */
    @Test
    public void equalsVerification() {
        try {
            EqualsVerifier.forClass(SMSLogEvent.class)
                    .usingGetClass()
                    .withIgnoredFields("extra")
                    .verify();
        } catch (Exception anyException) {
            anyException.printStackTrace();
            fail();
        }
    }
}
