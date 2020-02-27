package ingsw.group1.findmyphone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.activity.ActivityConstantsUtils;
import ingsw.group1.findmyphone.contacts.SMSContactManager;

/**
 * Activity for add a new contact
 *
 * @author Giorgia Bortoletti
 */
public class CreateContactFragment extends Fragment {

    private EditText contactNameField;
    private EditText contactPhoneField;

    private SMSContactManager contactManager;

    /**
     * Method called when the Fragment is first attached to an Activity. This happens before the
     * {@link Fragment#onCreate(Bundle)} call.
     *
     * @param context The context on which the {@link Fragment} gets attached.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contactManager = SMSContactManager.getInstance(context.getApplicationContext());
    }

    /**
     * Method called when the View is first created.
     *
     * @param inflater           The inflater to use when creating the View.
     * @param container          The container for the View.
     * @param savedInstanceState The saved instance state.
     * @return The View for this Fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.create_contact_fragment, container, false);

        contactNameField = root.findViewById(R.id.new_contact_name);
        contactPhoneField = root.findViewById(R.id.new_contact_address);

        root.findViewById(R.id.add_contact_button).setOnClickListener(view -> addNewContact());

        return root;
    }

    /**
     * Called when the user touches the button for adding a new contact.
     */
    private void addNewContact() {
        String contactPhone = contactPhoneField.getText().toString();
        String contactName = contactNameField.getText().toString();

        if (!contactManager.isValidContactPhone(contactPhone))
            showToastIfPossible(ActivityConstantsUtils.INVALID_CONTACT_PHONE);

        else if (contactManager.containsPeer(new SMSPeer(contactPhone)))
            showToastIfPossible(ActivityConstantsUtils.DUPLICATE_CONTACT_PHONE);

        else {
            contactManager.addContact(new SMSPeer(contactPhone), contactName);
            showToastIfPossible(ActivityConstantsUtils.CONTACT_INSERTED);
            NavHostFragment.findNavController(this).navigateUp();
        }
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