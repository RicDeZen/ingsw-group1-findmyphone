package ingsw.group1.findmyphone.log.holders;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.log.items.LogItem;

/**
 * Abstract class for any LogViewHolder and/or decorators. It's only purpose is to cache a
 * reference to the system resources and the views that will always be used.
 * The required Views are:
 * - A {@link TextView} with id {@link LogViewHolder#NAME_ID}.
 * - A {@link TextView} with id {@link LogViewHolder#ADDRESS_ID}.
 * - A {@link TextView} with id {@link LogViewHolder#TIME_ID}.
 * - A {@link TextView} with id {@link LogViewHolder#EXTRA_ID}.
 * - A {@link ImageView} with id {@link LogViewHolder#IMG_ID}.
 *
 * @author Riccardo De Zen.
 */
public class LogViewHolder extends RecyclerView.ViewHolder {
    /**
     * By default an event is assumed to not be expandable (no extra info) so it's marked as failed.
     */
    private static final int BASE_COLOR = R.color.failedEventColor;

    private static final int NAME_ID = R.id.log_textView_name;
    private static final int ADDRESS_ID = R.id.log_textView_address;
    private static final int TIME_ID = R.id.log_textView_time;
    private static final int EXTRA_ID = R.id.log_textView_extra;
    private static final int IMG_ID = R.id.log_imageView_icon;

    protected Resources resources;

    protected LogItem currentItem;

    protected TextView nameTextView;
    protected TextView addressTextView;
    protected TextView timeTextView;
    protected TextView extraTextView;
    protected ImageView iconImageView;

    /**
     * @param view      The View to use when holding data. Must be an inflation of
     *                  {@link ingsw.group1.findmyphone.R.layout#log_item}, or have a similar
     *                  structure, based on which decorators are involved.
     * @param resources The system resources, used to retrieve colors, strings and such.
     */
    public LogViewHolder(View view, Resources resources) {
        super(view);
        this.resources = resources;
        // The views are cached to avoid searching for them every time at runtime.
        nameTextView = itemView.findViewById(NAME_ID);
        nameTextView.setTextColor(resources.getColor(BASE_COLOR));
        addressTextView = itemView.findViewById(ADDRESS_ID);
        timeTextView = itemView.findViewById(TIME_ID);
        extraTextView = itemView.findViewById(EXTRA_ID);
        iconImageView = itemView.findViewById(IMG_ID);
    }

    /**
     * Constructor that copies the relevant fields from another holder. This constructor is used
     * when building decorators to avoid multiple calls to {@code findViewById}.
     *
     * @param holder another holder from which the view fields are directly copied.
     */
    protected LogViewHolder(@NonNull LogViewHolder holder) {
        super(holder.itemView);
        this.resources = holder.resources;
        this.currentItem = holder.currentItem;
        this.nameTextView = holder.nameTextView;
        this.addressTextView = holder.addressTextView;
        this.timeTextView = holder.timeTextView;
        this.extraTextView = holder.extraTextView;
        this.iconImageView = holder.iconImageView;
    }

    /**
     * Method to update the appearance of a LogViewHolder based on the data in a {@link LogItem}.
     *
     * @param newItem The item used to populate the ViewHolder's content.
     */
    public void populate(LogItem newItem) {
        currentItem = newItem;
        nameTextView.setText(
                (newItem.getName().toString().isEmpty()) ? newItem.getAddress() : newItem.getName()
        );
        addressTextView.setText(newItem.getAddress());
        timeTextView.setText(newItem.getTime());
        extraTextView.setText(newItem.getExtra());
        iconImageView.setImageDrawable(newItem.getDrawable());
    }

}
