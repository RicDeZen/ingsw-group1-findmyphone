package ingsw.group1.findmyphone;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * This is a Service that should be started when a ring request is initiated, and it shall play
 * the default ringtone. It displays a notification that can open an Activity where a button can
 * be pressed to stop the Service and ringtone, while also sending a message back to the
 * {@link com.eis.smslibrary.SMSPeer} that started the ring request.
 * If multiple ring requests are started simultaneously, only the first one is processed, the
 * others will be ignored and no response will travel back.
 *
 * @author Riccardo De Zen.
 * //TODO manifest
 */
public class RingService extends Service {
    /**
     * State variable indicating whether the Service is already playing a ring and waiting for
     * the user to turn it off. Should be accessed in synchronized blocks to ensure atomicity of
     * operations.
     */
    private boolean isAlreadyRunning = false;

    /**
     * This method is called by the Android system only if the Service is not already running.
     * Ensures all the needed resources are available.
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //TODO notification channel registering should be inside a "Main activity" otherwise settings
    // point to nothing.

    /**
     * This method is called every time a start command is sent to the synchronized.
     *
     * @param intent  The Intent starting this Service.
     * @param flags   Any passed flags.
     * @param startId The startId. Not needed since only one request is processed at a time.
     * @return The mode in which the Service should be started.
     */
    @Override
    public synchronized int onStartCommand(Intent intent, int flags, int startId) {
        // If the Service is already running we don't care for the request to be processed.
        if (isAlreadyRunning) return START_NOT_STICKY;
        return START_STICKY;
    }


    /**
     * Method called when a client connects to the Service.
     * //TODO
     *
     * @param intent The Intent containing the bind request.
     * @return An IBinder with the allowed interactions with the Service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
