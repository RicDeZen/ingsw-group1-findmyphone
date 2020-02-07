package ingsw.group1.findmyphone;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import ingsw.group1.findmyphone.activity.NavHolderActivity;
import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.event.LogEventType;
import ingsw.group1.findmyphone.event.SMSLoggableEvent;
import ingsw.group1.findmyphone.event.SMSLoggableEventDatabase;
import ingsw.group1.findmyphone.fragment.LogFragment;
import ingsw.group1.findmyphone.location.GeoPosition;
import ingsw.group1.msglibrary.RandomSMSPeerGenerator;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static org.junit.Assert.assertNotNull;

public class LogFragmentTest {

    private static final RandomSMSPeerGenerator GENERATOR = new RandomSMSPeerGenerator();
    private static final String DB_NAME = "test-db";

    private LogFragment fragment;

    private List<SMSLoggableEvent> exampleEvents = Arrays.asList(
            new SMSLoggableEvent(
                    LogEventType.RING_REQUEST_SENT,
                    new SMSContact(
                            GENERATOR.generateValidPeer(),
                            "Example Name"
                    ),
                    System.currentTimeMillis(),
                    String.valueOf(1000)
            ),
            new SMSLoggableEvent(
                    LogEventType.RING_REQUEST_RECEIVED,
                    new SMSContact(
                            GENERATOR.generateValidPeer(),
                            "Example Name"
                    ),
                    System.currentTimeMillis(),
                    String.valueOf(1000)
            ),
            new SMSLoggableEvent(
                    LogEventType.LOCATION_REQUEST_RECEIVED,
                    new SMSContact(
                            GENERATOR.generateValidPeer(),
                            "Example Name"
                    ),
                    System.currentTimeMillis(),
                    new GeoPosition(
                            100, 100
                    ).toString()
            ),
            new SMSLoggableEvent(
                    LogEventType.LOCATION_REQUEST_SENT,
                    new SMSContact(
                            GENERATOR.generateValidPeer(),
                            "Example Name"
                    ),
                    System.currentTimeMillis(),
                    new GeoPosition(
                            100, 100
                    ).toString()
            )
    );

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

    @Rule
    public ActivityTestRule<NavHolderActivity> rule =
            new ActivityTestRule<>(NavHolderActivity.class);

    @Before
    public void prepareData() {
        SMSLoggableEventDatabase.getInstance(rule.getActivity(), DB_NAME).addEvents(exampleEvents);
    }

    @Test
    public void assertFragmentExists() {
        fragment = new LogFragment(DB_NAME);
        rule.getActivity().replaceFragment(fragment);
        Espresso.onView(isRoot()).perform(waitFor(10000));
        assertNotNull(fragment);
    }
}
