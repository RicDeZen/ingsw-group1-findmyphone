package ingsw.group1.findmyphone.event;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

import ingsw.group1.findmyphone.R;

/**
 * Enum defining the possible kind of Events the Log is supposed to keep track of.
 *
 * @author Riccardo De Zen
 */
public enum LogEventType {
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
    //TODO icon for unknown type
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
    LogEventType(int id) {
        this.id = id;
    }

    /**
     * Getter for the resource id
     *
     * @return {@link LogEventType#id}
     */
    public int getDrawableId() {
        return id;
    }

    /**
     * Method used to get the drawable associated to a certain enum value.
     *
     * @param eventType The type of event.
     * @return The appropriate {@link Drawable} for the given event type.
     */
    @Nullable
    public static Drawable getDrawableForType(Context context, LogEventType eventType) {
        return context.getResources().getDrawable(eventType.getDrawableId(), null);
    }

    /**
     * Method used to retrieve a Map for all the Drawables.
     *
     * @param context The calling {@link Context}.
     * @return A Map that maps each type of event to its Drawable reference. null value for
     * {@link LogEventType#UNKNOWN} is not included.
     */
    @NonNull
    public static Map<LogEventType, Drawable> getCachedDrawables(Context context) {
        Map<LogEventType, Drawable> result = new ArrayMap<>();
        for (LogEventType eventType : values()) {
            if (eventType != UNKNOWN)
                result.put(eventType, getDrawableForType(context, eventType));
        }
        return result;
    }
}