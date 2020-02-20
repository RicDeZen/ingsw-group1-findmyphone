package ingsw.group1.findmyphone.activity;

import android.graphics.Canvas;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Ignore;

import com.google.android.material.snackbar.Snackbar;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.contacts.SMSContact;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * Class using in the {@link ContactListActivity} to do an action of deletion
 * on an contact item in the view.
 * When user swipes an item, it is invoked {@link SwipeToDeleteCallback#onSwiped(RecyclerView.ViewHolder, int)}.
 *
 * @author Giorgia Bortoletti
 */
public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private ContactAdapter contactAdapter;

    /**
     * Constructor
     *
     * @param adapter
     */
    public SwipeToDeleteCallback(ContactAdapter adapter) {
        super(0, ItemTouchHelper.LEFT);
        contactAdapter = adapter;
    }

    /**
     * It is necessary to override but it is never used.
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * This is called when an contact item is swiped off the screen in the {@link ContactListActivity}
     * In this way user deletes that item but he can undo the operation.
     *
     * @param viewHolder contact item swiped
     * @param direction  in which the item was swiped
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        //---delete contact item selected
        final int position = viewHolder.getAdapterPosition();
        final SMSContact contactRemoved = contactAdapter.getItem(position);
        contactAdapter.deleteItem(position);

        //---snackbar to undo the last deletion
        String nameContactRemoved = contactRemoved.getName();
        String messageDeletion = "Removed: " + nameContactRemoved;
        if (nameContactRemoved.isEmpty())
            messageDeletion += contactRemoved.getAddress();

        Snackbar.make(viewHolder.itemView, messageDeletion, Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        contactAdapter.addItem(position, contactRemoved);
                        contactAdapter.notifyItemInserted(position);
                    }
                }).show();
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
                .create()
                .decorate();

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }


}