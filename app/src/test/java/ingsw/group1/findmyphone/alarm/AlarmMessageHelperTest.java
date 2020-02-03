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

    @Before
    public void createMessages(){
        alarmMessageRequest = AlarmMessageHelper.ALARM_MESSAGE_REQUEST;
        notAlarmMessageRequest = "REQUEST_ALARM";
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

}