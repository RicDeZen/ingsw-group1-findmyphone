package ingsw.group1.findmyphone.contacts;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import ingsw.group1.msglibrary.SMSPeer;

import static org.junit.Assert.*;

public class SMSContactDatabaseTest {

    private static final String CONTACT_VALID_ADDRESS = "+393478989890"; //for contact and peer
    private static final String CONTACT_VALID_NAME = "NewContact";

    private SMSContactDatabase contactDatabase;
    private SMSContact contact; //database's entity

    @Before
    public void createDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        contactDatabase = Room.inMemoryDatabaseBuilder(context, SMSContactDatabase.class).build();

        SMSPeer peerTest = new SMSPeer(CONTACT_VALID_ADDRESS);
        contact = new SMSContact(peerTest, CONTACT_VALID_NAME);
    }

    @After
    public void closeDatabase() throws IOException {
        contactDatabase.close();
    }

    //---------------------------- TESTS ----------------------------

    @Test
    public void getTableName(){
        assertEquals(SMSContact.DEFAULT_TABLE_NAME, contactDatabase.access().getTableName());
    }

    @Test
    public void insertContact(){
        contactDatabase.access().insert(contact);

        assertTrue(contactDatabase.access().getAll().contains(contact));
    }

    @Test
    public void deleteContact(){
        contactDatabase.access().insert(contact);
        contactDatabase.access().delete(contact);

        assertFalse(contactDatabase.access().getAll().contains(contact));
    }


}