package ingsw.group1.findmyphone.contacts;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.activity.ContactListActivity;
import ingsw.group1.findmyphone.activity.MainActivity;

/**
 * Class adapter
 * from a list of {@link SMSContact}
 * and its graphic representation in a {@link RecyclerView} in {@link ContactListActivity}.
 * This class implements {@link Filterable} using a {@link ContactFilter}
 * that takes care of filtering the contacts to show.
 * Every action invokes its notify,
 * for example: {@link SMSContactRecyclerAdapter#addItem(int, SMSContact)} invokes {@link androidx.recyclerview.widget.RecyclerView.Adapter#notifyItemInserted(int)}.
 *
 * @author Giorgia Bortoletti
 */
public class SMSContactRecyclerAdapter extends RecyclerView.Adapter<SMSContactRecyclerAdapter.ContactViewHolder>
        implements Filterable, ContactRecyclerHelper<SMSContact> {

    private List<SMSContact> contacts; //contacts filtered
    private SMSContactManager contactManager;
    private Filter filter; //filter used in the searchView to filter contacts by name and address
    private int selectedPosition;

    //---------------------------- CONSTRUCTOR ----------------------------

    /**
     * Constructor
     *
     * @param contacts       Contacts to show in the {@link RecyclerView}
     * @param contactManager {@link SMSContactManager} used to manage contacts after a user's request
     */
    public SMSContactRecyclerAdapter(final List<SMSContact> contacts, SMSContactManager contactManager) {
        this.contacts = contacts;
        this.contactManager = contactManager;
        this.selectedPosition = -1;
        this.filter = new ContactFilter(this, contacts);
    }

    //---------------------------- OPERATIONS ON VIEW HOLDER (class ViewHolder is at the bottom) ---

    /**
     * Called when RecyclerView needs a new {@link ContactViewHolder} of the given type
     * to represent an item.
     *
     * @param parent
     * @param viewType
     * @return new {@link ContactViewHolder}
     */
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_contact_item,
                parent, false);
        return new ContactViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * should update the contents of the {@link ContactViewHolder#itemView} to reflect the item at
     * the given position.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    //---------------------------- RESEARCH FILTER ----------------------------

    /**
     * Return {@link ContactFilter} used to filter contacts name.
     *
     * @return {@link ContactFilter} used to filter contacts name
     */
    @Override
    public Filter getFilter() {
        return filter;
    }

    //---------------------------- OPERATIONS ON CONTACTS ----------------------------

    /**
     * Return numbers on contacts in the contacts list.
     *
     * @return numbers on contacts in the contacts list
     */
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    /**
     * Return {@link SMSContact} to the given position.
     *
     * @return {@link SMSContact} to the given position
     */
    public SMSContact getItem(int position) {
        return contacts.get(position);
    }

    /**
     * Return {@link SMSContact} to the selected position.
     *
     * @return {@link SMSContact} to the selected position, null if there isn't a selected position.
     */
    public SMSContact getSelectedItem() {
        if(selectedPosition == -1)
            return null;
        return contacts.get(selectedPosition);
    }

    /**
     * Add a {@link SMSContact} to the given position of contacts list
     * and notify that item has been inserted.
     *
     * @param position      Position where to insert contact in the list of contacts
     * @param contactToAdd  {@link SMSContact} to add
     */
    public void addItem(int position, SMSContact contactToAdd) {
        contacts.add(position, contactToAdd);
        contactManager.addContact(contactToAdd);
        filter = new ContactFilter(this, contacts);
        notifyItemInserted(position);
    }

    /**
     * Remove a {@link SMSContact} to the given position in the contacts list
     * and notify that item has been removed.
     *
     * @param position  Position of {@link SMSContact} to delete from contacts list
     */
    public void deleteItem(int position) {
        SMSContact contactToRemove = contacts.get(position);
        contactManager.removeContact(contactToRemove);
        contacts.remove(position);
        filter = new ContactFilter(this, contacts);
        notifyItemRemoved(position);
    }

    /**
     * Update the list of contacts to show
     * and notify that data have been updated.
     *
     * @param newContacts   New list of contacts to show and manage
     */
    public void updateItems(List<SMSContact> newContacts){
        contacts.clear();
        contacts.addAll(newContacts);
        filter = new ContactFilter(this, contacts);
        notifyDataSetChanged();
    }


    //---------------------------- ContactViewHolder ----------------------------

    /**
     * Class to represent a contact viewed in a row of RecycleView.
     *
     * @author Giorgia Bortoletti
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName;
        public TextView contactAddress;
        public ConstraintLayout layout;

        /**
         * Constructor
         *
         * @param itemView
         */
        public ContactViewHolder(View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            contactAddress = itemView.findViewById(R.id.contact_address);
            layout = itemView.findViewById(R.id.contact_item);
        }

        /**
         * Set the item
         * in its TextView and in its Listener to select one.
         *
         * @param contact   To set in the item.
         */
        public void bind(SMSContact contact) {
            layout.setBackgroundColor(Color.WHITE);
            contactName.setText(contact.getName());
            contactAddress.setText(contact.getAddress());

            //this listener is used to change status of a selected item after a click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.selectedContact));
                    if (selectedPosition != getAdapterPosition()) {
                        notifyItemChanged(selectedPosition);
                        selectedPosition = getAdapterPosition();
                        MainActivity.contactSelected = getSelectedItem();
                    }
                }
            });
        }

    }
}
