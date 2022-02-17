package boundedscroll;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ScrollIterator<E>  implements ListIterator<E> {

    Scroll<E> scroll;

    /**
     * Constructor of the scroll iterator
     * @param scroll the scroll we want to iterate
     */
    public ScrollIterator(Scroll<E> scroll){
        this.scroll = scroll;
    }

    /**
     * check whether the right side of the cursor has elements or not
     * @return a boolean value
     */
    @Override
    public boolean hasNext() {

        return scroll.rightLength() != 0;
    }

    /**
     * Find the next element
     * @return the next element
     */
    @Override
    public E next() {
        if(!hasNext()){
            throw new NoSuchElementException();
        }
        //get the next element and advances the cursor position
        return scroll.getNext();
//        E element = scroll.getNext();
//        scroll.advance();
//        return element;
    }

    /**
     * Check if the scroll has the previous element
     * @return whether the scroll has previous element
     */
    @Override
    public boolean hasPrevious() {

        return scroll.leftLength() - 1 != 0;
    }

    /**
     * get the previous element
     * @return the previous element of the scroll
     */
    @Override
    public E previous() {
        E element = scroll.getPrevious();
        return element;
    }

    /**
     * [A,B,C] [D,E,F] index of D is 3
     * Find the index of the next element (right of the cursor)
     * @return the next index
     */
    @Override
    public int nextIndex() {
        return scroll.leftLength();
    }

    /**
     * Find the index of the previous element (left of the cursor)
     * @return the previous index
     */
    @Override
    public int previousIndex() {
        return scroll.leftLength() - 1;
    }


    /**
     *  Not support this method
     * @throws UnsupportedOperationException
     */
    @Override
    public void remove() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }
    /**
     *  Not support this method
     * @throws UnsupportedOperationException
     */
    @Override
    public void set(E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    /**
     *  Not support this method
     * @throws UnsupportedOperationException
     */
    @Override
    public void add(E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }


}
