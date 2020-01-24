package ingsw.group1.findmyphone.deprecated;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import ingsw.group1.findmyphone.Constants;
import ingsw.group1.findmyphone.Manager;
import ingsw.group1.findmyphone.R;

/**
 * @deprecated
 */
public class AlarmAndLocateResponseActivity extends AppCompatActivity {
    private final String AlarmAndLocateActivityTAG = "Alarm&LocateActivityTAG";
    private String receivedTextMessage;
    private String receivedMessageAddress;
    private Manager manager;
    private MediaPlayer mediaPlayer;


    /**
     * This activity is created in all situations, for each request, so it needs to be executed also when screen is shut
     *
     * @param savedInstanceState system parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Needed to open Activity if screen is shut
        final Window win= getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_alarm_and_locate);

        //Params passed by method that calls this activity
        receivedTextMessage = getIntent().getStringExtra(Constants.receivedStringMessage);
        receivedMessageAddress = getIntent().getStringExtra(Constants.receivedStringAddress);
        manager=new Manager(getApplicationContext());
        manager.analyzeRequest(receivedTextMessage,receivedMessageAddress);

    }

    @Override
    protected void onDestroy() {
        manager.removeReceiveListener();
        if(mediaPlayer != null && mediaPlayer.isPlaying())
            mediaPlayer.stop();
        super.onDestroy();
    }
}
