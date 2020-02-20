package ingsw.group1.findmyphone.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.contacts.SMSContactManager;

/**
 * Class adapter
 * from a list of {@link SMSContact}
 * and its graphic representation in a RecycleView in {@link ContactListActivity}
 *
 * @author Giorgia Bortoletti
 */
class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<SMSContact> contacts;
    private SMSContactManager contactManager;

    //---------------------------- CONSTRUCTORS ----------------------------

    public ContactAdapter(List<SMSContact> contacts) {
        this.contacts = contacts;
    }

    public ContactAdapter(List<SMSContact> contacts, SMSContactManager contactManager) {
        this.contacts = contacts;
        this.contactManager = contactManager;
    }

    //---------------------------- OPERATIONS ON VIEW HOLDER ----------------------------

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_contact_item,
                        parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.contactName.setText(contacts.get(position).getName());
        holder.contactAddress.setText(contacts.get(position).getAddress());
    }

    //---------------------------- OPERATIONS ON THE CONTACTS ----------------------------

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void updateContactsView(List<SMSContact> dataset) {
        contacts.clear();
        contacts.addAll(dataset);
        notifyDataSetChanged();

    }

    public void deleteContact(int position) {
        SMSContact contactToRemove = contacts.remove(position);
        contactManager.removeContact(contactToRemove);
        //updateContactsView(contacts);
        notifyItemRemoved(position);
    }


    ////---------------------------- CLASS FOR SINGLE CONTACT ITEM IN THE RECYCLER VIEW ----------------------------

    /**
     * Class to represent a contact viewed in a row of RecycleView
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName;
        public TextView contactAddress;

        /**
         * Constructor
         *
         * @param itemView
         */
        public ContactViewHolder(View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            contactAddress = itemView.findViewById(R.id.contact_address);
        }

    }
}
