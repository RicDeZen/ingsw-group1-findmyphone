package ingsw.group1.findmyphone.contacts;

import android.widget.Filter;

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
     *
     * @param adapter          to notify contacts list change
     * @param selectedContacts contacts initial before any research
     */
    public ContactFilter(SMSContactRecyclerAdapter adapter, List<C> selectedContacts){
        this.allContacts = new ArrayList<>(selectedContacts);
        this.selectedContacts = selectedContacts;
        this.adapter = adapter;
    }

    /**
     * Filter contacts to show based on constraint passed.
     *
     * @param constraint text to filter in the contacts list of their names and addresses
     *
     * @return {@link android.widget.Filter.FilterResults} results after filtering
     */
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        List<C> filteredList = new ArrayList<>();

        if (constraint == null || constraint.length() == 0) {
            filteredList.addAll(allContacts);
        } else {
            String filterPattern = constraint.toString().toLowerCase().trim();

            for (C item : allContacts) {
                if (item.getName().toLowerCase().contains(filterPattern)) {
                    filteredList.add(item);
                }
                if (item.getAddress().contains(filterPattern)) {
                    filteredList.add(item);
                }
            }
        }

        FilterResults results = new FilterResults();
        results.values = filteredList;

        return results;
    }

    /**
     * Publish results of filtering and notify changes at adapter
     *
     * @param constraint filtered text in the contacts list
     * @param results    {@link android.widget.Filter.FilterResults} contacts to show after filtering
     */
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        selectedContacts.clear();
        selectedContacts.addAll((List) results.values);
        adapter.notifyDataSetChanged();
    }
}
