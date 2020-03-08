package ingsw.group1.findmyphone.event;

/**
 * Interface defining an Event that should be logged by the App.
 * - The time of the Event is defined by a {@link Long} value, ideally, the value returned by
 * {@link System#currentTimeMillis()} when the Event started.
 * - The extras should be encoded in a {@link String}.
 * - The possible types of events are defined in {@link EventType}.
 *
 * @param <C> The type of address for the associated contact type.
 * @author Riccardo De Zen.
 */
public interface LoggableEvent<C> extends
        Event<C, Long, String>,
        EnumTyped<EventType> {
}
