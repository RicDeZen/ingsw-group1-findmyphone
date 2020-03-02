package ingsw.group1.findmyphone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.activity.ActivityConstantsUtils;
import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.contacts.SMSContactManager;
import ingsw.group1.findmyphone.contacts.SMSContactSwipeCallback;

/**
 * Activity to modify a new contact in his name
 *
 * @author Giorgia Bortoletti
 */
public class ModifyContactFragment extends Fragment {

    private static final String DEF_NAME = "UNKNOWN";

    private static String contactAddress = "";

    private EditText contactNameField;
    private SMSContactManager contactManager;
    private String contactName = DEF_NAME;

    /**
     * Method called when the Fragment is attached to a Context.
     *
     * @param context The Context to which the Fragment is attached.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contactManager = SMSContactManager.getInstance(context.getApplicationContext());
    }

    /**
     * Method called when creating the view for the first time.
     *
     * @param inflater           The passed inflater. Needed to create the view.
     * @param container          The container asking for creation of the fragment.
     * @param savedInstanceState The saved state.
     * @return The root view for this fragment, in this case just an inflation of the layout.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.modify_contact_fragment, container, false);

        contactNameField = root.findViewById(R.id.modify_contact_name);
        SMSContact contact = contactManager.getContactForPeer(new SMSPeer(contactAddress));
        if (contact != null)
            contactName = contact.getName();
        contactNameField.setText(contactName);

        TextView contactPhoneField = root.findViewById(R.id.modify_contact_address);
        contactPhoneField.setText(contactAddress);

        root.findViewById(R.id.modify_contact_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        modifyContact();
                    }
                }
        );

        return root;
    }

    /**
     * Called when the user touches the button for modifying an existing contact.
     */
    public void modifyContact() {
        String newContactName = contactNameField.getText().toString();

        if (newContactName.equals(contactName))
            showToastIfPossible(ActivityConstantsUtils.EQUALS_CONTACT_NAME);
        else {
            contactManager.modifyContactName(new SMSPeer(contactAddress), newContactName);
            showToastIfPossible(ActivityConstantsUtils.CONTACT_UPDATED);
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

    /**
     * Setter for the contact address that cannot be changed.
     * It is invoked by {@link SMSContactSwipeCallback}.
     */
    public static void setContactAddress(String contactAddress) {
        ModifyContactFragment.contactAddress = contactAddress;
    }

}