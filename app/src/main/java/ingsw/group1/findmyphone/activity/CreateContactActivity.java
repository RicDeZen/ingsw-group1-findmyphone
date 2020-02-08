package ingsw.group1.findmyphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    private EditText contactName;
    private EditText contactPhone;

    private SMSContactManager contactManager;

    @Override
    public void onCreate(Bundle savedInstanceStatus){
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.activity_create_contact);

        Button newContactButton;

        contactName = findViewById(R.id.new_contact_name);
        contactPhone = findViewById(R.id.new_contact_phone);
        newContactButton = findViewById(R.id.add_contact_button);

        contactManager = new SMSContactManager(getApplicationContext());

        newContactButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contactManager.addContact(new SMSPeer(contactPhone.getText().toString()),
                            contactName.getText().toString());
                    startActivity(new Intent(CreateContactActivity.this, ContactListActivity.class));
                }
            }
        );


    }

}