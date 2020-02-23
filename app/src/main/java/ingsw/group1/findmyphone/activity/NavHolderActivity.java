package ingsw.group1.findmyphone.activity;

import android.os.Bundle;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.Map;

import ingsw.group1.findmyphone.PermissionHelper;
import ingsw.group1.findmyphone.PermissionInfoDialog;
import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.fragment.HomeFragment;

/**
 * Activity class used to contain a fragment that can be replaced. Also handles asking for
 * permissions and displaying information dialogs. Can be considered the main Activity for the app.
 *
 * @author Riccardo De Zen.
 */
public class NavHolderActivity extends FragmentActivity implements PermissionInfoDialog.PermissionsDialogListener {

    private static final String CURRENT_FRAGMENT_TAG = "CURRENT_FRAGMENT";
    private static final String INFO_DIALOG_TAG = "Permissions Info";

    /**
     * Map containing the running fragments, with their tag as their key.
     */
    private Map<String, Fragment> activeFragments = new ArrayMap<>();

    private int askedForLocation = 0;
    private int askedForMessages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_holder);
        if (PermissionHelper.areAllPermissionsGranted(this))
            startup();
        else
            requestPermissions();
    }

    /**
     * Method called to replace the currently displayed fragment.
     *
     * @param newFragment The new Fragment to display.
     */
    public void replaceFragment(@NonNull Fragment newFragment, @NonNull String tag) {
        //TODO a String tag for the fragment should be passed to allow building of back stack
        getSupportFragmentManager().beginTransaction().replace(
                R.id.home_root_layout,
                newFragment,
                CURRENT_FRAGMENT_TAG
        ).commit();
    }

    /**
     * Method called when starting the main screen of the app.
     */
    private void startup() {
        activeFragments.clear();
        replaceFragment(new HomeFragment(), HomeFragment.DEFAULT_TAG);
    }

    /**
     * Method called when some permissions are granted or declined.
     *
     * @param requestCode  The request code provided when asking for the permissions.
     * @param permissions  The permissions that were requested.
     * @param grantResults The results of the request for each permission.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (PermissionHelper.areAllPermissionsGranted(this))
            startup();
        else decidePermissionAction();
    }

    /**
     * @author Turcato, De Zen.
     * Requests Android permissions if not granted
     */
    private void requestPermissions() {
        if (!PermissionHelper.areMessagesAvailable(this)) {
            PermissionHelper.requestMessagesPermissions(this);
            askedForMessages++;
        } else if (!PermissionHelper.isLocationAvailable(this)) {
            PermissionHelper.requestLocationPermissions(this);
            askedForLocation++;
        }
    }

    /**
     * Method to judge what the best action to perform is based on the current state of the
     * permissions. This method should be started only if all permissions are not granted.
     * The Dialogs are shown with priority for the messages dialog.
     */
    private void decidePermissionAction() {
        if (!PermissionHelper.areMessagesAvailable(this) && askedForMessages > 0)
            showInfoDialog(PermissionInfoDialog.MESSAGES);
        else if (!PermissionHelper.isLocationAvailable(this) && askedForLocation > 0)
            showInfoDialog(PermissionInfoDialog.LOCATION);
        else
            //The permissions are not granted, but not all of them were asked for at least once.
            requestPermissions();
    }

    /**
     * Method called to show an Info Dialog of the given type.
     *
     * @param type The type of Dialog to show, see {@link PermissionInfoDialog} for info on the
     *             types.
     */
    private void showInfoDialog(int type) {
        DialogFragment infoDialog = new PermissionInfoDialog(type);
        infoDialog.show(getSupportFragmentManager(), INFO_DIALOG_TAG);
    }

    /**
     * When a positive callback from the info dialog is received, the app is started, but will
     * have limited features.
     *
     * @param dialog The dialog on which the positive button was clicked.
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        startup();
    }

    /**
     * When a negative callback from the info dialog is received, the app asks again for the
     * appropriate permissions.
     *
     * @param dialog The dialog on which the negative button was clicked.
     */
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        requestPermissions();
    }
}
