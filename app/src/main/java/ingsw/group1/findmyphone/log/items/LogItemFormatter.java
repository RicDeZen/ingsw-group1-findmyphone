package ingsw.group1.findmyphone.log.items;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.contacts.SMSContactManager;
import ingsw.group1.findmyphone.event.EventType;
import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.location.GeoPosition;

/**
 * Factory class to help with formatting of LogItems.
 * This class hides the formatting logic and provides the LogItems with UI displayable data. If a
 * name or extra are not available for the event, an empty String is returned.
 *
 * @author Riccardo De Zen.
 */
public class LogItemFormatter {

    private static final String DEFAULT_NAME = "";
    private static final String DEFAULT_EXTRA = "";
    private static final int MY_POSITION_STRING_ID = R.string.my_position_extra;
    private static final int CONTACT_POSITION_STRING_ID = R.string.contact_position_extra;
    private static final int MY_RING_STRING_ID = R.string.my_ring_extra;
    private static final int CONTACT_RING_STRING_ID = R.string.contact_ring_extra;
    private static final int DATE_FORMAT = SimpleDateFormat.MEDIUM;
    private static final int TIME_FORMAT = SimpleDateFormat.SHORT;

    private SMSContactManager contacts;
    private Resources resources;
    private Map<EventType, Drawable> cachedDrawables;

    public LogItemFormatter(Context context) {
        contacts = SMSContactManager.getInstance(context);
        resources = context.getResources();
        cachedDrawables = EventType.getCachedDrawables(context);
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
        if (eventToFormat.getType() == EventType.UNKNOWN)
            return null;
        Drawable appropriateDrawable = cachedDrawables.get(eventToFormat.getType());
        if (appropriateDrawable == null)
            return null;

        String
                formattedName = formatName(eventToFormat),
                formattedAddress = formatAddress(eventToFormat),
                formattedTime = formatDate(eventToFormat),
                formattedExtra = formatExtra(eventToFormat);

        GeoPosition positionIfAny = formatPosition(eventToFormat);

        return new LogItem(
                formattedAddress,
                formattedName,
                formattedTime,
                formattedExtra,
                appropriateDrawable,
                eventToFormat.getTime(),
                positionIfAny
        );
    }

    /**
     * Method used to format a collection of events. Items that return {@code null} when
     * formatted are excluded from the resulting list.
     *
     * @param eventsToFormat A Collection of events to format.
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
     * @param eventsToMap A Collection of events to format and map with the resulting item as key.
     * @return A {@link LogMap} containing a Map with the LogItems as keys and the events as values.
     */
    @NonNull
    public LogMap mapItems(@NonNull Collection<SMSLogEvent> eventsToMap) {
        LogMap result = new LogMap();
        for (SMSLogEvent event : eventsToMap) {
            LogItem formattedItem = formatItem(event);
            if (formattedItem != null) result.put(formattedItem, event);
        }
        return result;
    }

    /**
     * Method to get an appropriate name for the contact associated to the event. The name is
     * looked up in the {@link SMSContactManager} class, if no matching contact is found a
     * default value (an empty String) is returned.
     *
     * @param event The event for which the name must be formatted.
     * @return A String containing the name for the Contact if it was present in the contacts
     * database, or an empty String otherwise.
     */
    @NonNull
    private String formatName(@NonNull SMSLogEvent event) {
        SMSContact contact = contacts.getContactForAddress(event.getAddress());
        if (contact != null) return contact.getName();
        return DEFAULT_NAME;
    }

    /**
     * Method to format the address associated to an event.
     *
     * @param event The SMSLogEvent containing the address to format.
     * @return The formatted address, which corresponds to the address contained in {@code
     * logEvent}.
     */
    @NonNull
    private String formatAddress(@NonNull SMSLogEvent event) {
        return event.getAddress();
    }

    /**
     * @param event The event containing the date to format.
     * @return The formatted Date according to the local default format.
     */
    private String formatDate(@NonNull SMSLogEvent event) {
        return DateUtils.formatSameDayTime(
                event.getTime(),
                System.currentTimeMillis(),
                DATE_FORMAT,
                TIME_FORMAT
        ).toString();
    }

    /**
     * Method to format the extras of events. The extras of an unknown event are ignored.
     *
     * @param event The event to format.
     * @return The formatted extras. If {@code event.getExtra()} returns {@code null} then
     * {@link LogItemFormatter#DEFAULT_EXTRA} is returned.
     */
    private String formatExtra(@NonNull SMSLogEvent event) {
        EventType eventType = event.getType();
        String extraToFormat = event.getExtra();
        if (extraToFormat == null)
            return DEFAULT_EXTRA;
        if (eventType.isLocation()) {
            //Extra must contain position because of how SMSLogEvent is structured.
            GeoPosition position = new GeoPosition(extraToFormat);
            return String.format(
                    resources.getString((eventType.isOutgoing()) ?
                            CONTACT_POSITION_STRING_ID : MY_POSITION_STRING_ID
                    ),
                    position.getLatitude(),
                    position.getLongitude()
            );
        }
        //Extra must contain time in milliseconds because of how SMSLogEvent is structured.
        long timeSeconds = Long.parseLong(extraToFormat) / 1000;
        String formattedTime = (timeSeconds / 60) + " m " + (timeSeconds % 60) + " s";
        return String.format(
                resources.getString((eventType.isOutgoing()) ?
                        CONTACT_RING_STRING_ID : MY_RING_STRING_ID
                ),
                formattedTime
        );
    }

    /**
     * Method to retrieve the position in the extra information of an event, if there's any.
     *
     * @param event The event that may contain the position String.
     * @return The {@link GeoPosition} found in the extra, or null if no position was found.
     */
    @Nullable
    private GeoPosition formatPosition(@NonNull SMSLogEvent event) {
        if (!event.getType().isLocation() || event.getExtra() == null) return null;
        //Extra for a location type event must contain a position.
        return new GeoPosition(event.getExtra());
    }
}
