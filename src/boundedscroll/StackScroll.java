package boundedscroll;

import java.util.Stack;

public class StackScroll<E> extends AbstractScroll<E> {

    /**
     * the right and left stacks of the scroll
     */
    public Stack<E> left, right;

    /**
     * constructor of Stackscroll
     * @param max
     */
    public StackScroll(int max){
        super(max);
        left = new Stack<>();
        right = new Stack<>();
    }

    /**
     * Inserts an element to the right of the cursor
     * @param elem the element to be pushed into this scroll
     * @throws IllegalArgumentException
     */
    @Override
    public void insert(E elem) throws IllegalArgumentException   {
        if( right.size() + left.size() == getCapacity()){
            throw new IllegalStateException();
        }
        if( elem == null) throw new IllegalArgumentException();
        right.push(elem);
    }
    /**
     * Deletes and returns the element to the right of the cursor
     * @return the removed element
     * @throws IllegalStateException
     */
    @Override
    public E delete() throws IllegalStateException {
        if(right.isEmpty()){
            throw new IllegalStateException();
        }
        return right.pop();
    }
    /**
     * Advances the cursor one element to the right
     * @throws IllegalStateException
     */
    @Override
    public void advance() throws IllegalStateException {
        if (right.isEmpty())
            throw new IllegalStateException();
        left.push(right.pop());
    }
    /**
     * Retreats the cursor one element to the left
     * @throws IllegalStateException
     */
    @Override
    public void retreat() {
        if(left.isEmpty()){
            throw new IllegalStateException();
        }
        right.push(left.pop());
    }
    /**
     *  Resets the cursor to the beginning of the scroll
     */
    @Override
    public void reset() throws IllegalStateException {

        while(leftLength() != 0){
            retreat();
        }
    }
    /**
     * Advances the cursor to the end of the scroll
     * @throws IllegalStateException
     */
    @Override
    public void advanceToEnd() throws IllegalStateException {
        if(rightLength() == 0){
            throw new IllegalStateException();
        }
        while(rightLength() != 0){
            advance();
        }
    }

    /**
     * Swaps the right part of this scroll with the right part of that scroll
     * @param that that scroll wants to swap right
     * @throws IllegalStateException
     */
    @Override
    public void swapRights(Scroll<E> that) throws IllegalArgumentException,IllegalStateException {

        if(that == null){
            throw new IllegalArgumentException();
        }
        // make sure the scroll not out of bound
        if (that.rightLength() + this.leftLength() > this.capacity() ||
                this.rightLength() + that.leftLength() > that.capacity()) {
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

        return left.size();
    }

    /**
     * The number of elements to the right of the cursor
     * @return the right length
     */
    @Override
    public int rightLength() {

        return right.size();
    }
    /**
     * Creates a new instance of a scroll. The new scroll has the same concrete type that "this" scroll does,
     * and it also has the same capacity
     * @return a list scroll
     */
    @Override
    public Scroll<E> newInstance(){
        return new StackScroll<E>(capacity());
    }

}
