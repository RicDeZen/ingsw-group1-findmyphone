package ingsw.group1.findmyphone.event;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Interface defining information about an app event to be saved to be logged later.
 *
 * @param <C> The type of address associated to this event.
 * @param <T> The type of data for the event time.
 * @param <E> The type of data for the extra information.
 * @author Riccardo De Zen
 */
public interface Event<C, T, E> {
    /**
     * Method to get the contact associated with this {@link Event}.
     *
     * @return The contact associated with this {@link Event}.
     */
    @NonNull
    C getAddress();

    /**
     * Method to get the time when this {@link Event} happened.
     *
     * @return The time when this {@link Event} happened.
     */
    @NonNull
    T getTime();

    /**
     * Method to get the extra information associated with this {@link Event}, if any.
     *
     * @return The extra information associated with this {@link Event}, may return {@code
     * null} if no information is available.
     */
    @Nullable
    E getExtra();
}
