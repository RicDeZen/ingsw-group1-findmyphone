package ingsw.group1.findmyphone.log;

import android.graphics.drawable.Drawable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

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
    private static final boolean EXAMPLE_SHOULD_EXPAND = true;

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
     * Testing that {@link LogItem#shouldExpand()} returns the given parameter.
     */
    @Test
    public void getShouldExpandReturnsActual() {
        assertEquals(testedItem.shouldExpand(), EXAMPLE_SHOULD_EXPAND);
    }

}
