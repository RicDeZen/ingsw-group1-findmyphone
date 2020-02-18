package ingsw.group1.findmyphone.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ingsw.group1.findmyphone.event.EventObserver;
import ingsw.group1.findmyphone.event.EventOrder;
import ingsw.group1.findmyphone.event.ObservableEventContainer;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.event.SMSLogEvent;

/**
 * Class meant to manage a view to a list of {@link ingsw.group1.findmyphone.log.LogItem}, loaded
 * from a database, and notify a {@link androidx.recyclerview.widget.RecyclerView.Adapter} of
 * changes in the data set.
 * Due to the fact this class handles fairly heavy operations with potentially big lists, it is
 * advisable to run its operations in Thread outside of the UI thread if the amount of data were
 * to become excessively big. This should not ever be the case inside this app.
 *
 * @author Riccardo De Zen.
 */
public class LogManager implements EventObserver<SMSLogEvent> {

    private static final String DEF_QUERY = "";

    /**
     * List containing all the Items, ordered in some way.
     */
    private LogList allItems;
    /**
     * List containing all or part of the items in a certain order following calls to
     * {@link LogManager#setSortingOrder(EventOrder)} and {@link LogManager#filter(String)}.
     */
    private LogList itemsView;

    private RecyclerView.Adapter currentListener;
    private SMSLogDatabase targetDatabase;
    private LogItemFormatter itemFormatter;
    @Nullable
    private String currentQuery = DEF_QUERY;
    private EventOrder currentOrder = EventOrder.NEWEST_TO_OLDEST;
    private boolean isSearching = false;

    /**
     * Default constructor.
     *
     * @param targetDatabase The database this Manager should listen to.
     */
    public LogManager(@NonNull SMSLogDatabase targetDatabase,
                      @NonNull LogItemFormatter itemFormatter) {
        this.targetDatabase = targetDatabase;
        this.itemFormatter = itemFormatter;
        init();
    }

    /**
     * Method initializing this instance, retrieving the data from the database and formatting it.
     */
    private void init() {
        allItems = new LogList(itemFormatter.formatItems(targetDatabase.getAllEvents()));
        Collections.sort(allItems, LogItemComparatorHelper.newComparator(currentOrder));
        itemsView = new LogList(allItems);
    }

    /**
     * Method to return the item that should be displayed at the {@code position} position in a
     * {@link android.widget.ListView}/{@link RecyclerView}.
     *
     * @param position The position of the item.
     * @return The item that should be shown at the given position.
     */
    public LogItem getItem(int position) {
        return itemsView.get(position);
    }

    /**
     * Method to get the displayable item count.
     *
     * @return The number of items in {@link LogManager#itemsView}.
     */
    public int count() {
        return itemsView.size();
    }

    /**
     * Method to get a shallow copy of the items view.
     *
     * @return A shallow copy of {@link LogManager#itemsView}.
     */
    public List<LogItem> getItems() {
        return new ArrayList<>(itemsView);
    }

    /**
     * Method to set a new listener for this Manager.
     *
     * @param newListener The new Adapter that should get notified of changes.
     */
    public void setListener(@NonNull RecyclerView.Adapter newListener) {
        currentListener = newListener;
    }

    /**
     * Method resetting the current listener to {@code null}.
     */
    public void removeListener() {
        currentListener = null;
    }

    /**
     * Method to notify the current listener.
     */
    private void notifyListener() {
        if (currentListener != null)
            currentListener.notifyDataSetChanged();
    }

    /**
     * This method sorts the items and notifies the listener.
     *
     * @param newSortingOrder The sorting order to use.
     */
    public void setSortingOrder(EventOrder newSortingOrder) {
        if (newSortingOrder.equals(currentOrder)) return;
        Collections.sort(allItems, LogItemComparatorHelper.newComparator(newSortingOrder));
        itemsView = allItems.getMatching(currentQuery);
        notifyListener();
    }

    /**
     * This method filters the items and notifies the listener with the filtered list.
     *
     * @param newQuery The String to use in order to filter. {@code null} or empty {@code String} to
     *                 reset the list. The String is trimmed and turned into lower case.
     * @see String#isEmpty()
     */
    public void filter(String newQuery) {
        String sentinelQuery = (newQuery == null) ? DEF_QUERY : newQuery;
        String actualQuery = sentinelQuery.toLowerCase().trim();
        if (actualQuery.equals(currentQuery))
            return;
        if (actualQuery.isEmpty()) {
            isSearching = false;
            itemsView = allItems;
            itemsView.resetMarks();
        } else {
            isSearching = true;
            itemsView = allItems.getMatching(actualQuery);
            itemsView.addMark(actualQuery);
        }
        currentQuery = actualQuery;
        notifyListener();
    }

    /**
     * Method to return whether a search is being performed (current query is not null or empty).
     *
     * @return {@code true} if a search is being performed and {@code false} otherwise.
     */
    public boolean isSearching() {
        return isSearching;
    }

    /**
     * Method called when the database updates.
     *
     * @param changedObject The Observable Object that got changed.
     */
    @Override
    public void onChanged(ObservableEventContainer<SMSLogEvent> changedObject) {
        if (!(changedObject.equals(targetDatabase))) return;
        if (currentListener != null)
            currentListener.notifyDataSetChanged();
    }
}