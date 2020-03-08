package ingsw.group1.findmyphone;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Class dedicated to managing the permissions for the app.
 *
 * @author Riccardo De Zen.
 */
public class PermissionHelper {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 2018;
    public static final int MESSAGES_PERMISSION_REQUEST_CODE = 2019;
    public static final int BACKGROUND_LOCATION_PERMISSION_CODE = 2020;

    /**
     * Base permissions required to provide location features.
     */
    private static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    /**
     * Base permissions required to provide SMS features.
     */
    private static final String[] MESSAGES_PERMISSIONS = {
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE,
    };

    /**
     * Permission required to bind a notification listener service.
     */
    private static final String[] NOTIF_SERVICE_PERMISSION =
            {Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE};

    /**
     * Method to ensure all permissions are available.
     *
     * @param context The calling context.
     * @return {@code true} if all permissions are available, {@code false} otherwise.
     */
    public static boolean areAllPermissionsGranted(Context context) {
        return areMessagesAvailable(context) && isLocationAvailable(context);
    }

    /**
     * Method called to check the availability of messaging features.
     *
     * @param context The calling Context.
     * @return {@code true} if the messaging permissions are granted, false otherwise.
     */
    public static boolean areMessagesAvailable(Context context) {
        for (String eachPermission : MESSAGES_PERMISSIONS)
            if (PackageManager.PERMISSION_GRANTED !=
                    ContextCompat.checkSelfPermission(context, eachPermission))
                return false;
        return true;
    }

    /**
     * Method called to check the availability of location features.
     *
     * @param context The calling Context.
     * @return {@code true} if the location permissions are granted, {@code false} otherwise.
     */
    public static boolean isLocationAvailable(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !isBackgroundLocationAvailable(context))
            return false;
        for (String eachPermission : LOCATION_PERMISSIONS)
            if (PackageManager.PERMISSION_GRANTED !=
                    ContextCompat.checkSelfPermission(context, eachPermission))
                return false;
        return true;
    }

    /**
     * Method called to check the availability of the background location feature. Expects to be
     * called only on api level 29 and higher, otherwise the permission will not be recognized.
     *
     * @param context The calling Context.
     * @return {@code true} if the background location permission is available, {@code false}
     * otherwise.
     */
    @TargetApi(Build.VERSION_CODES.Q)
    private static boolean isBackgroundLocationAvailable(Context context) {
        final String BG_LOCATION_PERMISSION = Manifest.permission.ACCESS_BACKGROUND_LOCATION;
        return ContextCompat.checkSelfPermission(context, BG_LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Method called to request the permissions related to the messaging features.
     *
     * @param activity The calling Activity.
     */
    public static void requestMessagesPermissions(Activity activity) {
        ActivityCompat.requestPermissions(
                activity,
                MESSAGES_PERMISSIONS,
                MESSAGES_PERMISSION_REQUEST_CODE
        );
    }

    /**
     * Method called to request the permissions related to the location features.
     *
     * @param activity The calling Activity.
     */
    public static void requestLocationPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                    activity,
                    LOCATION_PERMISSIONS,
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        } else requestBackgroundLocationPermission(activity);
    }

    /**
     * Method called to request the permission related to the location features, including the
     * background location permission. If the api level is lower than 29, no action will be
     * performed and false will be returned.
     *
     * @param activity The calling Activity.
     */
    @TargetApi(Build.VERSION_CODES.Q)
    private static void requestBackgroundLocationPermission(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return;
        final String BG_LOCATION_PERMISSION = Manifest.permission.ACCESS_BACKGROUND_LOCATION;

        // This middle step is necessary, we can't reference the background permission in any way
        // in api lower than 29 and we can't ask for different permissions at the same time.
        int length = LOCATION_PERMISSIONS.length + 1;
        String[] allPermissions = new String[length];
        System.arraycopy(LOCATION_PERMISSIONS, 0, allPermissions, 0, length - 1);
        allPermissions[length - 1] = BG_LOCATION_PERMISSION;

        ActivityCompat.requestPermissions(
                activity,
                allPermissions,
                BACKGROUND_LOCATION_PERMISSION_CODE
        );
    }

    /**
     * Method called to request the permission related to the background location feature. If the
     * api level is lower than 22, no action will be performed and false will be returned.
     *
     * @param activity The calling Activity.
     * @return {@code true} if the request was made, {@code false} otherwise, either due to api
     * level too low or no suitable activity being found to grant it.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static boolean requestServicePermission(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1)
            return false;
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        if (intent.resolveActivity(activity.getPackageManager()) == null) return false;
        activity.startActivity(intent);
        return true;
    }
}
