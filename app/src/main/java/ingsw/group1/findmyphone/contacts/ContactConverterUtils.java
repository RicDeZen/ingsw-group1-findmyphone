package ingsw.group1.findmyphone.contacts;

import androidx.room.TypeConverter;

import com.eis.communication.Peer;


/**
 * Interface defining a converter
 * necessary to convert {@link P} to {@link C} and vice versa.
 * It doesn't check the validity of a contact or a peer.
 *
 * @author Giorgia Bortoletti
 */
public interface ContactConverterUtils<A extends Comparable<A>, P extends Peer<A>, C extends GenericContact<A, P>> {

    /**
     * Static method defining the conversion between an peer and a generic contact
     * that can be saved in the database
     * with address's contact equals to peer's address
     * and name's contact equals to empty string.
     *
     * @param peer the {@link P} to be converted
     * @return the {@link C} representation for the Peer (currently the address)
     */
    @TypeConverter
    static <A extends Comparable<A>, P extends Peer<A>, C extends GenericContact<A, P>> C contactFromPeer(P peer) {
        return null;
    }

    /**
     * Static method defining the conversion of a peer with a contact name
     * in a Contact field
     * that can be saved in the database.
     *
     * @param peer the {@link P} to be converted
     * @param name an optional String name for the contact
     * @return the {@link C} representation for the Peer (currently the address) with a
     * name
     */
    static <A extends Comparable<A>, P extends Peer<A>, C extends GenericContact<A, P>> C contactFromPeer(P peer, A name) {
        return null;
    }

    /**
     * Static method defining the conversion between a Contact saved in the database and a Peer.
     * Since only a valid Peer can be created an thus stored in the Database, no exceptions should
     * ever be thrown.
     *
     * @param contactToConvert the {@link C} to get its address as a {@link P}
     * @return the {@link P} created from the {@link C}
     */
    @TypeConverter
    static <A extends Comparable<A>, P extends Peer<A>, C extends GenericContact<A, P>> P peerFromContact(C contactToConvert) {
        return null;
    }

}
