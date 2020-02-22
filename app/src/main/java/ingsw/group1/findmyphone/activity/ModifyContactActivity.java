package ingsw.group1.findmyphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.contacts.SMSContactManager;
import ingsw.group1.msglibrary.SMSPeer;

/**
 * Activity for add a new contact
 *
 * @author Giorgia Bortoletti
 */
public class ModifyContactActivity extends AppCompatActivity {

    private static String contactAddress;

    private EditText contactNameField;
    private TextView contactPhoneField;

    private SMSContactManager contactManager;

    /**
     * Method invoked when activity is created
     *
     * @param savedInstanceStatus
     */
    @Override
    public void onCreate(Bundle savedInstanceStatus) {
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.activity_modify_contact);

        contactNameField = findViewById(R.id.modify_contact_name);
        contactPhoneField = findViewById(R.id.modify_contact_address);

        contactManager = new SMSContactManager(getApplicationContext());

        SMSContact contact = contactManager.getContactForPeer(new SMSPeer(contactAddress));

        contactNameField.setText(contact.getName());
        contactPhoneField.setText(contactAddress);

    }

    /**
     * Called when the user touches the button for modifying an existing contact
     */
    public void modifyContact(View view) {
        String contactPhone = contactPhoneField.getText().toString();
        String contactName = contactNameField.getText().toString();

        contactManager.modifyContactName(new SMSPeer(contactAddress), contactName);
        startActivity(new Intent(ModifyContactActivity.this,
                ContactListActivity.class));

    }

    /**
     * Setter for the contact address that cannot be changed.
     * It is invoked by {@link ContactSwipeCallback}.
     *
     *
     */
    public static void setContactAddress(String contactAddress) {
        ModifyContactActivity.contactAddress = contactAddress;
    }

}