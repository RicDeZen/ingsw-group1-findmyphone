package ingsw.group1.findmyphone.log.holders;

import android.view.View;

import androidx.annotation.NonNull;

import ingsw.group1.findmyphone.log.items.LogItem;

/**
 * Decorator class for {@link LogViewHolder}. This Decorator handles the visibility of the
 * address View, when no address is available.
 *
 * @author Riccardo De Zen.
 */
class LogNamelessDecorator extends LogDecorator {

    /**
     * Default constructor for the Decorator.
     *
     * @param holder The holder to wrap with the decoration.
     */
    public LogNamelessDecorator(@NonNull LogViewHolder holder) {
        super(holder);
        addressTextView.setVisibility(View.GONE);
    }

    /**
     * Method to update the appearance of a LogViewHolder based on the data in a {@link LogItem}.
     * After the superclass is done populating, a visibility filter is applied to the address
     * TextView if the item's name is empty.
     *
     * @param item The item used to populate the ViewHolder's content.
     */
    @Override
    public void populate(LogItem item) {
        super.populate(item);
    }
}
