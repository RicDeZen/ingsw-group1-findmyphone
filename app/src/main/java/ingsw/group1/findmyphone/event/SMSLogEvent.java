package ingsw.group1.findmyphone.event;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import ingsw.group1.findmyphone.location.GeoPosition;

/**
 * Class defining a {@link LoggableEvent} that uses {@link String} addresses.
 *
 * @author Riccardo De Zen.
 */
public class SMSLogEvent implements LoggableEvent<String> {

    /**
     * Key used when putting the result of a pending {@link SMSLogEvent} into a Bundle.
     *
     * @see ingsw.group1.findmyphone.pending.PendingSMSEventListener for more details.
     */
    public static final String RESULT_KEY = "sms-log-event-result";
    /**
     * Message for the error thrown on bad extra.
     */
    private static final String EXTRA_ERROR =
            "The provided String extra was not suitable for the provided type";
    /**
     * Message for the error thrown on negative time.
     */
    private static final String TIME_ERROR =
            "The provided time was lower than zero, must be >= 0.";

    /**
     * Type of event.
     */
    @NonNull
    private EventType eventType;

    /**
     * Address related to this event, it is relevant to note that this is expected to be a valid
     * phone number, and is treated as such.
     */
    @NonNull
    private String contactAddress;

    /**
     * Time of this event.
     */
    @NonNull
    private Long startTime;

    /**
     * Extras of this event. Should be:
     * - {@code null} if the Event represents a failed operation, suitable for any event type.
     * - A valid {@link String} for
     * {@link ingsw.group1.findmyphone.location.GeoPosition#GeoPosition(String)} if
     * {@link SMSLogEvent#eventType} is either {@link EventType#LOCATION_REQUEST_SENT} or
     * {@link EventType#LOCATION_REQUEST_RECEIVED}.
     * - A valid {@link String} for {@link Long#parseLong(String)} if
     * {@link SMSLogEvent#eventType} is either {@link EventType#RING_REQUEST_SENT} or
     * {@link EventType#RING_REQUEST_RECEIVED}, such {@code Long} should be a positive number.
     */
    @Nullable
    private String extra;

    /**
     * No parameter constructor. Creates an empty event with unknown type, needed for
     * deserialization from disk.
     */
    public SMSLogEvent() {
        this.eventType = EventType.UNKNOWN;
        this.contactAddress = "";
        this.startTime = 0L;
        this.extra = null;
    }

    /**
     * Default constructor.
     *
     * @param eventType      The type of event for this instance.
     * @param contactAddress The address for this event, must be a valid phone number, and is
     *                       always treated as that, no checks are performed inside this class.
     * @param startTime      The time at which the event started. Must be higher than or equal to 0.
     * @param extra          The extra info about this event.
     * @throws IllegalArgumentException If the extra info returns false for
     *                                  {@link SMSLogEvent#isValidExtra(EventType, String)} or if
     *                                  {@code startTime} was lower than 0.
     */
    public SMSLogEvent(
            @NonNull EventType eventType,
            @NonNull String contactAddress,
            @NonNull Long startTime,
            @Nullable String extra) {
        this.eventType = eventType;
        this.contactAddress = contactAddress;
        if (startTime < 0)
            throw new IllegalArgumentException(TIME_ERROR);
        this.startTime = startTime;
        if (!isValidExtra(eventType, extra))
            throw new IllegalArgumentException(EXTRA_ERROR);
        this.extra = extra;
    }

    /**
     * Method to get the event type.
     *
     * @return The type for this event.
     */
    @NonNull
    @Override
    public EventType getType() {
        return eventType;
    }

    /**
     * Method to get the address associated with this {@link Event}.
     *
     * @return The contact associated with this {@link Event}.
     */
    @NonNull
    @Override
    public String getAddress() {
        return contactAddress;
    }

    /**
     * Method to get the time when this {@link Event} happened.
     *
     * @return The time when this {@link Event} happened.
     */
    @NonNull
    @Override
    public Long getTime() {
        return startTime;
    }

    /**
     * Method to get the extra information associated with this {@link Event}, if any.
     *
     * @return The extra information associated with this {@link Event}, may return {@code
     * null} if no information is available.
     */
    @Nullable
    @Override
    public String getExtra() {
        return extra;
    }

    /**
     * Two {@link SMSLogEvent} are equal when all its parameters except
     * {@link SMSLogEvent#extra} are equal.
     *
     * @param otherObj The object to compare.
     * @return {@code true} if this object is equal to {@code otherObj} and {@code false} otherwise.
     */
    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null || getClass() != otherObj.getClass()) return false;
        SMSLogEvent that = (SMSLogEvent) otherObj;
        return eventType == that.eventType &&
                startTime.equals(that.startTime) &&
                contactAddress.equals(that.contactAddress);
    }

    /**
     * Auto-generated by Android Studio.
     *
     * @return An hashcode for this Object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(eventType, contactAddress, startTime);
    }

    /**
     * Method to detect a certain extra is suitable for a certain type of event.
     *
     * @param eventType The type of event.
     * @param extra     The new extra.
     * @return {@code true} if the extra is suitable, {@code false} if it isn't.
     * @see SMSLogEvent#extra for suitability criteria.
     */
    public static boolean isValidExtra(@NonNull EventType eventType, String extra) {
        if (extra == null || eventType == EventType.UNKNOWN) return true;
        //If the event is related to locations extra should be a valid GeoPosition.
        if (eventType.isLocation()) {
            try {
                new GeoPosition(extra);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                return false;
            }
            return true;
        }
        //If the event is related to a ringing operation then extra should contain a Long.
        try {
            return Long.parseLong(extra) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
