package ingsw.group1.findmyphone.contacts;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

import ingsw.group1.msglibrary.SMSPeer;

/**
 * Class that takes care of inserting and deleting contacts from the database.
 * We don't use a support structure, like a Map,
 * because it is assumed that few contacts are inserted and not frequently.
 * So they can be added and deleted directly from the database every time.
 *
 * @author Giorgia Bortoletti
 */
public class SMSContactManager {

    public static final String CONTACTS_DB_NAME = "contact-db";

    private SMSContactDatabase contactDatabase;

    //---------------------------- CONSTRUCTOR ----------------------------

    /**
     * Constructor
     *
     * @param applicationContext {@link Context} of the application
     */
    public SMSContactManager(Context applicationContext) {
        contactDatabase = Room.databaseBuilder(applicationContext, SMSContactDatabase.class,
                CONTACTS_DB_NAME)
                .enableMultiInstanceInvalidation()
                .allowMainThreadQueries()
                .build();
    }

    //---------------------------- OPERATIONS ON THE CONTACTS DATABASE ----------------------------

    /**
     * Add a {@link SMSPeer} as {@link SMSContact} in {@link SMSContactDatabase}
     * after using {@link SMSContactConverterUtils} to convert SMSPeer in a Contact entity
     *
     * @param peer {@link SMSPeer} to insert in the contacts database
     */
    public void addContact(SMSPeer peer) {
        SMSContact newContact = SMSContactConverterUtils.contactFromSMSPeer(peer);
        contactDatabase.access().insert(newContact);
    }

    /**
     * Add a {@link SMSPeer} as {@link SMSContact} in {@link SMSContactDatabase}
     * after using {@link SMSContactConverterUtils} to convert SMSPeer in a Contact entity
     *
     * @param peer        {@link SMSPeer} to insert in the contacts database
     * @param nameContact optional name for the new contact
     */
    public void addContact(SMSPeer peer, String nameContact) {
        SMSContact newContact = SMSContactConverterUtils.contactFromSMSPeer(peer, nameContact);
        contactDatabase.access().insert(newContact);
    }

    /**
     * Remove a {@link SMSPeer} from {@link SMSContactDatabase}
     *
     * @param peer {@link SMSPeer} to delete from the contacts database
     */
    public void removeContact(SMSPeer peer) {
        SMSContact oldContact = SMSContactConverterUtils.contactFromSMSPeer(peer);
        contactDatabase.access().delete(oldContact);
    }

    /**
     * Return all contacts present in the database
     *
     * @return an array of {@link SMSContact} saved in the database
     */
    public List<SMSContact> getAllContacts() {
        return contactDatabase.access().getAll();
    }

    /**
     * Check if a peer is present in the database
     *
     * @param peer {@link SMSPeer} to find
     * @return true if peer is present in the database, false otherwise
     */
    public boolean containsPeer(SMSPeer peer) {
        List<SMSContact> contactList = getAllContacts();
        for (SMSContact contact : contactList)
            if (contact.getAddress().equals(peer.getAddress()))
                return true;
        return false;
    }
}
