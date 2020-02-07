package ingsw.group1.findmyphone.event;

/**
 * Interface defining an {@link EventDatabase} containing {@link LoggableEvent} Objects.
 *
 * @param <L>
 */
public interface LoggableEventDatabase<L extends LoggableEvent> extends EventDatabase<L> {
}
