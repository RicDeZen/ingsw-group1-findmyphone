package ingsw.group1.findmyphone.location;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for {@link LocationMessageParser}
 *
 * @author Giorgia Bortoletti
 */

@RunWith(MockitoJUnitRunner.class)
public class LocationMessageParserTest {

    private final double EX_LATITUDE = 110110110;
    private final double EX_LONGITUDE = 151515151;

    private String locationMessageRequest, locationMessageResponse;
    private String notLocationMessageRequest, notLocationMessageResponse;
    private Location locationMock;

    @Before
    public void createMessages(){
        locationMessageRequest = LocationMessageParser.LOCATION_REQUEST_TAG;
        locationMessageResponse = LocationMessageParser.LOCATION_RESPONSE_TAG +
                LocationMessageParser.LATITUDE_TAG + EX_LATITUDE + LocationMessageParser.LATITUDE_END_TAG +
                LocationMessageParser.LONGITUDE_TAG + EX_LONGITUDE + LocationMessageParser.LONGITUDE_END_TAG;

        notLocationMessageRequest = "REQUEST_LOCATION";
        notLocationMessageResponse = LocationMessageParser.LOCATION_RESPONSE_TAG;

        locationMock = mock(Location.class);
        when(locationMock.getLatitude()).thenReturn(EX_LATITUDE);
        when(locationMock.getLongitude()).thenReturn(EX_LONGITUDE);

    }

    //---------------------------- TESTS ----------------------------

    @Test
    public void composeRequestLocation() {
        assertEquals(locationMessageRequest, LocationMessageParser.composeRequestLocation());
    }

    @Test
    public void composeResponseLocation() {
        assertEquals(locationMessageResponse, LocationMessageParser.composeResponseLocation(locationMock));
    }

    @Test
    public void isLocationRequest() {
        assertTrue(LocationMessageParser.isLocationRequest(locationMessageRequest));
        assertFalse(LocationMessageParser.isLocationRequest(notLocationMessageRequest));
    }

    @Test
    public void isLocationResponse() {
        assertTrue(LocationMessageParser.isLocationResponse(locationMessageResponse));
        assertFalse(LocationMessageParser.isLocationResponse(notLocationMessageResponse));
    }

    @Test
    public void getLatitudeFrom() {
        assertEquals(""+EX_LATITUDE, LocationMessageParser.getLatitudeFrom(locationMessageResponse));
    }

    @Test
    public void getLongitudeFrom() {
        assertEquals(""+EX_LONGITUDE, LocationMessageParser.getLongitudeFrom(locationMessageResponse));
    }
}