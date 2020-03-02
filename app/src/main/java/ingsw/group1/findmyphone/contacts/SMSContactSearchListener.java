package ingsw.group1.findmyphone.contacts;

import android.widget.SearchView;

/**
 * Class implements {@link SearchView.OnQueryTextListener}.
 * This is a listener to activate the search on the contact list through a {@link SMSContactRecyclerAdapter}.
 *
 * @author Giorgia Bortoletti
 */
public class SMSContactSearchListener implements SearchView.OnQueryTextListener {

    private SMSContactRecyclerAdapter recyclerAdapter;

    public SMSContactSearchListener(SMSContactRecyclerAdapter recyclerAdapter) {
        this.recyclerAdapter = recyclerAdapter;
    }

    /**
     * It is invoked when user submits the text to search.
     *
     * @param query     Text to search
     *
     * @return false, letting the SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * It is invoked when user changes the text to search in the box.
     *
     * @param newText   Text to search
     *
     * @return false, letting the SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        recyclerAdapter.getFilter().filter(newText);
        return false;
    }
}
