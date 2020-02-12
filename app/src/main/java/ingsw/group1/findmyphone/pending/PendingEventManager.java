package ingsw.group1.findmyphone.pending;

import androidx.annotation.NonNull;

import ingsw.group1.findmyphone.event.Event;

/**
 * Interface defining the behaviour of a class that manages the state of
 * {@link ingsw.group1.findmyphone.event.Event} that are still reaching completion.
 *
 * @param <E> Type of managed Event.
 * @param <X> Type of result for start and stop.
 * @author Riccardo De Zen.
 */
public interface PendingEventManager<E extends Event, X> {
    /**
     * Sets a new listener for the result of the events.
     *
     * @param newListener The new listener for this instance.
     */
    void setResultListener(@NonNull PendingEventListener newListener);

    /**
     * Sets the minimum time that should pass before a started Event can be considered failed
     * (usually meaning unanswered).
     *
     * @param timeoutInMilliseconds The timeout value expressed in milliseconds.
     */
    void setTimeout(@NonNull Long timeoutInMilliseconds);

    /**
     * Method to enqueue a new {@link Event}.
     *
     * @return The result of the operation.
     */
    @NonNull
    X startEvent(@NonNull E newEvent);

    /**
     * Method to remove an {@link Event} from the queue.
     *
     * @param eventToStop The Event that should be removed.
     * @return The result of the operation.
     */
    @NonNull
    X stopEvent(@NonNull E eventToStop);
}