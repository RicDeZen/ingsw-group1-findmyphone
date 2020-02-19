package ingsw.group1.findmyphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    @Override
    public void onCreate(Bundle savedInstanceStatus) {
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.activity_create_contact);

        FloatingActionButton newContactButton;

        contactNameField = findViewById(R.id.new_contact_name);
        contactPhoneField = findViewById(R.id.new_contact_phone);
        newContactButton = findViewById(R.id.add_contact_button);

        contactManager = new SMSContactManager(getApplicationContext());

        newContactButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String contactPhone = contactPhoneField.getText().toString();
                        String contactName = contactNameField.getText().toString();

                        if(!contactManager.isValidContactPhone(contactPhone))
                        {
                            Toast.makeText(getApplicationContext(), ActivityConstantsUtils.INVALID_CONTACT_PHONE, Toast.LENGTH_LONG);

                        }else if(!contactManager.containsSMSPeer(new SMSPeer(contactPhone))){
                            Toast.makeText(getApplicationContext(), ActivityConstantsUtils.DUPLICATE_CONTACT_PHONE, Toast.LENGTH_LONG);

                        }else{
                            contactManager.addContact(new SMSPeer(contactPhone), contactName);
                            Toast.makeText(getApplicationContext(), ActivityConstantsUtils.CONTACT_INSERTED, Toast.LENGTH_LONG);
                            startActivity(new Intent(CreateContactActivity.this,
                                    ContactListActivity.class));
                        }


                    }
                }
        );


    }

}