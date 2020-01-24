package ingsw.group1.findmyphone.location;

import android.location.Location;

/**
 * Manager that provides composition of message for alarm and location request and response
 * Message request is composed as:
 * {@link LocationMessageParser#LOCATION_REQUEST_TAG}
 * <p>
 * Message response is composed in this way:
 * {@link LocationMessageParser#LOCATION_RESPONSE_TAG}<LT>value_latitude</LT><LG>value_longitude</LG>
 *
 * @author Giorgia Bortoletti
 */
public class LocationMessageParser {

    static final String LOCATION_REQUEST_TAG = "LOCATION_REQUEST";
    static final String LOCATION_RESPONSE_TAG = "LOCATION_RESPONSE";
    static final String LONGITUDE_TAG = "<LG>", LONGITUDE_END_TAG = "</LG>";
    static final String LATITUDE_TAG = "<LT>", LATITUDE_END_TAG = "</LT>";

    /**
     * @param messageReceived the text message received
     * @return true if the received text contains the (formatted) location Request
     */
    static boolean isLocationRequest(String messageReceived) {
        return messageReceived.contains(LOCATION_REQUEST_TAG);
    }

    /**
     * @param messageReceived string containing the received txt message
     * @return true if the received message contains a location response message
     */
    static boolean isLocationResponse(String messageReceived) {
        return messageReceived.contains(LOCATION_RESPONSE_TAG);
    }

    /**
     * Compose a location request message
     *
     * @return a formatted message for a location request
     */
    static String composeRequestLocation() {
        return LOCATION_REQUEST_TAG;
    }

    /**
     * Return a String formatted as a message containing actual position
     *
     * @param foundLocation the found location the device needs to send back
     * @return a formatted string containing the location as <>longitude</> <>latitude</>
     */
    static String composeResponseLocation(Location foundLocation) {
        String locationResponseMessage = LOCATION_RESPONSE_TAG;
        locationResponseMessage += LATITUDE_TAG + foundLocation.getLatitude() + LATITUDE_END_TAG;
        locationResponseMessage += LONGITUDE_TAG + foundLocation.getLongitude() + LONGITUDE_END_TAG;
        return locationResponseMessage;
    }
}
