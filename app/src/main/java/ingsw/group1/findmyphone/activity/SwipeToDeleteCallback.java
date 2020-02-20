package ingsw.group1.findmyphone.activity;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ingsw.group1.findmyphone.R;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

//TODO? add specs
public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private ContactAdapter contactAdapter;

    public SwipeToDeleteCallback(ContactAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        contactAdapter = adapter;
    }

    /**
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * This is called when an contact item is swiped off the screen in the {@link ContactListActivity}
     *
     * @param viewHolder contact item swiped
     * @param direction
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        contactAdapter.deleteContact(position);
        //contactAdapter.notifyDataSetChanged(); this method is already invoked in the delete method
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        //from https://github.com/xabaras/RecyclerViewSwipeDecorator
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeRightBackgroundColor(ContextCompat.getColor(recyclerView.getContext(), R.color.failedEventColor))
                .addSwipeLeftActionIcon(R.drawable.round_delete_white)
                .addBackgroundColor(ContextCompat.getColor(recyclerView.getContext(), R.color.searchBarBackground))
                .addActionIcon(R.drawable.round_person_24)
                .create()
                .decorate();

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }


}