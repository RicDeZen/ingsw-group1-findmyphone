package ingsw.group1.findmyphone.event;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.random.RandomSMSContactGenerator;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link SMSLogDatabase}. Each tests assumes to be starting with a newly
 * created, or previously emptied instance of the database.
 *
 * @author Riccardo De Zen.
 */
@Config(sdk = 28)
@RunWith(RobolectricTestRunner.class)
public class SMSLogDatabaseTest {

    private static final String DEFAULT_DB_NAME = "test-db";
    private static final String ALTERNATIVE_DB_NAME = "another-db";
    /**
     * The number of actions in {@link SMSLogDatabaseTest#doSomeActions(SMSLogDatabase)} that
     * should trigger an Observer event.
     */
    private static final int N_OF_ACTIONS = 6;

    private static final SMSContact EXAMPLE_CONTACT =
            new RandomSMSContactGenerator().getRandomContact();

    private static final SMSLogEvent SIMPLE_EVENT = new SMSLogEvent(
            EventType.UNKNOWN,
            EXAMPLE_CONTACT,
            100L,
            "Hello"
    );
    private static final SMSLogEvent ANOTHER_EVENT = new SMSLogEvent(
            EventType.RING_REQUEST_RECEIVED,
            EXAMPLE_CONTACT,
            10000L,
            String.valueOf(1000L)
    );
    private static final List<SMSLogEvent> SOME_EVENTS = Arrays.asList(
            SIMPLE_EVENT, ANOTHER_EVENT
    );

    private SMSLogDatabase database;

    @Rule
    public MockitoRule mockRule = MockitoJUnit.rule();

    @Mock
    private EventObserver<SMSLogEvent> mockObserver;

    /**
     * Loading an instance of the database.
     */
    @Before
    public void loadDatabase() {
        database = SMSLogDatabase.getInstance(
                ApplicationProvider.getApplicationContext(),
                DEFAULT_DB_NAME
        );
    }

    /**
     * Just a facade call to allow actual clearing method to be static, to be used in any other
     * tests that need it.
     */
    @After
    public void clearInstances() {
        clearActiveInstances();
    }

