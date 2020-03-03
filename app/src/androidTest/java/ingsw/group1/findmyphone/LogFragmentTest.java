package ingsw.group1.findmyphone;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.eis.smslibrary.SMSPeer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import ingsw.group1.findmyphone.activity.NavHolderActivity;
import ingsw.group1.findmyphone.contacts.SMSContactManager;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.log.LogManager;
import ingsw.group1.findmyphone.random.RandomNameUtils;
import ingsw.group1.findmyphone.random.RandomSMSLogEventGenerator;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static org.junit.Assert.assertNotNull;

public class LogFragmentTest {

    private static final String DB_NAME = LogManager.DEFAULT_LOG_DATABASE;
    private static final Random randomNumber = new Random();

    /**
     * Rule to create an Activity
     */
    @Rule
    public ActivityTestRule<NavHolderActivity> rule =
            new ActivityTestRule<>(NavHolderActivity.class);

    private List<SMSLogEvent> exampleEvents = new RandomSMSLogEventGenerator().getMixedEventSet(50);

    /**
     * Rule to prepare some fake data in the database
     */
    @Before
    public void prepareDataAndAddFragment() {
        SMSLogDatabase.getInstance(rule.getActivity(), DB_NAME).clear();
        SMSLogDatabase.getInstance(rule.getActivity(), DB_NAME).addEvents(exampleEvents);

        SMSContactManager contacts = SMSContactManager.getInstance(rule.getActivity());
        for (SMSLogEvent eachEvent : exampleEvents)
            if (randomNumber.nextBoolean())
                contacts.addContact(
                        new SMSPeer(eachEvent.getAddress()),
                        RandomNameUtils.NAMES[randomNumber.nextInt(RandomNameUtils.NAMES.length)]
                );

        rule.getActivity().runOnUiThread(() -> rule.getActivity().navigate(R.id.log_fragment));
    }

    /**
     * Wait for 30 seconds to allow viewing results.
     */
    @Test
    public void assertFragmentExists() {
        Espresso.onView(isRoot()).perform(AndroidTestUtils.waitFor(120000));
        assertNotNull(rule);
    }

    /**
     * Clear the database from the fake data.
     */
    @After
    public void resetData() {
        SMSLogDatabase.getInstance(rule.getActivity(), DB_NAME).clear();
    }
}
