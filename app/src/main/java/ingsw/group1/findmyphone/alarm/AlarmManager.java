package ingsw.group1.findmyphone.alarm;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

/**
 * Manager that starts an alarm with the default ringtone of this device
 * For the composition of request and response message
 * it uses methods of {@link AlarmMessageHelper}
 *
 * @author Turcato
 * @author Giorgia Bortoletti (refactoring)
 */
public class AlarmManager {

    public final String ALARM_MANAGER_TAG = "AlarmManagerTag";

    public MediaPlayer mediaPlayer;

    /**
     * Verify if the text receive contains the default audioAlarmMessageRequest sets in this class
     *
     * @param messageReceived   The received text message
     * @return True if the received text contains the (formatted) alarm Request
     */
    public boolean isAlarmRequest(String messageReceived) {
        return AlarmMessageHelper.isAlarmRequest(messageReceived);
    }

    /**
     * Get an alarm request message composed by {@link AlarmMessageHelper}
     *
     * @return a formatted message for an alarm request
     */
    public String getAlarmRequestMessage() {
        return AlarmMessageHelper.composeRequestAlarm();
    }

    /**
     * Get an alarm response message composed by {@link AlarmMessageHelper}
     *
     * @return a formatted message for an alarm response indicating the time the device rang
     */
    public String getAlarmResponseMessage(double time) {
        return AlarmMessageHelper.composeResponseAlarm(time);
    }

    /**
     * @param messageReceived The received text message
     * @return The amount of time the device rang, if the received message is formatted correctly, otherwise -1
     */
    public double getResponseAlarmTime(String messageReceived) {
        return AlarmMessageHelper.getResponseTime(messageReceived);
    }

    /**
     * Starts an alarm with the default ringtone of the device,
     * stops when activity is closed by user
     *
     * @param context application context
     */
    //TODO? add timer for audio alarm
    public void startAlarm(Context context) {
        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        AudioManager audioManager = (AudioManager) context.getSystemService((Context.AUDIO_SERVICE));
        try {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        } catch (Exception e) {
            Log.e(ALARM_MANAGER_TAG, "Error in setStreamVolume: " + e.getMessage());
        }
        mediaPlayer.start();
    }

}
