package ingsw.group1.findmyphone.alarm;

/**
 * Parser class of static methods that provides composition of message for alarm request and response
 * Message request is composed as:
 * {@link AlarmMessageHelper#ALARM_MESSAGE_REQUEST}
 *
 * @author Giorgia Bortoletti
 */
public class AlarmMessageHelper {

    protected static final String ALARM_MESSAGE_REQUEST = "AUDIO_ALARM_REQUEST";

    /**
     * Verify if the text receive contains the default audioAlarmMessageRequest sets in this class
     *
     * @param messageReceived the text message received
     * @return true if the received text contains the (formatted) alarm Request
     */
    protected static boolean isAlarmRequest(String messageReceived) {
        return messageReceived.contains(ALARM_MESSAGE_REQUEST);
    }

    /**
     * Compose an alarm request message
     *
     * @return a formatted message for an alarm request
     */
    protected static String composeRequestAlarm() {
        return ALARM_MESSAGE_REQUEST;
    }

}
