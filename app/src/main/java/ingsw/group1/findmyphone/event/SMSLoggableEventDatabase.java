package ingsw.group1.findmyphone.event;

import android.content.Context;
import android.util.ArrayMap;

import androidx.annotation.NonNull;

import net.rehacktive.waspdb.WaspDb;
import net.rehacktive.waspdb.WaspFactory;
import net.rehacktive.waspdb.WaspHash;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Interface defining a {@link LoggableEventDatabase} containing {@link SMSLoggableEvent} Objects.
 * Since it works on the device's memory it is highly advisable to call it from the background.
 * An Event is its own key, the saved value is the saving time.
 *
 * @author Riccardo De Zen.
 */
public class SMSLoggableEventDatabase implements LoggableEventDatabase<SMSLoggableEvent> {

    private static final String DATABASE_NAME = "SMSLog";
    private static final String DATABASE_PASSWORD = null;

    private static Map<String, SMSLoggableEventDatabase> activeInstances = new ArrayMap<>();

    /**
     * Every instance actually accesses to a WaspHash which is more like a table.
     */
    @NonNull
    private WaspHash physicalDatabase;
    @NonNull
    private String name;

    /**
     * Only available constructor.
     *
     * @param physicalDatabase The actual {@link WaspHash} accessed by this database instance.
     * @param name             The name for this database's instance.
     */
    private SMSLoggableEventDatabase(@NonNull WaspHash physicalDatabase, @NonNull String name) {
        this.physicalDatabase = physicalDatabase;
        this.name = name;
    }

    /**
     * Only allowed way to retrieve instances of this class from outside. If the requested
     * instance does not exist, a new one is created.
     *
     * @param context The calling {@link Context}.
     * @param name    The name for this instance.
     * @return The appropriate {@link SMSLoggableEventDatabase} instance.
     */
    @NonNull
    public static SMSLoggableEventDatabase getInstance(Context context, @NonNull String name) {
        SMSLoggableEventDatabase existingInstance = activeInstances.get(name);
        if (existingInstance != null) return existingInstance;
        WaspDb allTables = WaspFactory.openOrCreateDatabase(
                context.getFilesDir().getPath(),
                DATABASE_NAME,
                DATABASE_PASSWORD
        );
        SMSLoggableEventDatabase newInstance = new SMSLoggableEventDatabase(
                allTables.openOrCreateHash(name),
                name
        );
        activeInstances.put(name, newInstance);
        return newInstance;
    }

    /**
     * Adds an {@link Event} to the database.
     *
     * @param newEvent The event to add.
     * @return {@code true} if the event has been added, {@code false} otherwise.
     */
    @Override
    public boolean addEvent(@NonNull SMSLoggableEvent newEvent) {
        return physicalDatabase.put(newEvent, System.currentTimeMillis());
    }

    /**
     * Adds a {@link Collection} of Events {@link Event} to the database.
     *
     * @param events The list of events.
     * @return a {@link Map} containing the result of the single addition operations for each event.
     */
    @Override
    public Map<SMSLoggableEvent, Boolean> addEvents(@NonNull Collection<SMSLoggableEvent> events) {
        Map<SMSLoggableEvent, Boolean> result = new ArrayMap<>();
        for (SMSLoggableEvent event : events)
            result.put(event, addEvent(event));
        return result;
    }

    /**
     * Removes an {@link Event} from the database.
     *
     * @param eventToRemove The event to remove.
     * @return {@code true} if the event was present and has been removed, {@code false} otherwise.
     */
    @Override
    public boolean removeEvent(@NonNull SMSLoggableEvent eventToRemove) {
        return physicalDatabase.remove(eventToRemove);
    }

    /**
     * Removes a {@link Collection} of Events from the database.
     *
     * @param events The list of events.
     * @return a {@link Map} containing the result of the single removal operations for each event.
     */
    @Override
    public Map<SMSLoggableEvent, Boolean> removeEvents(@NonNull Collection<SMSLoggableEvent> events) {
        Map<SMSLoggableEvent, Boolean> result = new ArrayMap<>();
        for (SMSLoggableEvent event : events)
            result.put(event, removeEvent(event));
        return result;
    }

    /**
     * Retrieves all the stored Events.
     *
     * @return A {@link List} containing all saved events.
     */
    @Override
    public List<SMSLoggableEvent> getAllEvents() {
        return physicalDatabase.getAllKeys();
    }

    /**
     * Retrieves presence of event.
     *
     * @param event The event to find.
     * @return {@code true} if the event is parsable and present, {@code false} otherwise.
     */
    @Override
    public boolean contains(@NonNull SMSLoggableEvent event) {
        return physicalDatabase.getAllKeys().contains(event);
    }

    /**
     * Method to get the number of Events in the database.
     *
     * @return The number of Events in the database.
     */
    @Override
    public int count() {
        return physicalDatabase.getAllKeys().size();
    }

    /**
     * Two instances are sure to be equal if the name is the same.
     *
     * @param otherObj The other Object being compared.
     * @return {@code true} if the two Objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null || getClass() != otherObj.getClass()) return false;
        SMSLoggableEventDatabase that = (SMSLoggableEventDatabase) otherObj;
        return name.equals(that.name);
    }

    /**
     * Automatically generated by Android Studio.
     *
     * @return The hashcode for this Object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
