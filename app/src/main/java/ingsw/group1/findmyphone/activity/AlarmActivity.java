package ingsw.group1.findmyphone.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.RingService;

/**
 * @author Riccardo De Zen.
 */
public class AlarmActivity extends AppCompatActivity {

    private RingService service;

    /**
     * Anonymous class defining the connection to the service.
     */
    private ServiceConnection connectionToRingService = new ServiceConnection() {
        /**
         * When the connection is opened the Service is told to stop its execution and send back
         * a result.
         * @param name The name of the component that connected.
         * @param serviceInterface The interface with the service.
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder serviceInterface) {
            RingService.RingBinder binderBridge = (RingService.RingBinder) serviceInterface;
            binderBridge.answer(System.currentTimeMillis());
            unbindService(this);
        }

        /**
         * The activity is closed when the connection is closed.
         * @param name The name of the component that disconnected.
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            finish();
        }
    };

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
        setContentView(R.layout.alarm_activity);
        findViewById(R.id.ring_hang_up_button).setOnClickListener(view -> {
            bindService(
                    new Intent(AlarmActivity.this, RingService.class),
                    connectionToRingService,
                    BIND_AUTO_CREATE
            );
        });
    }
}
