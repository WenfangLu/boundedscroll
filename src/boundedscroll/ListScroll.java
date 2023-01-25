package boundedscroll;

import java.util.ArrayList;
import java.util.List;

public class ListScroll<E> extends AbstractScroll<E>{

    public List<E> elements;
    private int pos;

    /**
     * the constructor of ListScroll
     * @param max
     */
    public ListScroll(int max) {
        super(max);
        elements = new ArrayList<>();
        pos = 0;
    }

    /**
     * Inserts an element to the right of the cursor
     * @param elem the element to be pushed into this scroll
     * @throws IllegalArgumentException
     */
    @Override
    public void insert(E elem) throws IllegalArgumentException {
        if (rightLength() + leftLength() == capacity()) {
            throw new IllegalStateException();
        }
        if (elem == null) {
            throw new IllegalArgumentException();
        }
        elements.add(pos, elem);
    }


    /**
     * Deletes and returns the element to the right of the cursor
     * @return the removed element
     * @throws IllegalStateException
     */
    @Override
    public E delete() throws IllegalStateException {
        if(rightLength() == 0){
            throw new IllegalStateException();
        }else{
            E ele = elements.remove(pos);
            return ele;
        }
    }

    /**
     * Advances the cursor one element to the right
     * @throws IllegalStateException
     */
    @Override
    public void advance() throws IllegalStateException {
        if (rightLength() == 0) {
            throw new IllegalStateException();
        }
        pos = pos + 1;
    }

    /**
     * Advances the cursor to the end of the scroll
     * @throws IllegalStateException
     */
    @Override
    public void advanceToEnd() throws IllegalStateException {
        pos = elements.size();
    }

    /**
     * Retreats the cursor one element to the left
     * @throws IllegalStateException
     */
    @Override
    public void retreat() throws IllegalStateException{
        if(leftLength() ==0){
            throw new IllegalStateException();
        }
        pos = pos - 1;
    }

    /**
     *  Resets the cursor to the beginning of the scroll
     */
    @Override
    public void reset() {
        pos = 0;
    }

    /**
     * Swaps the right part of this scroll with the right part of that scroll
     * @param that that scroll wants to swap right
     * @throws IllegalStateException
     */
    @Override
    public void swapRights(Scroll<E> that) throws IllegalStateException {
        if(this.leftLength() + that.rightLength() > this.capacity() ||
                this.rightLength() + that.leftLength() > that.capacity()){
            throw new IllegalStateException();
        }
        super.swapRights(that);
    }

    /**
     * The number of elements to the left of the cursor
     * @return the left length
     */
    @Override
    public int leftLength() {
        return pos;
    }

    /**
     * The number of elements to the right of the cursor
     * @return the right length
     */
    @Override
    public int rightLength() {
        int length = elements.size();
        return length - leftLength();
    }

    /**
     * the capacity of ListScroll
     * @return  the capacity
     */
    @Override
    public int capacity() {
        return getCapacity();
    }

    /**
     * Creates a new instance of a scroll. The new scroll has the same concrete type that "this" scroll does,
     * and it also has the same capacity
     * @return a list scroll
     */
    @Override
    public Scroll<E> newInstance() {
        return new ListScroll<E>(getCapacity());
    }
}