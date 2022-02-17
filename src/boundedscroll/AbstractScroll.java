package boundedscroll;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

public abstract class AbstractScroll<E>  implements Scroll<E> {

    private final int capacity;
    private Scroll<E> newInstance;

    public int getCapacity(){
        return capacity;
    }

    public AbstractScroll(int max){
        capacity =  max;
    }


    @Override
    public ListIterator<E> listIterator() {
        return new ScrollIterator<E>(this);
    }


    // [a,b,c] [d,e,f]
    // after -->
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

    @Override
    public E getPrevious() {
        // do the retreat and do something like getnext()
        if(leftLength() == 0){
            throw new IllegalStateException();
        }
        retreat();
        E ele = getNext();
        return ele;
    }

    @Override
    public E replace(E element) {
        if(rightLength() == 0){
            throw  new IllegalStateException();
        }
        E removed = delete();
        insert(element);
        return removed;
    }

    /**
     * THIS = [A,B,C] [D,E,F] , THAT = [] [X,Y,Z]
     * [A,B,C] [X,Y,Z,D,E,F]
     * @param that
     */
    @Override
    public void splice(Scroll<E> that) {
        Stack<E> tempRightItems = new Stack<E>();
        if(that.rightLength() + this.leftLength() + this.rightLength() > this.capacity || that.rightLength() == 0){
            throw new IllegalStateException();
        }else{
            if(that.rightLength() > 0){
                while(that.rightLength() > 0){
                    tempRightItems.push(that.delete());
                }
                while(tempRightItems.size() > 0){
                    E ele = tempRightItems.pop();
                    this.insert(ele);
                }
            }
        }
    }

    /**
     *  [] [A,B,C,D,E]
     *  [E,D,C,B,A]
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
    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public Iterator<E> iterator(){

        return new ScrollIterator(this);
    }

    @Override
    public void swapRights(Scroll<E> that) {
       Scroll<E> tempThis = this.newInstance();
       Scroll<E> tempThat = that.newInstance();
       while (rightLength() >0 ){
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


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        int length = leftLength();
        reset();
        sb.append("[");
        for (int i = 0; i < length; i++) {
            E elem  = getNext();
            sb.append(" ");
            advance();
        }
        sb.append("]");

        int lengthRight = rightLength();
        reset();
        sb.append("[");
        for (int i = 0; i < lengthRight; i++) {
            E elem  = getNext();
            sb.append(" ");
            advance();
        }
        sb.append("]");
        sb.append(":");
        sb.append(getCapacity());
        return sb.toString();
    }

    @Override
    public int hashCode(){
        return this.hashCode();
    }

    @Override
    public boolean equals(Object o){
        return this.getClass() == o.getClass();
    }




}
