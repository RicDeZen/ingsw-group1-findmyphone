package ingsw.group1.findmyphone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ingsw.group1.findmyphone.Manager;
import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.activity.AlarmAndLocateResponseActivity;
import ingsw.group1.msglibrary.ReceivedMessageListener;
import ingsw.group1.msglibrary.SMSManager;
import ingsw.group1.msglibrary.SMSMessage;
import ingsw.group1.msglibrary.SMSPeer;

/**
 * @author Turcato, Kumar
 */
public class HomeFragment extends Fragment implements ReceivedMessageListener<SMSMessage> {

    private EditText txtPhoneNumber;

    private Manager manager;
    private SMSPeer smsPeer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Method called when creating the view for the first time.
     *
     * @param inflater           The passed inflater. Needed to create the view.
     * @param container          The container asking for creation of the fragment.
     * @param savedInstanceState The saved state.
     * @return The root view for this fragment, in this case just an inflation of the layout.
     */
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);

        ImageButton viewContacts;

        txtPhoneNumber = root.findViewById(R.id.home_address_input);
        ImageButton sendAlarmRequestButton = root.findViewById(R.id.home_alarm_button);
        ImageButton sendLocationRequestButton = root.findViewById(R.id.home_location_button);
        viewContacts = root.findViewById(R.id.home_contacts_button);

        if (getContext() == null) return root;

        manager = new Manager(getContext());
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
                Navigation.findNavController(v).navigate(R.id.navigation_home_to_contacts);
            }
        });

        //TODO not the optimal place for this, should be moved elsewhere.
        SMSManager.getInstance(getContext()).setActivityToWake(AlarmAndLocateResponseActivity.class);

        return root;
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
    public void onDestroy() {
        manager.removeReceiveListener();
        super.onDestroy();
    }
}