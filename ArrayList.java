import java.util.Iterator;

/**
 * A list in the form of a dynamic array, although it may be slower than
 * standard arrays
 * it can be helpful where lots of manipulation in the array is needed. A simple
 * version
 * to help my understanding of java.util.ArrayList
 * 
 * @author kendr
 */
public class ArrayList<E> implements List<E> {
    /** Instance Variables **/
    public static final int DEFAULT_CAPACITY = 10; //Default initial array capacity
    private E[] data;   // Generic object array used to store list elements
    private int size; // Current number of elements
    
    /** Constructors **/
    public ArrayList() { //Default constructor creates ArrayList with default initial capacity
        this(DEFAULT_CAPACITY);
    }

    /** Creates an Array List with a given capacity **/
    @SuppressWarnings("unchecked")
    public ArrayList(int capacity){
        //Generic Array -> instantiate Object array, then narrowing type cast to E[]
        this.data = (E[]) new Object[capacity];
    }

    /** Private Helper Methods **/
    private void resize(int capacity){

    }

    private void checkValidIndex(int i, int n) throws IndexOutOfBoundsException {

    }

    /** Access Methods **/
    /**
     * Returns the number of elements in the list.
     * 
     * @return number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * Tests whether the list is empty.
     * 
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the index of the first instance of the object in the invoking list.
     * If obj is not an element of the list, -1 is returned
     * 
     * @param e - the element to find the index of
     * @return the index of the first instance of the object, otherwise -1 if object
     *         does not exist within the list
     */
    public int indexOf(E e) {

    }

    /**
     * Returns, but does not remove, the element at index i.
     * 
     * @param i - the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is negative or greater than
     *                                   size()-1
     */
    public E get(int i) throws IndexOutOfBoundsException {

    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make space (that is
     * any preexisting elements at or beyond the point of insertion are shifted up
     * thus no elements are overwritten).
     * 
     * @param i - the index at which the new element should be stored
     * @param e the new element to be stored
     * @throws IndexOutOfBoundsException if the index is negative or greater than
     *                                   size()
     */
    public void add(int i, E e) throws IndexOutOfBoundsException {

    }

    /**
     * Replaces the element at the specified index, and returns the element
     * previously stored.
     * 
     * @param i the index of the element to replace
     * @param e the new element to be stored
     * @return the previously stored element
     * @throws IndexOutOfBoundsException if the index is negative or greater than
     *                                   size()-1
     */
    public E set(int i, E e) throws IndexOutOfBoundsException {

    }

    /**
     * Removes and returns the element at the given index, shifting all subsequent
     * elements in the list one position closer to the front (that is, indexes of
     * subsequent elements are decremented by one).
     * 
     * @param i the index of the element to be removed
     * @return the element that had be stored at the given index
     * @throws IndexOutOfBoundsException if the index is negative or greater than
     *                                   size()
     */
    public E remove(int i) throws IndexOutOfBoundsException {

    }

    /**
     * Returns an iterator of the elements stored in the list.
     * 
     * @return iterator of the list's elements
     */
    public Iterator<E> iterator() {

    }

    /**
     * Returns an iterator to the invoking list that begins at specified index
     * 
     * @param i - the index of the where the iterator begins
     * @return iterator of the sublist's elements
     */
    public Iterator<E> iterator(int i) {

    }
}
