import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A list in the form of a dynamic array, although it may be slower than
 * standard arrays it can be helpful where lots of manipulation in the array is needed.
 * A simple version to help my understanding of java.util.ArrayList and Iterator.
 * 
 * Iterator is a software design pattern that abstracts the process of scanning through
 * a sequence of elements, on element at a time. The inner class has 3 methods: hasNext(),
 * next(), and remove(). hasNext() can be used to detect the right conditions for next() 
 * to be called. Whereas remove() can filter a collection of elements (ie. discard all 
 * negative numbers from a data set). Java Iterable class plays a fundamental role for 
 * the "for each loop" as 1) and 2) are equivalent
 * 1) for(ElementType variable: collection) { 
 *        loopBody //may refer to variable 
 *    }
 * 2) Iterator<ElementType> it = collection.iter();
 * while(iter.hasNext()){ 
 *  ElementType variable = iter.next()
 *  loopBody
 * }
 * 
 * Two types of iterators:
 * I. Snapshot iterators maintains its own private copy of the sequence of elements, made at
 * the time iterator was created. Unaffected by subsequent changes to primary collection. 
 * Downside is it requires O(n) time and auxliary space
 * II. Lazy iterator does not make a copy but performs a piecewise traversal of the primary
 * structure only wheen the next() method is called to request another element. Advantage is
 * O(1) space and construction time, but downside is that its behavior is affected by 
 * modifications to the primary structure before the iteration completes (except for iterator's
 * own remove() method).
 * 
 * The Lazy Iterator implementation is done here, with a big difference from Java libraries 
 * version, it does not implement a "fail-fast" behavior that invalidates the iterator if
 * underlying collection is modified unexpectedly. It also does have other methods including
 * hasPrevious() or nextIndex().
 * 
 * @author kendr
 */
public class ArrayList<E> implements List<E> {
    /** Instance Variables **/
    public static final int DEFAULT_CAPACITY = 10; // Default initial array capacity
    public static final String ILLEGAL_INDEX = "Illegal Index @ ";
    private static final String ILLEGAL_ARG = "Illegal Argument";
    private E[] data; // Generic object array used to store list elements
    private int size; // Current number of elements

    /** Constructors **/
    public ArrayList() { // Default constructor creates ArrayList with default initial capacity
        this(DEFAULT_CAPACITY);
    }

    /** Creates an Array List with a given capacity **/
    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        if(capacity < 0) { 
            this.data = (E[]) new Object[DEFAULT_CAPACITY];
            throw new IllegalArgumentException(ILLEGAL_ARG);
        }
        // Generic Array -> instantiate Object array, then narrowing type cast to E[]
        this.data = (E[]) new Object[capacity];
    }

    /************************
     * Private Helper Methods
     ********************************/
    /**
     * Resize the underlying internal array to a given capacity
     * 
     * @param capacity - The capacity to resize the ArrayList to
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        E[] newArray = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = data[i];
        }
        this.data = newArray;
    }

    /**
     * Checks whether the given index is within the range [0, n-1]
     * 
     * @param i - the given index to check validity for
     * @param n - the range to check the index against, usually the size
     * @throws IndexOutOfBoundsException if the given index is negative or greater
     *                                   than either n or n-1 (size or size-1)
     */
    private void checkValidIndex(int i, int n) throws IndexOutOfBoundsException {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException(ILLEGAL_INDEX + i);
        }
    }

    /**********************************
     * Access Methods
     ********************************/
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
        while (i < size) {
            if (data[i].equals(e)) {
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

    /********************************
     * Update Methods
     ********************************/
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
        checkValidIndex(i, size + 1);
        if (size == data.length) { // Resize if underlying array is full
            resize(2 * data.length); // Double the current capacity
        }
        // Shift any prexisting elements at or beyond point of insertion to the right
        for (int k = size - 1; k >= i; k--) {
            data[k + 1] = data[k];
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
        checkValidIndex(i, size);
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
        for (int k = i; k < size - 1; k++) {
            data[k] = data[k + 1];
        }
        data[size - 1] = null; // Make eligible for garbage collection
        size--;
        return toRemove;
    }

    /**
     * String representation of the contents of the ArrayList.
     * 
     * @return Textual representation of the ArrayList
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        for (int k = 0; k < size; k++) {
            if (k > 0) { str.append(", "); }
            str.append(data[k]);
        }
        str.append("]");
        return str.toString();
    }

    /************************* nested Iterator class  ********************************/
    private class ArrayIterator implements Iterator<E> {
        /** Instance Variables of ArrayIterator **/
        private int i;                     // Index of the next element
        private boolean removable = false; // Flag to check if remove can be called
        private static final String NO_SUCH_ELEMENT = "No next element!";
        private static final String ILLEGAL_STATE = "No element to remove!";

        /** Default ArrayIterator constructor that begins at the start of the list */
        public ArrayIterator() {
            this(0); // Default Iterator starts at index 0
        }

        /** ArrayIterator constructor that begins at a specified index of the list **/
        public ArrayIterator(int i){
            this.i = i;
        }

        /**
         * Tests whether the iterator has a next object
         * @return true if the iteration has more elements 
         */
        public boolean hasNext() {
            return i < size; // Use the member field "size" of the outer instance
        }

        /**
         * Returns the next element in the iterator
         * 
         * @returns the next element
         * @throws NoSuchElementException if there are no further elements
         */
        public E next() throws NoSuchElementException {
            if ( i == size) { throw new NoSuchElementException(NO_SUCH_ELEMENT); }
            removable = true;   // Element can be subsequently removed
            return data[i++];   // Post-Increment index to be ready for future call
        }

        /**
         * Removes the element returned by most recent call to next
         * @throws IllegalStateException If next has not been called, or if remove was
         *                               already called since recent next
         */
        public void remove() throws IllegalStateException {
            if(!removable) { throw new IllegalStateException(ILLEGAL_STATE); }
            ArrayList.this.remove(i-1); // Last element returned by most recent call to next is removed
            i--;                    // Next element has shifted one to the left
            removable = false;      // Flag removable not allowed until next is called again
        }

    } // end of Arrayiterator class

    /**
     * Returns an iterator of the elements stored in the list.
     * 
     * @return iterator of the list's elements
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator(); // Create a new instance of the Iterator inner class 
    }

    /**
     * Returns an iterator to the invoking list that begins at specified index
     * 
     * @param i - the index of the where the iterator begins
     * @return iterator of the sublist's elements
     */
    @Override
    public Iterator<E> iterator(int i) {
        return new ArrayIterator(i);    // Create a new instance of inner class at specified index
    }

} // end of ArrayList class 
