package ingsw.group1.findmyphone.log;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Collection;

import ingsw.group1.findmyphone.event.EventType;
import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.random.RandomSMSLogEventGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test class for {@link LogItemFormatter}.
 *
 * @author Riccardo De Zen.
 */
@RunWith(RobolectricTestRunner.class)
public class LogItemFormatterTest {

    private RandomSMSLogEventGenerator eventGenerator = new RandomSMSLogEventGenerator();
    private LogItemFormatter testedFormatter;

    /**
     * Creating the formatter to test.
     */
    @Before
    public void setupFormatter() {
        testedFormatter = new LogItemFormatter(ApplicationProvider.getApplicationContext());
    }

    /**
     * Checking that when an Unknown item is passed, the formatted returns null.
     */
    @Test
    public void formatItemReturnsNullForUnknown() {
        assertNull(testedFormatter.formatItem(eventGenerator.getRandomEvent(EventType.UNKNOWN)));
    }

    /**
     * Checking that when a collection of events containing unknown ones is passed, only the
     * known types are formatted.
     */
    @Test
    public void formatItemsExcludesUnknown() {
        final int expectedSize = 20;
        final int unknownAmount = 5;
        Collection<SMSLogEvent> someEvents = eventGenerator.getMixedEventSet(expectedSize);
        while (someEvents.size() < expectedSize + unknownAmount) {
            someEvents.add(eventGenerator.getRandomEvent(EventType.UNKNOWN));
        }
        assertEquals(expectedSize, testedFormatter.formatItems(someEvents).size());
    }
}
