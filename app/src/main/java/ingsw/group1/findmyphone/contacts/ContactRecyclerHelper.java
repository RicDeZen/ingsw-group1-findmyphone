package ingsw.group1.findmyphone.contacts;

import androidx.annotation.NonNull;

/**
 * Interface defining basic operation for a contact recycler adapter.
 *
 * @param <C> Type of item for the {@link androidx.recyclerview.widget.RecyclerView}.
 */
public interface ContactRecyclerHelper<C extends GenericContact> {

    /**
     * Return {@link C} to the given position.
     *
     * @param position Position of the item to return.
     * @return Contact {@link C} to the given position.
     */
    C getItem(int position);

    /**
     * Add a {@link C} to the given position of contacts list.
     *
     * @param position     Position where insert contact in the list of contacts.
     * @param contactToAdd Contact {@link C} to add.
     */
    void addItem(int position, @NonNull C contactToAdd);

    /**
     * Remove a {@link C} to the given position in the contacts list.
     *
     * @param position Position of {@link C} to delete from contacts list.
     */
    void deleteItem(int position);


}
