package ingsw.group1.findmyphone.contacts;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import com.eis.smslibrary.SMSPeer;


/**
 * Class of static methods necessary to convert {@link SMSPeer} to {@link SMSContact} and vice versa.
 * It doesn't check the validity of a contact or a peer.
 *
 * @author Giorgia Bortoletti
 */
class SMSContactConverterUtils implements ContactConverterUtils<String, SMSPeer, SMSContact> {

    /**
     * Static method defining the conversion between an SMSPeer and a Contact field
     * that can be saved in the database
     * with address's contact equals to peer's address
     * and name's contact equals to empty string.
     *
     * @param peer  The {@link SMSPeer} to be converted.
     * @return the {@link SMSContact} representation for the Peer (currently the address).
     */
    @TypeConverter
    public static SMSContact contactFromPeer(@NonNull SMSPeer peer) {
        return new SMSContact(peer, "");
    }

    /**
     * Static method defining the conversion between an SMSPeer with a contact name and a Contact
     * field that can be saved in the database.
     *
     * @param peer  The {@link SMSPeer} to be converted.
     * @param name  An optional String name for the contact.
     * @return the {@link SMSContact} representation for the Peer (currently the address) with a
     * name.
     */
    public static SMSContact contactFromPeer(@NonNull SMSPeer peer, @NonNull String name) {
        return new SMSContact(peer, name);
    }

    /**
     * Static method defining the conversion between a Contact saved in the database and an SMSPeer.
     * Since only a valid Peer can be created an thus stored in the Database, no exceptions should
     * ever be thrown.
     *
     * @param contactToConvert The {@link SMSContact} to get its address as a {@link SMSPeer}.
     * @return the {@link SMSPeer} created from the {@link SMSContact}.
     */
    @TypeConverter
    public static SMSPeer peerFromContact(@NonNull SMSContact contactToConvert) {
        return new SMSPeer(contactToConvert.getAddress());
    }

}
