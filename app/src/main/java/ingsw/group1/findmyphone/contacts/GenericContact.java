package ingsw.group1.findmyphone.contacts;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import ingsw.group1.msglibrary.Peer;

/**
 * Abstract class represents a generic contact
 * A contact is identify by an address phone
 * and it can have a name.
 *
 * @param <P> The type of peer that contains a valid phone address.
 *
 * @author Giorgia Bortoletti
 */
public abstract class GenericContact<P extends Peer<String>> {

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
     * @param peer represents a {@link P} peer with a valid phone address for this contact
     * @param name the name of this contact
     */
    public GenericContact(@NonNull P peer, @NonNull String name) {
        this.address = peer.getAddress();
        this.name = name;
    }

    /**
     * Constructor
     * taking an address as peerAddress and name equals at nameContact
     *
     * @param address a valid phone address of this contact
     * @param name the name of this contact
     */
    public GenericContact(@NonNull String address, @NonNull String name) {
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
        if (obj == null || !(obj instanceof Contact))
            return false;
        if (obj == this)
            return true;
        Contact contactMaybeEquals = (Contact) obj;
        if (name.equals(contactMaybeEquals.getName())
                && address.equals(contactMaybeEquals.getAddress()))
            return true;
        return false;
    }

}
