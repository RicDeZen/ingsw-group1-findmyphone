package ingsw.group1.findmyphone.contacts;

import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * Class extends {@link Filter}
 * used to filter contacts by name and address.
 * Research isn't case sensitive.
 *
 * @author Giorgia Bortoletti
 */
class ContactFilter<C extends SMSContact> extends Filter {

    private List<C> allContacts;
    private List<C> selectedContacts;
    private SMSContactRecyclerAdapter adapter;

    /**
     * Constructor
     * to set an adapter where perform a search in a list of selected contacts.
     *
     * @param adapter          Adapter {@link SMSContactRecyclerAdapter} to notify contacts list change
     * @param selectedContacts {@link List<C>} contacts before any research
     */
    public ContactFilter(@NonNull SMSContactRecyclerAdapter adapter, @NonNull List<C> selectedContacts){
        this.allContacts = new ArrayList<>(selectedContacts);
        this.selectedContacts = selectedContacts;
        this.adapter = adapter;
    }

    /**
     * Filter contacts to show based on constraint passed.
     *
     * @param constraint Text to filter in the contacts list of their names and addresses
     *
     * @return {@link android.widget.Filter.FilterResults} results after filtering
     */
    @Override
    protected FilterResults performFiltering(@Nullable CharSequence constraint) {
        List<C> filteredList = new ArrayList<>();

        if (constraint == null || constraint.length() == 0) {
            filteredList.addAll(allContacts);
        } else {
            String filterPattern = constraint.toString().toLowerCase().trim();

            for (C item : allContacts) {
                String nameItem = item.getName().toLowerCase();
                String addressItem = item.getAddress();
                if (nameItem.contains(filterPattern) || addressItem.contains(filterPattern)) {
                    filteredList.add(item);
                }
            }
        }

        FilterResults results = new FilterResults();
        results.values = filteredList;

        return results;
    }

    /**
     * Publish results of filtering and notify changes at adapter.
     *
     * @param constraint Filtered text in the contacts list
     * @param results    {@link android.widget.Filter.FilterResults} contacts to show after filtering
     */
    @Override
    protected void publishResults(@Nullable CharSequence constraint, FilterResults results) {
        selectedContacts.clear();
        selectedContacts.addAll((List) results.values);
        adapter.notifyDataSetChanged();
    }
}
