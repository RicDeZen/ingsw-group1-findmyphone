package ingsw.group1.findmyphone.alarm;

import android.location.Location;

/**
 * Manager that provides composition of message for alarm request and response
 * Message request is composed as: "ALARM_REQUEST"
 *
 * @author Giorgia Bortoletti
 */
public class AlarmMessageParser {

    static final String audioAlarmMessageRequest = "AUDIO_ALARM_REQUEST";
    static final String audioAlarmMessageResponse = "AUDIO_ALARM_RESPONSE";

    /**
     * Verify if the text receive contains the default audioAlarmMessageRequest sets in this class
     *
     * @param messageReceived the text message received
     * @return true if the received text contains the (formatted) alarm Request
     */
    static boolean isAlarmRequest(String messageReceived) {
        return messageReceived.contains(audioAlarmMessageRequest);
    }

    /**
     * Compose an alarm request message
     *
     * @return a formatted message for an alarm request
     */
    static String composeRequestAlarm() {
        return audioAlarmMessageRequest;
    }

}
