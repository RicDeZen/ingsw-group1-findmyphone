package ingsw.group1.findmyphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.contacts.SMSContactRecyclerAdapter;
import ingsw.group1.findmyphone.contacts.SMSContactSearchListener;
import ingsw.group1.findmyphone.contacts.SMSContactSwipeCallback;
import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.contacts.SMSContactManager;

/**
 * Activity for the view showing the contact list
 * using a {@link RecyclerView} to display contacts list
 * and a {@link SMSContactRecyclerAdapter} to populate and manage the list of contacts saved.
 * Contacts are saved in a database managed by a {@link SMSContactManager}
 * and viewed in alphabetical order.
 *
 * @author Giorgia Bortoletti
 */
public class ContactListActivity extends AppCompatActivity {

    private SMSContactManager contactManager;
    private SMSContactRecyclerAdapter recyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceStatus) {
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.activity_contact_list);

        RecyclerView recyclerView;
        FloatingActionButton newContactButton;

        recyclerView = findViewById(R.id.contact_list);
        newContactButton = findViewById(R.id.create_contact);

        Toolbar searchToolbar = findViewById(R.id.search_contact_toolbar);
        setSupportActionBar(searchToolbar);

        contactManager = SMSContactManager.getInstance(getApplicationContext());

        List<SMSContact> contacts = contactManager.getAllContacts();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new SMSContactRecyclerAdapter(contacts, contactManager);
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

        //---helper to delete contact after a swipe on its item in the recycler view
        ItemTouchHelper contactTouchHelper = new ItemTouchHelper((new SMSContactSwipeCallback(recyclerAdapter)));
        contactTouchHelper.attachToRecyclerView(recyclerView);

    }

    //---------------------------- CREATE MENU FOR SEARCHING A CONTACT ----------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_contact, menu);

        MenuItem searchItem = menu.findItem(R.id.search_contact);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SMSContactSearchListener(recyclerAdapter));

        return true;
    }

}

