package ingsw.group1.findmyphone.random;

import java.util.Random;

import ingsw.group1.findmyphone.managing.location.GeoPosition;

/**
 * Class made to generate pseudo-random GeoPositions.
 *
 * @author Riccardo De Zen.
 */
public class RandomGeoPositionGenerator {

    private static Random random = new Random();

    /**
     * Returns a randomly generated GeoPosition.
     *
     * @return a GeoPosition, with valid latitude and longitude.
     */
    public GeoPosition getRandomPosition() {
        double latitude = Math.abs(random.nextDouble() % 180) - 90;
        double longitude = Math.abs(random.nextDouble() % 360) - 180;
        return new GeoPosition(latitude, longitude);
    }
}
