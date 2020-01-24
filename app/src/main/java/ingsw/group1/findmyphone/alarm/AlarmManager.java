package ingsw.group1.findmyphone.alarm;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import ingsw.group1.findmyphone.alarm.ParserAlarmMessage;

/**
 * Manager that starts an alarm with the default ringtone of this device
 * For the composition of request and response message
 * it uses methods of {@link ParserAlarmMessage}
 *
 * @author
 * @author Giorgia Bortoletti (refactoring)
 */
public class AlarmManager {

    final String AlarmManagerTag = "AlarmManagerTag";

    private MediaPlayer mediaPlayer;

    /**
     * Verify if the text receive contains the default audioAlarmMessageRequest sets in this class
     *
     * @param messageReceived the text message received
     * @return true if the received text contains the (formatted) alarm Request
     */
    public boolean isAlarmRequest(String messageReceived) {
        return ParserAlarmMessage.isAlarmRequest(messageReceived);
    }

    /**
     * Get an alarm request message composed by {@link ParserAlarmMessage}
     *
     * @return a formatted message for an alarm request
     */
    public String getAlarmRequestMessage() {
        return ParserAlarmMessage.composeRequestAlarm();
    }

    /**
     * Starts an alarm with the default ringtone of the device,
     * stops when activity is closed by user
     *
     * @param context application context
     */
    public void startAlarm(Context context) {

        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        AudioManager audioManager = (AudioManager) context.getSystemService((Context.AUDIO_SERVICE));
        try {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        } catch (Exception e) {
            Log.e(AlarmManagerTag, "Error in setStreamVolume: " + e.getMessage());
        }
        mediaPlayer.start();
    }

}
