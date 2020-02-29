package ingsw.group1.findmyphone.log.holders;

import android.view.View;

import androidx.annotation.NonNull;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.log.items.LogItem;

/**
 * This decorator manages the expansion and contraption of a log view.
 * This decorator adds a click listener to a View with id
 * {@link LogExpansionDecorator#CLICK_TARGET_ID}, and whenever the click happens, the visibility
 * of the View with id {@link R.id#log_extra_layout} is toggled on and off.
 *
 * @author Riccardo De Zen.
 */
class LogExpansionDecorator extends LogDecorator {
    /**
     * An event that is expandable has the appropriate color.
     */
    private static final int EXPANDABLE_COLOR = R.color.baseTextColor;

    private static final int CLICK_TARGET_ID = R.id.log_info_layout;
    private static final int COLLAPSING_ID = R.id.log_extra_layout;

    private View collapsingView;

    /**
     * Default constructor. The OnClickListener always interacts with the View regardless of its
     * content.
     *
     * @param holder The holder to wrap with the decoration.
     */
    public LogExpansionDecorator(@NonNull LogViewHolder holder) {
        super(holder);

        nameTextView.setTextColor(resources.getColor(EXPANDABLE_COLOR));
        collapsingView = holder.itemView.findViewById(COLLAPSING_ID);

        holder.itemView.findViewById(CLICK_TARGET_ID).setOnClickListener(view -> {
            if (currentItem == null) return;
            currentItem.interact();
            checkVisibility();
        });
    }

    /**
     * A decorator just runs its wrapped holder's method by default.
     *
     * @param item The item to use when populating.
     */
    @Override
    public void populate(LogItem item) {
        super.populate(item);
        checkVisibility();
    }

    /**
     * Method setting the visibility of {@code collapsingView} to the appropriate value read from
     * the item.
     */
    private void checkVisibility() {
        collapsingView.setVisibility(
                currentItem.getState() ? View.VISIBLE : View.GONE
        );
    }

}
