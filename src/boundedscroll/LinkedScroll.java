package boundedscroll;

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

    public Node guard;
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
     * {@inheritDoc}
     */
    @Override
    public void insert(E elem) throws IllegalArgumentException {
        Node newNode = new Node(elem);

        //check whether the linkedScroll inserts element first time --> guard points to the new node
        if(guard.next == guard && guard.prev == guard){
            guard.next = newNode;
            guard.prev = newNode;
            newNode.next = guard;
            newNode.prev = guard;
            //update cursor
            cursor.next = newNode;
        }else{
            //find the cursor to insert at cursor's right side
            // make prev, next of new node the same as the cursor
            newNode.next = cursor.next;
            newNode.prev = cursor.prev;
            // make necessary other node point
            cursor.prev.next = newNode;
            cursor.next.prev = newNode;
            // modify the cursor links
            cursor.next = newNode;
        }



    }
    ///        cursor
    /** [A,B] [C,D] --> [A,B]
     * Deletes and returns the element to the right (next) of the cursor
     * @return
     * @throws IllegalStateException
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public E delete() throws IllegalStateException {
        // check whether the linkedList is empty
        if(cursor.next == guard && cursor.prev == guard){
            throw new IllegalStateException();
        }
        Node removedNode = cursor.next;
        // reset the pointer
        cursor.next = cursor.next.next;
        return (E) removedNode;
    }
    //      c               c
    /** [A][B,C] --> [A,B] [C]
     * Advances the cursor one element to the right
     * @throws IllegalStateException
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public void advance()  {
        cursor.next = cursor.next.next;
        cursor.prev = cursor;
        cursor = cursor.next;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void retreat() throws IllegalStateException {
        // move the cursor to the next element
        if(cursor.next == guard && cursor.prev == guard){
            throw new IllegalStateException();
        }
        cursor.prev = cursor.prev.prev;
        cursor.next = cursor;
        cursor = cursor.prev;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() throws IllegalStateException {
        cursor.prev  = guard;
        cursor.next = guard.next;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void advanceToEnd() throws IllegalStateException {
       cursor.next = guard;
       cursor.prev = guard.prev;
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
        int counter = 0;
        Node temp = guard;
        while (temp.next != cursor){
            temp  = temp.next;
            counter ++;
        }
        return counter;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int rightLength() {
         Node temp = cursor;
         int counter = 0;
         while(temp.next != guard){
             temp = temp.next;
             counter++;
         }
         return counter;

    }

    @Override
    public Scroll<E> newInstance() {
        return new LinkedScroll<E>(capacity());
    }

}
