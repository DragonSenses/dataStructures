import java.util.NoSuchElementException;

/**
 * A collection of objects with operations according to the last-in,first-out (LIFO)
 * principle. A user only has access or remove the most recently inserted object. 
 * 
 * Applications: Backtracking, Site/Search history, undo mechanisms, Depth-First Search, match braces
 * 
 * A dynamic Array-based stack to ensure constant operation times O(1) and memory efficiency
 * @author kendr
 */
public class Stack<E> {
    /**  Instance Variables **/
    private static final int INIT_SIZE = 8;  //Initial size of every default Stack

    private E[] arr;      // Underlying array of elements
    private int size;     // number of elements on stack

    /** Error Messages  **/
    private static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
    private static final String UNDERFLOW = "Stack Underflow";

    /**
     * Default Constructor that initializes an empty Stack
     */
    @SuppressWarnings("unchecked") //Compiler warns of unchecked cast due to generic array
    public Stack(){
        //E[] arr = new T[size] 
        //Declaring a generic array of type E[] but cannot instantiate, so must instantiate
        //an array of type Object[] then make a narrowing cast to type E[]
        this.arr = (E[]) new Object[INIT_SIZE];
        this.size = 0;
    }

    /**
     * Constructor that allows user to determine the initial size
     * @param initialCapacity the initial size of the Stack
     */
    @SuppressWarnings("unchecked")
    public Stack(int initialCapacity){
        if (initialCapacity <= 0) { // non-negative [0, infinity)
			this.arr = (E[]) new Object[INIT_SIZE];
			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
		} else { // If initialCapacity is a non-negative number, set Stack capacity
			this.arr = (E[]) new Object[initialCapacity];
		}
        this.size = 0;
    }

    /** Access Methods **/
    /**
     * @return number of element within the stack
     */
    public int size(){
        return this.size;
    }

    /**
     * @return true if stack is empty, false otherwise
     */
    public boolean isEmpty(){
        return this.size == 0;
    }

    /**
     * Returns, but does not remove, the element at the top of the Stack
     * @return The element most recently inserted to the Stack
     * @throws NoSuchElementException if stack is empty
     */
    public E peek() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException(UNDERFLOW); }
        return arr[size-1];
    }

    /** Helper Methods **/
    /**
     * Rescales the array to have the capacity to hold more or less elements
     * @param capacity - The capacity of the Stack to rescale to
     */
    @SuppressWarnings("unchecked")
    private void rescale(int capacity){
        E[] clone = (E[]) new Object[capacity];

        //Alternatively, clone = Arrays.copyOf(arr,capacity);
        for(int i = 0; i < this.size; i++){
            clone[i] = this.arr[i];
        }
        //Set the array reference to newly made clone
        this.arr = clone; 
    }

    /** Update Methods **/
    /**
     * Removes and Returns the element most recently added to the Stack
     * @return The element most recently inserted to the stack
     * @throws NoSuchElementException if Stack is empty
     */
    public E pop() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException(UNDERFLOW); }
        E element = arr[size-1]; //Hold the data temporarily
        //Delete the element from the array, then decrement size
        arr[size-1] = null; //Dereference to help garbage collection
        this.size--; 

        //Rescale if used stack capacity is 25%
        if(size > 0 && size == arr.length/4) { rescale(arr.length/2); }

        return element;
    }

    /**
     * Adds the element to the top of the stack
     * @param data The element to add
     */
    public void push(E data){
        //Rescale if full stack 
        if(this.size == arr.length) { rescale(2*arr.length); }
        arr[size++] = data;
    }

    /**
	 * Higher level notion of equivalence where we define two Stacks
	 * as equivalent if:
	 * I) They have the same Length
	 * II) The contents that are element-by-element are equivalent
	 * 
	 * @param o - The Stack object parameter to compare with the caller
	 * @return True, if both stacks are the same size and the contents are
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
		Stack<?> other = (Stack<?>) o; // Typecast and use unknown type 

		// 3. Size Check
		if (this.size != other.size) {
			return false;
		}
		E ptrA = this.arr[0];      // Traverses through the primary Stack
		E ptrB = (E) other.arr[0]; // Traverse through the secondary Stack

        // 4. Element-by-Element check
		//Traversal through both stacks
        for(int i = 0; i < this.arr.length; i++){
            if(ptrA != ptrB) { return false; }
            ptrA = this.arr[i];
            ptrB = (E) other.arr[i];
        }
		return true; // When reached, every element matched successfuly
	}
} // end of Stack class