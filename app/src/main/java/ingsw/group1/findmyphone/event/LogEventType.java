package ingsw.group1.findmyphone.event;

/**
 * Enum defining the possible kind of Events the Log is supposed to keep track of.
 *
 * @author Riccardo De Zen
 */
public enum LogEventType {
    /**
     * An outgoing Ring request.
     */
    RING_REQUEST_SENT,
    /**
     * An incoming Ring request.
     */
    RING_REQUEST_RECEIVED,
    /**
     * An outgoing Location request.
     */
    LOCATION_REQUEST_SENT,
    /**
     * An incoming Location request.
     */
    LOCATION_REQUEST_RECEIVED,
    /**
     * Value to be used when the Event type can't be determined.
     */
    UNKNOWN;
}
