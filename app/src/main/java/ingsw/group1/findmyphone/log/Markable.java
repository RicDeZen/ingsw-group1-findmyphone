package ingsw.group1.findmyphone.log;

/**
 * Interface defining behaviour for an Object that can be marked based on some kind of criteria.
 *
 * @param <C> The type of criteria based on which the item should mark itself.
 * @author Riccardo De Zen.
 */
public interface Markable<C> {
    /**
     * Method to be called in order to mark the item.
     *
     * @param criteria The criteria based on which to mark.
     */
    void addMark(C criteria);

    /**
     * Method to be called in order to remove the mark on the item.
     */
    void resetMarks();
}
