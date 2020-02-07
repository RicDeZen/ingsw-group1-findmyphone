package ingsw.group1.findmyphone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.event.SMSLoggableEvent;
import ingsw.group1.findmyphone.log.LogItem;
import ingsw.group1.findmyphone.log.LogRecyclerAdapter;

/**
 * Fragment holding the Log screen. Contains a list of the Log events.
 *
 * @author Riccardo De Zen.
 */
public class LogFragment extends Fragment {

    private final String databaseName;

    private List<LogItem> logItemList = new ArrayList<>();

    public LogFragment(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO should sort the items.
        List<SMSLoggableEvent> savedEvents =
                SMSLogDatabase.getInstance(getContext(), databaseName).getAllEvents();
        for (SMSLoggableEvent eachEvent : savedEvents)
            logItemList.add(new LogItem(eachEvent));
    }

    /**
     * Method called when creating the view for the first time.
     *
     * @param inflater           The passed inflater. Needed to create the view.
     * @param container          The container asking for creation of the fragment.
     * @param savedInstanceState The saved state.
     * @return The root view for this fragment, in this case just an inflation of the layout.
     */
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.log_fragment, container, false);
        RecyclerView logRecycler = root.findViewById(R.id.log_recycler);
        logRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        logRecycler.setAdapter(new LogRecyclerAdapter(container.getContext(), logItemList));
        return root;
    }
}
