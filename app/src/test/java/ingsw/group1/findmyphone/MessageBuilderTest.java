package ingsw.group1.findmyphone;

import com.eis.smslibrary.RandomSMSPeerGenerator;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import org.junit.Before;
import org.junit.Test;

import ingsw.group1.findmyphone.alarm.AlarmManager;
import ingsw.group1.findmyphone.location.GeoPosition;
import ingsw.group1.findmyphone.location.LocationManager;

import static org.junit.Assert.assertEquals;

public class MessageBuilderTest {
    private double exampleLatitude = 111.111d;
    private double exampleLongitude = 99.999d;
    private long exampleTime = 10;
    private GeoPosition position;
    private LocationManager locationManager;
    private AlarmManager alarmManager;
    private SMSPeer peer;

    @Before
    public void init() {
        locationManager = new LocationManager();
        alarmManager = new AlarmManager();
        RandomSMSPeerGenerator randomSMSPeerGenerator = new RandomSMSPeerGenerator();
        peer = randomSMSPeerGenerator.generateValidPeer(SMSPeer.getDefaultRegion());
        position = new GeoPosition(exampleLatitude, exampleLongitude);
    }

    @Test
    public void getLocationRequest() {
        String locationRequestText = locationManager.getRequestLocationMessage();
        SMSMessage locationRequest = new SMSMessage(peer, locationRequestText);
        assertEquals(locationRequest, MessageBuilder.getLocationRequest(peer));
    }

    @Test
    public void getLocationResponse() {
        String locationResponseText = locationManager.getResponseMessage(position.getLocation());
        SMSMessage locationResponse = new SMSMessage(peer, locationResponseText);
        assertEquals(locationResponse, MessageBuilder.getLocationResponse(peer, position.getLocation()));
    }

    @Test
    public void getAlarmRequest() {
        String alarmRequestText = alarmManager.getAlarmRequestMessage();
        SMSMessage alarmRequest = new SMSMessage(peer, alarmRequestText);
        assertEquals(alarmRequest, MessageBuilder.getAlarmRequest(peer));
    }

    @Test
    public void getAlarmResponse() {
        String alarmResponseText = alarmManager.getAlarmResponseMessage(exampleTime);
        SMSMessage alarmResponse = new SMSMessage(peer, alarmResponseText);
        assertEquals(alarmResponse, MessageBuilder.getAlarmResponse(peer, exampleTime));
    }

    @Test
    public void getMessageType() {
        assertEquals(
                MessageBuilder.Type.ALARM_REQUEST,
                MessageBuilder.getMessageType(MessageBuilder.getAlarmRequest(peer)));
        assertEquals(
                MessageBuilder.Type.ALARM_RESPONSE,
                MessageBuilder.getMessageType(MessageBuilder.getAlarmResponse(peer, exampleTime)));
        assertEquals(
                MessageBuilder.Type.LOCATION_REQUEST,
                MessageBuilder.getMessageType(MessageBuilder.getLocationRequest(peer)));
        assertEquals(
                MessageBuilder.Type.LOCATION_RESPONSE,
                MessageBuilder.getMessageType(MessageBuilder.getLocationResponse(peer, position.getLocation())));
    }

    @Test
    public void getMessageTypeUNKNOWN() {
        assertEquals(MessageBuilder.Type.UNKNOWN, MessageBuilder.getMessageType("QWERTY"));
    }
}