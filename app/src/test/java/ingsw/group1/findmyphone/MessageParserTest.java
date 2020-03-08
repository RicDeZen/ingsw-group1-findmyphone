package ingsw.group1.findmyphone;

import org.junit.Test;

import ingsw.group1.findmyphone.random.RandomGeoPositionGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test class for {@link MessageParser}.
 *
 * @author Riccardo De Zen.
 */
public class MessageParserTest {

    private static final String RING_RES = MessageType.RING_RESPONSE.toString();
    private static final String LOCATION_RES = MessageType.LOCATION_RESPONSE.toString();
    private static final Long EX_TIME = 1000L;
    private static final Long ANOTHER_TIME = 1234L;
    private static final String EX_POSITION =
            new RandomGeoPositionGenerator().getRandomPosition().toString();

    private static final String TIME_NO_ENUM = EX_TIME.toString();
    private static final String TIME_NO_NUMBER = RING_RES + "da" + EX_TIME;
    private static final String TIME_NUMBER = RING_RES + EX_TIME + "nah" + ANOTHER_TIME;
    private static final String TIME_NUMBER_TOO_LONG = RING_RES +
            "1234567898765432123456789876543212345678987654321";

    private static final String LOCATION_NO_ENUM = EX_POSITION;
    private static final String LOCATION_NO_POSITION = LOCATION_RES + "duh" + EX_POSITION;
    private static final String LOCATION_NUMBER = LOCATION_RES + EX_TIME;
    private static final String LOCATION_GEO_POSITION = LOCATION_RES + EX_POSITION + "bla bla";

    private MessageParser testedParser = new MessageParser();

    @Test
    public void nullTimeIfNoEnum() {
        assertNull(testedParser.getTimeIfRingResponse(TIME_NO_ENUM));
    }

    @Test
    public void nullTimeIfNoNumber() {
        assertNull(testedParser.getTimeIfRingResponse(TIME_NO_NUMBER));
    }

    @Test
    public void nullTimeIfTooLong() {
        assertNull(testedParser.getTimeIfRingResponse(TIME_NUMBER_TOO_LONG));
    }

    @Test
    public void findsTimeIgnoringRest() {
        assertEquals(EX_TIME, testedParser.getTimeIfRingResponse(TIME_NUMBER));
    }

    @Test
    public void nullPositionIfNoEnum() {
        assertNull(testedParser.getPositionIfLocationResponse(LOCATION_NO_ENUM));
    }

    @Test
    public void nullPositionIfNoPosition() {
        assertNull(testedParser.getPositionIfLocationResponse(LOCATION_NO_POSITION));
    }

    @Test
    public void nullPositionIfNumber() {
        assertNull(testedParser.getPositionIfLocationResponse(LOCATION_NUMBER));
    }

    @Test
    public void findsPositionIgnoringRest() {
        assertEquals(EX_POSITION,
                testedParser.getPositionIfLocationResponse(LOCATION_GEO_POSITION).toString());
    }

}
