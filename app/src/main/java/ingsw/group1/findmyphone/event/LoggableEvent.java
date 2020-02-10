package ingsw.group1.findmyphone.event;

import ingsw.group1.findmyphone.contacts.GenericContact;

/**
 * Interface defining an Event that should be logged by the App.
 * - The time of the Event is defined by a {@link Long} value, ideally, the value returned by
 * {@link System#currentTimeMillis()} when the Event started.
 * - The extras should be encoded in a {@link String}.
 * - The possible types of events are defined in {@link LogEventType}.
 *
 * @param <C> The type of Contact the implementing class wants to Log.
 * @author Riccardo De Zen.
 */
public interface LoggableEvent<C extends GenericContact> extends
        Event<C, Long, String>,
        EnumTyped<LogEventType> {
}
