package ingsw.group1.findmyphone.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import ingsw.group1.findmyphone.R;

public class NavHolderActivity extends FragmentActivity {

    private static final String[] permissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE
    };

    private final int APP_PERMISSION_REQUEST_CODE = 20182019;

    private static final String CURRENT_FRAGMENT_TAG = "CURRENT_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_holder);
        requestPermissions();
    }

    /***
     * @author Turcato
     * Requests Android permissions if not granted
     */
    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) +
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)
                != PackageManager.PERMISSION_GRANTED)

            ActivityCompat.requestPermissions(this, permissions, APP_PERMISSION_REQUEST_CODE);
        // Requesting background location only on api >= 29
        if (Build.VERSION.SDK_INT >= 29) {
            String[] permissionsApi29 = {Manifest.permission.ACCESS_BACKGROUND_LOCATION};
            if (ContextCompat.checkSelfPermission(
                    this,
                    permissionsApi29[0]) !=
                    PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                        this,
                        permissionsApi29,
                        APP_PERMISSION_REQUEST_CODE
                );
            }
        }
    }

    /**
     * Method called to replace the currently displayed fragment.
     *
     * @param newFragment The new Fragment to display.
     */
    public void replaceFragment(Fragment newFragment) {
        //TODO a tag for the fragment should be passed to allow building of back stack
        getSupportFragmentManager().beginTransaction().replace(
                R.id.home_root_layout,
                newFragment,
                CURRENT_FRAGMENT_TAG
        ).commit();
    }
}
