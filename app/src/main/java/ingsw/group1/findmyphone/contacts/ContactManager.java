package ingsw.group1.findmyphone.contacts;

import androidx.annotation.NonNull;

import com.eis.communication.Peer;

import java.util.List;

public interface ContactManager<A extends Comparable<A>, P extends Peer<A>, C extends GenericContact<A, P>> {

    //---------------------------- OPERATIONS ON THE SINGLE CONTACT ----------------------------

    /**
     * Verify if a contact could be a valid contact in its phone.
     *
     * @param contactPhone Contact phone to verify.
     * @return true if contact is valid, false otherwise.
     */
    boolean isValidContactPhone(@NonNull A contactPhone);

    //---------------------------- OPERATIONS ON THE DATABASE ----------------------------

    /**
     * Add a {@link P} as {@link C} in {@link SMSContactDatabase}.
     *
     * @param peer Peer {@link P} to insert in the contacts database.
     */
    void addContact(@NonNull P peer);

    /**
     * Add a {@link P} as {@link C} in {@link SMSContactDatabase}.
     *
     * @param peer        Peer {@link P} to insert in the contacts database.
     * @param nameContact Optional name for the new contact.
     */
    void addContact(@NonNull P peer, @NonNull A nameContact);

    /**
     * Add a {@link C} in {@link SMSContactDatabase}.
     *
     * @param newContact Contact {@link C} to insert in the contacts database.
     */
    void addContact(@NonNull C newContact);

    /**
     * Modify name of a contact.
     *
     * @param peerToModify Peer {@link P} represents the address of contact to modify.
     * @param newName      New name for the existing contact.
     */
    void modifyContactName(@NonNull P peerToModify, @NonNull A newName);

    /**
     * Remove a {@link P} from {@link SMSContactDatabase}.
     *
     * @param peer Peer {@link P} to delete from the contacts database.
     */
    void removeContact(@NonNull P peer);

    /**
     * Remove a {@link C} from {@link SMSContactDatabase}.
     *
     * @param contact Contact {@link C} to delete from the contacts database.
     */
    void removeContact(@NonNull C contact);

    /**
     * Return all contacts present in the database.
     *
     * @return a {@link List} of {@link C} saved in the database.
     */
    List<C> getAllContacts();

    /**
     * Check if a peer is present in the database.
     *
     * @param peer Peer {@link P} to find.
     * @return true if peer is present in the database, false otherwise.
     */
    boolean containsPeer(@NonNull P peer);

    /**
     * Returns the Contact corresponding to a Peer.
     *
     * @param peer Peer {@link P} to find.
     * @return the Contact with the given Peer address, {@code null} if it does not exist.
     */
    C getContactForPeer(@NonNull P peer);

}
