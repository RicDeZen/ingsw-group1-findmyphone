package ingsw.group1.findmyphone.log;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.event.LogEventType;
import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.location.GeoPosition;

/**
 * Factory class to help with formatting of LogItems.
 *
 * @author Riccardo De Zen.
 */
public class LogItemFormatter {

    private static final String DEFAULT_EXTRA = "";
    private static final int POSITION_STRING_ID = R.string.log_extra_position;
    private static final int RING_STRING_ID = R.string.log_extra_ring;

    private Resources resources;
    private Map<LogEventType, Drawable> cachedDrawables;

    public LogItemFormatter(Context context) {
        resources = context.getResources();
        cachedDrawables = LogEventType.getCachedDrawables(context);
    }

    /**
     * Method to format an SMSLogEvent. Name and Address are kept the same. Time is formatted as
     * a Date. Extra is formatted as specified in {@link LogItemFormatter#formatExtra(SMSLogEvent)}.
     *
     * @param eventToFormat The event to format. Unknown type events can not be parsed through
     *                      this method.
     * @return The formatted {@link LogItem}. Returns {@code null} if the event is of an unknown
     * type or if the drawable icon could not be loaded.
     */
    @Nullable
    public LogItem formatItem(SMSLogEvent eventToFormat) {
        if (eventToFormat.getType() == LogEventType.UNKNOWN)
            return null;
        Drawable appropriateDrawable = cachedDrawables.get(eventToFormat.getType());
        if (appropriateDrawable == null)
            return null;

        String
                formattedName = eventToFormat.getContact().getName(),
                formattedAddress = eventToFormat.getContact().getAddress(),
                formattedTime = formatDate(eventToFormat),
                formattedExtra = formatExtra(eventToFormat);

        boolean shouldExpand = !formattedExtra.isEmpty();

        return new LogItem(
                formattedAddress,
                formattedName,
                formattedTime,
                formattedExtra,
                appropriateDrawable,
                eventToFormat.getTime(),
                shouldExpand
        );
    }

    /**
     * Method used to format a collection of events. Items that return {@code null} when
     * formatted are excluded.
     *
     * @return A {@link List} of {@link LogItem}, ready to be displayed.
     */
    @NonNull
    public List<LogItem> formatItems(@NonNull Collection<SMSLogEvent> eventsToFormat) {
        List<LogItem> result = new ArrayList<>();
        for (SMSLogEvent event : eventsToFormat) {
            LogItem formattedItem = formatItem(event);
            if (formattedItem != null) result.add(formattedItem);
        }
        return result;
    }

    /**
     * @param event The event containing the date to format.
     * @return The formatted Date according to the local default format.
     */
    private String formatDate(SMSLogEvent event) {
        return SimpleDateFormat.getDateInstance().format(new Date(event.getTime()));
    }

    /**
     * Method to format the extras of events. The extras of an unknown event are ignored.
     *
     * @param event The event to format.
     * @return The formatted extras. If {@code event.getExtra()} returns {@code null} then
     * {@link LogItemFormatter#DEFAULT_EXTRA} is returned.
     */
    private String formatExtra(SMSLogEvent event) {
        LogEventType eventType = event.getType();
        String extraToFormat = event.getExtra();
        if (extraToFormat == null)
            return DEFAULT_EXTRA;
        if (eventType == LogEventType.LOCATION_REQUEST_SENT ||
                eventType == LogEventType.LOCATION_REQUEST_RECEIVED) {
            //Extra must contain position
            GeoPosition position = new GeoPosition(extraToFormat);
            return String.format(
                    resources.getString(POSITION_STRING_ID),
                    position.getLatitude(),
                    position.getLongitude()
            );
        }
        //Extra must contain time in milliseconds
        long timeSeconds = Long.parseLong(extraToFormat) / 1000;
        String formattedTime = (timeSeconds / 60) + " m " + (timeSeconds % 60) + " s";
        return String.format(
                resources.getString(RING_STRING_ID),
                formattedTime
        );
    }
}