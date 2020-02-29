package ingsw.group1.findmyphone.event;

import androidx.test.core.app.ApplicationProvider;

import com.eis.smslibrary.SMSPeer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertEquals;

@Config(sdk = 28)
@RunWith(RobolectricTestRunner.class)
public class EventManagerTest {

    private SMSLogDatabase eventManagerDatabase = SMSLogDatabase.getInstance(ApplicationProvider.getApplicationContext(), EventManager.EVENT_DATABASE);
    private EventManager eventManager = EventManager.getInstance(ApplicationProvider.getApplicationContext());
    private String passwordToSendSMS = "nonlosociao";
    private String passwordInExtra = EventManager.PASSWORD_TAG + "nonlosociao";


    /**
     * Asserts that findPassword returns the password if the given smsPeer is actually in the eventManager and hi's extra are not null.
     */
    @Test
    public void testFindPasswordEquals() {
        SMSPeer smsPeer = new SMSPeer("+393888624978");
        long defaultTime = 0L;
        SMSLoggableEvent smsLoggableEvent = new SMSLoggableEvent(EventType.LOCATION_REQUEST_SENT, smsPeer.getAddress(), defaultTime, passwordInExtra);
        eventManagerDatabase.addEvent(smsLoggableEvent);
        assertEquals(passwordToSendSMS, eventManager.findPassword(smsPeer));
    }

    /**
     * Asserts that findPassword doesn't return the password if the given smsPeer isn't actually in the eventManager.
     */
    @Test
    public void testFindPasswordNotEquals() {
        String correctNumber = "+393888624978";
        String wrongNumber = "+393888624977";
        SMSPeer correctPeer = new SMSPeer(correctNumber);
        SMSPeer wrongPeer = new SMSPeer(wrongNumber);
        long defaultTime = 0L;
        String passwordNotFound = EventManager.NO_PASSWORD_FOUND_ERROR;
        SMSLoggableEvent smsLoggableEvent = new SMSLoggableEvent(EventType.LOCATION_REQUEST_SENT, correctPeer.getAddress(), defaultTime, passwordInExtra);
        eventManagerDatabase.addEvent(smsLoggableEvent);
        assertEquals(passwordNotFound, eventManager.findPassword(wrongPeer));
    }
}
