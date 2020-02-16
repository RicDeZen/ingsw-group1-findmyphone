package ingsw.group1.findmyphone;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import ingsw.group1.findmyphone.activity.NavHolderActivity;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.fragment.LogFragment;
import ingsw.group1.findmyphone.random.RandomSMSLogEventGenerator;
import ingsw.group1.msglibrary.RandomSMSPeerGenerator;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static org.junit.Assert.assertNotNull;

public class LogFragmentTest {

    private static final RandomSMSPeerGenerator GENERATOR = new RandomSMSPeerGenerator();
    private static final String DB_NAME = "test-db";

    /**
     * Rule to create an Activity
     */
    @Rule
    public ActivityTestRule<NavHolderActivity> rule =
            new ActivityTestRule<>(NavHolderActivity.class);

    private LogFragment fragment;

    private List<SMSLogEvent> exampleEvents = new RandomSMSLogEventGenerator().getMixedEventSet(50);

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    /**
     * Rule to prepare some fake data in the database
     */
    @Before
    public void prepareDataAndAddFragment() {
        SMSLogDatabase.getInstance(rule.getActivity(), DB_NAME).clear();
        SMSLogDatabase.getInstance(rule.getActivity(), DB_NAME).addEvents(exampleEvents);
        fragment = new LogFragment(rule.getActivity(), DB_NAME);
        rule.getActivity().replaceFragment(fragment);
    }

    /**
     * Wait for 30 seconds to allow viewing results.
     */
    @Test
    public void assertFragmentExists() {
        Espresso.onView(isRoot()).perform(waitFor(30000));
        assertNotNull(fragment);
    }

    /**
     * Clear the database from the fake data.
     */
    @After
    public void resetData() {
        SMSLogDatabase.getInstance(rule.getActivity(), DB_NAME).clear();
    }
}
