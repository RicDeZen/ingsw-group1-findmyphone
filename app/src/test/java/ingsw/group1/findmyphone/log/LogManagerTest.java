package ingsw.group1.findmyphone.log;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.eis.smslibrary.SMSPeer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

import java.util.Comparator;
import java.util.List;

import ingsw.group1.findmyphone.TestUtils;
import ingsw.group1.findmyphone.contacts.SMSContactManager;
import ingsw.group1.findmyphone.event.EventOrder;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.log.items.LogItem;
import ingsw.group1.findmyphone.random.RandomSMSContactGenerator;
import ingsw.group1.findmyphone.random.RandomSMSLogEventGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link LogManager}.
 *
 * @author Riccardo De Zen
 */
@RunWith(RobolectricTestRunner.class)
public class LogManagerTest {

    private static final String DB_NAME = LogManager.DEFAULT_LOG_DATABASE;
    private static final int TEST_LOG_SIZE = 10;

    private static final RandomSMSLogEventGenerator randomEvent = new RandomSMSLogEventGenerator();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private SMSContactManager contacts;
    private SMSLogDatabase database;
    private LogManager manager;

    /**
     * Method to prepare the data for the tests.
     */
    @Before
    public void setupDatabaseAndManager() {
        Context context = ApplicationProvider.getApplicationContext();
        database = SMSLogDatabase.getInstance(context, DB_NAME);
        contacts = SMSContactManager.getInstance(context);
        // Adding events to the database
        List<SMSLogEvent> events = randomEvent.getMixedEventSet(TEST_LOG_SIZE);
        database.addEvents(events);
        // Adding a matching Contact to the contact database
        for (SMSLogEvent eachEvent : events)
            contacts.addContact(new SMSPeer(eachEvent.getAddress()),
                    RandomSMSContactGenerator.getRandomUsername());
        manager = LogManager.getInstance(context);
    }

    /**
     * Method to clean up the Contact and event databases.
     */
    @After
    public void cleanup() {
        // Necessary due to an issue with singleton SQL databases.
        TestUtils.resetLogManager();
        TestUtils.resetContactManager();
        TestUtils.resetSMSLogDatabase();
    }

    /**
     * Testing that the manager actually is listening to changes on its appropriate database.
     */
    @Test
    public void managerObservesDatabase() {
        LogRecyclerAdapter mockAdapter = mock(LogRecyclerAdapter.class);
        manager.setListener(mockAdapter);
        database.clear();
        verify(mockAdapter, atLeastOnce()).notifyDataSetChanged();
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
        manager.filter(item.getName().toString());
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
        manager.filter(item.getName().toString());
        for (LogItem eachItem : manager.getItems())
            if (!eachItem.matches(item.getName().toString()))
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
        manager.filter(item.getName().toString());
        for (LogItem eachOldItem : oldList)
            if (eachOldItem.matches(item.getName().toString()) && !manager.getItems().contains(eachOldItem))
                fail();
    }

    /**
     * Testing that filtering on a null String resets the view to all the items.
     */
    @Test
    public void nullFilterResetsTheView() {
        LogItem item = manager.getItem(0);
        int sizeBeforeFilter = manager.count();
        manager.filter(item.getName().toString());
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
        manager.filter(item.getName().toString());
        manager.filter("");
        int sizeAfterFilter = manager.count();
        assertEquals(sizeBeforeFilter, sizeAfterFilter);
    }

    // SORTING TESTS -------------------------------------------------------------------------------

    /**
     * Testing that items are ordered according to the appropriate comparator.
     */
    @Test
    public void newestToOldest() {
        // Newest to oldest should be set by default.
        Comparator<LogItem> comparator =
                LogItemComparatorHelper.newComparator(EventOrder.NEWEST_TO_OLDEST);
        LogItem previous = null;
        for (LogItem eachItem : manager) {
            if (previous != null && comparator.compare(previous, eachItem) > 0)
                fail();
            previous = eachItem;
        }
    }

    /**
     * Testing that items are ordered according to the appropriate comparator.
     */
    @Test
    public void oldestToNewest() {
        manager.setSortingOrder(EventOrder.OLDEST_TO_NEWEST);
        Comparator<LogItem> comparator =
                LogItemComparatorHelper.newComparator(EventOrder.OLDEST_TO_NEWEST);
        LogItem previous = null;
        for (LogItem eachItem : manager) {
            if (previous != null && comparator.compare(previous, eachItem) > 0)
                fail();
            previous = eachItem;
        }
    }

    /**
     * Testing that items are ordered according to the appropriate comparator.
     */
    @Test
    public void nameAscending() {
        manager.setSortingOrder(EventOrder.NAME_ASCENDING);
        Comparator<LogItem> comparator =
                LogItemComparatorHelper.newComparator(EventOrder.NAME_ASCENDING);
        LogItem previous = null;
        for (LogItem eachItem : manager) {
            if (previous != null && comparator.compare(previous, eachItem) > 0)
                fail();
            previous = eachItem;
        }
    }

    /**
     * Testing that items are ordered according to the appropriate comparator.
     */
    @Test
    public void nameDescending() {
        manager.setSortingOrder(EventOrder.NAME_DESCENDING);
        Comparator<LogItem> comparator =
                LogItemComparatorHelper.newComparator(EventOrder.NAME_DESCENDING);
        LogItem previous = null;
        for (LogItem eachItem : manager) {
            if (previous != null && comparator.compare(previous, eachItem) > 0)
                fail();
            previous = eachItem;
        }
    }
}
