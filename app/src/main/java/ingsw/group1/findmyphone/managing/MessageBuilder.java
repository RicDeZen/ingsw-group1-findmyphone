package ingsw.group1.findmyphone.managing;

import android.location.Location;

import androidx.annotation.NonNull;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import ingsw.group1.findmyphone.managing.alarm.AlarmManager;
import ingsw.group1.findmyphone.managing.location.LocationManager;

/**
 * Builder class useful for creating Request/Response messages
 *
 * @author Turcato
 */
public class MessageBuilder {

    public enum Type {
        LOCATION_RESPONSE,
        LOCATION_REQUEST,
        ALARM_RESPONSE,
        ALARM_REQUEST,
        UNKNOWN
    }

    /**
     * @param peer A valid SMSPeer
     * @return An SMSMessage containing a formatted Location Request for the given Peer
     */
    public static SMSMessage getLocationRequest(@NonNull SMSPeer peer) {
        LocationManager manager = new LocationManager();
        String text = manager.getRequestLocationMessage();

        return new SMSMessage(peer, text);
    }

    /**
     * @param peer     A valid SMSPeer
     * @param position A valid Location containing gps coordinates
     * @return An SMSMessage containing a formatted Location Response for the given Peer
     * containing the given location
     */
    public static SMSMessage getLocationResponse(@NonNull SMSPeer peer, @NonNull Location position) {
        LocationManager manager = new LocationManager();
        String text = manager.getResponseMessage(position);

        return new SMSMessage(peer, text);
    }

    /**
     * @param peer A valid SMSPeer
     * @return An SMSMessage containing a formatted Alarm Request for the given Peer
     */
    public static SMSMessage getAlarmRequest(@NonNull SMSPeer peer) {
        AlarmManager manager = new AlarmManager();
        String text = manager.getAlarmRequestMessage();

        return new SMSMessage(peer, text);
    }

    /**
     * @param peer A valid SMSPeer
     * @param time The amount of time the device rang (milliseconds)
     * @return An SMSMessage containing a formatted Alarm Response for the given Peer
     * containing the given amount of ringing time
     */
    public static SMSMessage getAlarmResponse(@NonNull SMSPeer peer, long time) {
        AlarmManager manager = new AlarmManager();
        String text = manager.getAlarmResponseMessage(time);

        return new SMSMessage(peer, text);
    }

    /**
     * @param message A valid SMSMessage
     * @return {@link MessageBuilder.Type} enum based on the content of the given message
     */
    public static Type getMessageType(@NonNull SMSMessage message) {
        return getMessageType(message.getData());
    }

    /**
     * @param message A text message
     * @return {@link MessageBuilder.Type} enum based on the content of the given message
     */
    public static Type getMessageType(@NonNull String message) {
        LocationManager locationManager = new LocationManager();
        AlarmManager alarmManager = new AlarmManager();
        if (locationManager.isLocationRequest(message))
            return Type.LOCATION_REQUEST;
        if (locationManager.isLocationResponse(message))
            return Type.LOCATION_RESPONSE;
        if (alarmManager.isAlarmRequest(message))
            return Type.ALARM_REQUEST;
        if (alarmManager.isAlarmResponse(message))
            return Type.ALARM_RESPONSE;
        return Type.UNKNOWN;
    }
}
