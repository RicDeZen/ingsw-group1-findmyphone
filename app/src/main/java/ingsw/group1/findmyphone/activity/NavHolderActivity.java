package ingsw.group1.findmyphone.activity;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.eis.smslibrary.SMSManager;

import ingsw.group1.findmyphone.PermissionHelper;
import ingsw.group1.findmyphone.PermissionInfoDialog;
import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.ServiceManager;

/**
 * Activity class used to contain a fragment that can be replaced. Also handles asking for
 * permissions and displaying information dialogs. Can be considered the main Activity for the app.
 *
 * @author Riccardo De Zen.
 */
public class NavHolderActivity extends AppCompatActivity implements PermissionInfoDialog.PermissionsDialogListener {

    private static final String INFO_DIALOG_TAG = "permissions-info";

    private int askedForLocation = 0;
    private int askedForMessages = 0;

    private AppBarConfiguration toolbarConfiguration;

    /**
     * Instantiates useful app resources:
     * - Checks permissions
     * - Sets up navigation
     * - Ensures appropriate message listener is set up
     * - Ensures notification channel is registered if needed
     *
     * @param savedInstanceState Any saved state from previous runs.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_holder);
        if (!PermissionHelper.areAllPermissionsGranted(this))
            requestPermissions();

        // Toolbar setup ---------------------------------------------------------------------------
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        toolbarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController);

        // Ensure proper message response is set ---------------------------------------------------
        SMSManager.getInstance().setReceivedListener(ServiceManager.class, getApplicationContext());

        // Ensure notification channel exists ------------------------------------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createNotificationChannel();
    }

    /**
     * Method used to navigate to a certain destination based on the given destination id.
     *
     * @param destination The destination id to navigate to.
     */
    public void navigate(int destination) {
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(destination);
    }

    /**
     * Method that creates a Notification channel. Must be called only on Api level >=
     * {@link Build.VERSION_CODES#O}.
     */
    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        String channelName = getString(R.string.notification_channel_name);
        String channelDescription = getString(R.string.notification_channel_description);
        String channelId = getString(R.string.notification_channel_id);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel = new NotificationChannel(
                channelId,
                channelName,
                importance
        );
        channel.setDescription(channelDescription);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null)
            notificationManager.createNotificationChannel(channel);
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
        if (!PermissionHelper.areAllPermissionsGranted(this))
            decidePermissionAction();
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
        //TODO set user accepted reduced features.
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Method called when an item is selected from the Toolbar's menu.
     *
     * @param item The clicked item.
     * @return {@code true} if the event was handled, {@code false} otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    /**
     * Method called when the up navigation button is pressed.
     *
     * @return true if the event was handled, false otherwise.
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, toolbarConfiguration)
                || super.onSupportNavigateUp();
    }


}
