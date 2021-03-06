package ingsw.group1.findmyphone;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.smslibrary.SMSPeer;

import java.util.Objects;

import ingsw.group1.findmyphone.activity.AlarmActivity;
import ingsw.group1.findmyphone.activity.NavHolderActivity;
import ingsw.group1.findmyphone.alarm.AlarmManager;

/**
 * This is a Service that should be started when a ring request is initiated, and it shall play
 * the default ringtone. It displays a notification that can open an Activity where a button can
 * be pressed to stop the Service and ringtone, while also sending a message back to the
 * {@link com.eis.smslibrary.SMSPeer} that started the ring request.
 * If multiple ring requests are started simultaneously, only the first one is processed, the
 * others will be ignored and no response will travel back.
 *
 * @author Riccardo De Zen.
 */
public class RingService extends Service {
    /**
     * Key to use when putting the address as an extra inside the Intent that starts the Service.
     */
    public static final String ADDRESS_KEY = "ring-request-address";

    /**
     * Timeout time for the Service, in milliseconds. After this many milliseconds have elapsed,
     * the Service is stopped.
     */
    public static final int TIMEOUT_MILLIS = 120000;

    /**
     * Id for the foreground notification.
     */
    private static final int NOTIFICATION_ID = 2020;

    /**
     * State variable indicating whether the Service is already playing a ring and waiting for
     * the user to turn it off. Should be accessed in synchronized blocks to ensure atomicity of
     * operations.
     */
    private boolean isAlreadyRunning = false;

    /**
     * AlarmManager used to start the ringtone and stop it when due.
     */
    private AlarmManager alarm = new AlarmManager();

    /**
     * Variable used to store the current address that requested a ring.
     */
    @NonNull
    private String address = "";

    /**
     * Variable used to store the start time for this ring operation.
     */
    @NonNull
    private Long startTime = 0L;

    /**
     * Class defining the possible interaction with this service.
     */
    public class RingBinder extends Binder {
        /**
         * Method to stop the ring and send back an answer.
         *
         * @param currentTime The time at which the ring was stopped.
         */
        public void answer(@NonNull Long currentTime) {
            alarm.stopAlarm();
            ResponseManager.getInstance(RingService.this).sendRingResponse(
                    new SMSPeer(address),
                    startTime,
                    currentTime - startTime
            );
            isAlreadyRunning = false;
            // The service can be stopped.
            stopSelf();
        }
    }

    /**
     * This method is called every time a start command is sent to the service, only one command
     * can be processed at a time, and the ones that come afterwards are always ignored to avoid
     * a huge queue of ring requests forming. Also ignores requests that do not include an address.
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

        // If no address is included the Service is not started.
        if (intent.getStringExtra(ADDRESS_KEY) == null) return START_NOT_STICKY;

        // If everything is ok then we want to process the start command.
        // The address will be non null due to the above check but the compiler can't detect it.
        address = Objects.requireNonNull(intent.getStringExtra(ADDRESS_KEY));
        startTime = System.currentTimeMillis();
        startForeground(NOTIFICATION_ID, buildNotification());
        alarm.startAlarm(this);
        isAlreadyRunning = true;
        startTimeout();
        // If the app is in the foreground we already open the AlarmActivity.
        if (NavHolderActivity.isOnForeground()) {
            Intent alarmIntent = new Intent(this, AlarmActivity.class);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(alarmIntent);
        }
        return START_STICKY;
    }


    /**
     * Method called when a client connects to the Service.
     *
     * @param intent The Intent containing the bind request.
     * @return An IBinder with the allowed interactions with the Service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RingBinder();
    }

    /**
     * Method building an appropriate {@link Notification} for the Service, assumes that, if the
     * api level is high enough, a Notification channel has already been registered.
     *
     * @return A {@link Notification} that can be used when running the Service in the
     * foreground, associated to the {@link R.string#notification_channel_id} channel if possible.
     */
    private Notification buildNotification() {

        PendingIntent notificationIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, AlarmActivity.class),
                0
        );

        Notification.Builder builder = ((Build.VERSION.SDK_INT < Build.VERSION_CODES.O) ?
                new Notification.Builder(this) : getBuilderForChannel())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle((!address.isEmpty()) ?
                        String.format(getString(R.string.notification_title), address) :
                        getString(R.string.notification_title_no_address)
                )
                .setContentText(getString(R.string.notification_description))
                .setContentIntent(notificationIntent);

        return builder.build();
    }

    /**
     * Method that hides the constructor requiring the channel id. Must be ran on api >= 26.
     *
     * @return The {@link Notification.Builder} created for the
     * {@link R.string#notification_channel_id} channel.
     */
    @TargetApi(Build.VERSION_CODES.O)
    private Notification.Builder getBuilderForChannel() {
        return new Notification.Builder(this, getString(R.string.notification_channel_id));
    }

    /**
     * Method to start a 2 minute timeout after which the service is shut down.
     */
    private void startTimeout() {
        new CountDownTimer(TIMEOUT_MILLIS, 1000) {
            /**
             * No action needs to be performed on tick
             */
            @Override
            public void onTick(long l) {
            }

            /**
             * The service is stopped if the timeout is reached.
             */
            @Override
            public void onFinish() {
                stopSelf();
            }
        }.start();
    }
}
