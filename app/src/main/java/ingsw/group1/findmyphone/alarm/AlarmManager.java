package ingsw.group1.findmyphone.alarm;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

/**
 * Manager that starts an alarm with the default ringtone of this device
 * For the composition of request and response message
 * it uses methods of {@link AlarmMessageParser}
 *
 * @author
 * @author Giorgia Bortoletti (refactoring)
 */
public class AlarmManager {

    final String ALARM_MANAGER_TAG = "AlarmManagerTag";

    private MediaPlayer mediaPlayer;

    /**
     * Verify if the text receive contains the default audioAlarmMessageRequest sets in this class
     *
     * @param messageReceived the text message received
     * @return true if the received text contains the (formatted) alarm Request
     */
    public boolean isAlarmRequest(String messageReceived) {
        return AlarmMessageParser.isAlarmRequest(messageReceived);
    }

    /**
     * Get an alarm request message composed by {@link AlarmMessageParser}
     *
     * @return a formatted message for an alarm request
     */
    public String getAlarmRequestMessage() {
        return AlarmMessageParser.composeRequestAlarm();
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
