package ingsw.group1.findmyphone.contacts;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

/**
 * Interface extending the BaseDao class for Contact
 *
 * @author Giorgia Bortoletti
 */
@Dao
abstract class SMSContactDao extends BaseDao<SMSContact> {

    private static final String TABLE_NAME = SMSContact.DEFAULT_TABLE_NAME;
    /**
     * String defining the query for a search.
     * Should be formatted via {@link String#format(String, Object...)} with the following
     * parameters, in order:
     * - Table name, should be the result of {@link SMSContactDao#getTableName()}.
     * - Column name, must be a column of the table.
     * - Method parameter name (can be an array) due to "IN".
     */
    private static final String CONTACT_FIND_QUERY =
            "SELECT * FROM " + TABLE_NAME + " WHERE " + SMSContact.ADDRESS_COLUMN_NAME + " IN (";

    /**
     * @return the name of the table containing the {@link SMSContact} entities.
     */
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    /**
     * Method searching the table of contacts and returning the ones that match the given addresses.
     * This is done by calling a {@link androidx.room.RawQuery} method.
     *
     * @param addresses The addresses to match.
     * @return The
     */
    @NonNull
    @Query(CONTACT_FIND_QUERY + ":addresses)")
    public abstract List<SMSContact> getContactsForAddresses(String... addresses);
}