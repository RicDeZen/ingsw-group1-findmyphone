package ingsw.group1.findmyphone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.event.EventOrder;
import ingsw.group1.findmyphone.location.GeoPosition;
import ingsw.group1.findmyphone.location.LocationManager;
import ingsw.group1.findmyphone.log.LogItemSwipeCallback;
import ingsw.group1.findmyphone.log.LogManager;
import ingsw.group1.findmyphone.log.LogRecyclerAdapter;
import ingsw.group1.findmyphone.log.items.MapLinkListener;

/**
 * Fragment holding the Log screen. Contains a list of the Log events.
 *
 * @author Riccardo De Zen.
 */
public class LogFragment extends Fragment implements PopupMenu.OnMenuItemClickListener,
        MapLinkListener {

    public static final String DEFAULT_TAG = "log-fragment";

    private LogManager logManager;
    private RecyclerView logRecycler;
    private ImageButton sortButton;

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

        if (getContext() == null) return root;

        // Navigation controller caching -----------------------------------------------------------
        NavController navController = NavHostFragment.findNavController(this);

        // LogManager setup ------------------------------------------------------------------------
        logManager = LogManager.getInstance(getContext());

        // Recycler setup --------------------------------------------------------------------------
        logRecycler = root.findViewById(R.id.log_recycler);
        logRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        LogRecyclerAdapter logAdapter = new LogRecyclerAdapter(
                container.getContext(), logManager, this
        );
        logRecycler.setAdapter(logAdapter);
        logManager.setListener(logAdapter);

        new ItemTouchHelper(new LogItemSwipeCallback(
                logAdapter,
                getResources(),
                navController))
                .attachToRecyclerView(logRecycler);

        // Sort button setup -----------------------------------------------------------------------
        sortButton = root.findViewById(R.id.sort_button);
        registerForContextMenu(sortButton);
        sortButton.setOnClickListener(
                this::showMenu
        );

        // Search view setup -----------------------------------------------------------------------
        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                logManager.filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                logManager.filter(s);
                return true;
            }
        });

        return root;
    }

    /**
     * When the fragment is out of focus, the manager does not need to update the recycler.
     */
    @Override
    public void onPause() {
        logManager.removeListener();
        super.onPause();
    }

    /**
     * When the fragment is resumed we ensure that, if the adapter was not deleted, it starts
     * listening to the manager again.
     */
    @Override
    public void onResume() {
        if (logRecycler.getAdapter() != null)
            logManager.setListener(logRecycler.getAdapter());
        super.onResume();
    }

    /**
     * Method called to inflate a popup menu and attach it to a View.
     * Needs to be called by passing {@link LogFragment#sortButton} and for the fragment's
     * {@link Context} to not be {@code null} in order to proceed.
     *
     * @param anchorView The view to which the menu will be attached.
     */
    public void showMenu(View anchorView) {
        if (getContext() == null || !anchorView.equals(sortButton)) return;
        PopupMenu popup = new PopupMenu(getContext(), anchorView);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.log_sort_menu);
        popup.show();

    }

    /**
     * Method called when a menu item is clicked.
     *
     * @param item The clicked item.
     * @return {@code true} if the action was managed, {@code false} otherwise.
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_menu_time_rto:
                logManager.setSortingOrder(EventOrder.NEWEST_TO_OLDEST);
                return true;
            case R.id.sort_menu_time_otr:
                logManager.setSortingOrder(EventOrder.OLDEST_TO_NEWEST);
                return true;
            case R.id.sort_menu_name_ascending:
                logManager.setSortingOrder(EventOrder.NAME_ASCENDING);
                return true;
            case R.id.sort_menu_name_descending:
                logManager.setSortingOrder(EventOrder.NAME_DESCENDING);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Method called when the position is forwarded to Google Maps.
     *
     * @param position The position that needs to be opened.
     */
    @Override
    public void onLinkOpened(@NonNull GeoPosition position) {
        if (getContext() == null) return;
        LocationManager.openMapsUrl(getContext(), position.getLatitude(), position.getLongitude());
    }
}
