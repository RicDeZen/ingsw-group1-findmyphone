package ingsw.group1.findmyphone.log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ingsw.group1.findmyphone.random.RandomLogItemGenerator;

import static org.junit.Assert.fail;

public class FilterableListTest {

    private static final String EXAMPLE_QUERY = "A";
    private static final RandomLogItemGenerator GENERATOR = new RandomLogItemGenerator();
    private static final int LIST_LENGTH = 15;

    private FilterableList<String, LogItem> testedList = new FilterableList<>();

    /**
     * Method filling the list with some random meaningless LogItems.
     */
    @Before
    public void fillList() {
        int i = 0;
        while (i++ < LIST_LENGTH - 1)
            testedList.add(GENERATOR.nextLogItem());
        //Just to ensure at least a matching item is in the list.
        testedList.add(GENERATOR.nextLogItem(EXAMPLE_QUERY));
    }

    /**
     * Method testing {@link FilterableList#getMatching(Object)} only returns matching items.
     */
    @Test
    public void getMatchingReturnsOnlyMatching() {
        List<LogItem> matchingItems = testedList.getMatching(EXAMPLE_QUERY);
        for (LogItem eachItem : matchingItems)
            if (!eachItem.matches(EXAMPLE_QUERY))
                fail();
    }

    /**
     * Method testing {@link FilterableList#getMatching(Object)} returns all the matching items.
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
     * Method testing {@link FilterableList#removeNonMatching(Object)} returns only items that do
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
     * Method testing {@link FilterableList#removeNonMatching(Object)} returns all the items that do
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
     * Method testing {@link FilterableList#removeNonMatching(Object)} removed all the items it
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
     * Method testing {@link FilterableList#removeNonMatching(Object)} removed only the items it
     * returned.
     */
    @Test
    public void removeNonMatchingRemovedOnlyTheReturnedItems() {
        List<LogItem> testedListCopy = new ArrayList<>(testedList);
        List<LogItem> nonMatchingItems = testedList.removeNonMatching(EXAMPLE_QUERY);
        for (LogItem eachOriginalItem : testedListCopy)
            if (eachOriginalItem.matches(EXAMPLE_QUERY) && !testedList.contains(eachOriginalItem))
                fail();
    }
}
