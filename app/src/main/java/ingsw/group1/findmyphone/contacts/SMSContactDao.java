package ingsw.group1.findmyphone.contacts;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

/**
 * Interface extending the {@link BaseDao} class for {@link SMSContact} entity
 *
 * @author Giorgia Bortoletti
 */
@Dao
abstract class SMSContactDao extends BaseDao<SMSContact> {

    private static final String TABLE_NAME = SMSContact.DEFAULT_TABLE_NAME;
    /**
     * String defining the query for a search on the contacts' address.
     */
    private static final String CONTACT_FIND_QUERY =
            "SELECT * FROM " + TABLE_NAME + " WHERE " + SMSContact.ADDRESS_COLUMN_NAME + " IN (";


    //---------------------------- OVERWRITTEN METHODS ----------------------------

    /**
     * This method overwrites method in {@link BaseDao}
     * because it is useful that contacts are always sorted alphabetically
     * and regardless of upper and lower case.
     *
     * @return all the {@link SMSContact} in the table sorted alphabetically.
     */
    @Override
    @Query(GET_ALL_QUERY + TABLE_NAME + " ORDER BY " + SMSContact.NAME_COLUMN_NAME + " COLLATE NOCASE")
    public abstract List<SMSContact> getAll();

    /**
     * @return the name of the table containing the {@link SMSContact} entities.
     */
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    //---------------------------- ADDITIONAL METHODS ----------------------------

    /**
     * Method searching the table of contacts and returning the ones that match the given addresses.
     * This is done by calling a {@link androidx.room.RawQuery} method.
     *
     * @param addresses The addresses to match.
     * @return The contacts that match the given addresses.
     */
    @Query(CONTACT_FIND_QUERY + ":addresses)")
    public abstract List<SMSContact> getContactsForAddresses(String... addresses);

}