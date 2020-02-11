package ingsw.group1.findmyphone.log;

import androidx.annotation.NonNull;

import java.util.Comparator;

import ingsw.group1.findmyphone.event.EventOrder;

/**
 * @author Riccardo De Zen
 */
public class LogItemComparatorFactory {

    /**
     * Method to return an appropriate comparator based on the desired order.
     *
     * @param eventOrder The desired order.
     * @return The Comparator of the appropriate type, erased to {@link Comparator}.
     */
    @NonNull
    public static Comparator<LogItem> newComparator(@NonNull EventOrder eventOrder) {
        switch (eventOrder) {
            case OLDEST_TO_NEWEST:
                return new OldestToNewestComparator();
            case NAME_ASCENDING:
                return new AscendingNameComparator();
            case NAME_DESCENDING:
                return new DescendingNameComparator();
            default:
                return new NewestToOldestComparator();
        }
    }

    /**
     * Class defining a comparator aiming to obtain an ordering based on time. Newest items come
     * first.
     */
    private static class NewestToOldestComparator implements Comparator<LogItem> {
        /**
         * Times are compared for descending order.
         *
         * @param first  The first {@link LogItem}.
         * @param second The second {@link LogItem}.
         * @return a negative integer, zero, or a positive integer as the first argument has
         * greater time than, equal to, or smaller than the second.
         */
        @Override
        public int compare(LogItem first, LogItem second) {
            return second.getTimeInMillis().compareTo(first.getTimeInMillis());
        }
    }

    /**
     * Class defining a comparator aiming to obtain an ordering based on time. Oldest items come
     * first.
     */
    private static class OldestToNewestComparator implements Comparator<LogItem> {
        /**
         * Times are compared for ascending order.
         *
         * @param first  The first {@link LogItem}.
         * @param second The second {@link LogItem}.
         * @return a negative integer, zero, or a positive integer as the first argument has
         * smaller time than, equal to, or greater than the second.
         */
        @Override
        public int compare(LogItem first, LogItem second) {
            return first.getTimeInMillis().compareTo(second.getTimeInMillis());
        }
    }

    /**
     * Class defining a comparator aiming to obtain an ascending ordering based on the name.
     */
    private static class AscendingNameComparator implements Comparator<LogItem> {
        /**
         * Names are compared for ascending order.
         *
         * @param first  The first {@link LogItem}.
         * @param second The second {@link LogItem}.
         * @return a negative integer, zero, or a positive integer as the first
         * argument is less than, equal to, or greater than the second.
         */
        @Override
        public int compare(LogItem first, LogItem second) {
            return first.getName().compareTo(second.getName());
        }
    }

    /**
     * Class defining a comparator aiming to obtain a descending ordering based on the name.
     */
    private static class DescendingNameComparator implements Comparator<LogItem> {
        /**
         * Names are compared for descending order.
         *
         * @param first  The first {@link LogItem}.
         * @param second The second {@link LogItem}.
         * @return a negative integer, zero, or a positive integer as the first argument is less
         * than, equal to, or greater than the second.
         */
        @Override
        public int compare(LogItem first, LogItem second) {
            return second.getName().compareTo(first.getName());
        }
    }

}
