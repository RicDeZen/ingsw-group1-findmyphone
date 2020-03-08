package ingsw.group1.findmyphone.event;

import androidx.annotation.NonNull;

/**
 * Interface defining an Object that can be characterized by a type.
 * The interface is designed for usage with an {@code enum} or an otherwise instance-limiting class.
 *
 * @param <T> The {@link Enum} (or class) defining the possible types of the Object.
 * @author Riccardo De Zen.
 */
public interface EnumTyped<T> {
    /**
     * Method to get the event type. Should never be null, a sentinel value should be used instead.
     *
     * @return The type for this event.
     */
    @NonNull
    T getType();
}
