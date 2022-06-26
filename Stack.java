/**
 * A collection of objects with operations according to the last-in,first-out (LIFO)
 * principle. A user only has access or remove the most recently inserted object. 
 * 
 * Applications: Backtracking, Site/Search history, undo mechanisms, Depth-First Search, match braces
 * 
 * A resizing Array-based stack to ensure constant operation times O(1) and memory efficiency
 * @author kendr
 */
public class Stack<E>{
    private static final int INIT_SIZE = 8;  //Initial size of the Stack

    private E[] arr;      // Underlying array of elements
    private int size;   // number of elements on stack

    /**
     * Default Constructor of Stack
     */
    @SuppressWarnings("unchecked") //Compiler warns of unchecked cast during generic array
    public Stack(){
        //E[] arr = new T[size] 
        //Declaring a generic array of type E[] but cannot instantiate, so must instantiate
        //an array of type Object[] then make a narrowing cast to type E[]
        this.arr = (E[]) new Object[INIT_SIZE];
        this.size = 0;
    }
}
