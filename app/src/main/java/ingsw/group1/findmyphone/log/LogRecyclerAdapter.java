package ingsw.group1.findmyphone.log;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ingsw.group1.findmyphone.R;

public class LogRecyclerAdapter extends RecyclerView.Adapter<LogRecyclerAdapter.LogViewHolder> {
    //Id of the base layout for items
    private static final int ROOT_LAYOUT = R.layout.log_item;
    //Text colors for the items.
    private static final int SUCCESS_COLOR = R.color.baseTextColor;
    private static final int FAILURE_COLOR = R.color.failedEventColor;

    private Resources resources;
    private List<LogItem> logItemList;

    /**
     * Constructor. Context is required to cache resources.
     *
     * @param context The calling {@link Context}.
     */
    public LogRecyclerAdapter(Context context, List<LogItem> logItemList) {
        this.resources = context.getResources();
        this.logItemList = logItemList;
    }

    /**
     * ViewHolder caching references to important views in the layout. Due to this, it expects to
     * receive an appropriate view.
     * The class has been made private to reduce boilerplate getters and setters.
     */
    public class LogViewHolder extends RecyclerView.ViewHolder {

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
        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
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
        private void populate(@NonNull LogItem item) {
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

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View. Ignored in this scenario.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(
                ROOT_LAYOUT,
                parent,
                false
        );
        return new LogViewHolder(rootView);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        LogItem usedItem = logItemList.get(position);
        holder.populate(usedItem);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return logItemList.size();
    }
}
