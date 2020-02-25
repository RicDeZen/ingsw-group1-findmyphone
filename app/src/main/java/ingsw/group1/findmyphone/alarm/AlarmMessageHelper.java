package ingsw.group1.findmyphone.alarm;

import androidx.annotation.NonNull;

/**
 * Parser class of static methods that provides composition of message for alarm request and response
 * Message request is composed as:
 * {@link AlarmMessageHelper#ALARM_MESSAGE_REQUEST}
 *
 * @author Giorgia Bortoletti
 * @author Turcato
 */
public class AlarmMessageHelper {

    protected static final String ALARM_MESSAGE_REQUEST = "AUDIO_ALARM_REQUEST";
    protected static final String ALARM_MESSAGE_RESPONSE = "AUDIO_ALARM_RESPONSE";

    /**
     * Verify if the received text contains the default audioAlarmMessageRequest sets in this class
     *
     * @param messageReceived the text message received
     * @return true if the received text contains the (formatted) alarm Request
     */
    protected static boolean isAlarmRequest(@NonNull String messageReceived) {
        return messageReceived.contains(ALARM_MESSAGE_REQUEST);
    }

    /**
     * Verify if the received text contains an audioAlarmMessageResponse
     *
     * @param messageReceived the text message received
     * @return true if the received text contains the (formatted) alarm ReResponsequest
     */
    protected static boolean isAlarmResponse(@NonNull String messageReceived) {
        return messageReceived.contains(ALARM_MESSAGE_RESPONSE);
    }

    /**
     * Compose an alarm request message
     *
     * @return a formatted message for an alarm request
     */
    protected static String composeRequestAlarm() {
        return ALARM_MESSAGE_REQUEST;
    }


    /**
     * Compose an alarm response message that informs of the amount time the device rang
     *
     * @param time The amount time the the device rang (milliseconds)
     * @return The correctly composed alarm response
     */
    protected static String composeResponseAlarm(long time) {
        return ALARM_MESSAGE_RESPONSE + time;
    }

    /**
     * Verify if the received text message is an alarm response and returns the amount time the device rang
     * written in the message
     *
     * @param messageReceived The text message received
     * @return The amount time (milliseconds) the device rang (date from the message), if the received
     * text doesn't contain the (formatted) alarm Response returns -1
     * @throws NumberFormatException If the message does not contain a parsable double
     */
    protected static long getResponseTime(@NonNull String messageReceived) {
        if (messageReceived.contains(ALARM_MESSAGE_RESPONSE))
            return Long.parseLong(messageReceived.substring(ALARM_MESSAGE_RESPONSE.length()));
        else
            return -1;
    }

}
