package ingsw.group1.findmyphone.log.items;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.log.LogItem;

/**
 * Class used to modify a View's properties based on the content of a LogItem.
 * {@link ingsw.group1.findmyphone.log.LogItem}.
 *
 * @author Riccardo De Zen.
 */
public class LogItemHelper {

    /**
     * Method detecting whether a View is suitable to hold the information in a LogItem.
     * In order to do this, it needs to contain Views with the same ids as the ones in
     * {@link ingsw.group1.findmyphone.R.layout#log_item}.
     *
     * @param root The view that needs to be checked.
     * @return {@code true} if the View contains all necessary child views to be populated, false
     * otherwise.
     */
    public static boolean isViewCompatible(View root) {
        if (root.findViewById(R.id.info_layout) == null) return false;
        if (root.findViewById(R.id.log_extra_layout) == null) return false;
        if (!(root.findViewById(R.id.log_textView_address) instanceof TextView)) return false;
        if (!(root.findViewById(R.id.log_textView_name) instanceof TextView)) return false;
        if (!(root.findViewById(R.id.log_textView_time) instanceof TextView)) return false;
        if (!(root.findViewById(R.id.log_textView_extra) instanceof TextView)) return false;
        if (!(root.findViewById(R.id.log_imageView_icon) instanceof ImageView)) return false;
        return false;
    }

    /**
     * @param targetView
     * @param logItem
     * @param listener
     */
    public static void populateView(View targetView, LogItem logItem, MapLinkListener listener) {


    }

}
