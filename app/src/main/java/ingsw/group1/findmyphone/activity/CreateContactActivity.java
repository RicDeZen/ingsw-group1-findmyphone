package ingsw.group1.findmyphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.contacts.SMSContactManager;
import ingsw.group1.msglibrary.SMSPeer;

/**
 * Activity for add a new contact
 *
 * @author Giorgia Bortoletti
 */
public class CreateContactActivity extends AppCompatActivity {

    private EditText contactNameField;
    private EditText contactPhoneField;

    private SMSContactManager contactManager;

    /**
     * Method invoked when activity is created
     *
     * @param savedInstanceStatus
     */
    @Override
    public void onCreate(Bundle savedInstanceStatus) {
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.activity_create_contact);

        contactNameField = findViewById(R.id.new_contact_name);
        contactPhoneField = findViewById(R.id.new_contact_address);

        contactManager = new SMSContactManager(getApplicationContext());

    }

    /**
     * Called when the user touches the button for adding a new contact
     */
    public void addNewContact(View view) {
        String contactPhone = contactPhoneField.getText().toString();
        String contactName = contactNameField.getText().toString();

        if (!contactManager.isValidContactPhone(contactPhone)) {
            Toast.makeText(getApplicationContext(), ActivityConstantsUtils.INVALID_CONTACT_PHONE, Toast.LENGTH_LONG).show();

        } else if (contactManager.containsSMSPeer(new SMSPeer(contactPhone))) {
            Toast.makeText(getApplicationContext(), ActivityConstantsUtils.DUPLICATE_CONTACT_PHONE, Toast.LENGTH_LONG).show();

        } else {
            contactManager.addContact(new SMSPeer(contactPhone), contactName);
            Toast.makeText(getApplicationContext(), ActivityConstantsUtils.CONTACT_INSERTED, Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),
                    ContactListActivity.class));
            finish();
        }

    }


}