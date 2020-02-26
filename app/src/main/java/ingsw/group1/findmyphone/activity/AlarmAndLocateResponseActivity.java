package ingsw.group1.findmyphone.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import ingsw.group1.findmyphone.R;

/**
 * @author Pardeep Kumar
 * @author Turcato
 */
public class AlarmAndLocateResponseActivity extends AppCompatActivity {

    public MediaPlayer mediaPlayer;

    /**
     * This activity is created in all situations, for each request, so it needs to be executed
     * also when the screen is shut.
     *
     * @param savedInstanceState system parameter.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Needed to open Activity if screen is shut
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_alarm_and_locate);

    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null && mediaPlayer.isPlaying())
            mediaPlayer.stop();
        super.onDestroy();
    }
}
