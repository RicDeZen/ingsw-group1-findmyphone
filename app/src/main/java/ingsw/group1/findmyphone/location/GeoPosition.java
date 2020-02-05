package ingsw.group1.findmyphone.location;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A class holding a Geographical position, in the form latitude and longitude.
 *
 * @author Riccardo De Zen
 */
public class GeoPosition {

    static final String POSITION_SPLIT_SEQUENCE = "@";

    /**
     * Latitude value. {@link Location#FORMAT_DEGREES} format is used.
     */
    private double latitude;
    /**
     * Longitude value. {@link Location#FORMAT_DEGREES} format is used.
     */
    private double longitude;

    /**
     * Constructor taking the Geographical coordinates as parameters.
     * Both parameters must be provided in {@link Location#FORMAT_DEGREES} format.
     *
     * @param latitude  The latitude for this {@link GeoPosition}.
     * @param longitude The longitude for this {@link GeoPosition}.
     */
    public GeoPosition(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Constructor taking a {@link String} as a parameter. The {@code String} should be formatted
     * as {@link GeoPosition#toString()} does.
     *
     * @param locationString The Non-null properly formatted {@link String}.
     * @throws ArrayIndexOutOfBoundsException If {@code locationString} is not divided in at
     *                                        least two parts by
     *                                        {@link GeoPosition#POSITION_SPLIT_SEQUENCE}.
     * @throws NumberFormatException          If {@code locationString} is not divided into parts
     *                                        that
     *                                        are parsable as Double values.
     */
    public GeoPosition(@NonNull String locationString) throws
            ArrayIndexOutOfBoundsException,
            NumberFormatException {
        this(
                Double.parseDouble(locationString.split(POSITION_SPLIT_SEQUENCE)[0]),
                Double.parseDouble(locationString.split(POSITION_SPLIT_SEQUENCE)[1])
        );
    }

    /**
     * Getter for {@link GeoPosition#latitude}.
     *
     * @return The latitude for this {@link GeoPosition}.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Setter for {@link GeoPosition#latitude}.
     *
     * @param newLatitude The new latitude for this {@link GeoPosition}.
     */
    public void setLatitude(double newLatitude) {
        this.latitude = newLatitude;
    }

    /**
     * Getter for {@link GeoPosition#longitude}.
     *
     * @return The longitude for this {@link GeoPosition}.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Setter for {@link GeoPosition#longitude}.
     *
     * @param newLongitude The new longitude for this {@link GeoPosition}.
     */
    public void setLongitude(double newLongitude) {
        this.longitude = newLongitude;
    }

    /**
     * Returns a {@link String} representation of this {@link GeoPosition}. This {@code String} is
     * considered valid when provided to {@link GeoPosition#GeoPosition(String)}.
     * More precisely, the String will be in the format:
     * [latitude]{@link GeoPosition#POSITION_SPLIT_SEQUENCE}[longitude]
     *
     * @return A {@link String} representing this {@link GeoPosition}.
     */
    @NonNull
    @Override
    public String toString() {
        return latitude + POSITION_SPLIT_SEQUENCE + longitude;
    }

    /**
     * Override for {@link Object#equals(Object)}. The equation criteria is as follows: Both
     * Latitudes and Longitudes are equal
     *
     * @param otherObject The Object with which to compare this {@link GeoPosition}.
     * @return True if {@code otherObject} is a {@link GeoPosition} and the equation criteria is
     * met.
     */
    @Override
    public boolean equals(@Nullable Object otherObject) {
        if (!(otherObject instanceof GeoPosition))
            return false;
        GeoPosition otherPosition = (GeoPosition) otherObject;
        return this.getLatitude() == otherPosition.getLatitude() &&
                this.getLongitude() == otherPosition.getLongitude();
    }

    /**
     * Method to compute distance between two positions, using
     * {@link Location#distanceBetween(double, double, double, double, float[])}.
     *
     * @param first  The first of the positions to compare.
     * @param second The second of the positions to compare.
     * @return The approximate distance between the positions, expressed in meters.
     * @see Location#distanceBetween(double, double, double, double, float[]) for details.
     */
    public static float getDistanceBetween(@NonNull GeoPosition first,
                                           @NonNull GeoPosition second) {
        float[] results = new float[1];
        Location.distanceBetween(
                first.getLatitude(),
                first.getLongitude(),
                second.getLatitude(),
                second.getLongitude(),
                results
        );
        return results[0];
    }
}
