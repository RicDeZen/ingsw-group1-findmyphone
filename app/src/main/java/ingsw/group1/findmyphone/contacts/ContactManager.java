package ingsw.group1.findmyphone.contacts;

import android.content.Context;
import androidx.room.Room;
import java.util.List;
import ingsw.group1.msglibrary.SMSPeer;

/**
 * Class that takes care of inserting and deleting contacts from the database
 * We don't use a support structure, like a Map,
 * because it is assumed that few contacts are inserted and not frequently.
 * So they can be added and deleted directly from the database every time.
 *
 * @author Giorgia Bortoletti
 */
public class ContactManager {

    private static final String CONTACTS_DB_NAME = "contacts-db";

    private ContactDatabase contactDatabase;

    //---------------------------- CONSTRUCTOR ----------------------------

    /**
     * Constructor
     *
     * @param applicationContext {@link Context} of the application
     */
    public ContactManager(Context applicationContext){
        contactDatabase = Room.databaseBuilder(applicationContext, ContactDatabase.class, CONTACTS_DB_NAME)
                .enableMultiInstanceInvalidation()
                .allowMainThreadQueries()
                .build();
    }

    //---------------------------- OPERATIONS ON THE CONTACTS DATABASE ----------------------------

    /**
     * Add a {@link SMSPeer} as {@link Contact} in {@link ContactDatabase}
     * after using {@link ContactConverter} to convert SMSPeer in a Contact entity
     *
     * @param peer to insert in the contacts database
     */
    public void addContact(SMSPeer peer){
        Contact newContact = ContactConverter.contactFromSMSPeer(peer);
        contactDatabase.access().insert(newContact);
    }

    /**
     * Add a {@link SMSPeer} as {@link Contact} in {@link ContactDatabase}
     * after using {@link ContactConverter} to convert SMSPeer in a Contact entity
     *
     * @param peer        to insert in the contacts database
     * @param nameContact optional name for the new contact
     */
    public void addContact(SMSPeer peer, String nameContact){
        Contact newContact = ContactConverter.contactFromSMSPeer(peer, nameContact);
        contactDatabase.access().insert(newContact);
    }

    /**
     * Remove a {@link SMSPeer} from {@link ContactDatabase}
     *
     * @param peer to delete from the contacts database
     */
    public void removeContact(SMSPeer peer){
        Contact oldContact = ContactConverter.contactFromSMSPeer(peer);
        contactDatabase.access().delete(oldContact);
    }

    /**
     * Return all contacts present in the database
     *
     * @return an array of {@link Contact} saved in the database
     */
    public List<Contact> getAllContacts(){
        return contactDatabase.access().getAll();
    }

}
