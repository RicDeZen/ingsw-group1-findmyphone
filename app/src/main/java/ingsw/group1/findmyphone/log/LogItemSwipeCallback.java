package ingsw.group1.findmyphone.log;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.activity.NavHolderActivity;
import ingsw.group1.findmyphone.log.holders.LogViewHolder;
import ingsw.group1.findmyphone.log.items.LogItem;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * Class defining a Delete swipe gesture for a LogRecyclerAdapter.
 *
 * @author Riccardo De Zen.
 */
public class LogItemSwipeCallback extends ItemTouchHelper.SimpleCallback {

    @NonNull
    private final LogRecyclerAdapter logAdapter;
    @NonNull
    private final NavController navController;

    private final String DELETION_MESSAGE;
    private final String UNDO_BUTTON;
    private final String DELETE_LABEL;
    private final String ADD_LABEL;

    private final int LEFT_COLOR;
    private final int RIGHT_COLOR;

    public LogItemSwipeCallback(@NonNull LogRecyclerAdapter adapter,
                                @NonNull Resources resources,
                                @NonNull NavController navController) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.logAdapter = adapter;
        this.navController = navController;
        DELETION_MESSAGE = resources.getString(R.string.delete_item_message);
        UNDO_BUTTON = resources.getString(R.string.undo_button);
        DELETE_LABEL = resources.getString(R.string.delete_label);
        ADD_LABEL = resources.getString(R.string.add_label);
        LEFT_COLOR = resources.getColor(R.color.deleteColor);
        RIGHT_COLOR = resources.getColor(R.color.modifyColor);
    }

    @Override
    public int getSwipeDirs(@NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder) {
        // If the holder does not require the right action then only left is returned.
        if ((viewHolder.getItemViewType() & LogItem.NAMELESS) != LogItem.NAMELESS)
            return ItemTouchHelper.LEFT;
        return super.getSwipeDirs(recyclerView, viewHolder);
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
     * Called by ItemTouchHelper on RecyclerView's onDraw callback.
     * When item is swiped for deletion, View responds to user interactions with customised design
     * using decorator from https://github.com/xabaras/RecyclerViewSwipeDecorator.
     *
     * @param viewHolder   The holder being drawn. It's type is used to change the behaviour of
     *                     the decorator. A type that does not match the {@link LogItem#NAMELESS}
     *                     flag does not allow right swipe decoration.
     * @param recyclerView Other parameters are simply fed into the
     *                     {@link RecyclerViewSwipeDecorator} builder.
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        //from https://github.com/xabaras/RecyclerViewSwipeDecorator
        RecyclerViewSwipeDecorator.Builder decoratorBuilder =
                new RecyclerViewSwipeDecorator.Builder(
                        c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                );
        // Delete action is always needed.
        decoratorBuilder.addSwipeLeftBackgroundColor(LEFT_COLOR)
                .addSwipeLeftActionIcon(R.drawable.round_delete_white)
                .addSwipeLeftLabel(DELETE_LABEL)
                .setSwipeLeftLabelColor(Color.WHITE);
        // Add action is only needed if the holder's type matches the flag.
        if ((viewHolder.getItemViewType() & LogItem.NAMELESS) == LogItem.NAMELESS) {
            decoratorBuilder.addSwipeRightBackgroundColor(RIGHT_COLOR)
                    .addSwipeRightActionIcon(R.drawable.round_person_add_24)
                    .addSwipeRightLabel(ADD_LABEL)
                    .setSwipeRightLabelColor(Color.WHITE);
        }

        decoratorBuilder.create().decorate();

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
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
        if ((viewHolder.getItemViewType() & LogItem.NAMELESS) != LogItem.NAMELESS) return;
        NavHolderActivity.sharedData.getNewContactAddress()
                .setValue(castHolder.getCurrentItem().getAddress().toString());
        navController.navigate(R.id.create_contact_fragment);
    }
}
