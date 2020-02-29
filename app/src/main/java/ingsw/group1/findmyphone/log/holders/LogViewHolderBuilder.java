package ingsw.group1.findmyphone.log.holders;

import android.content.res.Resources;
import android.view.View;

import ingsw.group1.findmyphone.log.items.LogItem;
import ingsw.group1.findmyphone.log.items.MapLinkListener;

/**
 * Builder class for {@link LogViewHolder} based on the requested features, described as
 * {@link ingsw.group1.findmyphone.log.items.LogItem} flags.
 * Neither the listener for the Holders is changed retroactively, nor the Resources.
 *
 * @author Riccardo De Zen.
 */
public class LogViewHolderBuilder {

    private Resources holderResources;
    private MapLinkListener holderListener;

    /**
     * Build a builder that will use the given Resources when building the Holders.
     * A {@link MapLinkListener} is not required because it can be {@code null} in the holders.
     *
     * @param holderResources The resources that will be used when instantiating the ViewHolders.
     */
    public LogViewHolderBuilder(Resources holderResources) {
        this.holderResources = holderResources;
    }

    /**
     * Sets the resources to use when building the Holders.
     *
     * @param holderResources The resources to use.
     */
    public LogViewHolderBuilder setResources(Resources holderResources) {
        this.holderResources = holderResources;
        return this;
    }

    /**
     * Sets the listener to be used if the built holders require it.
     *
     * @param holderListener The listener to use.
     */
    public LogViewHolderBuilder setMapLinkListener(MapLinkListener holderListener) {
        this.holderListener = holderListener;
        return this;
    }

    /**
     * Method to build an appropriate ViewHolder given a certain set of {@link LogItem} flags.
     * See {@link LogExpansionDecorator}, {@link LogNamelessDecorator}, {@link LogMapLinkDecorator}
     * and {@link LogViewHolder} for details on what Views are needed.
     *
     * @param view  The View to use when instantiating the Holder, must be of an appropriate type
     *              for the decorations defined by {@code flags}.
     * @param flags The flags defining the Holder's features.
     * @return A {@link LogViewHolder} with the appropriate decorations, as specified in {@code
     * flags}.
     */
    public LogViewHolder build(View view, Integer flags) {
        LogViewHolder resultingHolder = new LogViewHolder(view, holderResources);
        if ((flags & LogItem.NAMELESS) == LogItem.NAMELESS)
            resultingHolder = new LogNamelessDecorator(resultingHolder);
        if ((flags & LogItem.HAS_EXTRA) == LogItem.HAS_EXTRA)
            resultingHolder = new LogExpansionDecorator(resultingHolder);
        if ((flags & LogItem.HAS_POSITION) == LogItem.HAS_POSITION)
            resultingHolder = new LogMapLinkDecorator(resultingHolder, holderListener);
        return resultingHolder;
    }
}
