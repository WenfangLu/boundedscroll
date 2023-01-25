package boundedscroll;

import java.util.ListIterator;

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
     * @throws IllegalStateException
     */
    public void advance () throws IllegalStateException;

    /**
     * Retreats the cursor one element to the left
     * @throws IllegalStateException
     */
    public void retreat() throws IllegalStateException;


    /**
     * Resets the cursor to the beginning of the scroll
     * @throws IllegalStateException
     */
    public void reset() throws IllegalStateException;

    /**
     * Advances the cursor to the end of the scroll
     * @throws IllegalStateException
     */
    public void advanceToEnd() throws IllegalStateException;

    /**
     * Swaps the right part of this scroll with the right part of that scroll
     * @param that that scroll wants to swap right
     * @throws IllegalStateException
     */
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

    /**
     * A list iterator that starts at the cursor position.
     * Note that list iterators can move forward and backwards
     * @return ListIterator
     */
    public ListIterator<E> listIterator();

    /**
     * Gets a handle to (does not remove) the element to the right of the curso
     * @return the next element
     */
    public E getNext();

    /**
     * Gets a handle to the element to the left of the cursor
     * @return the previous element
     */
    public E getPrevious();

    /**
     * 	Replaces the element to the right of the cursor and returns the original
     * @param element
     * @return removed element
     */
    public E replace(E element);

    /**
     * Adds all the elements from *that* scroll to the left of the cursor in *this* scroll
     * in the same order.
     * PRE: this = [A, B, C][D, E, F] and that = [][X, Y, Z]
     * STMT: this.splice(that);
     * POST: this [A, B, C, X, Y, Z][D, E, F] and that = [][]
     * @param that that scroll
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

    /**
     * Creates a new instance of a scroll
     * @return the Scroll
     */
    Scroll<E> newInstance();
}