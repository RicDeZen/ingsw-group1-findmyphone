package ingsw.group1.findmyphone.log;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ingsw.group1.findmyphone.R;

/**
 * ViewHolder Class representing a Log item, it caches references to important views in the
 * layout. Due to this, it expects to receive an appropriate view.
 * The class has been made private to reduce boilerplate getters and setters.
 *
 * @author Riccardo De Zen.
 */
public class LogViewHolder extends RecyclerView.ViewHolder {

    //Text colors for the items.
    private static final int SUCCESS_COLOR = R.color.baseTextColor;
    private static final int FAILURE_COLOR = R.color.failedEventColor;
    //Reference to resources.
    private final Resources resources;

    private LogItem currentItem;
    private TextView nameTextView;
    private TextView addressTextView;
    private TextView timeTextView;
    private TextView extraTextView;
    private ImageView iconImageView;
    private View collapsingView;

    /**
     * Default constructor with the view needing to be held.
     *
     * @param itemView The view for the item. This view is assumed to be with the appropriate
     *                 layout ({@link ingsw.group1.findmyphone.R.layout#log_item}).
     */
    public LogViewHolder(@NonNull View itemView, Resources resources) {
        super(itemView);
        this.resources = resources;
        itemView.setOnClickListener(new View.OnClickListener() {
            /**
             * When the view is clicked, the expanded state is inverted.
             * @param view The clicked view.
             */
            @Override
            public void onClick(View view) {
                if (currentItem != null && currentItem.shouldExpand()) {
                    currentItem.setExpanded(!currentItem.isExpanded());
                    checkExpansion();
                }
            }
        });
        //Constants are used here directly, to better see if id and types match.
        nameTextView = itemView.findViewById(R.id.log_textView_name);
        addressTextView = itemView.findViewById(R.id.log_textView_address);
        timeTextView = itemView.findViewById(R.id.log_textView_time);
        extraTextView = itemView.findViewById(R.id.log_textView_extra);
        iconImageView = itemView.findViewById(R.id.log_imageView_icon);
        collapsingView = itemView.findViewById(R.id.log_extra_layout);
    }

    /**
     * Method to populate this view holder.
     */
    public void populate(@NonNull LogItem item) {
        currentItem = item;
        nameTextView.setText(item.getName());
        addressTextView.setText(item.getAddress());
        timeTextView.setText(item.getTime());
        extraTextView.setText(item.getExtra());
        iconImageView.setImageDrawable(item.getDrawable());
        checkExpansion();
        checkColor();
    }

    /**
     * Method to assert the expansion state of the view matches the one of the item.
     */
    private void checkExpansion() {
        if (currentItem == null) return;
        collapsingView.setVisibility((currentItem.isExpanded()) ? View.VISIBLE : View.GONE);
    }

    /**
     * Method asserting the color matches the ability to expand (and subsequently the success
     * of the event).
     *
     * @see LogItemFormatter for more info on item formatting.
     */
    private void checkColor() {
        nameTextView.setTextColor(currentItem.shouldExpand() ?
                resources.getColor(SUCCESS_COLOR) :
                resources.getColor(FAILURE_COLOR)
        );
    }
}
