package ingsw.group1.findmyphone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.activity.ModifyContactFragment;
import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.contacts.SMSContactManager;
import ingsw.group1.findmyphone.contacts.SMSContactRecyclerAdapter;
import ingsw.group1.findmyphone.contacts.SMSContactSearchListener;
import ingsw.group1.findmyphone.contacts.SMSContactSwipeCallback;

/**
 * Activity for the view showing the contact list
 * using a {@link RecyclerView} to display contacts list
 * and a {@link SMSContactRecyclerAdapter} to populate and manage the list of contacts saved.
 * Contacts are saved in a database managed by a {@link SMSContactManager}
 * and viewed in alphabetical order.
 *
 * @author Giorgia Bortoletti
 */
public class ContactListFragment extends Fragment {

    private SMSContactRecyclerAdapter recyclerAdapter;
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
        View root = inflater.inflate(R.layout.contact_list_fragment, container, false);

        RecyclerView recyclerView;
        FloatingActionButton newContactButton;

        recyclerView = root.findViewById(R.id.contact_list);
        newContactButton = root.findViewById(R.id.create_contact);

        List<SMSContact> contacts = contactManager.getAllContacts();

        if (getContext() != null)
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new SMSContactRecyclerAdapter(contacts, contactManager);
        recyclerView.setAdapter(recyclerAdapter);

        //---listener to open activity for adding new contact
        newContactButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavHostFragment.findNavController(ContactListFragment.this)
                                .navigate(R.id.navigation_contacts_to_new);
                    }
                }
        );

        // Search View -----------------------------------------------------------------------------
        SearchView searchView = root.findViewById(R.id.search_contact);

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SMSContactSearchListener(recyclerAdapter));

        //---helper to delete contact after a swipe on its item in the recycler view
        ItemTouchHelper contactTouchHelper =
                new ItemTouchHelper((new SMSContactSwipeCallback(recyclerAdapter)));
        contactTouchHelper.attachToRecyclerView(recyclerView);

        return root;
    }

    /**
     * This method is invoked after an onBackPressed() by {@link CreateContactFragment} and
     * {@link ModifyContactFragment}.
     * It updates the list of contacts to show.
     */
    @Override
    public void onResume() {
        List<SMSContact> updatedContacts = contactManager.getAllContacts();
        recyclerAdapter.updateItems(updatedContacts);
        super.onResume();
    }

}

