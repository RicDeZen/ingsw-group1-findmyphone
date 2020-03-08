package ingsw.group1.findmyphone.event;

import androidx.annotation.Nullable;

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
     * @param changedObject The Observable Object that got changed. Use {@code null} to notify
     *                      generically, not requiring an actual change to have happened.
     */
    void onChanged(@Nullable ObservableEventContainer<E> changedObject);
}
