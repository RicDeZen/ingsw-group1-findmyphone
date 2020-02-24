package ingsw.group1.findmyphone.alarm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for {@link AlarmMessageHelper}
 *
 * @author Giorgia Bortoletti
 */
public class AlarmMessageHelperTest {

    private String alarmMessageRequest;
    private String notAlarmMessageRequest;
    private String alarmMessageResponse;
    private String notAlarmMessageResponse;
    private double time;

    @Before
    public void createMessages(){
        alarmMessageRequest = AlarmMessageHelper.ALARM_MESSAGE_REQUEST;
        notAlarmMessageRequest = "REQUEST_ALARM";
        time = 10.0;
        alarmMessageResponse = AlarmMessageHelper.ALARM_MESSAGE_RESPONSE + time;
        notAlarmMessageResponse = "RESPONSE" + time;
    }

    //---------------------------- TESTS ----------------------------

    @Test
    public void isAlarmRequest() {
        Assert.assertTrue(AlarmMessageHelper.isAlarmRequest(alarmMessageRequest));
        Assert.assertFalse(AlarmMessageHelper.isAlarmRequest(notAlarmMessageRequest));
    }

    @Test
    public void composeRequestAlarm() {
        Assert.assertEquals(alarmMessageRequest, AlarmMessageHelper.composeRequestAlarm());
    }

    @Test
    public void composeResponseAlarm() {
        Assert.assertEquals(alarmMessageResponse, AlarmMessageHelper.composeResponseAlarm(time));
    }

    @Test(expected = NumberFormatException.class)
    public void getResponseTimeFiresException() {
        AlarmMessageHelper.getResponseTime(AlarmMessageHelper.ALARM_MESSAGE_RESPONSE + "Number");
    }

    @Test
    public void getResponseTime() {
        Assert.assertTrue(AlarmMessageHelper.getResponseTime(AlarmMessageHelper.composeResponseAlarm(time)) == time);
    }

    @Test
    public void getResponseTimeWrongResponse() {
        Assert.assertTrue(AlarmMessageHelper.getResponseTime(notAlarmMessageResponse) < 0);
    }
}