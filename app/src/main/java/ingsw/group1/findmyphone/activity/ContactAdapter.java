package ingsw.group1.findmyphone.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.contacts.Contact;

/**
 * Class adapter
 * from a list of {@link Contact}
 * and its graphic representation in a table RecycleView in {@link ContactListActivity}
 *
 * @author Giorgia Bortoletti
 */
class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        holder.contactName.setText(contacts.get(position).getName());
        holder.contactAddress.setText(contacts.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    /**
     * Class to represent a contact viewed in a row of RecycleView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName;
        public TextView contactAddress;

        public ViewHolder(View itemView){
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            contactAddress = itemView.findViewById(R.id.contact_address);
        }
    }
}
