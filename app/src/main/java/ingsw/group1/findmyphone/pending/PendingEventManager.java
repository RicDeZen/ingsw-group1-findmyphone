package ingsw.group1.findmyphone.pending;

import androidx.annotation.NonNull;

import ingsw.group1.findmyphone.contacts.GenericContact;
import ingsw.group1.findmyphone.event.Event;

/**
 * Interface defining the behaviour of a class that manages the state of
 * {@link ingsw.group1.findmyphone.event.Event} that are still reaching completion.
 *
 * @param <E> Type of managed Event.
 * @param <> Type of address for the Event's contact.
 * @param <X> Type of Extra for the Event type.
 * @param <> Type of result for event start and stop operations.
 * @author Riccardo De Zen.
 */
public interface PendingEventManager<C extends GenericContact, E extends Event, X> {
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
     * //TODO
     * Method to
     */
    void onEventResult();

    /**
     * Method to remove an {@link Event} from the queue.
     *
     * @param eventToStop The Event that should be removed.
     * @return The result of the operation.
     */
    @NonNull
    X stopEvent(@NonNull E eventToStop);
}