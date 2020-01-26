package ingsw.group1.findmyphone.alarm;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for {@link AlarmMessageParser}
 *
 * @author Giorgia Bortoletti
 */
public class AlarmMessageParserTest {

    private String alarmMessageRequest;
    private String notAlarmMessageRequest;

    @Before
    public void createMessages(){
        alarmMessageRequest = AlarmMessageParser.ALARM_MESSAGE_REQUEST;
        notAlarmMessageRequest = "REQUEST_ALARM";
    }

    //---------------------------- TESTS ----------------------------

    @Test
    public void isAlarmRequest() {
        assertTrue(AlarmMessageParser.isAlarmRequest(alarmMessageRequest));
        assertFalse(AlarmMessageParser.isAlarmRequest(notAlarmMessageRequest));
    }

    @Test
    public void composeRequestAlarm() {
        assertEquals(alarmMessageRequest, AlarmMessageParser.composeRequestAlarm());
    }

}