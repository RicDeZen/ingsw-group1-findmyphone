package ingsw.group1.findmyphone.event;

import android.content.Context;

import com.eis.smslibrary.SMSPeer;

import java.util.List;

/**
 * Class used to manage the EventDatabase used to handle all the outgoing events.
 *
 * @author Pardeep Kumar
 */
public class EventManager {
    private static EventManager instance;
    private SMSLogDatabase smsLogDatabase;
    public static final String EVENT_DATABASE = "eventDatabase";
    public static final String NO_PASSWORD_FOUND_ERROR = "There is no event with an extra with the same address";
    public static final String PASSWORD_TAG = "<PWD>";

    private EventManager(Context context) {
        smsLogDatabase = SMSLogDatabase.getInstance(context, EVENT_DATABASE);
    }

    public static EventManager getInstance(Context context) {
        if (instance == null) {
            instance = new EventManager(context);
        }
        return instance;
    }

    /**
     * Finds in the database the password of an event with the same address of smsPeer.
     *
     * @param smsPeer Containing the address to find in the database.
     * @return A string containing the password.
     */
    public String findPassword(SMSPeer smsPeer) {
        List<SMSLogEvent> listEvents = smsLogDatabase.getAllEvents();
        for (int i = 0; i < listEvents.size(); i++)
            if (listEvents.get(i).getAddress().equals(smsPeer.getAddress()))
                if (listEvents.get(i).getExtra().contains(PASSWORD_TAG))
                    return listEvents.get(i).getExtra().substring(PASSWORD_TAG.length());
        return NO_PASSWORD_FOUND_ERROR;
    }

    /**
     * Print all the relative info about all the events in the eventDatabase.
     *
     * @return String containing all the info about all the events.
     */
    public String printEverything() {
        StringBuilder stringBuilder = new StringBuilder();
        List<SMSLogEvent> listEvents = smsLogDatabase.getAllEvents();
        for (int i = 0; i < listEvents.size(); i++)
            stringBuilder.append(EVENT_DATABASE + " evento: " + i + " " + listEvents.get(i).getType() + " " + listEvents.get(i).getAddress() + " " + listEvents.get(i).getTime() + " " + listEvents.get(i).getExtra() + "\n");
        return stringBuilder.toString();
    }


}
