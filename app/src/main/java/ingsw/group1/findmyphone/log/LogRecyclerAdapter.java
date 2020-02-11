package ingsw.group1.findmyphone.log;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ingsw.group1.findmyphone.R;

/**
 * Class defining the Adapter a Recycler should use when wanting to display the Log's data.
 *
 * @author Riccardo De Zen.
 */
public class LogRecyclerAdapter extends RecyclerView.Adapter<LogViewHolder> {
    //Id of the base layout for items
    private static final int ROOT_LAYOUT = R.layout.log_item;

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
        return new LogViewHolder(rootView, resources);
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