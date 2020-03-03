package ingsw.group1.findmyphone.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ingsw.group1.findmyphone.contacts.SMSContact;

/**
 * This class is used to shared {@link SMSContact} between fragments,
 * especially for {@link ContactListFragment} and {@link HomeFragment}.
 * User selects a contact in the {@link ContactListFragment}
 * that saves selected contact in this ViewModel
 * and {@link HomeFragment} can display contact in the home.
 *
 * @author Giorgia Bortoletti
 */
public class ContactSharedViewModel extends ViewModel {
    private final MutableLiveData<SMSContact> selected = new MutableLiveData<SMSContact>();

    /**
     * Method used to save che contact selected from rubric.
     *
     * @param item {@link SMSContact} contact selected from rubric.
     */
    public void select(@NonNull SMSContact item) {
        selected.setValue(item);
    }

    /**
     * Method used to get the contact previously selected from rubric.
     *
     * @return {@link LiveData<SMSContact>} previously selected from rubric.
     */
    public LiveData<SMSContact> getSelected() {
        return selected;
    }
}