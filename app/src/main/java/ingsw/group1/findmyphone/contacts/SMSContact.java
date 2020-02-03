package ingsw.group1.findmyphone.contacts;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import ingsw.group1.msglibrary.SMSPeer;

/**
 * This class represents a contact entity for the {@link SMSContactDatabase}
 * and it implements interface {@link GenericContact}
 * with address of type String and peer of type {@link SMSPeer}.
 *
 * @author Giorgia Bortoletti
 */
@Entity(tableName = SMSContact.DEFAULT_TABLE_NAME)
public class SMSContact implements GenericContact<String, SMSPeer> {

    @Ignore
    public static final String DEFAULT_TABLE_NAME = "contact_table";
    @Ignore
    public static final String ADDRESS_COLUMN_NAME = "address";
    @Ignore
    public static final String NAME_COLUMN_NAME = "name";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = ADDRESS_COLUMN_NAME)
    private String address; //address: phone number

    @NonNull
    @ColumnInfo(name = NAME_COLUMN_NAME)
    private String name; //name: contact name

    /**
     * Constructor
     * taking an address as peerAddress and name equals at nameContact
     *
     * @param peer represents a {@link SMSPeer} with a valid phone address for this contact
     * @param name the name of this contact
     */
    public SMSContact(@NonNull SMSPeer peer, @NonNull String name) {
        this.address = peer.getAddress();
        this.name = name;
    }

    /**
     * Constructor
     * taking an address as peerAddress and name equals at nameContact
     *
     * @param address a valid phone address for this contact
     * @param name the name of this contact
     */
    public SMSContact(@NonNull String address, @NonNull String name) {
        this.address = address;
        this.name = name;
    }

    /**
     * Return this address contact
     *
     * @return a String represents the address contact
     */
    public String getAddress(){ return address; }

    /**
     * Return the name of this contact
     *
     * @return a String represents the name of this contact
     */
    public String getName(){ return name; }

    /**
     * Set to change contact name
     *
     * @param newName new contact name
     */
    public void setName(String newName){ name = newName; }

    /**
     * Equals
     *
     * @param obj       the reference object with which to compare.
     *
     * @return true if this object is equal to obj argument, false otherwise.
     */
    public boolean equals(@NonNull Object obj) {
        if (obj == null || !(obj instanceof SMSContact))
            return false;
        if (obj == this)
            return true;
        SMSContact contactMaybeEquals = (SMSContact) obj;
        if (name.equals(contactMaybeEquals.getName())
                && address.equals(contactMaybeEquals.getAddress()))
            return true;
        return false;
    }


}
