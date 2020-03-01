package ingsw.group1.findmyphone.log;

import java.util.List;

/**
 * Interface defining the behaviour of a {@link List} designed to specifically contain
 * {@link Markable} items. The List itself implements {@link Markable}.
 *
 * @param <C> The type of criteria used to mark the items.
 * @param <M> The type of Object contained.
 */
public interface MarkableList<C, M extends Markable<C>> extends List<M>, Markable<C> {
    /**
     * Method to be called in order to mark the list, this translates to marking all of the
     * items, unless otherwise specified.
     *
     * @param criteria The criteria based on which to mark.
     */
    @Override
    void addMark(C criteria);

    /**
     * Method to be called in order to remove the mark on the list, this translates to removing
     * the marks from all the items, unless otherwise specified.
     */
    @Override
    void resetMarks();
}
