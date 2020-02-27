package ingsw.group1.findmyphone.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ingsw.group1.findmyphone.managing.location.GeoPosition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for extra String validity in {@link SMSLogEvent}.
 *
 * @author Riccardo De Zen.
 */
@RunWith(Parameterized.class)
public class SMSLogEventExtraTest {

    private static final String locationString = new GeoPosition(100, 100).toString();
    private static final String positiveNumString = String.valueOf(100L);
    private static final String negativeNumString = String.valueOf(-100L);
    private static final String alwaysInvalidString = "I'm not a suitable String";

    private EventType testedEventType;
    private String validString;
    private String invalidString;

    /**
     * @return Parameters for the test, in the form:
     * - Type of event.
     * - Valid extra.
     * - Invalid extra.
     */
    @Parameterized.Parameters(name = "{index}: {0} + {1} + {2}")
    public static Object[][] params() {
        return new Object[][]{
                {EventType.LOCATION_REQUEST_SENT, locationString, alwaysInvalidString},
                {EventType.LOCATION_REQUEST_RECEIVED, locationString, alwaysInvalidString},
                {EventType.RING_REQUEST_SENT, positiveNumString, negativeNumString},
                {EventType.RING_REQUEST_RECEIVED, positiveNumString, negativeNumString},
                {EventType.RING_REQUEST_SENT, positiveNumString, alwaysInvalidString},
                {EventType.RING_REQUEST_RECEIVED, positiveNumString, alwaysInvalidString},
        };
    }

    /**
     * Constructor for the test.
     *
     * @param testedType    Tested Event type.
     * @param validString   Valid String.
     * @param invalidString Invalid String.
     */
    public SMSLogEventExtraTest(
            EventType testedType,
            String validString,
            String invalidString) {
        this.testedEventType = testedType;
        this.validString = validString;
        this.invalidString = invalidString;
    }

    /**
     * Testing the various Strings are accepted by unknown event type.
     */
    @Test
    public void unknownAcceptsAnything() {
        assertTrue(SMSLogEvent.isValidExtra(EventType.UNKNOWN, validString));
    }

    /**
     * Testing {@code null} is a valid extra for every type.
     */
    @Test
    public void nullExtraIsAlwaysAccepted() {
        assertTrue(SMSLogEvent.isValidExtra(testedEventType, null));
    }

    /**
     * Testing the provided valid String returns {@code true}.
     */
    @Test
    public void validStringReturnsTrue() {
        assertTrue(SMSLogEvent.isValidExtra(testedEventType, validString));
    }

    /**
     * Testing the provided invalid String returns {@code false}.
     */
    @Test
    public void invalidStringReturnsFalse() {
        assertFalse(SMSLogEvent.isValidExtra(testedEventType, invalidString));
    }
}
