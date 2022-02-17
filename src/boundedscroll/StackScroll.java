package boundedscroll;

import java.util.Stack;

public class StackScroll<E> extends AbstractScroll<E> {

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
    // scroll [A,B,C] [D,E,F]
    // left = [a,b,c] right = [f,e,d]
    //      bottom
    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public E delete() throws IllegalStateException {
        if(right.isEmpty()){
            throw new IllegalStateException();
        }
        return right.pop();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void advance() throws IllegalStateException {
        if (right.isEmpty())
            throw new IllegalStateException();
        left.push(right.pop());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void retreat() {
        if(left.isEmpty()){
            throw new IllegalStateException();
        }
        right.push(left.pop());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() throws IllegalStateException {

//        if(leftLength() == 0){
//            throw new IllegalStateException();
//        }
        while(leftLength() != 0){
            retreat();
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void advanceToEnd() throws IllegalStateException {
        if(rightLength() ==0){
            throw new IllegalStateException();
        }
        while(rightLength() != 0){
            advance();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void swapRights(Scroll<E> that) throws IllegalArgumentException {

        // make sure the scroll not out of bound
        if(this.leftLength() + that.rightLength() > this.capacity() ||
                this.rightLength() + that.leftLength() > that.capacity()){
            throw new IllegalArgumentException();
        }

        if (that instanceof StackScroll) {
            // swap right stack
            StackScroll<E> stackScrollThat = (StackScroll<E>) that;
            Stack<E> temp = this.right;
            this.right = stackScrollThat.right;
            stackScrollThat.right = temp;
        }else{
            super.swapRights(that);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int leftLength() {

        return left.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int rightLength() {

        return right.size();
    }

    @Override
    public Scroll<E> newInstance(){
        return new StackScroll<E>(capacity());
    }

}
