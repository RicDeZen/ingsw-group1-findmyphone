package ingsw.group1.findmyphone.log;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ingsw.group1.findmyphone.event.EventObserver;
import ingsw.group1.findmyphone.event.EventOrder;
import ingsw.group1.findmyphone.event.ObservableEventContainer;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.log.items.LogItem;
import ingsw.group1.findmyphone.log.items.LogItemFormatter;
import ingsw.group1.findmyphone.log.items.LogMap;

/**
 * Class meant to manage a view to a list of {@link LogItem}, loaded
 * from a database, and notify a {@link androidx.recyclerview.widget.RecyclerView.Adapter} of
 * changes in the data set.
 * Due to the fact this class handles fairly heavy operations with potentially big lists, it is
 * advisable to run its operations in Thread outside of the UI thread if the amount of data were
 * to become excessively big. This should not ever be the case inside this app.
 *
 * @author Riccardo De Zen.
 */
public class LogManager implements EventObserver<SMSLogEvent>, Iterable<LogItem> {

    public static final String DEFAULT_LOG_DATABASE = "find-my-phone-log";
    private static final String DEF_QUERY = "";

    private static LogManager activeInstance;

    /**
     * List containing all the Items, ordered in some way.
     */
    private LogMap allItems;
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

    private SMSLogEvent lastRemovedEvent;

    /**
     * If this is true the data set needs to be updated, so {@link LogManager#filter(String)} and
     * {@link LogManager#setSortingOrder(EventOrder)} should not ignore parameters that are equal
     * to the ones already set.
     */
    private boolean updateRequired = false;

    /**
     * Default constructor.
     *
     * @param targetDatabase The database this Manager should listen to.
     */
    private LogManager(@NonNull SMSLogDatabase targetDatabase,
                       @NonNull LogItemFormatter itemFormatter) {
        this.targetDatabase = targetDatabase;
        this.itemFormatter = itemFormatter;
        init();
    }

    /**
     * Only way to access the LogManager.
     *
     * @param context The Context requesting the instance.
     *                {@link Context#getApplicationContext()} is always called before use.
     * @return The only possible active instance of this class.
     */
    public static LogManager getInstance(@NonNull Context context) {
        if (activeInstance != null) return activeInstance;
        SMSLogDatabase logDatabase = SMSLogDatabase.getInstance(
                context.getApplicationContext(),
                DEFAULT_LOG_DATABASE
        );
        LogItemFormatter formatter = new LogItemFormatter(context.getApplicationContext());
        activeInstance = new LogManager(logDatabase, formatter);
        return activeInstance;
    }

    /**
     * Method initializing this instance, retrieving the data from the database and formatting it.
     */
    private void init() {
        targetDatabase.addObserver(this);
        allItems = itemFormatter.mapItems(targetDatabase.getAllEvents());
        itemsView = new LogList(allItems.getLogList());
        sortView();
    }

    /**
     * Method used to sort the current items view.
     */
    private void sortView() {
        Collections.sort(itemsView, LogItemComparatorHelper.newComparator(currentOrder));
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
     * The method removes the event that corresponds to an item from the database, caching it. If
     * undone using {@link LogManager#undoLastRemove()} the event is re-added to the database.
     *
     * @param item An item to remove.
     */
    public void removeItem(LogItem item) {
        lastRemovedEvent = allItems.get(item);
        if (lastRemovedEvent == null) return;
        targetDatabase.removeEvent(lastRemovedEvent);
    }

    /**
     * Method used to undo the last removal operation. This resets
     * {@link LogManager#lastRemovedEvent}.
     */
    public void undoLastRemove() {
        targetDatabase.addEvent(lastRemovedEvent);
        lastRemovedEvent = null;
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
        if (!updateRequired && newSortingOrder.equals(currentOrder)) return;
        currentOrder = newSortingOrder;
        itemsView = allItems.getLogList().getMatching(currentQuery);
        sortView();
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
        if (!updateRequired && actualQuery.equals(currentQuery))
            return;
        if (actualQuery.isEmpty()) {
            itemsView = allItems.getLogList();
            itemsView.resetMarks();
        } else {
            itemsView = allItems.getLogList().getMatching(actualQuery);
            itemsView.addMark(actualQuery);
        }
        currentQuery = actualQuery;
        sortView();
        notifyListener();
    }

    /**
     * Method called when the database updates.
     *
     * @param changedObject The Observable Object that got changed.
     */
    @Override
    public void onChanged(@Nullable ObservableEventContainer<SMSLogEvent> changedObject) {
        if (changedObject != null && !changedObject.equals(targetDatabase)) return;
        allItems.clear();
        allItems.putAll(itemFormatter.mapItems(targetDatabase.getAllEvents()));
        updateRequired = true;
        setSortingOrder(currentOrder);
        filter(currentQuery);
        updateRequired = false;
        if (currentListener != null)
            currentListener.notifyDataSetChanged();
    }

    /**
     * @return An Iterator for this LogManager, the same as it would be in the item view.
     */
    @NonNull
    @Override
    public Iterator<LogItem> iterator() {
        return itemsView.iterator();
    }
}
