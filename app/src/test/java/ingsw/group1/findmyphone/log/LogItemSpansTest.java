package ingsw.group1.findmyphone.log;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import ingsw.group1.findmyphone.location.GeoPosition;
import ingsw.group1.findmyphone.log.items.LogItem;
import ingsw.group1.findmyphone.random.RandomGeoPositionGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link LogItem}, testing its {@link Markable} features and
 * {@link android.text.SpannableString}. These tests have been separated from the rest because
 * they require Robolectric while the others don't.
 *
 * @author Riccardo De Zen.
 */
@RunWith(RobolectricTestRunner.class)
public class LogItemSpansTest {

    private static final Drawable EXAMPLE_DRAWABLE = new ShapeDrawable();
    private static final String EXAMPLE_ADDRESS = "Phone Number";
    private static final String EXAMPLE_NAME = "Contact Name";
    private static final String EXAMPLE_TIME = "01/01/2000";
    private static final String EXAMPLE_EXTRA = "Something something something";
    private static final Long EXAMPLE_TIME_IN_MILLIS = 10000L;
    private static final GeoPosition EXAMPLE_POSITION =
            new RandomGeoPositionGenerator().getRandomPosition();

    private static final String NAME_MARK = "Name";
    private static final String ADDRESS_MARK = "Phone";
    private static final String BOTH_MARK = "n";

    private LogItem testedItem;

    /**
     * Creating the item to test.
     */
    @Before
    public void createItem() {
        testedItem = new LogItem(
                EXAMPLE_ADDRESS,
                EXAMPLE_NAME,
                EXAMPLE_TIME,
                EXAMPLE_EXTRA,
                EXAMPLE_DRAWABLE,
                EXAMPLE_TIME_IN_MILLIS,
                EXAMPLE_POSITION
        );
    }

    // MARKABLE TESTS ------------------------------------------------------------------------------

    /**
     * Checking that {@link LogItem#addMark(String)} can add a mark to the name when it contains
     * the given String.
     */
    @Test
    public void addMarkCanMarkNameOnMatch() {
        testedItem.addMark(NAME_MARK);
        Object[] nameSpans = testedItem.getName().getSpans(
                0,
                testedItem.getName().length(),
                Object.class
        );
        assertTrue(nameSpans.length > 0);
    }

    /**
     * Checking that {@link LogItem#addMark(String)} won't add a mark to the name when it does
     * not contain the given String.
     */
    @Test
    public void addMarkDoesNotMarkNameOnNotMatch() {
        testedItem.addMark(ADDRESS_MARK);
        Object[] nameSpans = testedItem.getName().getSpans(
                0,
                testedItem.getName().length(),
                Object.class
        );
        assertEquals(0, nameSpans.length);
    }

    /**
     * Checking that {@link LogItem#addMark(String)} can add a mark to the address when it contains
     * the given String.
     */
    @Test
    public void addMarkCanMarkAddressOnMatch() {
        testedItem.addMark(ADDRESS_MARK);
        Object[] addressSpans = testedItem.getAddress().getSpans(
                0,
                testedItem.getAddress().length(),
                Object.class
        );
        assertTrue(addressSpans.length > 0);
    }

    /**
     * Checking that {@link LogItem#addMark(String)} won't add a mark to the address when it does
     * not contain the given String.
     */
    @Test
    public void addMarkDoesNotMarkAddressOnNotMatch() {
        testedItem.addMark(NAME_MARK);
        Object[] addressSpans = testedItem.getAddress().getSpans(
                0,
                testedItem.getAddress().length(),
                Object.class
        );
        assertEquals(0, addressSpans.length);
    }

    /**
     * Checking that {@link LogItem#addMark(String)} can mark both name and address if they both
     * contain a String.
     */
    @Test
    public void addMarkCanMarkBothOnBothMatch() {
        testedItem.addMark(BOTH_MARK);
        Object[] nameSpans = testedItem.getName().getSpans(
                0,
                testedItem.getName().length(),
                Object.class
        );
        Object[] addressSpans = testedItem.getAddress().getSpans(
                0,
                testedItem.getAddress().length(),
                Object.class
        );
        assertTrue(nameSpans.length > 0 && addressSpans.length > 0);
    }

    /**
     * Checking that {@link LogItem#resetMarks()} removes all the marks from both the name and
     * the address.
     */
    @Test
    public void resetMarksRemovesAllMarks() {
        testedItem.addMark(BOTH_MARK);
        testedItem.resetMarks();
        Object[] nameSpans = testedItem.getName().getSpans(
                0,
                testedItem.getName().length(),
                Object.class
        );
        Object[] addressSpans = testedItem.getAddress().getSpans(
                0,
                testedItem.getAddress().length(),
                Object.class
        );
        assertTrue(nameSpans.length == 0 && addressSpans.length == 0);
    }

}
