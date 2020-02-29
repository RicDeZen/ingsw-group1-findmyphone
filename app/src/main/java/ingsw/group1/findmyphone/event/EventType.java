package ingsw.group1.findmyphone.event;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

import ingsw.group1.findmyphone.R;

/**
 * Enum defining the possible kind of Events the app is supposed to keep track of.
 * The "outgoing" and "incoming" events both refer to the perspective of the device on which the
 * event is stored, so a RING_REQUEST_SENT means this device asked another one to trigger a ring,
 * and then may or may not have received an answer about the termination of said ring, while a
 * RING_REQUEST_RECEIVED means this device received a request to trigger a ring, and may or may
 * not have fulfilled it and answered back.
 *
 * @author Riccardo De Zen
 */
public enum EventType {
    /**
     * An outgoing Ring request.
     */
    RING_REQUEST_SENT(R.drawable.ring_sent),
    /**
     * An incoming Ring request.
     */
    RING_REQUEST_RECEIVED(R.drawable.ring_received),
    /**
     * An outgoing Location request.
     */
    LOCATION_REQUEST_SENT(R.drawable.location_sent),
    /**
     * An incoming Location request.
     */
    LOCATION_REQUEST_RECEIVED(R.drawable.location_received),
    /**
     * Value to be used when the Event type can't be determined. Events having this type should
     * NOT be formatted and displayed.
     */
    UNKNOWN(R.drawable.search_background);

    /**
     * The id of the appropriate Drawable for this event type.
     */
    private int id;

    /**
     * Private enum constructor.
     *
     * @param id This item's id.
     */
    EventType(int id) {
        this.id = id;
    }

    /**
     * Getter for the appropriate Drawable's id for a certain {@link EventType}.
     *
     * @return {@link EventType#id}
     */
    public int getDrawableId() {
        return id;
    }

    /**
     * @return {@code true} if this enum value is a location type event, {@code false} otherwise.
     */
    public boolean isLocation() {
        return equals(LOCATION_REQUEST_SENT) || equals(LOCATION_REQUEST_RECEIVED);
    }

    /**
     * @return {@code true} if this enum value is a ring type event, {@code false} otherwise.
     */
    public boolean isRing() {
        return equals(RING_REQUEST_SENT) || equals(RING_REQUEST_RECEIVED);
    }

    /**
     * @return {@code true} if this enum value is an incoming event, {@code false} otherwise.
     */
    public boolean isIncoming() {
        return equals(RING_REQUEST_RECEIVED) || equals(LOCATION_REQUEST_RECEIVED);
    }

    /**
     * @return {@code true} if this enum value is an outgoing event, {@code false} otherwise.
     */
    public boolean isOutgoing() {
        return equals(RING_REQUEST_SENT) || equals(LOCATION_REQUEST_SENT);
    }

    /**
     * Method used to get the drawable associated to a certain enum value.
     *
     * @param eventType The type of event.
     * @return The appropriate {@link Drawable} for the given event type.
     */
    @Nullable
    public static Drawable getDrawableForType(Context context, EventType eventType) {
        return context.getResources().getDrawable(eventType.getDrawableId(), null);
    }

    /**
     * Method used to retrieve a Map for all the Drawables.
     *
     * @param context The calling {@link Context}.
     * @return A Map that maps each type of event to its Drawable reference. null value for
     * {@link EventType#UNKNOWN} is not included.
     */
    @NonNull
    public static Map<EventType, Drawable> getCachedDrawables(Context context) {
        Map<EventType, Drawable> result = new ArrayMap<>();
        for (EventType eventType : values()) {
            if (eventType != UNKNOWN)
                result.put(eventType, getDrawableForType(context, eventType));
        }
        return result;
    }
}
