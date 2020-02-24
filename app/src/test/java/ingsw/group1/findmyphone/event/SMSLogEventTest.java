package ingsw.group1.findmyphone.event;

import com.eis.smslibrary.RandomSMSPeerGenerator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.location.GeoPosition;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for {@link SMSLogEvent}.
 * This class only evaluates basic pojo features, equation and hashcode.
 * Tests for {@link SMSLogEvent#isValidExtra(EventType, String)} are at
 * {@link SMSLogEventExtraTest}.
 *
 * @author Riccardo De Zen.
 */
@RunWith(JUnit4.class)
public class SMSLogEventTest {
    //First set of parameters
    private static final EventType EXAMPLE_EVENT_TYPE = EventType.UNKNOWN;
    private static final SMSContact EXAMPLE_CONTACT = new SMSContact(
            new RandomSMSPeerGenerator().generateValidPeer(),
            "Contact"
    );
    private static final Long EXAMPLE_START_TIME = 100L;
    private static final String EXAMPLE_EXTRA_INFO = new GeoPosition(
            45,
            26
    ).toString();

    //Second set of parameters
    private static final EventType ANOTHER_EVENT_TYPE = EventType.LOCATION_REQUEST_RECEIVED;
    private static final SMSContact ANOTHER_CONTACT = new SMSContact(
            new RandomSMSPeerGenerator().generateValidPeer(),
            "Another Contact"
    );
    private static final Long ANOTHER_START_TIME = 1000L;
    private static final String ANOTHER_EXTRA_INFO = new GeoPosition(
            100,
            100
    ).toString();

    private SMSLogEvent testedEvent;

    /**
     * Checking the empty constructor returns an unknown event.
     */
    @Before
    public void defaultConstructorGivesUnknown() {
        assertEquals(EventType.UNKNOWN, new SMSLogEvent().getType());
    }

    /**
     * Checking the default constructor passes.
     */
    @Before
    public void doesConstructorPass() {
        testedEvent = new SMSLogEvent(
                EXAMPLE_EVENT_TYPE,
                EXAMPLE_CONTACT,
                EXAMPLE_START_TIME,
                EXAMPLE_EXTRA_INFO
        );
    }

    /**
     * Checking the constructor can actually fail for an invalid extra.
     * More in depth testing is at {@link SMSLogEventExtraTest}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void doesConstructorFail() {
        new SMSLogEvent(
                EventType.RING_REQUEST_RECEIVED,
                EXAMPLE_CONTACT,
                EXAMPLE_START_TIME,
                "I'm not appropriate"
        );
    }

    /**
     * Testing {@link SMSLogEvent#getType()} returns the actual value of the type.
     */
    @Test
    public void getTypeReturnsActual() {
        assertEquals(EXAMPLE_EVENT_TYPE, testedEvent.getType());
    }

    /**
     * Testing {@link SMSLogEvent#getContact()} returns the actual value of the contact.
     */
    @Test
    public void getContactReturnsActual() {
        assertEquals(EXAMPLE_CONTACT, testedEvent.getContact());
    }

    /**
     * Testing {@link SMSLogEvent#getTime()} returns the actual value of the start time.
     */
    @Test
    public void getTimeReturnsActual() {
        assertEquals(EXAMPLE_START_TIME, testedEvent.getTime());
    }

    /**
     * Testing {@link SMSLogEvent#getExtra()} returns the actual value of the extra.
     */
    @Test
    public void getExtraReturnsActual() {
        assertEquals(EXAMPLE_EXTRA_INFO, testedEvent.getExtra());
    }


    /**
     * Testing equals returns true for same Object.
     */
    @Test
    public void equalsReturnsTrueOnSame() {
        assertEquals(
                testedEvent,
                testedEvent
        );
    }

    /**
     * Testing equals returns true for all equal parameters except extra.
     */
    @Test
    public void equalsReturnsTrueOnContract() {
        assertEquals(
                testedEvent,
                new SMSLogEvent(
                        EXAMPLE_EVENT_TYPE,
                        EXAMPLE_CONTACT,
                        EXAMPLE_START_TIME,
                        EXAMPLE_EXTRA_INFO
                )
        );
    }

    /**
     * Testing equals returns true for all equal parameters except extra, with a different extra
     */
    @Test
    public void equalsReturnsTrueOnOnlyDifferentExtra() {
        assertEquals(
                testedEvent,
                new SMSLogEvent(
                        EXAMPLE_EVENT_TYPE,
                        EXAMPLE_CONTACT,
                        EXAMPLE_START_TIME,
                        ANOTHER_EXTRA_INFO
                )
        );
    }

    /**
     * Testing equals returns false if type is different
     */
    @Test
    public void equalsReturnsFalseOnOnlyDifferentType() {
        assertNotEquals(
                testedEvent,
                new SMSLogEvent(
                        ANOTHER_EVENT_TYPE,
                        EXAMPLE_CONTACT,
                        EXAMPLE_START_TIME,
                        EXAMPLE_EXTRA_INFO
                )
        );
    }

    /**
     * Testing equals returns false if contact is different
     */
    @Test
    public void equalsReturnsFalseOnOnlyDifferentContact() {
        assertNotEquals(
                testedEvent,
                new SMSLogEvent(
                        EXAMPLE_EVENT_TYPE,
                        ANOTHER_CONTACT,
                        EXAMPLE_START_TIME,
                        EXAMPLE_EXTRA_INFO
                )
        );
    }

    /**
     * Testing equals returns false if time is different.
     */
    @Test
    public void equalsReturnsFalseOnOnlyDifferentTime() {
        assertNotEquals(
                testedEvent,
                new SMSLogEvent(
                        EXAMPLE_EVENT_TYPE,
                        EXAMPLE_CONTACT,
                        ANOTHER_START_TIME,
                        EXAMPLE_EXTRA_INFO
                )
        );
    }

    /**
     * Testing {@link SMSLogEvent#hashCode()} returns the same hashcode for the same Object.
     */
    @Test
    public void hashCodesMatchForSame() {
        assertEquals(
                testedEvent.hashCode(),
                testedEvent.hashCode()
        );
    }

    /**
     * Testing {@link SMSLogEvent#hashCode()} returns the same hashcode for equal Objects.
     */
    @Test
    public void hashCodesMatchForEquals() {
        assertEquals(
                testedEvent.hashCode(),
                new SMSLogEvent(
                        EXAMPLE_EVENT_TYPE,
                        EXAMPLE_CONTACT,
                        EXAMPLE_START_TIME,
                        EXAMPLE_EXTRA_INFO
                ).hashCode()
        );
    }
}
