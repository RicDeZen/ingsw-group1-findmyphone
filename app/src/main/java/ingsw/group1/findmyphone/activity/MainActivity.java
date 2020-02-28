package ingsw.group1.findmyphone.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.Manager;
import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.ServiceManager;
import ingsw.group1.findmyphone.contacts.SMSContact;


/**
 * @author Pardeep Kumar
 * @author Turcato
 */
public class MainActivity extends AppCompatActivity {

    private static final String[] permissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE
    };
    private final int APP_PERMISSION_REQUEST_CODE = 1;

    private static SMSContact contactSelected = null;

    private EditText txtPhoneNumber;
    private ImageButton sendAlarmRequestButton;
    private ImageButton sendLocationRequestButton;

    private Manager manager;
    private SMSPeer smsPeer;

    /***
     * @param savedInstanceState system parameters.
     * @author Turcato
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton viewContacts;

        txtPhoneNumber = findViewById(R.id.phoneNumber);

        sendAlarmRequestButton = findViewById(R.id.sendAlarmRequestButton);
        sendLocationRequestButton = findViewById(R.id.sendLocationRequestButton);
        viewContacts = findViewById(R.id.view_contact_list);

        //main menu in the toolbar
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        manager = new Manager(getApplicationContext());
        manager.setReceiveListener(ServiceManager.class);

        sendLocationRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsPeer = new SMSPeer(txtPhoneNumber.getText().toString());
                manager.sendLocationRequest(smsPeer);
            }
        });

        sendAlarmRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsPeer = new SMSPeer(txtPhoneNumber.getText().toString());
                manager.sendAlarmRequest(smsPeer);
            }
        });

        viewContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContactListActivity.class));
            }
        });

    }

    //---------------------------- STATIC METHODS TO SET selected contact FROM SMSContactRecyclerAdapter ----------

    /**
     * Return {@link SMSContact} selected contact whose address is shown in this view.
     */
    public static SMSContact getContactSelected(){
        return contactSelected;
    }

    /**
     * Set contact selected from contacts list.
     *
     * @param newContactSelected    {@link SMSContact} contact selected from contacts list.
     */
    public static void setContactSelected(SMSContact newContactSelected){
        contactSelected = newContactSelected;
    }

    /**
     * This method is invoked after an onBack of another activity.
     * This is used to see if the phone number is been selected from contact list
     * and the number is updated.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(contactSelected != null)
            txtPhoneNumber.setText(contactSelected.getAddress());
        else
            txtPhoneNumber.setText("");
    }

    @Override
    protected void onStart() {
        requestPermissions();
        super.onStart();

    }

    /**
     * Requests Android permissions if not granted.
     *
     * @author Turcato
     */
    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) +
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) +
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)
                != PackageManager.PERMISSION_GRANTED)

            ActivityCompat.requestPermissions(this, permissions, APP_PERMISSION_REQUEST_CODE);
    }

    //---------------------------- OPERATIONS ON THE MAIN MENU ----------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the toolbar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_home:
                //TODO? add operation to open log activity
                return true;
            case R.id.settings_home:
                //TODO? add operation to open settings activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

