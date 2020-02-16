package ingsw.group1.findmyphone.log;

import androidx.annotation.NonNull;
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
 * advisable to run its operations in Thread outside of the UI thread.
 *
 * @author Riccardo De Zen.
 */
public class LogManager implements EventObserver<SMSLogEvent> {
    /**
     * List containing all the Items, ordered in some way.
     */
    private FilterableList<String, LogItem> allItems;
    /**
     * List containing all or part of the items in a certain order following calls to
     * {@link LogManager#setSortingOrder(EventOrder)} and {@link LogManager#filter(String)}.
     */
    private FilterableList<String, LogItem> itemsView;

    private RecyclerView.Adapter currentListener;
    private SMSLogDatabase targetDatabase;
    private LogItemFormatter itemFormatter;
    private String currentQuery = "";
    private EventOrder currentOrder = EventOrder.NEWEST_TO_OLDEST;

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
        allItems = new FilterableList<>(itemFormatter.formatItems(targetDatabase.getAllEvents()));
        Collections.sort(allItems, LogItemComparatorFactory.newComparator(currentOrder));
        itemsView = new FilterableList<>(allItems);
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
    public List<LogItem> getAll() {
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
        if (newSortingOrder == currentOrder) return;
        Collections.sort(allItems, LogItemComparatorFactory.newComparator(newSortingOrder));
        itemsView = allItems.getMatching(currentQuery);
        notifyListener();
    }

    /**
     * This method filters the items and notifies the listener with the filtered list.
     *
     * @param newQuery The String to use in order to filter. {@code null} or empty {@code String} to
     *                 reset the list.
     * @see String#isEmpty()
     */
    public void filter(String newQuery) {
        if (newQuery != null && newQuery.equals(currentQuery))
            return;
        if (newQuery == null || newQuery.isEmpty())
            itemsView = allItems;
        else
            itemsView = allItems.getMatching(newQuery);
        currentQuery = newQuery;
        notifyListener();
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
