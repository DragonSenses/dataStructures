/**
 * A collection of objects with operations according to the first-in,first-out (FIFO)
 * principle. Only the element that has been in the queue the longest can be removed.
 * 
 * @author kendr
 */
public class Queue <E> {
    private static final int INIT_SIZE = 8;  //Initial size of every default Queue
    
    /**  Instance Variables **/
    private E[] data;       //Underlying array for element storage
    private int front = 0;  //Index of the front element
    private int size = 0;   //Current number of elements

    /** Constructors **/ 
    public Queue() { //Default Constructor;
        this(INIT_SIZE);
    }

    /** 
     * Constructs Queue with given capacity
     */
    @SuppressWarnings("unchecked")
    public Queue(int capacity){ 
        //Generic Array -> instantiate Object array, then narrowing type cast to E[]
        data = (E[]) new Object[capacity];
    }

    /** Access Methods **/
    /** Update Methods **/
    /** Helper Methods **/

}
