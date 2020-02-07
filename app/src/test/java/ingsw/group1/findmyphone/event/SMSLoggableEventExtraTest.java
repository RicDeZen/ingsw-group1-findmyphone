package ingsw.group1.findmyphone.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ingsw.group1.findmyphone.location.GeoPosition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for extra String validity in {@link SMSLoggableEvent}.
 *
 * @author Riccardo De Zen.
 */
@RunWith(Parameterized.class)
public class SMSLoggableEventExtraTest {

    private static final String locationString = new GeoPosition(100, 100).toString();
    private static final String positiveNumString = String.valueOf(100L);
    private static final String negativeNumString = String.valueOf(-100L);
    private static final String alwaysInvalidString = "I'm not a suitable String";

    private SMSLoggableEvent testedEvent;
    private LogEventType testedEventType;
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
                {LogEventType.LOCATION_REQUEST_SENT, locationString, alwaysInvalidString},
                {LogEventType.LOCATION_REQUEST_RECEIVED, locationString, alwaysInvalidString},
                {LogEventType.RING_REQUEST_SENT, positiveNumString, negativeNumString},
                {LogEventType.RING_REQUEST_RECEIVED, positiveNumString, negativeNumString},
                {LogEventType.RING_REQUEST_SENT, positiveNumString, alwaysInvalidString},
                {LogEventType.RING_REQUEST_RECEIVED, positiveNumString, alwaysInvalidString},
        };
    }

    /**
     * Constructor for the test.
     *
     * @param testedType    Tested Event type.
     * @param validString   Valid String.
     * @param invalidString Invalid String.
     */
    public SMSLoggableEventExtraTest(
            LogEventType testedType,
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
        assertTrue(SMSLoggableEvent.isValidExtra(LogEventType.UNKNOWN, validString));
    }

    /**
     * Testing {@code null} is a valid extra for every type.
     */
    @Test
    public void nullExtraIsAlwaysAccepted() {
        assertTrue(SMSLoggableEvent.isValidExtra(testedEventType, null));
    }

    /**
     * Testing the provided valid String returns {@code true}.
     */
    @Test
    public void validStringReturnsTrue() {
        assertTrue(SMSLoggableEvent.isValidExtra(testedEventType, validString));
    }

    /**
     * Testing the provided invalid String returns {@code false}.
     */
    @Test
    public void invalidStringReturnsFalse() {
        assertFalse(SMSLoggableEvent.isValidExtra(testedEventType, invalidString));
    }
}
