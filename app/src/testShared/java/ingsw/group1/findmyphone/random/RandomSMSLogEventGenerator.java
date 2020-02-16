package ingsw.group1.findmyphone.random;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.event.EventType;
import ingsw.group1.findmyphone.event.SMSLogEvent;

/**
 * Class generating random {@link SMSLogEvent}. The events always have a random time
 * corresponding to one of the following time-frames:
 * - The event's time is on the same day as the current time
 * - The event's time is earlier than today but on the same month as the current time
 * - The event's time is earlier than the current month
 *
 * @author Riccardo De Zen.
 */
public class RandomSMSLogEventGenerator {

    private static RandomSMSContactGenerator randomContact = new RandomSMSContactGenerator();
    private static RandomGeoPositionGenerator randomPosition = new RandomGeoPositionGenerator();
    private static Random randomNumbers = new Random();

    /**
     * The event's time is on the same day as the current time.
     */
    private static final int SAME_DAY = 0;
    /**
     * The event's time is earlier than today but on the same month as the current time
     */
    private static final int SAME_MONTH = 1;
    /**
     * The event's time is earlier than the current month
     */
    private static final int EARLIER = 2;

    /**
     * Method to generate a random event of a random type.
     *
     * @return A randomly generated {@link SMSLogEvent} containing some valid extra depending on
     * type.
     */
    @NonNull
    public SMSLogEvent getRandomEvent() {
        return getRandomEvent(
                EventType.values()[randomNumbers.nextInt(EventType.values().length)]
        );
    }

    /**
     * Method to generate a random event of a certain type. {@link EventType#UNKNOWN} events
     * generate a default String.
     *
     * @param type The type of event to generate.
     * @return A randomly generated {@link SMSLogEvent} containing some valid extra depending on
     * type: location events will contain a {@link ingsw.group1.findmyphone.location.GeoPosition}
     * string, while ring events will contain a numerical string. {@link EventType#UNKNOWN} will
     * have a default extra.
     */
    @NonNull
    public SMSLogEvent getRandomEvent(EventType type) {
        SMSContact contact = randomContact.getRandomContact();
        Long time = getRandomTime();
        String extra;
        switch (type) {
            case RING_REQUEST_RECEIVED:
            case RING_REQUEST_SENT:
                extra = String.valueOf(Math.abs(randomNumbers.nextLong()));
                break;
            case LOCATION_REQUEST_RECEIVED:
            case LOCATION_REQUEST_SENT:
                extra = randomPosition.getRandomPosition().toString();
                break;
            default:
                extra = "Unknown extra";
                break;
        }
        return new SMSLogEvent(
                type,
                contact,
                time,
                extra
        );
    }

    /**
     * Method to generated a random event of a random type, with {@link null} extra.
     *
     * @return A randomly generated {@link SMSLogEvent} containing {@link null} extra.
     */
    @NonNull
    public SMSLogEvent getRandomFailedEvent() {
        return getRandomFailedEvent(
                EventType.values()[randomNumbers.nextInt(EventType.values().length)]
        );
    }

    /**
     * Method to generate a random event of a certain type.
     *
     * @param type The type of event to generate.
     * @return A randomly generated {@link SMSLogEvent} containing {@link null} extra.
     */
    @NonNull
    public SMSLogEvent getRandomFailedEvent(EventType type) {
        SMSContact contact = randomContact.getRandomContact();
        Long time = getRandomTime();
        return new SMSLogEvent(
                type,
                contact,
                time,
                null
        );
    }

    /**
     * Method to generate a list of distinct events, type, extra and failure are all random.
     *
     * @param amount The size of the list to generate.
     * @return A {@link List} containing all distinct events, of random type and failure.
     */
    @NonNull
    public List<SMSLogEvent> getMixedEventSet(int amount) {
        Set<SMSLogEvent> events = new HashSet<>();
        while (events.size() < amount)
            events.add((randomNumbers.nextBoolean()) ? getRandomEvent() : getRandomFailedEvent());
        return new ArrayList<>(events);
    }

    /**
     * Method to generate a random time.
     */
    @NonNull
    private Long getRandomTime() {
        Long sentinel = 0L;
        final int frame = randomNumbers.nextInt(EARLIER + 1);

        //Gregorian calendar chooses system's timezone and locale by default
        //Calendar pointing to midnight of today.
        Calendar startOfToday = new GregorianCalendar();
        startOfToday.set(Calendar.HOUR_OF_DAY, 0);
        startOfToday.set(Calendar.MINUTE, 0);
        startOfToday.set(Calendar.SECOND, 0);
        startOfToday.set(Calendar.MILLISECOND, 0);

        //Calendar pointing at midnight of the first day of the month.
        Calendar startOfMonth = new GregorianCalendar();
        startOfMonth.setTimeInMillis(startOfToday.getTimeInMillis());
        startOfMonth.set(Calendar.DAY_OF_MONTH, 0);

        //Calendar pointing at time zero (1970).
        Calendar zeroTime = new GregorianCalendar();
        zeroTime.setTimeInMillis(0L);

        long todayTime = startOfToday.getTimeInMillis();
        long thisMonthTime = startOfMonth.getTimeInMillis();

        switch (frame) {
            case SAME_DAY:
                return Math.abs(randomNumbers.nextLong())
                        % (System.currentTimeMillis() - todayTime + 1)
                        + todayTime;
            case SAME_MONTH:
                return Math.abs(randomNumbers.nextLong())
                        % (todayTime - thisMonthTime + 1)
                        + thisMonthTime;
            case EARLIER:
                return Math.abs(randomNumbers.nextLong())
                        % thisMonthTime;
            default:
                return sentinel;
        }
    }
}
