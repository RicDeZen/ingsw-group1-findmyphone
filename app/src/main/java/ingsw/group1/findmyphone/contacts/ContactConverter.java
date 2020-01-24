package ingsw.group1.findmyphone.contacts;

import androidx.room.TypeConverter;

import ingsw.group1.msglibrary.SMSPeer;

/**
 * Class of static methods necessary to convert {@link SMSPeer} to {@link Contact} and vice versa.
 * It doesn't check the validity of a contact or peer.
 *
 * @author Giorgia Bortoletti
 */
class ContactConverter {
    /**
     * Static method defining the conversion between an SMSPeer and a Contact field
     * that can be saved in the database
     * with address's contact equals to peer's address
     * and name's contact equals to empty string.
     *
     * @param peer the {@link SMSPeer} to be converted
     *
     * @return the {@link Contact} representation for the Peer (currently the address)
     */
    @TypeConverter
    public static Contact contactFromSMSPeer(SMSPeer peer) {
        return new Contact(peer.getAddress(), "");
    }

    /**
     * Static method defining the conversion between an SMSPeer with a contact name and a Contact field
     * that can be saved in the database.
     *
     * @param peer the {@link SMSPeer} to be converted
     * @param name an optional String name for the contact
     *
     * @return the {@link Contact} representation for the Peer (currently the address) with a name
     */
    public static Contact contactFromSMSPeer(SMSPeer peer, String name) {
        return new Contact(peer.getAddress(), name);
    }

    /**
     * Static method defining the conversion between a Contact saved in the database and an SMSPeer.
     * Since only a valid Peer can be created an thus stored in the Database, no exceptions should
     * ever be thrown.
     *
     * @param contactToConvert the {@link Contact} to get its address as a {@link SMSPeer}
     *
     * @return the {@link SMSPeer} created from the {@link Contact}
     */
    @TypeConverter
    public static SMSPeer peerFromContact(Contact contactToConvert) {
        return new SMSPeer(contactToConvert.getAddress());
    }

    /**
     * Static method defining the conversion between a Contact saved in the database and an String.
     * String represents contact's name.
     *
     * @param contactToConvert the {@link Contact} to get its as a String
     *
     * @return the String name of the {@link Contact} contactToConvert
     */
    @TypeConverter
    public static String nameFromContact(Contact contactToConvert) {
        return contactToConvert.getName();
    }

}
