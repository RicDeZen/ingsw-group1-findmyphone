package ingsw.group1.findmyphone.managing.location;

import android.location.Location;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for {@link LocationMessageFormatter}
 *
 * @author Giorgia Bortoletti
 */

@RunWith(MockitoJUnitRunner.class)
public class LocationMessageHelperTest {

    private final double EX_LATITUDE = 110110110;
    private final double EX_LONGITUDE = 151515151;

    private String locationMessageRequest;
    private String locationMessageResponse;
    private String notLocationMessageRequest;
    private String notLocationMessageResponse;
    private Location locationMock;

    @Before
    public void createMessages() {
        locationMessageRequest = LocationMessageFormatter.LOCATION_REQUEST_TAG;
        locationMessageResponse = LocationMessageFormatter.LOCATION_RESPONSE_TAG +
                LocationMessageFormatter.LATITUDE_TAG + EX_LATITUDE + LocationMessageFormatter.LATITUDE_END_TAG +
                LocationMessageFormatter.LONGITUDE_TAG + EX_LONGITUDE + LocationMessageFormatter.LONGITUDE_END_TAG;

        notLocationMessageRequest = "REQUEST_LOCATION";
        notLocationMessageResponse = LocationMessageFormatter.LOCATION_RESPONSE_TAG;

        locationMock = mock(Location.class);
        when(locationMock.getLatitude()).thenReturn(EX_LATITUDE);
        when(locationMock.getLongitude()).thenReturn(EX_LONGITUDE);

    }

    //---------------------------- TESTS ----------------------------

    @Test
    public void composeRequestLocation() {
        Assert.assertEquals(locationMessageRequest, LocationMessageFormatter.composeRequestLocation());
    }

    @Test
    public void composeResponseLocation() {
        Assert.assertEquals(locationMessageResponse, LocationMessageFormatter.composeResponseLocation(locationMock));
    }

    @Test
    public void isLocationRequest() {
        Assert.assertTrue(LocationMessageFormatter.isLocationRequest(locationMessageRequest));
        Assert.assertFalse(LocationMessageFormatter.isLocationRequest(notLocationMessageRequest));
    }

    @Test
    public void isLocationResponse() {
        Assert.assertTrue(LocationMessageFormatter.isLocationResponse(locationMessageResponse));
        Assert.assertFalse(LocationMessageFormatter.isLocationResponse(notLocationMessageResponse));
    }

    @Test
    public void getLatitudeFrom() {
        Assert.assertEquals("" + EX_LATITUDE, LocationMessageFormatter.getLatitudeFrom(locationMessageResponse));
    }

    @Test
    public void getLongitudeFrom() {
        Assert.assertEquals("" + EX_LONGITUDE, LocationMessageFormatter.getLongitudeFrom(locationMessageResponse));
    }
}