package ingsw.group1.findmyphone.event;

/**
 * Interface defining the behaviour of an Observable class containing some type of Event.
 *
 * @author Riccardo De Zen.
 */
public interface ObservableEventContainer<E extends Event> {
    /**
     * Method to call to register a new observer for this container.
     *
     * @param newObserver The new {@link EventObserver}.
     */
    void observe(EventObserver<E> newObserver);

    /**
     * Method to call to unregister an observer from this container.
     *
     * @param observerToForget The {@link EventObserver} to unregister.
     */
    void forget(EventObserver<E> observerToForget);

    /**
     * Method to call in order to notify the registered observers of variations in the contained
     * Events. This should be done by calling
     * {@link EventObserver#onChanged(ObservableEventContainer)}.
     */
    void notifyObservers();
}
