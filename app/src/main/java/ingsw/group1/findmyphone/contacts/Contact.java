package ingsw.group1.findmyphone.contacts;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import ingsw.group1.msglibrary.SMSPeer;

/**
 * Class entity represents a contact
 * A contact is identify by an address phone
 * and it can have a name.
 *
 * @author Giorgia Bortoletti
 */
@Entity(tableName = ContactDao.DEFAULT_TABLE_NAME)
public class Contact extends GenericContact<SMSPeer> {

    /**
     * Constructor
     * taking an address as peerAddress and name equals at nameContact
     *
     * @param peer represents a {@link SMSPeer} with a valid phone address for this contact
     * @param name the name of this contact
     */
    public Contact(@NonNull SMSPeer peer, @NonNull String name) {
        super(peer, name);
    }

    /**
     * Constructor
     * taking an address as peerAddress and name equals at nameContact
     *
     * @param address a valid phone address for this contact
     * @param name the name of this contact
     */
    public Contact(@NonNull String address, @NonNull String name) {
        super(address, name);
    }


}
