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
    public static final String ILLEGAL_INDEX = "Illegal Index @ ";
    private E[] data;   // Generic object array used to store list elements
    private int size;   // Current number of elements
    
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

    /************************ Private Helper Methods ********************************/
    /**
     * Resize the underlying internal array to a given capacity
     * @param capacity - The capacity to resize the ArrayList to
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity){
        E[] newArray = (E[]) new Object[capacity];
        for(int i=0; i < size; i++){
            newArray[i] = data[i];
        }
        this.data = newArray;
    }

    /**
     * Checks whether the given index is within the range [0, n-1]
     * @param i - the given index to check validity for
     * @param n - the range to check the index against, usually the size
     * @throws IndexOutOfBoundsException if the given index is negative or greater than
     *                                   either n or n-1 (size or size-1)
     */
    private void checkValidIndex(int i, int n) throws IndexOutOfBoundsException {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException(ILLEGAL_INDEX + i);
        }
    }
    
    /********************************** Access Methods ********************************/
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
        int i = 0;
        while(i < size){
            if(data[i].equals(e)){
                return i;
            }
            i++;
        }

        return -1; // Element not found
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
        checkValidIndex(i, size); // If index is outside of size
        return data[i];
    }

    /******************************** Update Methods ********************************/
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
        checkValidIndex(i, size+1); 
        if (size == data.length){   //Resize if underlying array is full
            resize (2*data.length); //Double the current capacity
        }
        // Shift any prexisting elements at or beyond point of insertion to the right
        for(int k = size-1; k >= i; k--){
            data[k+1] = data[k];
        }
        data[i] = e; // Made space for insertion
        size++;
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
        checkValidIndex(i,size);
        E displaced = data[i];
        data[i] = e;
        return displaced;
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
        checkValidIndex(i, size);
        E toRemove = data[i];
        // Shift the subsequent elements at the removed index by 1 to fill the gap
        for(int k= i; k < size-1; k++){
            data[k] = data[k+1];
        }
        data[size-1] = null; // Make eligible for garbage collection
        size--;
        return toRemove;
    }

    /**********************************  Methods ********************************/
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
