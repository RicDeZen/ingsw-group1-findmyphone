package ingsw.group1.findmyphone;

/**
 * Enum defining the various types of messages that travel through the app.
 * The actual formatting for the messages is described in the {@link MessageParser} class.
 *
 * @author Riccardo De Zen
 */
public enum MessageType {
    /**
     * A message that contains a ring request.
     */
    RING_REQUEST,
    /**
     * A message that contains a ring response, with the result of the operation.
     */
    RING_RESPONSE,
    /**
     * A message that contains a location request.
     */
    LOCATION_REQUEST,
    /**
     * A message that contains a location response.
     */
    LOCATION_RESPONSE,
    /**
     * A default value for messages that do not respect the formatting of the 4 above types.
     */
    UNKNOWN;

    /**
     * @return {@code true} if this enum value refers to a request operation, {@code false}
     * otherwise.
     */
    public boolean isRequest() {
        return equals(RING_REQUEST) || equals(LOCATION_REQUEST);
    }

    /**
     * @return {@code true} if this enum value refers to a response operation, {@code false}
     * otherwise.
     */
    public boolean isResponse() {
        return equals(RING_RESPONSE) || equals(LOCATION_RESPONSE);
    }
}
