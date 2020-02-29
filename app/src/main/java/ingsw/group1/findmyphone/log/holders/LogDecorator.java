package ingsw.group1.findmyphone.log.holders;

import androidx.annotation.NonNull;

import ingsw.group1.findmyphone.log.items.LogItem;

/**
 * Base Decorator class for {@link LogViewHolder}, wraps a Holder, caches the same views.
 * Contains some redundant calls due to limitations in implementing a Decorator in a class with
 * no default constructor.
 *
 * @author Riccardo De Zen.
 */
class LogDecorator extends LogViewHolder {

    protected LogViewHolder holder;

    /**
     * Default constructor.
     *
     * @param holder A LogDecorator takes a LogViewHolder as its parameter, caches the same views
     *               by calling the appropriate constructor and keeps a reference to the holder in
     *               order to wrap it.
     */
    protected LogDecorator(@NonNull LogViewHolder holder) {
        super(holder);
        this.holder = holder;
    }

    /**
     * A decorator just runs its wrapped holder's method by default.
     *
     * @param item The item to use when populating.
     */
    @Override
    public void populate(LogItem item) {
        holder.populate(item);
        currentItem = item;
    }

}
