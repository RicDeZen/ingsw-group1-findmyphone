package ingsw.group1.findmyphone.contacts;

import androidx.room.Dao;

/**
 * Interface extending the BaseDao class for Contact
 *
 * @author Giorgia Bortoletti
 */
@Dao
abstract class SMSContactDao extends BaseDao<SMSContact> {

    /**
     * @return the name of the table containing the {@link SMSContact} entities.
     */
    @Override
    public String getTableName() {
        return SMSContact.DEFAULT_TABLE_NAME;
    }


}