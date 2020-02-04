package ingsw.group1.findmyphone.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import ingsw.group1.findmyphone.Manager;
import ingsw.group1.findmyphone.R;

/**
 *
 */
public class AlarmAndLocateResponseActivity extends AppCompatActivity {

    private Manager manager;
    public MediaPlayer mediaPlayer;

    /**
     * This activity is created in all situations, for each request, so it needs to be executed
     * also when screen is shut
     *
     * @param savedInstanceState system parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Needed to open Activity if screen is shut
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_alarm_and_locate);

        //Params passed by method that calls this activity
        //TODO this double way of reading the message is due to the fact the activity is started
        // by Manager and SMSReceiver in different ways. Should (and will, when we'll start using
        // the shared library) be removed.
        String receivedTextMessage;
        String receivedMessageAddress;
        SmsMessage[] intentMessages = Telephony.Sms.Intents.getMessagesFromIntent(getIntent());
        if (intentMessages != null && intentMessages.length > 0) {
            receivedTextMessage = intentMessages[0].getDisplayMessageBody();
            receivedMessageAddress = intentMessages[0].getDisplayOriginatingAddress();
        } else {
            receivedTextMessage =
                    getIntent().getStringExtra(ActivityConstantsUtils.RECEIVED_STRING_MESSAGE);
            receivedMessageAddress =
                    getIntent().getStringExtra(ActivityConstantsUtils.RECEIVED_STRING_ADDRESS);
        }
        manager = new Manager(getApplicationContext());
        manager.analyzeRequest(receivedTextMessage, receivedMessageAddress);

    }

    @Override
    protected void onDestroy() {
        manager.removeReceiveListener();
        if (mediaPlayer != null && mediaPlayer.isPlaying())
            mediaPlayer.stop();
        super.onDestroy();
    }
}
