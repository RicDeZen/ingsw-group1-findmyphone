package ingsw.group1.findmyphone.log;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.log.items.MapLinkListener;

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
    private final MapLinkListener mapLinkListener;

    private LogItem currentItem;
    private boolean shouldExpand;

    private View infoView;
    private TextView nameTextView;
    private TextView addressTextView;
    private TextView timeTextView;
    private TextView extraTextView;
    private ImageView iconImageView;
    private View collapsingView;
    private View mapsLinkView;

    /**
     * Default constructor with the view needing to be held.
     *
     * @param itemView The view for the item. This view is assumed to be with the appropriate
     *                 layout ({@link ingsw.group1.findmyphone.R.layout#log_item}).
     */
    public LogViewHolder(@NonNull View itemView, Resources resources, MapLinkListener listener) {
        super(itemView);
        this.resources = resources;
        this.mapLinkListener = listener;
        // The views are cached to avoid searching for them multiple times.
        infoView = itemView.findViewById(R.id.info_layout);
        nameTextView = itemView.findViewById(R.id.log_textView_name);
        addressTextView = itemView.findViewById(R.id.log_textView_address);
        timeTextView = itemView.findViewById(R.id.log_textView_time);
        extraTextView = itemView.findViewById(R.id.log_textView_extra);
        iconImageView = itemView.findViewById(R.id.log_imageView_icon);
        collapsingView = itemView.findViewById(R.id.log_extra_layout);
        mapsLinkView = itemView.findViewById(R.id.map_link_Button);
        // Listeners are always set, what they do varies based on the current item.
        infoView.setOnClickListener(new View.OnClickListener() {
            /**
             * When the view is clicked, the expanded state is inverted.
             *
             * @param view The clicked view.
             */
            @Override
            public void onClick(View view) {
                if (currentItem != null && shouldExpand) {
                    currentItem.interact();
                    applyVisibility();
                }
            }
        });
        mapsLinkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentItem.getPosition() != null && mapLinkListener != null)
                    mapLinkListener.onLinkOpened(currentItem.getPosition());
            }
        });
    }

    /**
     * @return whether the current contained item is expanded or not.
     */
    public boolean isExpanded() {
        return currentItem.getState();
    }

    /**
     * Method to populate this view holder, with a given item. The item is cached in the view
     * holder so that it can communicate the touch events to it.
     *
     * @param item The {@link LogItem} containing the data to use when populating.
     */
    public void populate(@NonNull LogItem item) {
        currentItem = item;
        nameTextView.setText(
                (item.getName().toString().isEmpty()) ? item.getAddress() : item.getName()
        );
        addressTextView.setText(item.getAddress());
        timeTextView.setText(item.getTime());
        extraTextView.setText(item.getExtra());
        iconImageView.setImageDrawable(item.getDrawable());
        shouldExpand = !item.getExtra().isEmpty();
        applyVisibility();
        applyColor();
    }

    /**
     * Method checking that the visibilities are correctly set.
     */
    private void applyVisibility() {
        if (currentItem == null) return;
        mapsLinkView.setVisibility((currentItem.getPosition() != null) ? View.VISIBLE : View.GONE);
        collapsingView.setVisibility((currentItem.getState()) ? View.VISIBLE : View.GONE);
        addressTextView.setVisibility(
                (!currentItem.getName().toString().isEmpty()) ? View.VISIBLE : View.GONE
        );
    }

    /**
     * Method asserting the color matches the ability to expand (and subsequently the success
     * of the event).
     *
     * @see LogItemFormatter for more info on item formatting.
     */
    private void applyColor() {
        nameTextView.setTextColor(shouldExpand ?
                resources.getColor(SUCCESS_COLOR) :
                resources.getColor(FAILURE_COLOR)
        );
    }
}
