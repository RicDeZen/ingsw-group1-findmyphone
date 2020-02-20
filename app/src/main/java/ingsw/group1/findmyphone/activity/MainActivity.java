package ingsw.group1.findmyphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import ingsw.group1.findmyphone.Manager;
import ingsw.group1.findmyphone.R;
import ingsw.group1.msglibrary.ReceivedMessageListener;
import ingsw.group1.msglibrary.SMSManager;
import ingsw.group1.msglibrary.SMSMessage;
import ingsw.group1.msglibrary.SMSPeer;

/**
 * @author Turcato, Kumar
 */
public class MainActivity extends AppCompatActivity implements ReceivedMessageListener<SMSMessage> {

    private EditText txtPhoneNumber;
    private ImageButton sendAlarmRequestButton;
    private ImageButton sendLocationRequestButton;

    private Manager manager;
    private SMSPeer smsPeer;

    /***
     * @author Turcato
     * @param savedInstanceState system parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton viewContacts;

        txtPhoneNumber = findViewById(R.id.home_address_input);
        sendAlarmRequestButton = findViewById(R.id.home_alarm_button);
        sendLocationRequestButton = findViewById(R.id.home_location_button);
        viewContacts = findViewById(R.id.home_contacts_button);

        manager = new Manager(getApplicationContext());
        manager.setReceiveListener(this);

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

        //TODO not the optimal place for this, should be moved elsewhere.
        SMSManager.getInstance(getApplicationContext()).setActivityToWake(AlarmAndLocateResponseActivity.class);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    /***
     * @author Turcato
     * This method is executed both when the app is running or not.
     * Based on the message's content, opens AlarmAndLocateResponseActivity if it's a request
     * message,
     * otherwise if it contains the location response (the only one expected) it opens the
     * default maps application
     * to the received location
     *
     * @param message Received SMSMessage class of SmsHandler library
     */
    public void onMessageReceived(SMSMessage message) {
        manager.activeResponse(message, AlarmAndLocateResponseActivity.class);
    }

    /**
     * @author Turcato
     * Safely deletes the listeners
     */
    @Override
    protected void onDestroy() {
        manager.removeReceiveListener();
        super.onDestroy();
    }

}

