package boundedscroll;

import java.util.ListIterator;

// TODO Add javadocs
// TODO Add exceptions to signatures

/**
 * A stack scroll data structure
 * @author Wenfang lu
 * @param <E> the type of elements in this scroll
 */
public interface Scroll<E> extends Iterable<E> {

    /**
     * Adds the specified element to this scroll
     * @param elem the element to be pushed into this scroll
     * @throws IllegalArgumentException
     */
    public void insert(E elem) throws IllegalArgumentException;


    /**
     * Removes and returns the element from this scroll
     * @return the element from this scroll
     * @throws IllegalStateException
     */
    public E delete() throws IllegalStateException;

    /**
     * Get the cursor's right stack's bottom element and put it to this left stack
     */
    public void advance () throws IllegalStateException;

    /**
     * Retreats the cursor one element to the left
     */
    public void retreat() throws IllegalStateException;


    /**
     * Resets the cursor to the beginning of the scroll
     * @throws IllegalStateException
     */
    public void reset() throws IllegalStateException;

    public void advanceToEnd() throws IllegalStateException;

    public void swapRights(Scroll<E> that) throws IllegalArgumentException;

    /**
     * get the length of this left stack
     * @return
     */
    public int leftLength();

    /**
     * get the length of this right stack
     * @return the length of this right stack
     */
    public int rightLength();

    /**
     * get the capacity of this scroll
     * @return the capacity  of this scroll
     */
    public int capacity();

    public ListIterator<E> listIterator();

    public E getNext();

    public E getPrevious();

    public E replace(E element);

    /**
     *
     * @param that
     */
    public void splice(Scroll<E> that);

    /**
     * Moves all the elements from the right of the cursor to the left of the cursor in reverse order.
     * The cursor of the current scroll (this) must be at the beginning of the scroll when the method is called.
     * The cursor will be at the end of the current scroll when the call is complete.
     */
    public void reverse();

    /**
     * Checks for equality between the scroll and another object
     * @param o the compare object
     * @return boolean value
     */
    public boolean equals(Object o);

    /**
     * Returns the hash code of the scroll
     * @return the hash code of the scroll
     */
    public int hashCode();

    /**
     * Returns a string representation of the scroll
     * @return a string
     */
    public String toString();

    Scroll<E> newInstance();
}