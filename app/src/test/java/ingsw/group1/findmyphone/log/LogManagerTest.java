package ingsw.group1.findmyphone.log;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.random.RandomSMSLogEventGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for {@link LogManager}.
 *
 * @author Riccardo De Zen
 */
@RunWith(RobolectricTestRunner.class)
public class LogManagerTest {

    private static final int TEST_LOG_SIZE = 100;

    private SMSLogDatabase database;
    private LogManager manager;

    /**
     * Method to prepare the data for the tests.
     */
    @Before
    public void setupDatabaseAndManager() {
        Context context = ApplicationProvider.getApplicationContext();
        database = SMSLogDatabase.getInstance(context, "TEST");
        database.addEvents(new RandomSMSLogEventGenerator().getMixedEventSet(TEST_LOG_SIZE));
        manager = new LogManager(database, new LogItemFormatter(context));
    }

    /**
     * Asserting the size of the log is the same as the database.
     * Warning: this test is valid only if the database does not contain unknown events, as in
     * this case.
     */
    @Test
    public void getCountReturnsDatabaseSize() {
        assertEquals(database.count(), manager.count());
    }

    /**
     * Testing that filtering on a String that is surely matched by at least one item restricts
     * the visible items count.
     */
    @Test
    public void filterRestrictsTheView() {
        LogItem item = manager.getItem(0);
        int sizeBeforeFilter = manager.count();
        manager.filter(item.getName());
        int sizeAfterFilter = manager.count();
        assertTrue(sizeAfterFilter < sizeBeforeFilter);
    }

    /**
     * Testing that filtering on a String that is surely matched by at least one item restricts
     * the visible items to only items that match that String.
     */
    @Test
    public void filterRestrictsTheViewOnMatchingOnly() {
        LogItem item = manager.getItem(0);
        manager.filter(item.getName());
        for (LogItem eachItem : manager.getItems())
            if (!eachItem.matches(item.getName()))
                fail();
    }

    /**
     * Testing that filtering on a String that is surely matched by at least one item restricts
     * the visible items to all the items that matched that String. Equivalently, no items that
     * match the query have been excluded.
     */
    @Test
    public void filterRestrictsTheViewOnAllMatching() {
        List<LogItem> oldList = manager.getItems();
        LogItem item = manager.getItem(0);
        manager.filter(item.getName());
        for (LogItem eachOldItem : oldList)
            if (eachOldItem.matches(item.getName()) && !manager.getItems().contains(eachOldItem))
                fail();
    }

    /**
     * Testing that filtering on a null String resets the view to all the items.
     */
    @Test
    public void nullFilterResetsTheView() {
        LogItem item = manager.getItem(0);
        int sizeBeforeFilter = manager.count();
        manager.filter(item.getName());
        manager.filter(null);
        int sizeAfterFilter = manager.count();
        assertEquals(sizeBeforeFilter, sizeAfterFilter);
    }

    /**
     * Testing that filtering on an empty String resets the view to all the items.
     */
    @Test
    public void emptyFilterResetsTheView() {
        LogItem item = manager.getItem(0);
        int sizeBeforeFilter = manager.count();
        manager.filter(item.getName());
        manager.filter("");
        int sizeAfterFilter = manager.count();
        assertEquals(sizeBeforeFilter, sizeAfterFilter);
    }
}
