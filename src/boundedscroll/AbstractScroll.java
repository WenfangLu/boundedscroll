package boundedscroll;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * AbstractScroll implements Scroll
 * @param <E> generic type of the scroll
 */
public abstract class AbstractScroll<E>  implements Scroll<E> {
    /**
     * the capacity scroll
     */
    private final int capacity;
    /**
     * Scroll
     */
    private Scroll<E> newInstance;

    /**
     * get the capacity of AbstractScroll
     * @return
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     *  Constructor of scroll
     * @param max the max capacity
     */
    public AbstractScroll(int max){
        capacity =  max;
    }

    /**
     * scroll = [A B C][D E]
     * Only print out D E
     * post: scroll = [A B C D E][]
     * @return The list iterator of the scroll
     */
    @Override
    public ListIterator<E> listIterator() {
        return new ScrollIterator<E>(this);
    }

    /**
     * get the element right of the cursor
     * @return the element on the right of cursor
     */
    @Override
    public E getNext() {
        // exception here
        if(rightLength() == 0){
            throw new IllegalStateException();
        }
        E result = delete();
        insert(result);
        return result;
    }

    /**
     * get the element left of the cursor
     * @return  the element on the left of cursor
     * @throws IllegalStateException
     */
    @Override
    public E getPrevious() throws IllegalStateException {
        // do the retreat and do something like getnext()
        if(leftLength() == 0){
            throw new IllegalStateException();
        }
        retreat();
        E ele = getNext();
        return ele;
    }

    /**
     * replace the element to the right of the cursor and returns the original
     * @param element the element to replace
     * @return the removed element
     * @throws IllegalStateException
     */
    @Override
    public E replace(E element) throws IllegalStateException {
        if(rightLength() == 0){
            throw  new IllegalStateException();
        }
        E removed = delete();
        insert(element);
        return removed;
    }




    /**
     * <p>
     *  pre-state: THIS = [A,B,C] [D,E,F] , THAT = [] [X,Y,Z]
     *  statement: THIS.splice(that)
     *  post-state:  [A,B,C,X,Y,Z] [D,E,F]
     * </p>
     * @param that that scroll
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     */
    @Override
    public void splice(Scroll<E> that) throws IllegalStateException, IllegalArgumentException {
        if(that  == null || that.leftLength() != 0 ){
            throw new IllegalArgumentException();
        }
        if(that.rightLength() + this.leftLength() + this.rightLength()  > this.capacity ){
            throw new IllegalStateException();
        }
        if(that.rightLength() == 0){
            return;
        }
        this.insert(that.delete());
        this.advance();
        this.splice(that);
    }


    /**
     * Moves all the elements from the right of the cursor to the left of the cursor in reverse order.
     * <p>
     * pre-state: [] [A,B,C,D,E]
     * post-state: [E,D,C,B,A] []
     * <p/>
     */
    @Override
    public void reverse() {
        ArrayList<E> temp = new ArrayList<E>();
        // move that elements into temp scroll
        while(rightLength() != 0){
            temp.add(this.delete());
        }
        // move this cursor to the beginning of the scroll
        this.reset();

        while(temp.size() != 0){
            E ele = temp.remove(0);
            this.insert(ele);
        }

        // move cursor to the end of the scroll
        this.advanceToEnd();
    }

    /**
     * the capacity of the scroll
     * @return the capacity
     */
    @Override
    public int capacity() {
        return capacity;
    }

    /**
     * the iterator of the scroll
     * @return the ScrollIterator
     */
    @Override
    public Iterator<E> iterator(){

        return new ScrollIterator(this);
    }

    /**
     * Swaps the right part of this scroll with the right part of that scroll.
     * @param that that scroll to swapright
     */
    @Override
    public void swapRights(Scroll<E> that) {
       Scroll<E> tempThis = this.newInstance();
       Scroll<E> tempThat = that.newInstance();
       while (this.rightLength() != 0 ){
           tempThis.insert(this.delete());
       }
       while(that.rightLength() > 0){
           tempThat.insert(that.delete());
       }
       tempThat.reset();
       while(tempThat.rightLength() > 0){
           this.insert(tempThat.delete());
       }
       tempThis.reset();
       while (tempThis.rightLength() > 0){
           that.insert(tempThis.delete());
       }

    }

    /**
     * Print out the format of scroll
     * @return the format of scroll
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int lengthLeft = leftLength();
        reset();
        sb.append("[");

        if(lengthLeft > 0){
            for (int i = 0; i < lengthLeft; i++) {
                E elem  = getNext();
                sb.append(elem);
                advance();
            }
        }
        sb.append("]");

        int lengthRight = rightLength();
        sb.append("[");
        if(lengthRight > 0){
            for (int i = 0; i < lengthRight; i++) {
                E elem  = getNext();
                sb.append(elem);
                advance();
            }
        }
        sb.append("]");
        sb.append(":");
        sb.append(getCapacity());
        return sb.toString();
    }

    /**
     * get the hashCode of the scroll
     * @return the hashCode value of the scroll
     */
    @Override
    public int hashCode(){
        int result = 17;
        // record the original cursor position
        int left = leftLength();
        this.reset();

        while(leftLength() < left) {
            result = 20 * result + getNext().hashCode();
            this.advance();
        }

        while (rightLength() > 0){
            result = 20 * result + getNext().hashCode();
            this.advance();
        }
        this.reset();
        while(leftLength() < left){
            this.advance();
        }
        result = result * 20 + capacity();
        return result;
    }

    /**
     * Check two object are equal or not
     * @param o the compare object
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }

        if(o == this){
           return true;
        }
        if( ! (o instanceof AbstractScroll) ){
            return false;
        }
        AbstractScroll<?> that = (AbstractScroll<?>) o;
        if(this.capacity() != that.capacity()){
            return false;
        }
        if(this.rightLength() != that.rightLength()){
            return false;
        }
        if(this.leftLength() != that.leftLength()){
            return false;
        }
        Iterator<E> thisIter = this.iterator();
        Iterator<?> thatIter = that.iterator();
        // record the original cursor position
        int left = leftLength();

        while(thisIter.hasNext()){
            E elem = thisIter.next();
            Object obj = thatIter.next();
            if (!elem.equals(obj)){
                while (leftLength() > left){
                    this.retreat();
                    that.retreat();
                }
                if (leftLength() < left){
                    this.advance();
                    that.advance();
                }
                return false;
            }
        }
        return true;
    }




}
