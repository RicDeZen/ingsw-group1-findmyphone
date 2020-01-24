package ingsw.group1.findmyphone.contacts;

import androidx.room.Dao;
import androidx.room.Ignore;

import ingsw.group1.msglibrary.SMSMessage;

/**
 * Interface extending the BaseDao class for Contact
 *
 * @author Giorgia Bortoletti
 */
@Dao
abstract class ContactDao extends BaseDao<Contact> {

    @Ignore
    public static final String DEFAULT_TABLE_NAME = "contacts_table";

    /**
     * @return the name of the table containing the {@link Contact} entities.
     */
    @Override
    public String getTableName(){
        return DEFAULT_TABLE_NAME;
    }


}