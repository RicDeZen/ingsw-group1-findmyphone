package ingsw.group1.findmyphone.log;

import org.junit.Test;

import java.util.Comparator;

import ingsw.group1.findmyphone.event.EventOrder;
import ingsw.group1.findmyphone.random.RandomLogItemGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class testing the behaviour of the internal comparator classes in
 * {@link LogItemComparatorHelper}.
 */
public class LogItemComparatorHelperTest {

    private static final String EXAMPLE_NAME = "Asdrubale";
    private static final String BIGGER_NAME = "Paolo";
    private static final Long EXAMPLE_TIME = 100L;
    private static final Long BIGGER_TIME = 1000L;

    private static final RandomLogItemGenerator GENERATOR = new RandomLogItemGenerator();
    private static final LogItem EXAMPLE_ITEM = GENERATOR.nextLogItem(
            EXAMPLE_NAME, EXAMPLE_TIME
    );
    private static final LogItem NEWER_ITEM = GENERATOR.nextLogItem(
            EXAMPLE_NAME, BIGGER_TIME
    );
    private static final LogItem BIGGER_NAME_ITEM = GENERATOR.nextLogItem(
            BIGGER_NAME, EXAMPLE_TIME
    );

    /**
     * Asserting the newest-to-oldest comparator declares the first item as smaller if its newer.
     */
    @Test
    public void newestToOldestFirstNewer() {
        Comparator<LogItem> ntoComparator =
                LogItemComparatorHelper.newComparator(EventOrder.NEWEST_TO_OLDEST);
        assertTrue(ntoComparator.compare(NEWER_ITEM, EXAMPLE_ITEM) < 0);
    }

    /**
     * Asserting the newest-to-oldest comparator declares items with the same time as equal.
     */
    @Test
    public void newestToOldestEqual() {
        Comparator<LogItem> ntoComparator =
                LogItemComparatorHelper.newComparator(EventOrder.NEWEST_TO_OLDEST);
        assertEquals(0, ntoComparator.compare(EXAMPLE_ITEM, EXAMPLE_ITEM));
    }

    /**
     * Asserting the newest-to-oldest comparator declares the first item as greater if its older.
     */
    @Test
    public void newestToOldestFirstOlder() {
        Comparator<LogItem> ntoComparator =
                LogItemComparatorHelper.newComparator(EventOrder.NEWEST_TO_OLDEST);
        assertTrue(ntoComparator.compare(EXAMPLE_ITEM, NEWER_ITEM) > 0);
    }

    /**
     * Asserting the oldest-to-newest comparator declares the first item greater if its newer.
     */
    @Test
    public void oldestToNewestFirstNewer() {
        Comparator<LogItem> otnComparator =
                LogItemComparatorHelper.newComparator(EventOrder.OLDEST_TO_NEWEST);
        assertTrue(otnComparator.compare(NEWER_ITEM, EXAMPLE_ITEM) > 0);
    }

    /**
     * Asserting the oldest-to-newest comparator declares items with the same time as equal.
     */
    @Test
    public void oldestToNewestEqual() {
        Comparator<LogItem> otnComparator =
                LogItemComparatorHelper.newComparator(EventOrder.OLDEST_TO_NEWEST);
        assertEquals(0, otnComparator.compare(EXAMPLE_ITEM, EXAMPLE_ITEM));
    }

    /**
     * Asserting the oldest-to-newest comparator declares the first item smaller if its older.
     */
    @Test
    public void oldestToNewestFirstOlder() {
        Comparator<LogItem> otnComparator =
                LogItemComparatorHelper.newComparator(EventOrder.OLDEST_TO_NEWEST);
        assertTrue(otnComparator.compare(EXAMPLE_ITEM, NEWER_ITEM) < 0);
    }

    /**
     * Asserting the ascending name comparator declares the first item as smaller if its name is.
     */
    @Test
    public void nameAscendingFirstSmaller() {
        Comparator<LogItem> naComparator =
                LogItemComparatorHelper.newComparator(EventOrder.NAME_ASCENDING);
        assertTrue(naComparator.compare(EXAMPLE_ITEM, BIGGER_NAME_ITEM) < 0);
    }

    /**
     * Asserting the ascending name comparator declares items with the same time as equal.
     */
    @Test
    public void nameAscendingEqual() {
        Comparator<LogItem> naComparator =
                LogItemComparatorHelper.newComparator(EventOrder.NAME_ASCENDING);
        assertEquals(0, naComparator.compare(EXAMPLE_ITEM, EXAMPLE_ITEM));
    }

    /**
     * Asserting the ascending name comparator declares the first item as greater if its name is.
     */
    @Test
    public void nameAscendingFirstBigger() {
        Comparator<LogItem> naComparator =
                LogItemComparatorHelper.newComparator(EventOrder.NAME_ASCENDING);
        assertTrue(naComparator.compare(BIGGER_NAME_ITEM, EXAMPLE_ITEM) > 0);
    }

    /**
     * Asserting the descending name comparator declares the first item greater if its name is
     * smaller.
     */
    @Test
    public void nameDescendingFirstSmaller() {
        Comparator<LogItem> ndComparator =
                LogItemComparatorHelper.newComparator(EventOrder.NAME_DESCENDING);
        assertTrue(ndComparator.compare(EXAMPLE_ITEM, BIGGER_NAME_ITEM) > 0);
    }

    /**
     * Asserting the descending name comparator declares items with the same time as equal.
     */
    @Test
    public void nameDescendingEqual() {
        Comparator<LogItem> ndComparator =
                LogItemComparatorHelper.newComparator(EventOrder.NAME_DESCENDING);
        assertEquals(0, ndComparator.compare(EXAMPLE_ITEM, EXAMPLE_ITEM));
    }

    /**
     * Asserting the descending name comparator declares the first item smaller if its name is
     * greater.
     */
    @Test
    public void nameDescendingFirstBigger() {
        Comparator<LogItem> ndComparator =
                LogItemComparatorHelper.newComparator(EventOrder.NAME_DESCENDING);
        assertTrue(ndComparator.compare(BIGGER_NAME_ITEM, EXAMPLE_ITEM) < 0);
    }

}
