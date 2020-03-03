package ingsw.group1.findmyphone;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import ingsw.group1.findmyphone.log.items.LogItem;

/**
 * ViewModel containing data that can be shared by the app.
 *
 * @author Riccardo De Zen.
 */
public class SharedData extends AndroidViewModel {
    /**
     * The address displayed in the home screen's address bar.
     */
    private MutableLiveData<String> homeAddress = new MutableLiveData<>();
    /**
     * The address to insert in the create contact screen.
     */
    private MutableLiveData<String> newContactAddress = new MutableLiveData<>();
    /**
     * The list of events that reached conclusion in this session.
     */
    private MutableLiveData<LogItem> lastEvent = new MutableLiveData<>();

    /**
     * Default constructor.
     *
     * @param application The application to which this ViewModel should be attached.
     */
    public SharedData(@NonNull Application application) {
        super(application);
    }

    /**
     * @return {@link SharedData#homeAddress}
     */
    public MutableLiveData<String> getHomeAddress() {
        return homeAddress;
    }

    /**
     * @return {@link SharedData#newContactAddress}
     */
    public MutableLiveData<String> getNewContactAddress() {
        return newContactAddress;
    }

    /**
     * @return {@link SharedData#lastEvent}
     */
    public MutableLiveData<LogItem> getLastEvent() {
        return lastEvent;
    }
}
