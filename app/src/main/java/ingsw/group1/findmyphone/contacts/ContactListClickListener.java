package ingsw.group1.findmyphone.contacts;

import android.view.View;

/**
 * Interface that defines the schema of a Listener used on an item of RecyclerView
 * in {@link ingsw.group1.findmyphone.fragment.ContactListFragment}.
 *
 * @author Giorgia Bortoletti
 */
public interface ContactListClickListener {

    /**
     * This method is invoked by the {@link View.OnClickListener} of each contact item in the rubric.
     * The implementation of this method is in the {@link ingsw.group1.findmyphone.fragment.ContactListFragment}
     * because it requires some instructions accessible only inside a Fragment.
     *
     * @param view              {@link View} where it is generated the click.
     * @param selectedContact   {@link SMSContact} contact selected in the RecyclerView.
     */
    void onClick(View view, SMSContact selectedContact);
}