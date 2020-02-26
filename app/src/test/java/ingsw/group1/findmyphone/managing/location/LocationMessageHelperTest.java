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
 * Test for {@link LocationMessageHelper}
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
        locationMessageRequest = LocationMessageHelper.LOCATION_REQUEST_TAG;
        locationMessageResponse = LocationMessageHelper.LOCATION_RESPONSE_TAG +
                LocationMessageHelper.LATITUDE_TAG + EX_LATITUDE + LocationMessageHelper.LATITUDE_END_TAG +
                LocationMessageHelper.LONGITUDE_TAG + EX_LONGITUDE + LocationMessageHelper.LONGITUDE_END_TAG;

        notLocationMessageRequest = "REQUEST_LOCATION";
        notLocationMessageResponse = LocationMessageHelper.LOCATION_RESPONSE_TAG;

        locationMock = mock(Location.class);
        when(locationMock.getLatitude()).thenReturn(EX_LATITUDE);
        when(locationMock.getLongitude()).thenReturn(EX_LONGITUDE);

    }

    //---------------------------- TESTS ----------------------------

    @Test
    public void composeRequestLocation() {
        Assert.assertEquals(locationMessageRequest, LocationMessageHelper.composeRequestLocation());
    }

    @Test
    public void composeResponseLocation() {
        Assert.assertEquals(locationMessageResponse, LocationMessageHelper.composeResponseLocation(locationMock));
    }

    @Test
    public void isLocationRequest() {
        Assert.assertTrue(LocationMessageHelper.isLocationRequest(locationMessageRequest));
        Assert.assertFalse(LocationMessageHelper.isLocationRequest(notLocationMessageRequest));
    }

    @Test
    public void isLocationResponse() {
        Assert.assertTrue(LocationMessageHelper.isLocationResponse(locationMessageResponse));
        Assert.assertFalse(LocationMessageHelper.isLocationResponse(notLocationMessageResponse));
    }

    @Test
    public void getLatitudeFrom() {
        Assert.assertEquals("" + EX_LATITUDE, LocationMessageHelper.getLatitudeFrom(locationMessageResponse));
    }

    @Test
    public void getLongitudeFrom() {
        Assert.assertEquals("" + EX_LONGITUDE, LocationMessageHelper.getLongitudeFrom(locationMessageResponse));
    }
}