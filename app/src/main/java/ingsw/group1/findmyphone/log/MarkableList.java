package ingsw.group1.findmyphone.log;

import java.util.List;

/**
 * Interface defining the behaviour of a {@link List} designed to specifically contain
 * {@link Markable} items.
 *
 * @param <Q> The type of criteria used to mark the items.
 * @param <M> The type of Object contained.
 */
public interface MarkableList<Q, M extends Markable<Q>> extends List<M>, Markable<Q> {
}
