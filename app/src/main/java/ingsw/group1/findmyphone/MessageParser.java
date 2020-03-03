package ingsw.group1.findmyphone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import java.util.regex.Pattern;

import ingsw.group1.findmyphone.location.GeoPosition;

/**
 * Class aimed to read and write the messages commonly used in the app.
 * By default, the class acts as follows:
 * - No encryption/decryption is performed
 * - Messages are considered of a certain type if they contain the {@code .toString()} result of
 * the corresponding enum value
 * - In addition to the above, response types need to contain a valid operation result
 * immediately following the enum value, such result is a number for ring requests (a long value
 * indicating the ring duration in milliseconds) and a valid
 * {@link ingsw.group1.findmyphone.location.GeoPosition#GeoPosition(String)} for location requests.
 * - No other information is processed, the message can have anything before and after the
 * necessary information block.
 * - Messages are built containing only the content stated above, and nothing else.
 *
 * @author Riccardo De Zen
 */
public class MessageParser {

    private static final String DIGIT_REGEX = "\\d";

    /**
     * Method to compose a ring request message directed to the given peer.
     *
     * @param destination The peer to which the request is directed.
     * @return An SMSMessage containing only {@link MessageType#RING_REQUEST}{@code .toString()}.
     */
    @NonNull
    public SMSMessage getRingRequest(@NonNull SMSPeer destination) {
        return new SMSMessage(destination, MessageType.RING_REQUEST.toString());
    }

    /**
     * Method to compose a ring response message directed to the given peer.
     *
     * @param destination The peer to which the response is directed.
     * @param duration    The duration of the ring, in milliseconds. Should be higher than zero
     *                    obviously, but no checks are performed.
     * @return An SMSMessage containing only {@link MessageType#RING_RESPONSE}{@code .toString()}
     * and {@code duration} immediately after.
     */
    @NonNull
    public SMSMessage getRingResponse(@NonNull SMSPeer destination, @NonNull Long duration) {
        return new SMSMessage(destination, MessageType.RING_RESPONSE.toString() + duration);
    }

    /**
     * Method to compose a location request message directed to the given peer.
     *
     * @param destination The peer to which the request is directed.
     * @return An SMSMessage containing only {@link MessageType#LOCATION_REQUEST}{@code .toString
     * ()}.
     */
    @NonNull
    public SMSMessage getLocationRequest(@NonNull SMSPeer destination) {
        return new SMSMessage(destination, MessageType.LOCATION_REQUEST.toString());
    }

    /**
     * Method to compose a location response message directed to the given peer.
     *
     * @param destination The peer to which the response is directed.
     * @param position    The position that must be communicated to the destination peer.
     * @return An SMSMessage containing only {@link MessageType#LOCATION_REQUEST}{@code .toString
     * ()} and {@code position.toString()}.
     */
    @NonNull
    public SMSMessage getLocationResponse(
            @NonNull SMSPeer destination,
            @NonNull GeoPosition position) {
        return new SMSMessage(
                destination,
                MessageType.LOCATION_RESPONSE.toString() + position.toString()
        );
    }

    /**
     * Method to get the type of message whose criteria is matched.
     *
     * @param message Any non-null {@link SMSMessage}.
     * @return The type of message that {@code message} is. If no matching type is found,
     * {@link MessageType#UNKNOWN} is returned. For response types, there must be a valid result
     * for the operation. If the message fulfills the criteria for more than one message type,
     * the type returned is the first in the order the enum values are declared in
     * {@link MessageType}.
     */
    @NonNull
    public MessageType getMessageType(@NonNull SMSMessage message) {
        MessageType type = getFirstContainedType(message);
        // UNKNOWN types and both request types don't need other checks.
        switch (type) {
            case RING_REQUEST:
                return type;
            case RING_RESPONSE:
                return (getTimeIfRingResponse(message.getData()) != null) ?
                        type : MessageType.UNKNOWN;
            case LOCATION_REQUEST:
                return type;
            case LOCATION_RESPONSE:
                return (getPositionIfLocationResponse(message.getData()) != null) ?
                        type : MessageType.UNKNOWN;
            default:
                return MessageType.UNKNOWN;
        }
    }

    /**
     * @param message A non-null String representing some kind of message.
     * @return Any positive Long value that is found immediately following the first occurrence of
     * {@link MessageType#RING_RESPONSE}.
     * Returns {@code null} if any of the following are true:
     * - {@link MessageType#RING_RESPONSE} is not contained in the message.
     * - No numerical value is returned by {@link MessageParser#getNextLong(String, int)}.
     */
    @Nullable
    public Long getTimeIfRingResponse(@NonNull String message) {
        int index = message.indexOf(MessageType.RING_RESPONSE.toString());
        if (index < 0) return null;
        return getNextLong(message, index + MessageType.RING_RESPONSE.toString().length());
    }

    /**
     * @param message A non-null String representing some kind of message.
     * @return A valid String for the {@link GeoPosition} constructor immediately following the
     * first occurrence of {@link MessageType#LOCATION_RESPONSE}, if any is present.
     * All the possible Strings are attempted, starting from the String made out of all the
     * characters following the enum value, gradually decreasing the end index.
     * Returns {@code null} if any of the following are true:
     * - {@link MessageType#LOCATION_RESPONSE} is not contained in the message.
     * - No suitable String is found following the enum value.
     */
    @Nullable
    public GeoPosition getPositionIfLocationResponse(@NonNull String message) {
        int index = message.indexOf(MessageType.LOCATION_RESPONSE.toString());
        if (index < 0) return null;
        int startIndex = index + MessageType.LOCATION_RESPONSE.toString().length();
        int endIndex = message.length();
        while (endIndex > startIndex) {
            try {
                return new GeoPosition(message.substring(startIndex, endIndex));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                endIndex--;
            }
        }
        return null;
    }

    /**
     * Method searching for the next Long value after the given index.
     *
     * @param aString A non-null String that needs to be searched for long values.
     * @param start   The starting index.
     * @return Any positive Long value that is found immediately following the given index,
     * parsed by taking all digits found until a non-digit character is found.
     * Returns {@code null} if any of the following are true:
     * - No numerical value is contained immediately after said enum value.
     * - The number is too long to be cast into a Long value, or it is a negative number.
     * @throws StringIndexOutOfBoundsException If the given start index is negative invalid.
     */
    @Nullable
    private Long getNextLong(@NonNull String aString, int start) throws StringIndexOutOfBoundsException {
        String allFollowingDigits = aString.substring(start, getNextNonDigit(aString, start));
        try {
            return Long.parseLong(allFollowingDigits);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Method searching for the next non digit character after the given index.
     *
     * @param aString Any non-null String.
     * @param start   The starting index.
     * @return The index of the next non-digit character in the String or the length of the
     * String if no other non-digits are found.
     * @throws StringIndexOutOfBoundsException If the given start index is negative.
     */
    private int getNextNonDigit(@NonNull String aString, int start) throws StringIndexOutOfBoundsException {
        for (int i = start; i < aString.length(); i++)
            if (!Pattern.matches(DIGIT_REGEX, "" + aString.charAt(i))) return i;
        return aString.length();
    }

    /**
     * @param message Any non-null {@link SMSMessage}.
     * @return The first enum value that is found in the message, where first refers to how the
     * values are returned by {@link MessageType#values()}.
     */
    @NonNull
    private MessageType getFirstContainedType(@NonNull SMSMessage message) {
        String messageBody = message.getData();
        for (MessageType eachType : MessageType.values())
            if (messageBody.contains(eachType.toString()))
                return eachType;
        return MessageType.UNKNOWN;
    }

}
