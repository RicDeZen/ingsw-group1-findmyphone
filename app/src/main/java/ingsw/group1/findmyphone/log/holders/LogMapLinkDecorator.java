package ingsw.group1.findmyphone.log.holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ingsw.group1.findmyphone.R;
import ingsw.group1.findmyphone.log.items.MapLinkListener;

/**
 * Decorator class that decorates a {@link LogViewHolder} by showing a Button that calls back to
 * a listener to open a {@link ingsw.group1.findmyphone.location.GeoPosition} in Google Maps.
 * Should be used when such a position is available in the represented items. The Button must
 * already be a child View in the holder's cached view, with
 * id {@link R.id#map_link_Button}, this decorator simply turns it visible at creation time.
 *
 * @author Riccardo De Zen.
 */
class LogMapLinkDecorator extends LogDecorator {

    private static final int MAP_BUTTON_ID = R.id.map_link_Button;

    /**
     * Default constructor.
     *
     * @param holder       The holder to wrap with the decoration.
     * @param linkListener The listener for the map button click. The listener can be null and in
     *                     that case no action is performed on click.
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
