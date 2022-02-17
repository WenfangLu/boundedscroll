package boundedscroll;

import java.util.ArrayList;
import java.util.List;

public class ListScroll<E> extends AbstractScroll<E>{

    public List<E> elements;
    private int pos;

    public ListScroll(int max) {
        super(max);
        elements = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(E elem) throws IllegalArgumentException {
        if (elements.size() == capacity()) {
            throw new IllegalStateException();
        }
        if (elem == null) {
            throw new IllegalStateException();
        }
        elements.add(pos, elem);
    }



    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void advance() throws IllegalStateException {
        if (rightLength() == 0) {
            throw new IllegalStateException();
        }
        pos = pos + 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void advanceToEnd() throws IllegalStateException {
        if(rightLength() > 0){
            while(pos < elements.size() -1){
                advance();
            }
        }else{
            throw new IllegalStateException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void retreat() throws IllegalStateException{

//        if (leftLength() == 0) {
//            throw new IllegalStateException();
//        }
        pos = pos - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        pos = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void swapRights(Scroll<E> that) throws IllegalArgumentException {
        if(this.leftLength() + that.rightLength() > this.capacity() ||
                this.rightLength() + that.leftLength() > that.capacity()){
            throw new IllegalArgumentException();
        }
        super.swapRights(that);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int leftLength() {
        return pos;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int rightLength() {
        int length = elements.size();
        return length - leftLength();
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public int capacity() {
        return getCapacity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scroll<E> newInstance() {
        return new LinkedScroll<E>(capacity());
    }
}