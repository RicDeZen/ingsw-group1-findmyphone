package ingsw.group1.findmyphone.contacts;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.zip.CheckedOutputStream;

/**
 * Class entity represents a contact
 * A contact is identify by an address phone
 * and it can have a name.
 *
 * @author Giorgia Bortoletti
 */
@Entity(tableName = ContactDao.DEFAULT_TABLE_NAME)
public class Contact {

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
     * @param address a valid phone address of this contact
     * @param name the name of this contact
     */
    public Contact(@NonNull String address, @NonNull String name) {
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
        //Should not reach this return
        return false;
    }
}
