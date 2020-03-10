package ingsw.group1.findmyphone.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Objects;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.fragment.InstructionsPageFragment;

/**
 * Activity displayed when the app is first started.
 *
 * @author Riccardo De Zen.
 */
public class WelcomeActivity extends AppCompatActivity {

    private static final int INSTRUCTIONS_PREF_KEY = R.string.do_not_show_startup_instructions;

    private SharedPreferences sharedPreferences;
    private ViewPager2 instructionsViewPager;
    private InstructionsPagerAdapter pagerAdapter;
    private CheckBox doNotShowAgainCheckBox;
    private Button confirmButton;

    /**
     * When the activity is created, it checks whether the usage instructions should be displayed
     * to the user or not.
     *
     * @param savedInstanceState Any saved instance state.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(getString(INSTRUCTIONS_PREF_KEY), true))
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

        // View Pager setup.
        instructionsViewPager = findViewById(R.id.instructions_view_pager);
        pagerAdapter = new InstructionsPagerAdapter(WelcomeActivity.this);
        instructionsViewPager.setAdapter(pagerAdapter);
        instructionsViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            /**
             * When a page is selected, if it is the last page, then enable the button.
             *
             * @param position The position of the currently selected page.
             */
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == pagerAdapter.getItemCount() - 1) {
                    makeConfirmButtonClickable();
                }
            }
        });

        // CheckBox reference.
        doNotShowAgainCheckBox = findViewById(R.id.do_not_show_again_check_box);

        // I Understand Button setup. Click is disabled by default but it will be enabled when
        // the last page of instructions is read.
        confirmButton = findViewById(R.id.instructions_confirm_button);
    }

    /**
     * Method altering the state of {@link WelcomeActivity#confirmButton} in order to make it
     * clickable.
     */
    private void makeConfirmButtonClickable() {
        confirmButton.setClickable(true);
        confirmButton.setOnClickListener(view -> {
            if (doNotShowAgainCheckBox.isChecked())
                sharedPreferences.edit()
                        .putBoolean(getString(INSTRUCTIONS_PREF_KEY), false)
                        .apply();
            startup();
        });
        confirmButton.setBackgroundTintList(
                ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary))
        );
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

    /**
     * When the user presses the back button, instead of directly closing the Activity we go back
     * one page, and only close when the current page is the first one.
     */
    @Override
    public void onBackPressed() {
        int currentPage = instructionsViewPager.getCurrentItem();
        if (currentPage == 0) {
            super.onBackPressed();
        } else {
            instructionsViewPager.setCurrentItem(currentPage - 1);
        }
    }

    /**
     * ViewPager adapter used to show the pages for the app's instructions.
     */
    private static class InstructionsPagerAdapter extends FragmentStateAdapter {

        /**
         * Default constructor.
         *
         * @param fa The {@link FragmentActivity} hosting the
         *           {@link androidx.viewpager2.widget.ViewPager2} that will use this Adapter.
         */
        public InstructionsPagerAdapter(@NonNull FragmentActivity fa) {
            super(fa);
        }

        /**
         * Retrieve the Fragment to show at the given position.
         * Retrieved Fragment will always be NonNull due to the fact the adapter positions range
         * is the same as the pages in InstructionsPageFragment.
         *
         * @param position The current position.
         * @return The Fragment from {@link InstructionsPageFragment}'s pool.
         */
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return Objects.requireNonNull(InstructionsPageFragment.getFragment(position));
        }

        /**
         * Returns the item count for this Adapter.
         *
         * @return {@link InstructionsPageFragment#getPageCount()}.
         */
        @Override
        public int getItemCount() {
            return InstructionsPageFragment.getPageCount();
        }
    }

}
