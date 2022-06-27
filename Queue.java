import java.util.NoSuchElementException;

/**
 * A collection of objects with operations according to the first-in,first-out (FIFO)
 * principle. Only the element that has been in the queue the longest can be removed.
 * Entities can be inserted on one end, but removed from the other end of the sequence.
 * 
 * Implementing a circular array based queue, using modular arithhemetic we use instance 
 * variable "front" to determine the next open available index, so we can wrap around the 
 * array. 
 * 
 * @author kendr
 */
public class Queue <E> {
    private static final int INIT_SIZE = 8;  //Initial size of every default Queue
    
    /**  Instance Variables **/
    private E[] arr;       //Underlying array for element storage
    private int front = 0;  //Index of the front element, front = (front+1) % arr.length
    private int size = 0;   //Current number of elements

    /** Constructors **/ 
    public Queue() { //Default Constructor
        this(INIT_SIZE);
    }

    /** 
     * Constructs Queue with given capacity
     */
    @SuppressWarnings("unchecked")
    public Queue(int capacity){ 
        //Generic Array -> instantiate Object array, then narrowing type cast to E[]
        arr = (E[]) new Object[capacity];
    }

    /** Access Methods **/
    /**
     * @return number of element within the queue
     */
    public int size(){
        return this.size;
    }

    /**
     * @return true if queue is empty, false otherwise
     */
    public boolean isEmpty(){
        return this.size == 0;
    }

    /**
     * Returns, but does not remove, the first element of the queue
     * @return the first element
     * @throws NoSuchElementException if queue is empty
     */
    public E first() throws NoSuchElementException { 
        if(isEmpty()) { throw new NoSuchElementException("Queue Underflow"); }
        return arr[front];
    }

    /** Update Methods **/
    /**
     * Returns and Removes the first element of the queue
     * @return The first element
     * @throws NoSuchElementException if the queue is empty
     */
    public E dequeue() throws NoSuchElementException {
        if(isEmpty()) { throw new NoSuchElementException("Queue Underflow"); }
        E data = arr[front];
        arr[front] = null; //Dereference for garbage collection
        //Move up the front index to the next element in line, % keeps it within arr
        front = (front + 1) % arr.length; 
        size--;
        return data;
    }

    /**
     * Inserts an element at the tail end of the queue
     * @param e The element to insert
     * @throws IllegalStateException - When the queue is full
     */
    public void enqueue(E e) throws IllegalStateException {
        if(size == arr.length) { throw new IllegalStateException("Queue Overflow"); }
        int open = (front + size) % arr.length; 
        arr[open] = e;
        this.size++;
    }

    /** Helper Methods **/

}
