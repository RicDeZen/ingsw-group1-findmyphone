package ingsw.group1.findmyphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.contacts.SMSContactManager;

/**
 * Activity for the view showing the contact list
 *
 * @author Giorgia Bortoletti
 */
public class ContactListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private FloatingActionButton newContactButton;

    @Override
    public void onCreate(Bundle savedInstanceStatus) {
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.activity_contact_list);

        recyclerView = findViewById(R.id.contact_list);
        newContactButton = findViewById(R.id.create_contact);

        SMSContactManager contactManager = new SMSContactManager(getApplicationContext());

        List<SMSContact> contacts = contactManager.getAllContacts();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(recyclerAdapter);

        newContactButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ContactListActivity.this, CreateContactActivity.class));
                }
            }
        );


    }

}