package ingsw.group1.findmyphone.log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class extending {@link ArrayList} to specifically contain {@link Filterable} items.
 *
 * @param <Q> The type of query the items can be matched on.
 * @param <F> The type of Object contained.
 */
public class FilterableList<Q, F extends Filterable<Q>> extends ArrayList<F> {
    /**
     * Default constructor.
     */
    public FilterableList() {
        super();
    }

    /**
     * Collection constructor
     */
    public FilterableList(Collection<F> collection) {
        super(collection);
    }

    /**
     * Method to apply a certain query to this list. The items that do not match the query are
     * removed from the list.
     *
     * @param query The query to match.
     * @return A {@link FilterableList} containing the items that did not match the query and
     * have therefore been removed.
     */
    public FilterableList<Q, F> removeNonMatching(Q query) {
        FilterableList<Q, F> copyOfThisList = new FilterableList<>(this);
        FilterableList<Q, F> removedItems = new FilterableList<>();
        for (F eachItem : copyOfThisList) {
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
     * @return A {@link FilterableList} containing the items that matched the query.
     */
    public FilterableList<Q, F> getMatching(Q query) {
        FilterableList<Q, F> matchingItems = new FilterableList<>();
        for (F eachItem : this)
            if (eachItem.matches(query))
                matchingItems.add(eachItem);
        return matchingItems;
    }
}
