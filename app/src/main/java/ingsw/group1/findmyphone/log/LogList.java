package ingsw.group1.findmyphone.log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class extending {@link ArrayList} to specifically contain {@link LogItem} items.
 *
 * @author Riccardo De Zen.
 */
public class LogList extends ArrayList<LogItem> implements
        FilterableList<String, LogItem>,
        MarkableList<String, LogItem> {
    /**
     * Default constructor.
     */
    public LogList() {
        super();
    }

    /**
     * Collection constructor
     */
    public LogList(Collection<LogItem> collection) {
        super(collection);
    }

    /**
     * Method to apply a certain query to this list. The items that do not match the query are
     * removed from the list.
     *
     * @param query The query to match.
     * @return A {@link LogList} containing the items that did not match the query and
     * have therefore been removed.
     */
    @Override
    public LogList removeNonMatching(String query) {
        LogList copyOfThisList = new LogList(this);
        LogList removedItems = new LogList();
        for (LogItem eachItem : copyOfThisList) {
            if (!eachItem.matches(query)) {
                remove(eachItem);
                removedItems.add(eachItem);
            }
        }
        return removedItems;
    }

    /**
     * Method to get the elements of the list that match the given query, without modifying the
     * list itself.
     *
     * @param query The query to match.
     * @return A {@link LogList} containing the items that matched the query.
     */
    @Override
    public LogList getMatching(String query) {
        LogList matchingItems = new LogList();
        for (LogItem eachItem : this)
            if (eachItem.matches(query))
                matchingItems.add(eachItem);
        return matchingItems;
    }

    /**
     * Method to be called in order to mark the Object. For a {@link LogList} this means marking
     * every one of its items.
     *
     * @param criteria The criteria based on which to mark.
     */
    @Override
    public void addMark(String criteria) {
        for (LogItem eachItem : this)
            eachItem.addMark(criteria);
    }

    /**
     * Method to remove the marks from this list's items.
     */
    @Override
    public void resetMarks() {
        for (LogItem eachItem : this)
            eachItem.resetMarks();
    }
}
