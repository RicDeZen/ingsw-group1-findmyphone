package ingsw.group1.findmyphone.log.items;

import java.util.HashMap;

import ingsw.group1.findmyphone.event.SMSLogEvent;
import ingsw.group1.findmyphone.log.LogList;

/**
 * Utility class used to map Items to their creating event.
 *
 * @author Riccardo De Zen.
 */
public class LogMap extends HashMap<LogItem, SMSLogEvent> {
    /**
     * @return a new LogList containing all the items.
     */
    public LogList getLogList() {
        return new LogList(keySet());
    }

}
