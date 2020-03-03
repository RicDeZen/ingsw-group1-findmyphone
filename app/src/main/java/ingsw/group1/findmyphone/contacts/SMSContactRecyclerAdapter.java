package ingsw.group1.findmyphone.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.fragment.ContactListFragment;


/**
 * Class adapter
 * from a list of {@link SMSContact}
 * and its graphic representation in a {@link RecyclerView} in {@link ContactListFragment}.
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

    private ContactListClickListener itemListener; //listener on single item

    //---------------------------- CONSTRUCTOR ----------------------------

    /**
     * Constructor
     * with contacts to show and manager to manage operations on contacts.
     *
     * @param contacts       Contacts to show in the {@link RecyclerView}.
     * @param contactManager {@link SMSContactManager} used to manage contacts after a user's request.
     */
    public SMSContactRecyclerAdapter(@NonNull final List<SMSContact> contacts, SMSContactManager contactManager, ContactListClickListener itemListener) {
        this.contacts = contacts;
        this.contactManager = contactManager;

        this.filter = new ContactFilter(this, contacts);

        this.itemListener = itemListener;
    }

    //---------------------------- OPERATIONS ON VIEW HOLDER (class ViewHolder is at the bottom) ---

    /**
     * Called when RecyclerView needs a new {@link ContactViewHolder} of the given type
     * to represent an item.
     *
     * @param parent   {@link ViewGroup} where insert every item.
     * @param viewType Type of view.
     * @return new {@link ContactViewHolder} created.
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
     * @param holder   {@link ContactViewHolder} item.
     * @param position Item's position.
     */
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    //---------------------------- RESEARCH FILTER ----------------------------

    /**
     * Return {@link ContactFilter} used to filter contacts name.
     *
     * @return {@link ContactFilter} used to filter contacts name.
     */
    @Override
    public Filter getFilter() {
        return filter;
    }

    //---------------------------- OPERATIONS ON CONTACTS ----------------------------

    /**
     * Return numbers on contacts in the contacts list.
     *
     * @return numbers on contacts in the contacts list.
     */
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    /**
     * Return {@link SMSContact} of the given position.
     *
     * @return {@link SMSContact} of the given position.
     */
    public SMSContact getItem(int position) {
        return contacts.get(position);
    }

    /**
     * Add a {@link SMSContact} to the given position of contacts list
     * and notify that item has been inserted.
     *
     * @param position     Position where to insert contact in the list of contacts.
     * @param contactToAdd {@link SMSContact} to add.
     */
    public void addItem(int position, @NonNull SMSContact contactToAdd) {
        contacts.add(position, contactToAdd);
        contactManager.addContact(contactToAdd);
        filter = new ContactFilter(this, contacts);
        notifyItemInserted(position);
    }

    /**
     * Remove a {@link SMSContact} to the given position in the contacts list
     * and notify that item has been removed.
     *
     * @param position Position of {@link SMSContact} to delete from contacts list.
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
     * @param newContacts New list of contacts to show and manage.
     */
    public void updateItems(List<SMSContact> newContacts) {
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
         * used to compose the single item.
         *
         * @param itemView  {@link View} where to insert item.
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
         * @param contact To set in the item.
         */
        public void bind(@NonNull SMSContact contact) {
            contactName.setText(contact.getName());
            contactAddress.setText(contact.getAddress());

            itemView.setOnClickListener(new View.OnClickListener() {
                /**
                 * This method is invoked when a contact item is clicked.
                 * This contact is saved in a {@link ingsw.group1.findmyphone.fragment.ContactSharedViewModel}
                 * and shown in the {@link ingsw.group1.findmyphone.fragment.HomeFragment}.
                 *
                 * @param view      {@link View} which invoked the click.
                 */
                @Override
                public void onClick(View view) {
                    //layout.setBackgroundColor(ContextCompat.getColor(layout.getContext(), R.color.selectedContact));
                    int selectedPosition = getAdapterPosition();
                    SMSContact selectedContact = getItem(selectedPosition);

                    itemListener.onClick(view, selectedContact); /**its implementation is on {@link ContactListFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)} */
                }

            });
        }

    }
}
