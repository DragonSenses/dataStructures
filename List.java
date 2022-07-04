import java.util.Iterator;

/**
 * List Interface, a simple version of java.util.List.
 * 
 * Extend Iterable, allows the object to be a target for enhance for each
 * statement
 * 
 * @author kendr
 */
public interface List<E> extends Iterable<E> {
    /**
     * Returns the number of elements in the list.
     * 
     * @return number of elements in the list
     */
    int size();

    /**
     * Tests whether the list is empty.
     * 
     * @return true if the list is empty, false otherwise
     */
    boolean isEmpty();

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
    void add(int i, E e) throws IndexOutOfBoundsException;

    /**
     * Returns the index of the first instance of the object in the invoking list.
     * If obj is not an element of the list, -1 is returned
     * @param e - the element to find the index of
     * @return the index of the first instance of the object, otherwise -1 if object
     *         does not exist within the list
     */
    int indexOf(E e);

    /**
     * Returns, but does not remove, the element at index i.
     * 
     * @param i - the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is negative or greater than
     *                                   size()-1
     */
    E get(int i) throws IndexOutOfBoundsException;

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
    E set(int i, E e) throws IndexOutOfBoundsException;

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
    E remove(int i) throws IndexOutOfBoundsException;

    /**
     * Returns an iterator of the elements stored in the list.
     * 
     * @return iterator of the list's elements
     */
    Iterator<E> iterator();

    /**
     * Returns an iterator to the invoking list that begins at specified index
     * @param i - the index of the where the iterator begins
     * @return iterator of the sublist's elements
     */
    Iterator<E> iterator(int i);
}