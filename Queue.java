import java.util.NoSuchElementException;

/**
 * A collection of objects with operations according to the first-in,first-out (FIFO)
 * principle. Only the element that has been in the queue the longest can be removed.
 * Entities can be inserted on one end, but removed from the other end of the sequence.
 * 
 * Implementing a circular array based queue, using modular arithemetic we use instance 
 * variable "front" to determine the next open available index, so we can wrap around the 
 * array. 
 * 
 * @author kendr
 */
public class Queue <E> {
    private static final int INIT_SIZE = 8;  //Initial size of every default Queue
    
    /**  Instance Variables **/
    private E[] arr;    //Underlying array for element storage
    private int front;  //Index of the front element, front = (front+1) % arr.length
    private int size;   //Current number of elements

    /** Error Messages **/
    private static final String UNDERFLOW = "Queue Underflow";
    private static final String OVERFLOW = "Queue Overflow";
    public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";

    /** Constructors **/ 
    public Queue() { //Default Constructor
        this(INIT_SIZE);
    }

    /** 
     * Constructs Queue with given capacity
     */
    @SuppressWarnings("unchecked")
    public Queue(int capacity){ 
        if (capacity <= 0) { // capacitty must be non-negative [0, infinity)
             //Generic Array -> instantiate Object array, then narrowing type cast to E[]
			this.arr = (E[]) new Object[INIT_SIZE];
			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
		} else { 
			this.arr = (E[]) new Object[capacity];
		}
        this.front = 0;
        this.size = 0;
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
        if(isEmpty()) { throw new NoSuchElementException(UNDERFLOW); }
        return arr[front];
    }

    /** Update Methods **/
    /**
     * Returns and Removes the first element of the queue
     * @return The first element
     * @throws NoSuchElementException if the queue is empty
     */
    public E dequeue() throws NoSuchElementException {
        if(isEmpty()) { throw new NoSuchElementException(UNDERFLOW); }
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
        if(size == arr.length) { throw new IllegalStateException(OVERFLOW); }
        //To find the next open index, add front to the amount of elements, then modulo by length
        int open = (front + size) % arr.length; 
        arr[open] = e;
        this.size++;
    }

    /**
	 * Higher level notion of equivalence where we define two Queues
	 * as equivalent if:
	 * I) They have the same Length
	 * II) The contents that are element-by-element are equivalent
	 * 
	 * @param o - The Queue object parameter to compare with the caller
	 * @return True, if both queues are the same size and the contents are
	 *         element-by-element equivalent, otherwise false
	 */
    @SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		// 1. Null Treatment
		if (o == null) {
			return false;
		}
		/*
		 * 2. Class Equivalence - getClass() vs. instanceof
		 * getClass() only returns true if object is actually an instance of the
		 * specified class but instanceof operator returns true even if the object
		 * is a subclass of a specified class or interface in Java; allows implementation
		 * of equality between super and subclasses but does not satisfy symmetry: 
		 * x.equals(y) is true then y.equals(x) is also true, but if you swap x
		 * with a subclass then x instanceof y is true but y instanceof x will be false,
		 * hence equals is false. 
		 */
		if (this.getClass() != o.getClass()) {
			return false;
		}
		// Although declared formal type parameter <E> cannot detect at runtime whether
		// other list has a matching type. See Type erasure.
		Queue<?> other = (Queue<?>) o; // Typecast and use unknown type 

		// 3. Size Check
		if (this.size != other.size) {
			return false;
		}

        /** Circular Array requires two different front pointers to keep track of the
         *  first element so recall that we :
         *  - Move up the front index to the next element in line, % keeps it within arr
         *  - such that: front = (front + 1) % arr.length; 
         */
        int f1 = this.front;
        int f2 = other.front;
		E ptrA = this.arr[f1];      // Traverses through the primary Queue
		E ptrB = (E) other.arr[f2]; // Traverse through the secondary Queue
        
        // 4. Element-by-Element check
		//Traversal through both queues, iterations based on number of elements
        for(int i = this.size(); i == 0; i--){
            if(ptrA != ptrB) { return false; }
            ptrA = this.arr[f1];
            ptrB = (E) other.arr[f2];
            f1 = (f1 + 1) % arr.length;
            f2 = (f2 + 1) % other.arr.length;
        }
		return true; // When reached, every element matched successfuly
	}
} // end of Queue Class
