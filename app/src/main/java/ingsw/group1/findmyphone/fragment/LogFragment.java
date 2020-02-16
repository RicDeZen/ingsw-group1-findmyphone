package ingsw.group1.findmyphone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.event.SMSLogDatabase;
import ingsw.group1.findmyphone.log.LogItemFormatter;
import ingsw.group1.findmyphone.log.LogManager;
import ingsw.group1.findmyphone.log.LogRecyclerAdapter;

/**
 * Fragment holding the Log screen. Contains a list of the Log events.
 *
 * @author Riccardo De Zen.
 */
public class LogFragment extends Fragment {

    private SMSLogDatabase logDatabase;
    private LogManager logManager;

    /**
     * Constructor for the Fragment.
     *
     * @param context      The calling {@link Context}, this is only needed to read from the
     *                     database,
     *                     and to format the Log items. No reference is kept afterwards.
     * @param databaseName The name of the database where the log data is kept.
     */
    public LogFragment(Context context, String databaseName) {
        logDatabase = SMSLogDatabase.getInstance(context, databaseName);
        logManager = new LogManager(logDatabase, new LogItemFormatter(context));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        LogRecyclerAdapter logAdapter = new LogRecyclerAdapter(container.getContext(), logManager);
        logRecycler.setAdapter(logAdapter);
        logManager.setListener(logAdapter);
        return root;
    }
}
