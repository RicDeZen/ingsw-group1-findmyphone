package ingsw.group1.findmyphone;

import java.lang.reflect.Field;
import java.util.Map;

import ingsw.group1.findmyphone.contacts.SMSContactManager;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.log.LogManager;

/**
 * Class containing some useful methods to be used during tests.
 *
 * @author Riccardo De Zen.
 */
public class TestUtils {

    private static final String FIELD_ERR = "Field not found.";
    private static final String ACCESS_ERR = "Can't access field";

    public static void resetContactManager() {
        try {
            Field instanceField = SMSContactManager.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(FIELD_ERR + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(ACCESS_ERR + e.getMessage());
        }
    }

    public static void resetLogManager() {
        try {
            Field instanceField = LogManager.class.getDeclaredField("activeInstance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(FIELD_ERR + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(ACCESS_ERR + e.getMessage());
        }
    }

    public static void resetSMSLogDatabase() {
        try {
            Field instanceField = SMSLogDatabase.class.getDeclaredField("activeInstances");
            instanceField.setAccessible(true);
            Map instances = (Map) instanceField.get(null);
            if (instances != null)
                instances.clear();
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(FIELD_ERR + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(ACCESS_ERR + e.getMessage());
        }
    }
}
