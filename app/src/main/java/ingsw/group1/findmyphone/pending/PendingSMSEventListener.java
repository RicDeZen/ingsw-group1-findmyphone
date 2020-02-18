package ingsw.group1.findmyphone.pending;

import android.os.Bundle;

import ingsw.group1.findmyphone.event.SMSLogEvent;

/**
 * This interface specifies the listener behaviour when listening for an {@link SMSLogEvent}'s
 * result.
 *
 * @author Riccardo De Zen.
 */
public interface PendingSMSEventListener extends PendingEventListener<SMSLogEvent, Bundle> {
    /**
     * Method called when an {@link SMSLogEvent} is completed.
     * The {@code result} is a {@link Bundle} that must contain an Object with the key
     * {@link SMSLogEvent#RESULT_KEY}. This Object has to be a suitable String to be used as extra
     * for the event itself. If the key corresponds to a {@code null} value, the event is
     * considered failed.
     * Please note that {@code completedEvent} does not contain any extra.
     *
     * @param completedEvent The Event that was completed.
     * @param result         The result of the completed Event.
     */
    @Override
    void onEventCompleted(SMSLogEvent completedEvent, Bundle result);
}
