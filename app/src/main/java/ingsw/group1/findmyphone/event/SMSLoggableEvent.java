package ingsw.group1.findmyphone.event;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SMSLoggableEvent extends SMSLogEvent {
    /**
     * Type of event.
     */
    @NonNull
    private EventType eventType;

    //Contact address and name are split apart in order to allow serialization.
    /**
     * Contact address related to this event.
     */
    @NonNull
    private String contactAddress;

    /**
     * Time of this event.
     */
    @NonNull
    private Long startTime;

    /**
     *
     */
    @Nullable
    private String extra;

    /**
     * No parameter constructor. Creates an empty event with unknown type, needed for
     * deserialization from disk.
     */
    public SMSLoggableEvent() {
        this.eventType = EventType.UNKNOWN;
        this.contactAddress = "";
        this.startTime = 0L;
        this.extra = null;
    }

    /**
     * Default constructor.
     *
     * @param eventType The type of event for this instance.
     * @param startTime The time at which this event started.
     * @param extra     The extra info about this event.
     * @throws IllegalArgumentException If the extra info returns false for
     *                                  {@link SMSLogEvent#isValidExtra(EventType, String)}.
     */
    public SMSLoggableEvent(
            @NonNull EventType eventType,
            @NonNull String contactAddress,
            @NonNull Long startTime,
            @Nullable String extra) {
        this.eventType = eventType;
        this.contactAddress = contactAddress;
        this.startTime = startTime;
        this.extra = extra;
    }

    /**
     * Two {@link SMSLoggableEvent} are equal when all its parameters except
     * {@link SMSLoggableEvent#extra} are equal.
     *
     * @param otherObj The object to compare.
     * @return {@code true} if this object is equal to {@code otherObj} and {@code false} otherwise.
     */
    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null || getClass() != otherObj.getClass()) return false;
        SMSLoggableEvent that = (SMSLoggableEvent) otherObj;
        return eventType == that.eventType &&
                contactAddress.equals(that.contactAddress);
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
}
