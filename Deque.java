
/**
 * A double-ended queue, or deque, is a queue-like data struct that supports 
 * insertion and deletion at both the front and the back of the queue. It is
 * more general Abstract Data Type in that it can be useful in certain 
 * applications. Example is when maintaining a waitlist or a queue at a 
 * restaurant. Occasionally, the first person may be removed from the queue
 * only to find out there are no seats available, and so is reinserted back
 * into the waitlist at the first position of the queue. On the other end, a
 * customer may be impatient and leave the restaurant whihch calls a remove
 * at the back of the line. 
 * 
 * Implementation: A doubly-linked list is a convenient data structure to
 * implement our dequeue. This should give us a running time of O(1) for
 * methods: size(), isEmpty(), first(), last(), addFirst(), addLast(),
 * removeFirst(), and removeLast(). However, due to the implementation of
 * DoublyLinkedList, these methods will be renamed to match the behavior
 * of java.util.Deque. Since there is no size limit, then addFirst() and
 * addLast() will remain the same.
 * 
 * Note: Java Collections Framework has a java.util.Deque interface which
 * has a clear difference when accessing/removing first or last elements
 * of an empty deque. The getFirst(), getLast(), removeFirst(), removeLast() 
 * all throw NoSuchElementException, whereas peekFirst(), peekLast(), pollFirst(),
 * pollLast() returns a null reference when deque is empty. When a deque 
 * is full, the addFirst(), addLast() methods throw an exception while offerFirst()
 * and offerLast() returns false. Therefore, this deque implementation behaves
 * more similarly to in that it returns a special value rather than throwing 
 * exceptions during these special cases. 
 * 
 * @kendr
 */
public class Deque <E> {
    DoublyLinkedList<E> data;

    public Deque (){
        this.data = new DoublyLinkedList<>();
    }

    /**
     * Returns the number of elements in the deque.
     * @return number of elements in deque
     */
    public int size(){
        return data.size();
    }

    /**
     * Tests whether the deque is empty.
     * @return true if the deque is empty, false otherwise
     */
    public boolean isEmpty(){
        return data.isEmpty();
    }

    /**
     * Returns (but does not remove) the first element of the deque
     * @return first element of the deque, or null if empty
     */
    public E peekFirst(){
        return data.first();
    }

    /**
     * Returns (but does not remove) the last element of the deque.
     * @return last element of the dequeue (or null if empty)
     */
    public E peekLast(){
        return data.last();
    }

    /**
     * Removes and returns the first element of the deque.
     * @return the first element removed (or null if empty)
     */
    public E pollFirst(){
        return data.removeFirst();
    }

    /**
     * Removes and returns the last element of the deque.
     * @return the last element removed (or null if empty)
     */
    public E pollLast(){
        return data.removeLast();
    }


    /**
     * Inserts an element at the front of the deque.
     * @param e The element to insert
     */
    public void addFirst(E e){
        data.addFirst(e);
    }

    /**
     * Inserts an element at the back of the deque.
     * @param e The element to insert
     */
    public void addLast(E e){
        data.addLast(e);
    }
}