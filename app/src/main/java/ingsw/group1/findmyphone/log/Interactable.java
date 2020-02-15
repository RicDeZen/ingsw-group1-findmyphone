package ingsw.group1.findmyphone.log;

/**
 * Interface defining an Object that can be interacted with, alternating on one of two states.
 *
 * @param <T> The enum or class containing the two states.
 * @author Riccardo De Zen.
 */
public interface Interactable<T> {
    /**
     * Method to change the state of the Object.
     */
    void interact();

    /**
     * Method to retrieve the state of the Object.
     *
     * @return The current state of the Object.
     */
    T getState();
}
