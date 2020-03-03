package ingsw.group1.findmyphone.log;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.activity.NavHolderActivity;
import ingsw.group1.findmyphone.log.holders.LogViewHolder;
import ingsw.group1.findmyphone.log.items.LogItem;

/**
 * Class defining a Delete swipe gesture for a LogRecyclerAdapter.
 *
 * @author Riccardo De Zen.
 */
public class LogItemCallback extends ItemTouchHelper.SimpleCallback {

    @NonNull
    private final LogRecyclerAdapter logAdapter;
    @NonNull
    private final NavController navController;
    @NonNull
    private final String DELETION_MESSAGE;
    @NonNull
    private final String UNDO_BUTTON;

    public LogItemCallback(@NonNull LogRecyclerAdapter adapter,
                           @NonNull Resources resources,
                           @NonNull NavController navController) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.logAdapter = adapter;
        this.navController = navController;
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
        switch (direction) {
            case ItemTouchHelper.LEFT:
                deleteAction(viewHolder);
                break;
            case ItemTouchHelper.RIGHT:
                registerAction(viewHolder);
                break;
        }
    }

    /**
     * The delete action removes the item from the recycler and displays a Snackbar.
     *
     * @param viewHolder The holder that triggered the action.
     */
    private void deleteAction(@NonNull RecyclerView.ViewHolder viewHolder) {
        logAdapter.removeItem(viewHolder.getAdapterPosition());
        Snackbar.make(viewHolder.itemView, DELETION_MESSAGE, Snackbar.LENGTH_LONG)
                .setAction(UNDO_BUTTON, view -> logAdapter.undoLastRemove())
                .show();
    }

    /**
     * The register action opens the CreateContactFragment with the address of this item.
     * Won't do anything if the Holder's item does not match the
     * {@link ingsw.group1.findmyphone.log.items.LogItem#NAMELESS} flag, or the holder's attached
     * item is null.
     *
     * @param viewHolder The holder that triggered the action. Must be an instance of
     *                   {@link LogViewHolder} and have a non-null item attached.
     */
    private void registerAction(@NonNull RecyclerView.ViewHolder viewHolder) {
        if (!(viewHolder instanceof LogViewHolder)) return;
        LogViewHolder castHolder = (LogViewHolder) viewHolder;
        LogItem attachedItem = castHolder.getCurrentItem();
        if (attachedItem == null) return;
        if ((attachedItem.getFlags() & LogItem.NAMELESS) != LogItem.NAMELESS) return;
        NavHolderActivity.sharedData.getNewContactAddress().setValue(attachedItem.getAddress().toString());
        navController.navigate(R.id.create_contact_fragment);
    }
}
