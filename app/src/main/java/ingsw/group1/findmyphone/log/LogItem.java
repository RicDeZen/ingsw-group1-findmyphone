package ingsw.group1.findmyphone.log;

import androidx.annotation.NonNull;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.event.SMSLoggableEvent;

/**
 * Package-private class representing the data for an item view in the log list.
 */
public class LogItem {

    private final SMSLoggableEvent event;
    private final int color;
    private boolean expanded = false;

    /**
     * Only available constructor.
     *
     * @param event The event this item should represent.
     */
    public LogItem(@NonNull SMSLoggableEvent event) {
        this.event = event;
        this.color = (event.getExtra() != null) ? R.color.baseTextColor : R.color.failedEventColor;
    }

    /**
     * Getter for the event.
     *
     * @return {@link LogItem#event}.
     */
    @NonNull
    public SMSLoggableEvent getEvent() {
        return event;
    }

    /**
     * Setter for {@code expanded}.
     *
     * @param expanded The new value for expanded.
     */
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    /**
     * Getter for {@code expanded}. Returns whether the associated view should be expanded or not.
     *
     * @return {@code expanded}.
     */
    public boolean isExpanded() {
        return expanded;
    }
}
