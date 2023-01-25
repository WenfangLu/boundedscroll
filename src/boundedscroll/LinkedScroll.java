package boundedscroll;

/**
 * Implement scroll by circle linkedList
 * @param <E>
 */
public class LinkedScroll<E> extends AbstractScroll<E> {

    /**
     * Define the node in LinkedScroll
     */
    class Node {
        Node next;
        Node prev;
        E contents;
        public Node(E contents){
            this.contents = contents;
        }
    }

    /**
     * The start node of guard
     */
    public Node guard;
    /**
     * the cursor node
     */
    public Node cursor;

    /**
     * Constructor of LinkedScroll
     * @param max the capacity of LinkedScroll
     */
    public LinkedScroll(int max) {
        super(max);
        guard = new Node(null);
        cursor = new Node(null);
        guard.next = guard;
        guard.prev = guard;
        cursor.next = guard;
        cursor.prev = guard;
    }

    /**
     * Inserts an element to the right of the cursor
     * @param elem the element to be pushed into this scroll
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    @Override
    public void insert(E elem) throws IllegalArgumentException, IllegalStateException {

        // error handle
        if(elem == null){
            throw new IllegalArgumentException();
        }
        if(this.leftLength() + this.rightLength() == getCapacity()){
            throw new IllegalStateException();
        }

        Node newNode = new Node(elem);
        //find the cursor to insert at cursor's right side
        // make necessary other node point
        cursor.prev.next = newNode;
        cursor.next.prev = newNode;
        // make prev, next of new node the same as the cursor
        newNode.next = cursor.next;
        newNode.prev = cursor.prev;
        // modify the cursor links
        cursor.next = newNode;


    }

    /**
     *
     * Deletes and returns the element to the right (next) of the cursor
     * @return the deleted element
     * @throws IllegalStateException
     */
    @Override
    public E delete() throws IllegalStateException {
        // check whether the linkedList is empty
        if(rightLength() == 0 ){
            throw new IllegalStateException();
        }
        E removedNode = cursor.next.contents;
        // reset the pointer
        cursor.next = cursor.next.next;
        cursor.next.prev = cursor.prev;
        cursor.prev.next = cursor.next;
        return removedNode;
    }

    /**
     * Advances the cursor one element to the right
     * pre-state: [A][B,C]
     * post-state : [A,B] [C]
     * @throws IllegalStateException
     */
    @Override
    public void advance() throws IllegalStateException  {
        if(rightLength() == 0){
            throw new IllegalStateException();
        }
        cursor.prev = cursor.next;
        cursor.next = cursor.next.next;

    }

    /**
     * Retreats the cursor one element to the left
     * @throws IllegalStateException
     */
    @Override
    public void retreat() throws IllegalStateException {
        // move the cursor to the next element
        if(leftLength() == 0){
            throw new IllegalStateException();
        }
        cursor.next = cursor.prev;
        cursor.prev = cursor.prev.prev;


    }

    /**
     *  Resets the cursor to the beginning of the scroll
     */
    @Override
    public void reset(){
        cursor.prev  = guard;
        cursor.next = guard.next;
    }

    /**
     * Advances the cursor to the end of the scroll
     * @throws IllegalStateException
     */
    @Override
    public void advanceToEnd() throws IllegalStateException {
       cursor.next = guard;
       cursor.prev = guard.prev;
    }

    /**
     * Swaps the right part of this scroll with the right part of that scroll
     * @param that that scroll wants to swap right
     * @throws IllegalStateException
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
     * The number of elements to the left of the cursor
     * @return the left length
     */
    @Override
    public int leftLength() {
        int counter = 0;
        Node temp = guard;
        while (temp.next != cursor.next){
            temp  = temp.next;
            counter ++;
        }
        return counter;
    }

    /**
     * The number of elements to the right of the cursor
     * @return the right length
     */
    @Override
    public int rightLength() {
         Node temp = guard;
         int counter = 0;
         while(temp.prev != cursor.prev){
             temp = temp.prev;
             counter++;
         }
         return counter;
    }
    /**
     * Creates a new instance of a scroll. The new scroll has the same concrete type that "this" scroll does,
     * and it also has the same capacity
     * @return a LinkedScroll
     */
    @Override
    public Scroll<E> newInstance() {
        return new LinkedScroll<E>(this.capacity());
    }

}
