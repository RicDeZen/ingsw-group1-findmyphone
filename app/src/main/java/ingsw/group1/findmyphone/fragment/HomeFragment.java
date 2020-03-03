package ingsw.group1.findmyphone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.Manager;
import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.activity.ActivityConstantsUtils;
import ingsw.group1.findmyphone.contacts.SMSContact;

import static android.app.Activity.RESULT_OK;

/**
 * @author Turcato, Kumar
 */
public class HomeFragment extends Fragment {

    private static final String INVALID_ADDRESS_MESSAGE = "The provided phone address is invalid";

    private EditText addressInput;
    private EditText passwordInput;

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

        addressInput = root.findViewById(R.id.home_address_input);
        passwordInput = root.findViewById(R.id.home_password_input);
        ImageButton sendAlarmRequestButton = root.findViewById(R.id.home_alarm_button);
        ImageButton sendLocationRequestButton = root.findViewById(R.id.home_location_button);
        viewContacts = root.findViewById(R.id.home_contacts_button);

        if (getContext() == null) return root;

        manager = new Manager(getContext());

        sendLocationRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsPeer = new SMSPeer(addressInput.getText().toString());
                String password = passwordInput.getText().toString();
                if (smsPeer.getInvalidityReason() == null)
                    //TODO add password
                    manager.sendLocationRequest(smsPeer);
                else showToastIfPossible(INVALID_ADDRESS_MESSAGE);
            }
        });

        sendAlarmRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsPeer = new SMSPeer(addressInput.getText().toString());
                String password = passwordInput.getText().toString();
                if (smsPeer.getInvalidityReason() == null)
                    //TODO add password
                    manager.sendAlarmRequest(smsPeer);
                else showToastIfPossible(INVALID_ADDRESS_MESSAGE);
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


    /**
     * This method is used to set the EditText with selected contact from rubric.
     * The selected contact is read from {@link ContactSharedViewModel}.
     */
    public void onResume() {
        super.onResume();
        ContactSharedViewModel model = new ViewModelProvider(requireActivity()).get(ContactSharedViewModel.class);
        LiveData<SMSContact> selectedContact = model.getSelected();
        if(selectedContact.getValue() == null)
            addressInput.setText("");
        else
            addressInput.setText(selectedContact.getValue().getAddress());
    }

    /**
     * Method to safely show a Toast, fails silently if the Context is not available.
     *
     * @param message The message the Toast should contain.
     */
    private void showToastIfPossible(String message) {
        if (getContext() == null) return;
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}