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
    private final double TIME = 10;

    @Before
    public void createMessages(){
        alarmMessageRequest = AlarmMessageHelper.ALARM_MESSAGE_REQUEST;
        notAlarmMessageRequest = "REQUEST_ALARM";
        alarmMessageResponse = AlarmMessageHelper.ALARM_MESSAGE_RESPONSE + TIME;
        notAlarmMessageResponse = "RESPONSE" + TIME;
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
        Assert.assertEquals(alarmMessageResponse, AlarmMessageHelper.composeResponseAlarm(TIME));
    }

    @Test(expected = NumberFormatException.class)
    public void getResponseTimeFiresException() {
        AlarmMessageHelper.getResponseTime(AlarmMessageHelper.ALARM_MESSAGE_RESPONSE + "Number");
    }

    @Test
    public void getResponseTime() {
        Assert.assertTrue(AlarmMessageHelper.getResponseTime(AlarmMessageHelper.composeResponseAlarm(TIME)) == TIME);
    }

    @Test
    public void getResponseTimeWrongResponse() {
        Assert.assertTrue(AlarmMessageHelper.getResponseTime(notAlarmMessageResponse) < 0);
    }
}