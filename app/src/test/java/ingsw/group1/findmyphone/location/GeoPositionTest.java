package ingsw.group1.findmyphone.location;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;


/**
 * Test class for {@link GeoPosition}.
 *
 * @author Riccardo De Zen.
 */
public class GeoPositionTest {

    private double exampleLatitude = 111.111d;
    private double exampleLongitude = 99.999d;
    private GeoPosition examplePosition;

    /**
     * Checking the default constructor passes.
     */
    @Before
    public void doesConstructorPass() {
        examplePosition = new GeoPosition(exampleLatitude, exampleLongitude);
    }

    /**
     * Testing {@link GeoPosition#getLatitude()} returns the actual value of the latitude.
     */
    @Test
    public void getLatitude_returnsActual() {
        assertEquals(exampleLatitude, examplePosition.getLatitude());
    }

    /**
     * Testing {@link GeoPosition#getLongitude()} returns the actual value of the longitude.
     */
    @Test
    public void getLongitude_returnsActual() {
        assertEquals(exampleLongitude, examplePosition.getLongitude());
    }

    /**
     * Testing {@link GeoPosition#setLatitude(double)} sets a new value of the latitude.
     */
    @Test
    public void setLatitude_setsCorrectly() {
        double expectedLatitude = exampleLatitude * 2;
        examplePosition.setLatitude(expectedLatitude);
        assertEquals(expectedLatitude, examplePosition.getLatitude());
    }

    /**
     * Testing {@link GeoPosition#setLongitude(double)} sets a new value of the longitude.
     */
    @Test
    public void setLongitude_setsCorrectly() {
        double expectedLongitude = exampleLongitude * 2;
        examplePosition.setLongitude(expectedLongitude);
        assertEquals(expectedLongitude, examplePosition.getLongitude());
    }

    /**
     * Testing {@link GeoPosition#equals(Object)} returns false for a non {@link GeoPosition}
     * Object.
     */
    @Test
    public void equals_falseForNonPosition() {
        assertNotEquals(examplePosition, "Hello there");
    }

    /**
     * Testing {@link GeoPosition#equals(Object)} returns false for a {@link GeoPosition} Object
     * that does not match the equation criteria.
     */
    @Test
    public void equals_falseForNonEqualPosition() {
        //Latitude and longitude are switched.
        GeoPosition notTheSamePosition = new GeoPosition(
                exampleLongitude,
                exampleLatitude
        );
        assertNotEquals(notTheSamePosition, examplePosition);
    }

    /**
     * Testing {@link GeoPosition#equals(Object)} returns true for a {@link GeoPosition} Object
     * that does match the equation criteria.
     */
    @Test
    public void equals_trueForEqualPosition() {
        GeoPosition theSamePosition = new GeoPosition(
                exampleLatitude,
                exampleLongitude
        );
        assertEquals(examplePosition, theSamePosition);
    }

    /**
     * Testing that {@link GeoPosition#toString()} returns a {@link String} formatted as stated
     * in the specs:
     * [latitude]{@link GeoPosition#POSITION_SPLIT_SEQUENCE}[longitude]
     */
    @Test
    public void toString_actsAsStated() {
        String splitSequence = GeoPosition.POSITION_SPLIT_SEQUENCE;
        String expectedString = exampleLatitude + splitSequence + exampleLongitude;
        assertEquals(expectedString, examplePosition.toString());
    }

    /**
     * Testing that {@link GeoPosition#GeoPosition(String)} does not accept a String that splits
     * into more than two parts.
     */
    @Test(expected = IllegalArgumentException.class)
    public void stringConstructor_failsForInvalidSplitString() {
        String splitSequence = GeoPosition.POSITION_SPLIT_SEQUENCE;
        String invalidString = "a" + splitSequence + "b" + splitSequence + "c";
        new GeoPosition(invalidString);
    }

    /**
     * Testing that {@link GeoPosition#GeoPosition(String)} does not accept a String that splits
     * into two parts that are not parsable into {@link Double} values.
     */
    @Test(expected = NumberFormatException.class)
    public void stringConstructor_failsForNonDoubleStrings() {
        String splitSequence = GeoPosition.POSITION_SPLIT_SEQUENCE;
        String invalidString = "General Kenobi" + splitSequence + "You are a bold one";
        new GeoPosition(invalidString);
    }

    /**
     * Testing that {@link GeoPosition#GeoPosition(String)} accepts the result of
     * {@link GeoPosition#toString()} as its parameter.
     */
    @Test
    public void stringConstructor_acceptsToStringResult() {
        try {
            assertEquals(examplePosition, new GeoPosition(examplePosition.toString()));
        } catch (IllegalArgumentException ie) {
            fail();
        }
    }
}
