package ingsw.group1.findmyphone.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Ignore;

import com.google.android.material.snackbar.Snackbar;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.activity.ContactListActivity;
import ingsw.group1.findmyphone.activity.ModifyContactActivity;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * Class using in the {@link ContactListActivity} to do an action of contact deletion
 * after a swipe on an item in the contacts list.
 * When user swipes an item to LEFT or RIGHT, it is invoked {@link SMSContactSwipeCallback#onSwiped(RecyclerView.ViewHolder, int)}
 * to delete (or undo the deletion of a contact) or to modify that item
 * and {@link SMSContactSwipeCallback#onChildDraw(Canvas, RecyclerView, RecyclerView.ViewHolder, float, float, int, boolean)}
 * to show a colorful background and an icon.
 *
 * @author Giorgia Bortoletti
 */
public class SMSContactSwipeCallback extends ItemTouchHelper.SimpleCallback {

    private SMSContactRecyclerAdapter contactAdapter;

    /**
     * Constructor
     *
     * @param adapter   {@link SMSContactRecyclerAdapter} attached on this callback
     */
    public SMSContactSwipeCallback(SMSContactRecyclerAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        contactAdapter = adapter;
    }

    /**
     * Called when user wants to move the dragged item from its old position to the new position.
     * The method is implemented to not allow the user to move an item.
     *
     * @param recyclerView View that contains items
     * @param viewHolder   Item to move
     * @param target       Its position is the final position for viewHolder
     */
    @Ignore
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * This is called when an contact item is swiped off the screen in the {@link ContactListActivity}
     * In this way user deletes that item but he can undo the operation.
     *
     * @param viewHolder Contact item swiped
     * @param direction  Direction in which the item was swiped
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        final SMSContact contactSelected = contactAdapter.getItem(position);

        switch (direction) {
            case ItemTouchHelper.LEFT: //DELETE
                //---delete contact item selected
                contactAdapter.deleteItem(position);

                //---snackbar to undo the last deletion
                String nameContactSelected = contactSelected.getName();
                String messageDeletion = "Removed: " + nameContactSelected;
                if (nameContactSelected.isEmpty())
                    messageDeletion += contactSelected.getAddress();

                Snackbar.make(viewHolder.itemView, messageDeletion, Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                contactAdapter.addItem(position, contactSelected);
                            }
                        }).show();
                break;
            case ItemTouchHelper.RIGHT: //MODIFY
                ModifyContactActivity.setContactAddress(contactSelected.getAddress());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Context context = viewHolder.itemView.getContext();
                intent.setClass(context, ModifyContactActivity.class);
                context.startActivity(intent);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }

    }

    /**
     * Called by ItemTouchHelper on RecyclerView's onDraw callback.
     * When item is swiped for deletion, View responds to user interactions with customised design
     * using decorator from https://github.com/xabaras/RecyclerViewSwipeDecorator.
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //from https://github.com/xabaras/RecyclerViewSwipeDecorator
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(recyclerView.getContext(), R.color.failedEventColor))
                .addSwipeLeftActionIcon(R.drawable.round_delete_white)
                .addSwipeLeftLabel("Delete")
                .addSwipeRightBackgroundColor(ContextCompat.getColor(recyclerView.getContext(), R.color.modifiedEventColor))
                .addSwipeRightActionIcon(R.drawable.round_create_24)
                .addSwipeRightLabel("Edit")
                .setSwipeRightLabelColor(Color.WHITE)
                .setSwipeLeftLabelColor(Color.WHITE)
                .create()
                .decorate();

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }


}