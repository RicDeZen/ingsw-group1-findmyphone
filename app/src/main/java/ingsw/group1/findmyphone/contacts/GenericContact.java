package ingsw.group1.findmyphone.contacts;


import com.eis.communication.Peer;

/**
 * Abstract class represents a generic contact
 * A contact is identify by an address phone
 * and it can have a name.
 *
 * @param <A> The type of address for the peer {@link P}.
 * @param <P> The type of peer that contains a valid phone address.
 * @author Giorgia Bortoletti
 */
//CODE REVIEW
public interface GenericContact<A extends Comparable<A>, P extends Peer<A>> {
    /**
     * Return this address contact
     *
     * @return a {@link A} represents the address contact
     */
    A getAddress();

    /**
     * Return the name of this contact
     *
     * @return a {@link A} represents the name of this contact
     */
    A getName();

    /**
     * Set to change contact name
     *
     * @param newName New contact name
     */
    void setName(A newName);

}
