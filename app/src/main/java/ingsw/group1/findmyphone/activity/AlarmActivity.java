package ingsw.group1.findmyphone.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.kuassivi.component.RipplePulseRelativeLayout;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.RingService;

/**
 * @author Riccardo De Zen.
 */
public class AlarmActivity extends AppCompatActivity {

    private ImageButton stopRingButton;

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
            onServiceDisconnected(name);
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
        stopRingButton = findViewById(R.id.ring_hang_up_button);
        stopRingButton.setOnClickListener(view -> {
            bindService(
                    new Intent(AlarmActivity.this, RingService.class),
                    connectionToRingService,
                    BIND_AUTO_CREATE
            );
        });
        RipplePulseRelativeLayout pulseLayout = findViewById(R.id.pulse_layout);
        pulseLayout.startPulse();
        tryToAnimate();
    }

    /**
     * This method tries to animate the stop ring button. If the Drawable in the stop ring button
     * is not an {@link androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat}
     * then no animation is played.
     */
    private void tryToAnimate() {
        Drawable ringDrawable = stopRingButton.getDrawable();

        if (!(ringDrawable instanceof AnimatedVectorDrawable)) return;
        // We check and cast to AnimatedVectorDrawable, which requires api >= 21, but we then use
        // the Compat animationCallback because the non Compat requires api >= 24.
        AnimatedVectorDrawable animatedRingDrawable =
                (AnimatedVectorDrawable) ringDrawable;

        // Due to the fact the animation is organized as a set, the looping must be set
        // programmatically.
        AnimatedVectorDrawableCompat.registerAnimationCallback(
                animatedRingDrawable,
                new Animatable2Compat.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        super.onAnimationEnd(drawable);
                        if (drawable instanceof AnimatedVectorDrawable)
                            ((AnimatedVectorDrawable) drawable).start();
                    }
                }
        );
        animatedRingDrawable.start();
    }
}
