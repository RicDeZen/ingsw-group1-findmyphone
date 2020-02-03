package ingsw.group1.findmyphone.contacts;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Abstract class to allow Room library to instantiate the database.
 *
 * @author Giorgia Bortoletti
 */
@Database(entities = {SMSContact.class}, version = 1, exportSchema = false)
@TypeConverters({SMSContactConverter.class})
public abstract class SMSContactDatabase extends RoomDatabase {
    public abstract SMSContactDao access();
}