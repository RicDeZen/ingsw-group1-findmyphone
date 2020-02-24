package ingsw.group1.findmyphone.pending;

import ingsw.group1.findmyphone.event.Event;

/**
 * Interface defining actions a listener for the result of PendingEvents should handle.
 *
 * @param <E> The type of Events that should be listened to.
 * @param <R> The type of result for the Events.
 * @author Riccardo De Zen.
 */
public interface PendingEventListener<E extends Event, R> {
    /**
     * Method called when an Event is completed.
     *
     * @param completedEvent The Event that was completed.
     * @param result         The result of the completed Event.
     */
    void onEventCompleted(E completedEvent, R result);
}