    /**
     * This method is necessary, due to an issue with ObjectPools where static instances of the
     * database cannot be reset between tests automatically. Method is static to allow using it
     * from other test classes if necessary.
     */
    public static void clearActiveInstances() {
        try {
            //Getting the field named "activeInstances" (the map with all active databases)
            Field instance = SMSLogDatabase.class.getDeclaredField("activeInstances");
            //Making it accessible
            instance.setAccessible(true);
            //Casting it to a Map
            Map<?, ?> privateField = (Map<?, ?>) instance.get(null);
            //Making it empty
            if (privateField != null)
                privateField.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test confirming same name results in same instance.
     */
    @Test
    public void sameNamesAreSame() {
        SMSLogDatabase defaultInstance = SMSLogDatabase.getInstance(
                ApplicationProvider.getApplicationContext(),
                DEFAULT_DB_NAME
        );
        SMSLogDatabase alsoDefaultInstance = SMSLogDatabase.getInstance(
                ApplicationProvider.getApplicationContext(),
                DEFAULT_DB_NAME
        );
        assertSame(defaultInstance, alsoDefaultInstance);
    }

    /**
     * Test confirming equals returns true for instances that are the same Object.
     */
    @Test
    public void sameNamesAreEqual() {
        SMSLogDatabase defaultInstance = SMSLogDatabase.getInstance(
                ApplicationProvider.getApplicationContext(),
                DEFAULT_DB_NAME
        );
        SMSLogDatabase alsoDefaultInstance = SMSLogDatabase.getInstance(
                ApplicationProvider.getApplicationContext(),
                DEFAULT_DB_NAME
        );
        assertTrue(
                defaultInstance.equals(alsoDefaultInstance) &&
                        defaultInstance.hashCode() == alsoDefaultInstance.hashCode()
        );
    }

    /**
     * Test confirming hashcode returns same value for equal instances.
     */
    @Test
    public void equalInstancesShareHashcode() {
        SMSLogDatabase defaultInstance = SMSLogDatabase.getInstance(
                ApplicationProvider.getApplicationContext(),
                DEFAULT_DB_NAME
        );
        SMSLogDatabase alsoDefaultInstance = SMSLogDatabase.getInstance(
                ApplicationProvider.getApplicationContext(),
                DEFAULT_DB_NAME
        );
        assertEquals(defaultInstance.hashCode(), alsoDefaultInstance.hashCode());
    }

    /**
     * Test confirming different name results in different Objects.
     */
    @Test
    public void differentNamesAreNotSame() {
        SMSLogDatabase anInstance = SMSLogDatabase.getInstance(
                ApplicationProvider.getApplicationContext(),
                DEFAULT_DB_NAME
        );
        SMSLogDatabase anotherInstance = SMSLogDatabase.getInstance(
                ApplicationProvider.getApplicationContext(),
                ALTERNATIVE_DB_NAME
        );
        assertNotSame(anInstance, anotherInstance);
    }

    /**
     * Test confirming instances with different names are also not equal.
     */
    @Test
    public void differentNamesAreNotEqual() {
        SMSLogDatabase anInstance = SMSLogDatabase.getInstance(
                ApplicationProvider.getApplicationContext(),
                DEFAULT_DB_NAME
        );
        SMSLogDatabase anotherInstance = SMSLogDatabase.getInstance(
                ApplicationProvider.getApplicationContext(),
                ALTERNATIVE_DB_NAME
        );
        assertNotEquals(anInstance, anotherInstance);
    }

    /**
     * Testing whether the database actually starts empty.
     */
    @Test
    public void startsEmpty() {
        final int expectedStartCount = 0;
        assertEquals(expectedStartCount, database.count());
    }

    /**
     * Testing single insertion.
     */
    @Test
    public void canAddOne() {
        final int expectedCount = 1;
        assertTrue(
                //Add method returns true and only one item was added
                database.addEvent(SIMPLE_EVENT) && expectedCount == database.count()
        );
    }

    /**
     * Testing the insertion is actually correct, through
     * {@link SMSLogDatabase#contains(SMSLogEvent)}.
     */
    @Test
    public void canAddOneAndItsCorrect() {
        assertTrue(
                //Add method returns true and the item is actually present
                database.addEvent(SIMPLE_EVENT) && database.contains(SIMPLE_EVENT)
        );
    }

    /**
     * Testing multiple insertions.
     */
    @Test
    public void canAddMultiple() {
        final int expectedCount = SOME_EVENTS.size();
        assertTrue(
                //Multiple additions all return true and the final count is the expected value
                !database.addEvents(SOME_EVENTS).containsValue(Boolean.FALSE) &&
                        expectedCount == database.count()
        );
    }

    /**
     * Testing multiple insertions are actually correct, through
     * {@link SMSLogDatabase#getAllEvents()}.
     */
    @Test
    public void canAddMultipleAndTheyAreCorrect() {
        if (database.addEvents(SOME_EVENTS).containsValue(Boolean.FALSE)) fail();
        List<SMSLogEvent> containedEvents = database.getAllEvents();
        for (SMSLogEvent eachEvent : SOME_EVENTS) {
            if (!containedEvents.contains(eachEvent))
                fail();
        }
    }

    /**
     * Testing one removal.
     */
    @Test
    public void canRemoveOne() {
        final int expectedCount = 0;
        database.addEvent(SIMPLE_EVENT);
        database.removeEvent(SIMPLE_EVENT);
        assertEquals(expectedCount, database.count());
    }

    /**
     * Testing multiple removals.
     */
    @Test
    public void canRemoveMultiple() {
        final int expectedCount = 0;
        database.addEvents(SOME_EVENTS);
        database.removeEvents(SOME_EVENTS);
        assertEquals(expectedCount, database.count());
    }

    // OBSERVING TESTS -----------------------------------------------------------------------------
    // Due to technical limitations of Mockito, onChange() method of the database cannot be
    // tested directly.

    /**
     * Testing the database instance notifies its registered observers when he is notified by the
     * on-disk database.
     */
    @Test
    public void notifiesObserversOnChange() {
        SMSLogDatabase spyDatabase = Mockito.spy(database);
        spyDatabase.addObserver(mockObserver);
        doSomeActions(spyDatabase);
        verify(mockObserver, times(N_OF_ACTIONS)).onChanged(database);
    }

    /**
     * Testing the database instance notifies its registered observers when he is notified by the
     * on-disk database.
     */
    @Test
    public void forgottenObserversAreNotNotified() {
        SMSLogDatabase spyDatabase = Mockito.spy(database);
        spyDatabase.addObserver(mockObserver);
        spyDatabase.removeObserver(mockObserver);
        doSomeActions(spyDatabase);
        verify(mockObserver, times(0)).onChanged(database);
    }

    /**
     * Method to perform some example actions on a Database.
     *
     * @param db The database to use.
     */
    private void doSomeActions(SMSLogDatabase db) {
        //1 interaction
        db.addEvent(SIMPLE_EVENT);
        //1 interaction
        db.addEvent(ANOTHER_EVENT);
        //1 interaction
        db.clear();
        //2 interactions
        db.addEvents(SOME_EVENTS);
        //1 interaction
        db.clear();
    }

}