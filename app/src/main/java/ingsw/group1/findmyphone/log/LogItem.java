package ingsw.group1.findmyphone.log;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;

import ingsw.group1.findmyphone.event.SMSLogEvent;

/**
 * Class representing the data for an item view in the log list.
 * Compared to {@link SMSLogEvent} the data should already be formatted for display here.
 * This is a simple pojo, no logic is contained here.
 *
 * @author Riccardo De Zen.
 * @see LogItemFormatter for details on item formatting.
 */
public class LogItem implements Filterable<String>, Markable<String>, Interactable<Boolean> {

    private static final int SEARCH_SPAN_COLOR = Color.CYAN;

    @NonNull
    private final SpannableString spannableAddress;
    @NonNull
    private final SpannableString spannableName;
    @NonNull
    private final String formattedAddress;
    @NonNull
    private final String formattedName;
    @NonNull
    private final String formattedTime;
    @NonNull
    private final String formattedExtra;
    @NonNull
    private final Drawable drawable;
    @NonNull
    private final Long timeInMillis;
    /**
     * Whether this item should be allowed to expand or not.
     */
    private final boolean shouldExpand;
    private boolean expanded = false;

    /**
     * Default constructor, no tests are performed on the given parameters.
     *
     * @param formattedAddress The address for this LogItem
     * @param formattedName    The name for this LogItem
     * @param formattedTime    The time for this LogItem
     * @param formattedExtra   The extra info for this LogItem
     * @param drawable         The drawable for this LogItem
     * @param timeInMillis     The time of the event's start in milliseconds, used when ordering.
     * @param shouldExpand     Whether this item is supposed to expand or not
     */
    public LogItem(@NonNull String formattedAddress,
                   @NonNull String formattedName,
                   @NonNull String formattedTime,
                   @NonNull String formattedExtra,
                   @NonNull Drawable drawable,
                   @NonNull Long timeInMillis,
                   boolean shouldExpand) {
        this.spannableAddress = new SpannableString(formattedAddress);
        this.spannableName = new SpannableString(formattedName);
        this.formattedAddress = formattedAddress;
        this.formattedName = formattedName;
        this.formattedTime = formattedTime;
        this.formattedExtra = formattedExtra;
        this.drawable = drawable;
        this.timeInMillis = timeInMillis;
        this.shouldExpand = shouldExpand;
    }

    /**
     * Method to change the state of the Object.
     */
    @Override
    public void interact() {
        expanded = !expanded;
    }

    /**
     * Method to retrieve the state of the Object.
     *
     * @return The current state of the Object. {@code true} if the item is expanded, {@code
     * false} otherwise.
     */
    @Override
    public Boolean getState() {
        return expanded;
    }

    /**
     * Getter for the event contact's address, in spannable String form.
     *
     * @return The address of the Contact associated with this item's event. The last mark from
     * {@link LogItem#mark(String)} will be already applied.
     */
    @NonNull
    public SpannableString getSpannableAddress() {
        return spannableAddress;
    }

    /**
     * Getter for the event contact's name, in spannable String form.
     *
     * @return The name of the Contact associated with this item's event. The last mark from
     * {@link LogItem#mark(String)} will be already applied.
     */
    @NonNull
    public SpannableString getSpannableName() {
        return spannableName;
    }

    /**
     * Getter for the event contact's address.
     *
     * @return The address of the Contact associated with this item's event.
     */
    @NonNull
    public String getAddress() {
        return formattedAddress;
    }

    /**
     * Getter for the event contact's name.
     *
     * @return The name of the Contact associated with this item's event.
     */
    @NonNull
    public String getName() {
        return formattedName;
    }

    /**
     * Getter for the event time, formatted appropriately.
     *
     * @return The address of the Contact associated with this item's event.
     */
    @NonNull
    public String getTime() {
        return formattedTime;
    }

    /**
     * Getter for the event time, formatted appropriately.
     *
     * @return The address of the Contact associated with this item's event.
     */
    @NonNull
    public String getExtra() {
        return formattedExtra;
    }

    /**
     * Getter for the appropriate Drawable.
     *
     * @return The {@link Drawable} for this item's icon.
     */
    @NonNull
    public Drawable getDrawable() {
        return drawable;
    }

    /**
     * Getter for the event time in milliseconds.
     *
     * @return The time at which this event started in milliseconds.
     */
    @NonNull
    public Long getTimeInMillis() {
        return timeInMillis;
    }

    /**
     * Getter for {@code shouldExpand}. Returns whether the associated view should be expanded or
     * not.
     *
     * @return {@code shouldExpand}.
     */
    public boolean shouldExpand() {
        return shouldExpand;
    }

    /**
     * A LogItem matches a certain String query if its name contains it. Check is performed with
     * lower case.
     *
     * @param query The String to match.
     * @return {@code true} if this item's name contains {@code query}. {@code false} otherwise.
     */
    @Override
    public boolean matches(String query) {
        return nameMatches(query) || addressMatches(query);
    }

    /**
     * Returns whether the item's name matches the given String.
     *
     * @param query The String to match.
     * @return {@code true} if this item's name contains {@code query}. {@code false} otherwise.
     */
    private boolean nameMatches(String query) {
        return getName().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Returns whether the item's address matches the given String.
     *
     * @param query The String to match.
     * @return {@code true} if this item's address contains {@code query}. {@code false} otherwise.
     */
    private boolean addressMatches(String query) {
        return getAddress().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Method to be called in order to mark the item. The name and/or address are marked by
     * coloring the section of their text where the parameter String appears. At most one section
     * is highlighted for each field.
     *
     * @param criteria The criteria based on which to mark.
     */
    @Override
    public void mark(String criteria) {
        if (nameMatches(criteria)) {
            int startIndex = formattedName.indexOf(criteria);
            spannableName.setSpan(
                    new ForegroundColorSpan(SEARCH_SPAN_COLOR),
                    startIndex,
                    startIndex + criteria.length(),
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
        if (addressMatches(criteria)) {
            int startIndex = formattedAddress.indexOf(criteria);
            spannableAddress.setSpan(
                    new ForegroundColorSpan(SEARCH_SPAN_COLOR),
                    startIndex,
                    startIndex + criteria.length(),
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
    }
}
