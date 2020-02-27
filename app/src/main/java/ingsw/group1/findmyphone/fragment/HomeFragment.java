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

import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.Manager;
import ingsw.group1.findmyphone.R;

/**
 * @author Turcato, Kumar
 */
public class HomeFragment extends Fragment {

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

        return root;
    }
}