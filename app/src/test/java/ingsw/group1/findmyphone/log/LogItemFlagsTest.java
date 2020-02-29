package ingsw.group1.findmyphone.log;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;

import java.util.Arrays;
import java.util.List;

import ingsw.group1.findmyphone.location.GeoPosition;
import ingsw.group1.findmyphone.log.items.LogItem;
import ingsw.group1.findmyphone.random.RandomGeoPositionGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Class testing the behaviour of the flags in LogItem.
 *
 * @author Riccardo De Zen.
 */
@RunWith(ParameterizedRobolectricTestRunner.class)
public class LogItemFlagsTest {
    //The following parameters are meant to never trigger the flags.
    private static final Drawable EXAMPLE_DRAWABLE = new ShapeDrawable();
    private static final String EXAMPLE_ADDRESS = "Phone Number";
    private static final String EXAMPLE_NAME = "Contact Name";
    private static final String EXAMPLE_TIME = "01/01/2000";
    private static final String EXAMPLE_EXTRA = "";
    private static final Long EXAMPLE_TIME_IN_MILLIS = 10000L;
    private static final GeoPosition EXAMPLE_POSITION = null;

    private int testedFlag;
    private LogItem flaggedItem;
    private LogItem nonFlaggedItem;

    /**
     * @return Parameters for the test. In the form:
     * - The flag to test.
     * - A parameter that should trigger the flag.
     */
    @ParameterizedRobolectricTestRunner.Parameters(name = "{index} = {0}")
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {LogItem.NAMELESS, ""},
                {LogItem.HAS_EXTRA, "I am a non empty String, so the item should expand."},
                {LogItem.HAS_POSITION, new RandomGeoPositionGenerator().getRandomPosition()}
        });
    }

    /**
     * @param flag  The flag that is being tested.
     * @param param The parameter for said test.
     */
    public LogItemFlagsTest(int flag, Object param) {
        this.testedFlag = flag;
        this.flaggedItem = new LogItem(
                EXAMPLE_ADDRESS,
                (flag == LogItem.NAMELESS) ? (String) param : EXAMPLE_NAME,
                EXAMPLE_TIME,
                (flag == LogItem.HAS_EXTRA) ? (String) param : EXAMPLE_EXTRA,
                EXAMPLE_DRAWABLE,
                EXAMPLE_TIME_IN_MILLIS,
                (flag == LogItem.HAS_POSITION) ? (GeoPosition) param : EXAMPLE_POSITION
        );
        this.nonFlaggedItem = new LogItem(
                EXAMPLE_ADDRESS,
                EXAMPLE_NAME,
                EXAMPLE_TIME,
                EXAMPLE_EXTRA,
                EXAMPLE_DRAWABLE,
                EXAMPLE_TIME_IN_MILLIS,
                EXAMPLE_POSITION
        );
    }

    /**
     * Testing that when the flag conditions are met the flag is actually set.
     */
    @Test
    public void isFlagTriggeredWhenDue() {
        assertEquals(testedFlag, flaggedItem.getFlags() & testedFlag);
    }

    /**
     * Testing that when the flag conditions are not met the flag is not set.
     */
    @Test
    public void isFlagAbsentWhenDue() {
        assertNotEquals(testedFlag, nonFlaggedItem.getFlags() & testedFlag);
    }

}
