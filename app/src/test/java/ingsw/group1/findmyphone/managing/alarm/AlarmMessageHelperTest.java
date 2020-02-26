package ingsw.group1.findmyphone.managing.alarm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for {@link AlarmMessageFormatter}
 *
 * @author Giorgia Bortoletti
 */
public class AlarmMessageHelperTest {

    private String alarmMessageRequest;
    private String notAlarmMessageRequest;
    private String alarmMessageResponse;
    private String notAlarmMessageResponse;
    private final long TIME = 10;

    @Before
    public void createMessages() {
        alarmMessageRequest = AlarmMessageFormatter.ALARM_MESSAGE_REQUEST;
        notAlarmMessageRequest = "REQUEST_ALARM";
        alarmMessageResponse = AlarmMessageFormatter.ALARM_MESSAGE_RESPONSE + TIME;
        notAlarmMessageResponse = "RESPONSE" + TIME;
    }

    //---------------------------- TESTS ----------------------------

    @Test
    public void isAlarmRequest() {
        Assert.assertTrue(AlarmMessageFormatter.isAlarmRequest(alarmMessageRequest));
        Assert.assertFalse(AlarmMessageFormatter.isAlarmRequest(notAlarmMessageRequest));
    }

    @Test
    public void composeRequestAlarm() {
        Assert.assertEquals(alarmMessageRequest, AlarmMessageFormatter.composeRequestAlarm());
    }

    @Test
    public void composeResponseAlarm() {
        Assert.assertEquals(alarmMessageResponse, AlarmMessageFormatter.composeResponseAlarm(TIME));
    }

    @Test(expected = NumberFormatException.class)
    public void getResponseTimeFiresException() {
        AlarmMessageFormatter.getResponseTime(AlarmMessageFormatter.ALARM_MESSAGE_RESPONSE + "Number");
    }

    @Test
    public void getResponseTime() {
        Assert.assertTrue(AlarmMessageFormatter.getResponseTime(AlarmMessageFormatter.composeResponseAlarm(TIME)) == TIME);
    }

    @Test
    public void getResponseTimeWrongResponse() {
        Assert.assertTrue(AlarmMessageFormatter.getResponseTime(notAlarmMessageResponse) < 0);
    }
}