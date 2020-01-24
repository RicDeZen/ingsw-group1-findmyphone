package ingsw.group1.findmyphone.contacts;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Abstract class to allow Room library to instantiate the database.
 *
 * @author Giorgia Bortoletti
 */
@Database(entities = {Contact.class}, version = 1, exportSchema = false)
@TypeConverters({ContactConverter.class})
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDao access();
}