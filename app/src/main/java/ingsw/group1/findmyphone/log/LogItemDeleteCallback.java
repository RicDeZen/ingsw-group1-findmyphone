package ingsw.group1.findmyphone.log;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import ingsw.group1.findmyphone.R;

/**
 * Class defining a Delete swipe gesture for a LogRecyclerAdapter.
 *
 * @author Riccardo De Zen.
 */
public class LogItemDeleteCallback extends ItemTouchHelper.SimpleCallback {

    @NonNull
    private final LogRecyclerAdapter logAdapter;
    @NonNull
    private final String DELETION_MESSAGE;
    @NonNull
    private final String UNDO_BUTTON;

    public LogItemDeleteCallback(@NonNull LogRecyclerAdapter adapter,
                                 @NonNull Resources resources) {
        super(0, ItemTouchHelper.LEFT);
        this.logAdapter = adapter;
        DELETION_MESSAGE = resources.getString(R.string.remove_item_message);
        UNDO_BUTTON = resources.getString(R.string.undo_button);
    }

    /**
     * Move events are not handled.
     *
     * @param recyclerView The Recycler view triggering the event.
     * @param viewHolder   The moved ViewHolder.
     * @param target       The ViewHolder that gets replaced.
     * @return {@code false} meaning the event was not handled.
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * When the item is swiped left, it is removed from the adapter.
     *
     * @param viewHolder The ViewHolder that triggered the swipe.
     * @param direction  The direction in which the swipe happened.
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction != ItemTouchHelper.LEFT) return;
        logAdapter.removeItem(viewHolder.getAdapterPosition());
        Snackbar.make(viewHolder.itemView, DELETION_MESSAGE, Snackbar.LENGTH_LONG)
                .setAction(UNDO_BUTTON, view -> logAdapter.undoLastRemove())
                .show();
    }
}
