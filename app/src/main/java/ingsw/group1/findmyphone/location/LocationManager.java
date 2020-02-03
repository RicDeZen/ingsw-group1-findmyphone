package ingsw.group1.findmyphone.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

/**
 * Manager of Location that analyze message received and sent.
 * For the composition of request and response message
 * it uses methods of {@link LocationMessageHelper}.
 *
 * @author Turcato
 * @author Giorgia Bortoletti (refactoring)
 */
public class LocationManager {
    protected final String LocationManagerTag = "LocationManagerTag";
    private final String MAPS_START_URL = "https://www.google.com/maps/search/?api=1&query=";
    //NOTE: concat latitude,longitude

    private LocationRequest locationRequest;
    private PendingIntent locationIntent;
    private Location mLastLocation;
    private FusedLocationProviderClient mFusedLocationClient;

    //---------------------------- OPERATIONS TO REQUEST A POSITION ----------------------------

    /**
     * Get a location request message composed by {@link LocationMessageHelper}
     *
     * @return a formatted message for a location request
     */
    public String getRequestLocationMessage() {
        return LocationMessageHelper.composeRequestLocation();
    }

    //---------------------------- OPERATIONS TO SEND MY POSITION ----------------------------
    /*
     * I have received a LocationRequest so containsLocationRequest is true.
     * I check my lastLocation and I composed responseStringMessage.
     */

    /**
     * @param locationStringRequest the text message received
     * @return true if the received text contains the (formatted) location Request
     */
    public boolean isLocationRequest(String locationStringRequest) {
        return LocationMessageHelper.isLocationRequest(locationStringRequest);
    }

    /**
     * Method that gets the last Location available of the device, and executes the imposed command
     * calling command.execute(foundLocation)
     *
     * @param command object of a class that implements interface Command
     */
    public void getLastLocation(Context applicationContext, final Command command) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext);
        mFusedLocationClient.flushLocations();
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(PRIORITY_HIGH_ACCURACY);

        mFusedLocationClient.getLocationAvailability().addOnSuccessListener(new OnSuccessListener<LocationAvailability>() {
            @Override
            public void onSuccess(LocationAvailability locationAvailability) {
                Log.d(LocationManagerTag, "onSuccess: locationAvailability.isLocationAvailable " + locationAvailability.isLocationAvailable());

                mFusedLocationClient.requestLocationUpdates(locationRequest, locationIntent)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Log.d(LocationManagerTag, "Update Result: " + task.getResult());
                            }
                        });

                Log.d(LocationManagerTag, "Requested updated location: ");

                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Log.d(LocationManagerTag, "Completed lastLocation");
                        Log.d(LocationManagerTag, "Task<Location> successful " + task.isSuccessful());

                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            Log.d(LocationManagerTag, "Victory!" + mLastLocation.toString());
                            command.execute(mLastLocation);
                            /*
                            mLastLocation is used directly here because once out of OnComplete
                            the Location isn't available and the variable that contains it
                            becomes null
                            */
                        } else if (!task.isSuccessful()) {
                            Log.d(LocationManagerTag, "Task<Location> not successful");
                        } else if (task.getResult() == null) {
                            Log.d(LocationManagerTag, "Task<Location> result is null");
                        }
                        Log.d(LocationManagerTag, "End of OnComplete " + mLastLocation.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(LocationManagerTag, "Task<Location>: " + e.getMessage());
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.d(LocationManagerTag, "Task<Location> getLastLocation: Canceled");
                    }
                });
            }
        })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.d(LocationManagerTag, "Task<Location>: Canceled");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LocationManagerTag, "Task<Location>: " + e.getMessage());
                    }
                });

        //The request is high priority, this instruction removes it to be more efficient
        mFusedLocationClient.removeLocationUpdates(locationIntent);
    }

    /**
     * Returns a String formatted as a message containing actual position
     *
     * @param foundLocation the found location the device needs to send back
     * @return a formatted string containing the location as <>longitude</> <>latitude</>
     */
    public String getResponseMessage(Location foundLocation) {
        return LocationMessageHelper.composeResponseLocation(foundLocation);
    }

    //---------------------------- OPERATIONS AFTER RECEIVING A LOCATION ----------------------------
    /*
     * I have received a message with isLocationResponse so I check Latitude and Longitude.
     * I want to open position in MapsUrl.
     * */

    /**
     * @param locationStringResponse string containing the received txt message
     * @return true if the received message contains a location response message
     */
    public boolean isLocationResponse(String locationStringResponse) {
        return LocationMessageHelper.isLocationResponse(locationStringResponse);
    }

    /**
     * Return latitude from a received message if it is present,
     * empty string otherwise.
     *
     * @param receivedMessage string containing the text received sy sms
     * @return the latitude from the receivedMessage, empty string if it isn't present
     */
    public String getLatitudeFrom(String receivedMessage) {
        return LocationMessageHelper.getLatitudeFrom(receivedMessage);
    }

    /**
     * Return longitude from a received message if it is present,
     * empty string otherwise.
     *
     * @param receivedMessage string containing the text received sy sms
     * @return the longitude from the receivedMessage, empty string if it isn't present
     */
    public String getLongitudeFrom(String receivedMessage) {
        return LocationMessageHelper.getLongitudeFrom(receivedMessage);
    }

    //---------------------------- OPEN MAPS ----------------------------

    /**
     * @author Turcato
     * Open the default maps application at the given Location(latitude, longitude)
     *
     * @param context Context where open maps
     * @param mapsLatitude  latitude extracted by response sms
     * @param mapsLongitude longitude extracted by response sms
     */
    public void openMapsUrl(Context context, Double mapsLatitude, Double mapsLongitude) {
        String url = MAPS_START_URL + mapsLatitude + "," + mapsLongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

}
