package ingsw.group1.findmyphone.random;

import android.graphics.drawable.ShapeDrawable;

import java.util.Random;

import ingsw.group1.findmyphone.log.LogItem;

/**
 * Class generating random LogItem instances.
 * This class does not cover generation of valid log data. Just random Strings for the item.
 *
 * @author Riccardo De Zen.
 */
public class RandomLogItemGenerator {

    private static RandomGeoPositionGenerator randomPosition = new RandomGeoPositionGenerator();
    private static Random random = new Random();

    /**
     * Method returning a pseudo-random LogItem.
     *
     * @return returns a LogItem, this item will not contain meaningful data.
     */
    public LogItem nextLogItem() {
        return nextLogItem("", random.nextLong());
    }

    /**
     * Method returning a pseudo-random LogItem, whose name starts with the given parameter String.
     *
     * @param nameMatches The String to put at the start of the name.
     * @return returns a LogItem, this item will not contain meaningful data.
     */
    public LogItem nextLogItem(String nameMatches) {
        return nextLogItem(nameMatches, random.nextLong());
    }

    /**
     * Method returning a pseudo-random LogItem, and whose time is the given long parameter.
     *
     * @param time The time in milliseconds of the LogItem.
     * @return returns a LogItem, this item will not contain meaningful data.
     */
    public LogItem nextLogItem(Long time) {
        return nextLogItem("", time);
    }

    /**
     * Method returning a pseudo-random LogItem, whose name starts with the given parameter
     * string, and whose time is the given long parameter.
     *
     * @param nameMatches The String to put at the start of the name.
     * @param time        The time in milliseconds of the LogItem.
     * @return returns a LogItem, whose name starts with {@code nameMatches} this item will
     * not contain meaningful data.
     */
    public LogItem nextLogItem(String nameMatches, Long time) {
        return new LogItem(
                nextRandomString(),
                nameMatches + nextRandomString(),
                nextRandomString(),
                nextRandomString(),
                new ShapeDrawable(),
                time,
                (random.nextBoolean()) ? null : randomPosition.getRandomPosition()
        );
    }

    /**
     * Method generating a random String for the contact's username.
     *
     * @return A pseudo-random String made up of ASCII characters between 48 and 122 (inclusive),
     * of length between 16 and 48.
     */
    private String nextRandomString() {
        final int length = random.nextInt(49) + 16;
        StringBuilder result = new StringBuilder();
        while (result.length() < length) {
            result.append((char) random.nextInt(75) + 48);
        }
        return result.toString();
    }
}
