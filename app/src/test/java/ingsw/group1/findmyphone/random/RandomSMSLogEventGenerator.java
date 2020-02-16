package ingsw.group1.findmyphone.random;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ingsw.group1.findmyphone.contacts.SMSContact;
import ingsw.group1.findmyphone.event.EventType;
import ingsw.group1.findmyphone.event.SMSLogEvent;

/**
 * Class generating random {@link ingsw.group1.findmyphone.event.SMSLogEvent}.
 *
 * @author Riccardo De Zen.
 */
public class RandomSMSLogEventGenerator {

    private static RandomSMSContactGenerator randomContact = new RandomSMSContactGenerator();
    private static RandomGeoPositionGenerator randomPosition = new RandomGeoPositionGenerator();
    private static Random randomNumbers = new Random();

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
        Long time = Math.abs(randomNumbers.nextLong());
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
        Long time = Math.abs(randomNumbers.nextLong());
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
}
