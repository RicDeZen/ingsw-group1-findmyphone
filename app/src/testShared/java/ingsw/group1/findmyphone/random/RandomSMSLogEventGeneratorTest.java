package ingsw.group1.findmyphone.random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ingsw.group1.findmyphone.event.EventType;
import ingsw.group1.findmyphone.event.SMSLogEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Test class for {@link RandomSMSLogEventGenerator}.
 *
 * @author Riccardo De Zen.
 */
@RunWith(Parameterized.class)
public class RandomSMSLogEventGeneratorTest {

    private RandomSMSLogEventGenerator generator = new RandomSMSLogEventGenerator();
    private EventType testedType;

    /**
     * @return The parameters for this test: the various possible values for {@link EventType}.
     */
    @Parameterized.Parameters
    public static Object[] parameters() {
        return EventType.values();
    }

    /**
     * Default constructor.
     *
     * @param testedType The type of event whose generation is being tested.
     */
    public RandomSMSLogEventGeneratorTest(EventType testedType) {
        this.testedType = testedType;
    }

    /**
     * @see RandomSMSLogEventGenerator#getRandomEvent(EventType)
     * Testing the random event is of the given type.
     */
    @Test
    public void getRandomEventReturnsAppropriateType() {
        assertEquals(testedType, generator.getRandomEvent(testedType).getType());
    }

    /**
     * @see RandomSMSLogEventGenerator#getRandomEvent(EventType)
     * Testing the random event of the given type is successful (non-null extra).
     */
    @Test
    public void getRandomEventReturnsSuccessful() {
        assertNotNull(generator.getRandomEvent(testedType).getExtra());
    }

    /**
     * @see RandomSMSLogEventGenerator#getRandomFailedEvent(EventType)
     * Testing the random failed event is of the given type.
     */
    @Test
    public void getRandomFailedEventReturnsAppropriateType() {
        assertEquals(testedType, generator.getRandomFailedEvent(testedType).getType());
    }

    /**
     * @see RandomSMSLogEventGenerator#getRandomFailedEvent(EventType)
     * Testing the random failed event of a given type is failed (null extra).
     */
    @Test
    public void getRandomFailedEventReturnsFailed() {
        assertNull(generator.getRandomFailedEvent(testedType).getExtra());
    }

    // NON PARAMETERIZED TESTS ---------------------------------------------------------------------
    // The following tests are not involved in the parameterization but are left here to quickly
    // run them multiple times.

    /**
     * @see RandomSMSLogEventGenerator#getRandomEvent()
     * Testing the random event of random type is successful (non-null extra).
     */
    @Test
    public void getRandomTypeEventReturnsSuccessful() {
        assertNotNull(generator.getRandomEvent().getExtra());
    }

    /**
     * @see RandomSMSLogEventGenerator#getRandomFailedEvent()
     * Testing the random event of random type is failed (null extra).
     */
    @Test
    public void getRandomTypeFailedEventReturnsFailed() {
        assertNull(generator.getRandomFailedEvent().getExtra());
    }

    /**
     * @see RandomSMSLogEventGenerator#getMixedEventSet(int)
     * Testing the random events are exactly how many were requested.
     */
    @Test
    public void getMixedEventSetReturnsCorrectSize() {
        final int expected = 20;
        List<SMSLogEvent> result = generator.getMixedEventSet(expected);
        assertEquals(expected, result.size());
    }

    /**
     * @see RandomSMSLogEventGenerator#getMixedEventSet(int)
     * Testing the random event list contains all different events (is convertible to a set of
     * the same size).
     */
    @Test
    public void getMixedEventSetReturnsAllDifferent() {
        final int expected = 20;
        List<SMSLogEvent> result = generator.getMixedEventSet(expected);
        Set<SMSLogEvent> resultSet = new HashSet<>(result);
        assertEquals(expected, resultSet.size());
    }
}
