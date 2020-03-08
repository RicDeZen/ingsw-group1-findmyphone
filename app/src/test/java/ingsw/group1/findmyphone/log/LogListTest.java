package ingsw.group1.findmyphone.log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import ingsw.group1.findmyphone.log.items.LogItem;
import ingsw.group1.findmyphone.random.RandomLogItemGenerator;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link LogList}.
 *
 * @author Riccardo De Zen.
 */
@RunWith(RobolectricTestRunner.class)
public class LogListTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private static final String EXAMPLE_QUERY = "A";
    private static final int LIST_LENGTH = 15;
    private static final RandomLogItemGenerator itemGenerator = new RandomLogItemGenerator();

    private LogList testedList = new LogList();

    /**
     * Method filling the list with some random meaningless LogItems.
     */
    @Before
    public void fillList() {
        int i = 0;
        while (i++ < LIST_LENGTH - 1)
            testedList.add(itemGenerator.nextLogItem());
        //Just to ensure at least a matching item is in the list.
        testedList.add(itemGenerator.nextLogItem(EXAMPLE_QUERY));
    }

    /**
     * Method testing {@link LogList#getMatching(String)} only returns matching items.
     */
    @Test
    public void getMatchingReturnsOnlyMatching() {
        List<LogItem> matchingItems = testedList.getMatching(EXAMPLE_QUERY);
        for (LogItem eachItem : matchingItems)
            if (!eachItem.matches(EXAMPLE_QUERY))
                fail();
    }

    /**
     * Method testing {@link LogList#getMatching(String)} returns all the matching items.
     */
    @Test
    public void getMatchingReturnsAllTheMatchingItems() {
        List<LogItem> matchingItems = testedList.getMatching(EXAMPLE_QUERY);
        //If an item from the original list matches the query and was not returned, the test fails.
        for (LogItem eachOriginalItem : testedList)
            if (eachOriginalItem.matches(EXAMPLE_QUERY) && !matchingItems.contains(eachOriginalItem))
                fail();
    }

    /**
     * Method testing {@link LogList#removeNonMatching(String)} returns only items that do
     * not match the query.
     */
    @Test
    public void removeNonMatchingReturnsOnlyNonMatching() {
        List<LogItem> nonMatchingItems = testedList.removeNonMatching(EXAMPLE_QUERY);
        for (LogItem eachItem : nonMatchingItems)
            if (eachItem.matches(EXAMPLE_QUERY))
                fail();
    }

    /**
     * Method testing {@link LogList#removeNonMatching(String)} returns all the items that do
     * not match the query.
     */
    @Test
    public void removeNonMatchingReturnsAllTheNonMatching() {
        List<LogItem> nonMatchingItems = testedList.removeNonMatching(EXAMPLE_QUERY);
        //If an item from the original list matches the query and was not returned, the test fails.
        for (LogItem eachOriginalItem : testedList)
            if (!eachOriginalItem.matches(EXAMPLE_QUERY) && !nonMatchingItems.contains(eachOriginalItem))
                fail();
    }

    /**
     * Method testing {@link LogList#removeNonMatching(String)} removed all the items it
     * returned.
     */
    @Test
    public void removeNonMatchingRemovedAllTheReturnedItems() {
        List<LogItem> nonMatchingItems = testedList.removeNonMatching(EXAMPLE_QUERY);
        for (LogItem eachItem : nonMatchingItems)
            if (testedList.contains(eachItem))
                fail();
    }

    /**
     * Method testing {@link LogList#removeNonMatching(String)} removed only the items it
     * returned.
     */
    @Test
    public void removeNonMatchingRemovedOnlyTheReturnedItems() {
        List<LogItem> testedListCopy = new ArrayList<>(testedList);
        List<LogItem> nonMatchingItems = testedList.removeNonMatching(EXAMPLE_QUERY);
        for (LogItem eachOriginalItem : testedListCopy)
            if (!nonMatchingItems.contains(eachOriginalItem) && !testedList.contains(eachOriginalItem))
                fail();
    }

    /**
     * Method testing whether {@link LogList#addMark(String)} adds marks to the items.
     */
    @Test
    public void addMarkMarksItems() {
        final String exampleQuery = "Sup";
        LogItem testItem = itemGenerator.nextLogItem(exampleQuery);
        LogItem spyItem = spy(testItem);
        testedList.add(spyItem);
        testedList.addMark(exampleQuery);
        verify(spyItem, atLeast(1)).addMark(exampleQuery);
    }

    /**
     * Method testing whether {@link LogList#resetMarks()} removes the marks from the items.
     */
    @Test
    public void resetMarksRemovesMarksFromItems() {
        final String exampleQuery = "Sup";
        LogItem testItem = itemGenerator.nextLogItem(exampleQuery);
        LogItem spyItem = spy(testItem);
        testedList.add(spyItem);
        testedList.addMark(exampleQuery);
        testedList.resetMarks();
        verify(spyItem, atLeast(1)).resetMarks();
    }
}