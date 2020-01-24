package ingsw.group1.findmyphone.location;

import android.location.Location;

/**
 * Manager that provides composition of message for alarm and location request and response
 * Message request is composed as:
 * LOCATION_REQUEST
 * Message response is composed in this way:
 * LOCATION_RESPONSE<LT>value_latitude</LT><LG>value_longitude</LG>
 *
 * @author Giorgia Bortoletti
 */
public class LocationMessageParser {

    static final String locationMessageRequest = "LOCATION_REQUEST";
    static final String locationMessageResponse = "LOCATION_RESPONSE";
    static final String longitudeTag = "<LG>", longitudeTagEnd = "</LG>";
    static final String latitudeTag = "<LT>", latitudeTagEnd = "</LT>";

    /**
     * @param messageReceived the text message received
     * @return true if the received text contains the (formatted) location Request
     */
    static boolean isLocationRequest(String messageReceived) {
        return messageReceived.contains(locationMessageRequest);
    }

    /**
     * @param messageReceived string containing the received txt message
     * @return true if the received message contains a location response message
     */
    static boolean isLocationResponse(String messageReceived) {
        return messageReceived.contains(locationMessageResponse);
    }

    /**
     * Compose a location request message
     *
     * @return a formatted message for a location request
     */
    static String composeRequestLocation() {
        return locationMessageRequest;
    }

    /**
     * Return a String formatted as a message containing actual position
     *
     * @param foundLocation the found location the device needs to send back
     * @return a formatted string containing the location as <>longitude</> <>latitude</>
     */
    static String composeResponseLocation(Location foundLocation) {
        String locationResponseMessage = locationMessageResponse;
        locationResponseMessage += latitudeTag + foundLocation.getLatitude() + latitudeTagEnd;
        locationResponseMessage += longitudeTag + foundLocation.getLongitude() + longitudeTagEnd;
        return locationResponseMessage;
    }
}
