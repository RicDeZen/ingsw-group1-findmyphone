package ingsw.group1.findmyphone.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import ingsw.group1.findmyphone.R;

/**
 * Activity displayed when the app is first started.
 *
 * @author Riccardo De Zen.
 */
public class WelcomeActivity extends AppCompatActivity {

    private static final int INSTRUCTIONS_PREF_KEY = R.string.do_not_show_startup_instructions;

    /**
     * When the activity is created, it checks whether the usage instructions should be displayed
     * to the user or not.
     *
     * @param savedInstanceState Any saved instance state.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean(getString(INSTRUCTIONS_PREF_KEY), true))
            showInstructions();
        else
            startup();
    }

    /**
     * If the instructions should be displayed, the appropriate layout with a ViewPager is shown.
     */
    private void showInstructions() {
        // Setting the layout to the view pager.
        setContentView(R.layout.welcome_instructions_layout);
    }

    /**
     * If the instructions should not be displayed a simple splash screen with the icon is shown
     * instead, after one second, the app is started normally.
     */
    private void startup() {
        // Setting the layout to just the icon screen.
        setContentView(R.layout.welcome_icon_layout);

        new CountDownTimer(1000, 1000) {
            /**
             * The ticks are not important for this timer.
             */
            @Override
            public void onTick(long l) {
            }

            /**
             * When this timer is finished, the NavHolderActivity is started and this is closed.
             */
            @Override
            public void onFinish() {
                startActivity(new Intent(WelcomeActivity.this, NavHolderActivity.class));
                finish();
            }
        }.start();
    }
}
