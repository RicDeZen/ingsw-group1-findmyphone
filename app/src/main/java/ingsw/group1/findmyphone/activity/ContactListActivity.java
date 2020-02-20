package ingsw.group1.findmyphone.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.contacts.SMSContactManager;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * Activity for the view showing the contact list
 *
 * @author Giorgia Bortoletti
 */
public class ContactListActivity extends AppCompatActivity {

    private SMSContactManager contactManager;

    @Override
    public void onCreate(Bundle savedInstanceStatus) {
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.activity_contact_list);

        RecyclerView recyclerView;
        ContactAdapter recyclerAdapter;
        FloatingActionButton newContactButton;

        recyclerView = findViewById(R.id.contact_list);
        newContactButton = findViewById(R.id.create_contact);

        contactManager = new SMSContactManager(getApplicationContext());

        List<SMSContact> contacts = contactManager.getAllContacts();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new ContactAdapter(contacts);
        recyclerView.setAdapter(recyclerAdapter);

        //---listener to open activity for adding new contact
        newContactButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ContactListActivity.this,
                                CreateContactActivity.class));
                    }
                }
        );

        //---helper to delete after a swipe
        ItemTouchHelper contactTouchHelper = new ItemTouchHelper((new SwipeToDeleteCallback(recyclerAdapter)));
        contactTouchHelper.attachToRecyclerView(recyclerView);

    }

}

