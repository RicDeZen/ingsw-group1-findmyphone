package ingsw.group1.findmyphone.log.holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.log.items.MapLinkListener;

/**
 * Decorator class that decorates a {@link LogViewHolder} with a Button that opens a map link
 * through a listener. The Button must already be a child View in the holder's cached view, with
 * id {@link R.id#map_link_Button}, this decorator simply turns it visible.
 *
 * @author Riccardo De Zen.
 */
class LogMapLinkDecorator extends LogDecorator {

    private static final int MAP_BUTTON_ID = R.id.map_link_Button;

    /**
     * Default constructor.
     *
     * @param holder       The holder to wrap with the decoration.
     * @param linkListener The listener for the map button click.
     */
    public LogMapLinkDecorator(@NonNull LogViewHolder holder,
                               @Nullable MapLinkListener linkListener) {
        super(holder);
        View mapLinkButton = holder.itemView.findViewById(MAP_BUTTON_ID);
        mapLinkButton.setVisibility(View.VISIBLE);
        // If either the listener or the position held by the item are null, no action is performed.
        mapLinkButton.setOnClickListener(view -> {
            if (currentItem == null || linkListener == null) return;
            if (currentItem.getPosition() != null)
                linkListener.onLinkOpened(currentItem.getPosition());
        });
    }

}
