package ingsw.group1.findmyphone.log;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.location.GeoPosition;

/**
 * Class representing the data for an item view in the log list.
 * Compared to {@link SMSLogEvent} the data should already be formatted for display here.
 * This is a simple pojo, no logic is contained here.
 *
 * @author Riccardo De Zen.
 * @see LogItemFormatter for details on item formatting.
 */
public class LogItem implements Filterable<String>, Markable<String>, Interactable<Boolean> {

    private static int searchSpanColor = Color.BLACK;

    @NonNull
    private final SpannableString spannableAddress;
    @NonNull
    private final SpannableString spannableName;
    @NonNull
    private final String formattedTime;
    @NonNull
    private final String formattedExtra;
    @NonNull
    private final Drawable drawable;
    @NonNull
    private final Long timeInMillis;

    @Nullable
    private final GeoPosition position;

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
     * @param position         The geographical position related to this event, if any.
     */
    public LogItem(@NonNull String formattedAddress,
                   @NonNull String formattedName,
                   @NonNull String formattedTime,
                   @NonNull String formattedExtra,
                   @NonNull Drawable drawable,
                   @NonNull Long timeInMillis,
                   @Nullable GeoPosition position) {
        this.spannableAddress = new SpannableString(formattedAddress);
        this.spannableName = new SpannableString(formattedName);
        this.formattedTime = formattedTime;
        this.formattedExtra = formattedExtra;
        this.drawable = drawable;
        this.timeInMillis = timeInMillis;
        this.position = position;
    }

    /**
     * Method to change the state of the Object. A LogItem alternates one of two states:
     * "expanded" and "collapsed", interacting with it switches the state.
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
     * {@link LogItem#addMark(String)} will be already applied.
     */
    @NonNull
    public SpannableString getAddress() {
        return spannableAddress;
    }

    /**
     * Getter for the event contact's name, in spannable String form.
     *
     * @return The name of the Contact associated with this item's event. The last mark from
     * {@link LogItem#addMark(String)} will be already applied.
     */
    @NonNull
    public SpannableString getName() {
        return spannableName;
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
     * Getter for the associated position.
     *
     * @return The position associated with this event, can be {@code null} if this item is not
     * related to a location request.
     */
    @Nullable
    public GeoPosition getPosition() {
        return position;
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
        return getName().toString().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Returns whether the item's address matches the given String.
     *
     * @param query The String to match.
     * @return {@code true} if this item's address contains {@code query}. {@code false} otherwise.
     */
    private boolean addressMatches(String query) {
        return getAddress().toString().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Method to be called in order to mark the item. The name and/or address are marked by
     * coloring the section of their text where the parameter String appears. Only one mark can
     * be applied at a time, which means marks are always reset before a new one is applied. At
     * most one section is highlighted for each field.
     *
     * @param criteria The criteria based on which to mark.
     */
    @Override
    public void addMark(String criteria) {
        resetMarks();
        if (nameMatches(criteria)) {
            int startIndex = getName().toString().toLowerCase().indexOf(criteria.toLowerCase());
            spannableName.setSpan(
                    new ForegroundColorSpan(searchSpanColor),
                    startIndex,
                    startIndex + criteria.length(),
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
        if (addressMatches(criteria)) {
            int startIndex = getAddress().toString().toLowerCase().indexOf(criteria.toLowerCase());
            spannableAddress.setSpan(
                    new ForegroundColorSpan(searchSpanColor),
                    startIndex,
                    startIndex + criteria.length(),
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
    }

    /**
     * Method called to remove all the marks on the item.
     */
    @Override
    public void resetMarks() {
        Object[] addressSpans =
                spannableAddress.getSpans(0, spannableAddress.length(), Object.class);
        Object[] nameSpans =
                spannableName.getSpans(0, spannableName.length(), Object.class);
        for (Object eachSpan : addressSpans)
            spannableAddress.removeSpan(eachSpan);
        for (Object eachSpan : nameSpans)
            spannableName.removeSpan(eachSpan);
    }

    /**
     * Sets a new value for the search span color used by the {@link LogItem} class.
     *
     * @param newColor The new color to be used.
     */
    public static void setSearchSpanColor(int newColor) {
        searchSpanColor = newColor;
    }

    /**
     * Getter for the current search span color used by the {@link LogItem} class.
     *
     * @return The current value for the search span color.
     */
    public static int getSearchSpanColor() {
        return searchSpanColor;
    }
}
