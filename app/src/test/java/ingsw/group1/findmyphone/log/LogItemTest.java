package ingsw.group1.findmyphone.log;

import android.graphics.drawable.Drawable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link LogItem}. This is a simple Pojo. Tests are just getters.
 *
 * @author Riccardo De Zen.
 */
@RunWith(MockitoJUnitRunner.class)
public class LogItemTest {

    @Mock
    private static Drawable mockDrawable;

    private static final String EXAMPLE_ADDRESS = "Phone Number";
    private static final String EXAMPLE_NAME = "Contact Name";
    private static final String EXAMPLE_TIME = "01/01/2000";
    private static final String EXAMPLE_EXTRA = "Something something something";
    private static final Long EXAMPLE_TIME_IN_MILLIS = 10000L;
    private static final boolean EXAMPLE_SHOULD_EXPAND = true;

    private static final String MATCHING_QUERY = "Name";
    private static final String NON_MATCHING_QUERY = "No way I'm in the item";

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
                mockDrawable,
                EXAMPLE_TIME_IN_MILLIS,
                EXAMPLE_SHOULD_EXPAND
        );
    }

    /**
     * Testing that {@link LogItem#getAddress()} returns the given parameter.
     */
    @Test
    public void getAddressReturnsActual() {
        assertEquals(testedItem.getAddress(), EXAMPLE_ADDRESS);
    }

    /**
     * Testing that {@link LogItem#getName()} returns the given parameter.
     */
    @Test
    public void getNameReturnsActual() {
        assertEquals(testedItem.getName(), EXAMPLE_NAME);
    }

    /**
     * Testing that {@link LogItem#getTime()} returns the given parameter.
     */
    @Test
    public void getTimeReturnsActual() {
        assertEquals(testedItem.getTime(), EXAMPLE_TIME);
    }

    /**
     * Testing that {@link LogItem#getExtra()} returns the given parameter.
     */
    @Test
    public void getExtraReturnsActual() {
        assertEquals(testedItem.getExtra(), EXAMPLE_EXTRA);
    }

    /**
     * Testing that {@link LogItem#getDrawable()} returns the given parameter.
     */
    @Test
    public void getDrawableReturnsActual() {
        assertEquals(testedItem.getDrawable(), mockDrawable);
    }

    /**
     * Testing that {@link LogItem#getTimeInMillis()} returns the given parameter.
     */
    @Test
    public void getTimeInMillisReturnsActual() {
        assertEquals(testedItem.getTimeInMillis(), EXAMPLE_TIME_IN_MILLIS);
    }

    /**
     * Testing that {@link LogItem#shouldExpand()} returns the given parameter.
     */
    @Test
    public void getShouldExpandReturnsActual() {
        assertEquals(testedItem.shouldExpand(), EXAMPLE_SHOULD_EXPAND);
    }

    /**
     * Testing the Item can match a query that's in the name.
     */
    @Test
    public void ifNameContainsQueryMatchesReturnsTrue() {
        assertTrue(testedItem.matches(MATCHING_QUERY));
    }

    /**
     * Testing the Items won't match a String not contained in the name.
     */
    @Test
    public void ifNameDoesNotContainQueryMatchesReturnsFalse() {
        assertFalse(testedItem.matches(NON_MATCHING_QUERY));
    }

}
