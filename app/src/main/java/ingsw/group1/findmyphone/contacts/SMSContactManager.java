package ingsw.group1.findmyphone.contacts;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import java.util.List;

import ingsw.group1.msglibrary.SMSPeer;
import ingsw.group1.msglibrary.exceptions.InvalidAddressException;

/**
 * Class that takes care of inserting and deleting contacts from the database.
 * We don't use a support structure, like a Map,
 * because it is assumed that few contacts are inserted and not frequently.
 * So they can be added and deleted directly from the {@link SMSContactDatabase} every time.
 *
 * @author Giorgia Bortoletti
 */
public class SMSContactManager {

    private static final String SINGLETON_ERROR = "This class uses the singleton design pattern. Use getInstance() to get a reference to the single instance of this class";
    private static final String CONTACTS_DB_NAME = "contact-db";

    private SMSContactDatabase contactDatabase;
    private static SMSContactManager instance;

    //---------------------------- CONSTRUCTOR ----------------------------

    /**
     * Private constructor
     *
     * @param applicationContext {@link Context} of the application
     */
    private SMSContactManager(@NonNull Context applicationContext) {
        if(instance != null)
            throw new RuntimeException(SINGLETON_ERROR);

        contactDatabase = Room.databaseBuilder(applicationContext, SMSContactDatabase.class,
                CONTACTS_DB_NAME)
                .enableMultiInstanceInvalidation()
                .allowMainThreadQueries()
                .build();
    }

    /**
     * Method to get the only valid on-disk instance of this class. A new instance is created only if it was
     * null previously. The used context is always the parent application context of the parameter.
     *
     * @param applicationContext The calling context
     */
    public static SMSContactManager getInstance(@NonNull Context applicationContext) {
        if (instance == null) {
            instance = new SMSContactManager(applicationContext);
        }
        return instance;
    }

    //---------------------------- OPERATIONS ON THE SINGLE CONTACT ----------------------------

    /**
     * Verify if a contact could be a valid contact in its phone
     *
     * @param contactPhone
     *
     * @return true if contact is valid, false otherwise
     */
    public boolean isValidContactPhone(String contactPhone){
        try {
            SMSPeer peerContact = new SMSPeer(contactPhone);
        }catch (InvalidAddressException addressException){
            return false;
        }
        return true;
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
     * Add a {@link SMSContact} in {@link SMSContactDatabase}
     *
     * @param newContact {@link SMSContact} to insert in the contacts database
     */
    public void addContact(SMSContact newContact) {
        contactDatabase.access().insert(newContact);
    }

    /**
     * Modify name of a contact
     *
     * @param peerToModify {@link SMSPeer} represents the address of contact to modify
     * @param newName new name for the existing contact
     */
    public void modifyContactName(SMSPeer peerToModify, String newName){
        SMSContact contact = SMSContactConverterUtils.contactFromSMSPeer(peerToModify, newName);
        contactDatabase.access().update(contact);
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
     * Remove a {@link SMSContact} from {@link SMSContactDatabase}
     *
     * @param contact {@link SMSContact} to delete from the contacts database
     */
    public void removeContact(SMSContact contact) {
        contactDatabase.access().delete(contact);
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
    public boolean containsSMSPeer(SMSPeer peer) {
        return getContactForPeer(peer) != null;
    }

    /**
     * Returns the Contact corresponding to a Peer.
     *
     * @param peer {@link SMSPeer} to find
     * @return The Contact with the given Peer address, {@code null} if it does not exist.
     */
    @Nullable
    public SMSContact getContactForPeer(SMSPeer peer) {
        List<SMSContact> queryResult =
                contactDatabase.access().getContactsForAddresses(peer.getAddress());
        if (queryResult == null || queryResult.isEmpty()) return null;
        return queryResult.get(0);
    }
}
