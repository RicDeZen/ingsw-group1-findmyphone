package ingsw.group1.findmyphone.log.items;

import androidx.annotation.NonNull;

import ingsw.group1.findmyphone.location.GeoPosition;

/**
 * Interface that defines a callback for when a position needs to be opened in Google Maps.
 *
 * @author Riccardo De Zen.
 */
public interface MapLinkListener {
    /**
     * Method called when the position is forwarded to Google Maps.
     *
     * @param position The position that needs to be opened.
     */
    void onLinkOpened(@NonNull GeoPosition position);
}
