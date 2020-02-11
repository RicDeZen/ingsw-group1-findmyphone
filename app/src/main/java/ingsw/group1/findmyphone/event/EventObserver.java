package ingsw.group1.findmyphone.event;

/**
 * Interface defining behaviour for a class wanting to Observe changes in an
 * ObservableEventContainer.
 *
 * @author Riccardo De Zen.
 */
public interface EventObserver<E extends Event> {
    /**
     * Method called by the Observable Object when its content is changed.
     *
     * @param changedObject The Observable Object that got changed.
     */
    void onChanged(ObservableEventContainer<E> changedObject);
}
