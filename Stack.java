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
public class Stack<E>{
    //Instance variables
    private static final int INIT_SIZE = 8;  //Initial size of every Stack

    private E[] arr;      // Underlying array of elements
    private int size;     // number of elements on stack

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
    public E peek(){
        if(isEmpty()){ throw new NoSuchElementException("Stack is empty"); }
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
    public E pop(){
        if(isEmpty()){ throw new NoSuchElementException("Stack is empty"); }
        E element = arr[size-1]; //Hold the data temporarily
        //Delete the element from the array, then decrement size
        arr[size-1] = null; 
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
}