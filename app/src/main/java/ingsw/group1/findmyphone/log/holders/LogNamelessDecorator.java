package ingsw.group1.findmyphone.log.holders;

import android.view.View;

import androidx.annotation.NonNull;

import ingsw.group1.findmyphone.log.items.LogItem;

/**
 * Decorator class for {@link LogViewHolder}. This Decorator renders the address
 * {@link android.widget.TextView} invisible and puts the address where the name would be. Should
 * be used when no name is available but an address is. It's been chosen not to hide the name
 * TextView instead due to how the {@code log_item} layout file is structured.
 *
 * @author Riccardo De Zen.
 */
class LogNamelessDecorator extends LogDecorator {

    /**
     * Default constructor for the Decorator. It simply hides the address text view.
     *
     * @param holder The holder to wrap with the decoration.
     */
    public LogNamelessDecorator(@NonNull LogViewHolder holder) {
        super(holder);
        // Making the address text view invisible
        addressTextView.setVisibility(View.GONE);
    }

    /**
     * Other than what the holder already would do, this Decorator substitutes the name with the
     * address.
     *
     * @param item The item to use when populating.
     */
    @Override
    public void populate(LogItem item) {
        super.populate(item);
        nameTextView.setText(item.getAddress());
    }
}
