package ingsw.group1.findmyphone.event;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface defining behaviour for a class able to store Events into the memory.
 *
 * @param <E> Event type.
 * @author Riccardo De Zen. Heavily based on an interface made by Luca Crema, mostly due to
 * similar requirements of the two contexts.
 */
public interface EventDatabase<E extends Event> {
    /**
     * Adds an {@link Event} to the database.
     *
     * @param newEvent The event to add.
     * @return {@code true} if the event has been added, {@code false} otherwise.
     */
    boolean addEvent(@NonNull final E newEvent);

    /**
     * Adds a {@link Collection} of Events {@link Event} to the database.
     *
     * @param events The list of events.
     * @return a {@link Map} containing the result of the single addition operations for each event.
     */
    Map<E, Boolean> addEvents(@NonNull final Collection<E> events);

    /**
     * Removes an {@link Event} from the database.
     *
     * @param eventToRemove The event to remove.
     * @return {@code true} if the event was present and has been removed, {@code false} otherwise.
     */
    boolean removeEvent(@NonNull final E eventToRemove);

    /**
     * Removes a {@link Collection} of Events from the database.
     *
     * @param events The list of events.
     * @return a {@link Map} containing the result of the single removal operations for each event.
     */
    Map<E, Boolean> removeEvents(@NonNull final Collection<E> events);

    /**
     * Retrieves all the stored Events.
     *
     * @return A {@link List} containing all saved events.
     */
    List<E> getAllEvents();

    /**
     * Retrieves presence of event.
     *
     * @param event The event to find.
     * @return {@code true} if the event is parsable and present, {@code false} otherwise.
     */
    boolean contains(@NonNull final E event);

    /**
     * Method to get the number of Events in the database.
     *
     * @return The number of Events in the database.
     */
    int count();

    /**
     * Method to clear the database of all its contents.
     */
    void clear();
}
