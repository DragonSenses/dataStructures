import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of a positional list using a Doubly Linked List. 
 * 
 * A positional list is a collection of positions, each of which stores an
 * element. Positions are a simple abstract data type (ADT) in which it 
 * provides a general abstraction for the location of an element within a
 * structure.
 * 
 * Access Methods: first(), last(), before(p), after(p), isEmpty(), size()
 * 
 * Note that the most of these access methods return the associated positions
 * not the elements. The advantage of receiving a position as a return value
 * is that we can subsequently use that position to traverse the list. In this
 * implementation the null reference is returned when after() is called on the
 * last position, or before() is called at the front of the list, or when 
 * first() or last() are called on an empty list. 
 * 
 * Update Methods: addFirst(e), addLast(e), addBefore(p,e), addAfter(p,e), 
 * set(p,e), remove(p).
 */
public class LinkedPositionalList<E> {

    /**
     * Node of a doubly linked list, which stores a reference to its
     * element and to both the previous and next node in the list. In
     * order to identify locations withn the linked list we set nodes
     * as the positions. 
     */
    private static class Node<E> implements Position<E> {
        private E element; // reference to the element stored at this node
        private Node<E> prev; // reference to the previous node in the list
        private Node<E> next; // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param p reference to a node that should precede the new node
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        /** Public Access Methods **/
        /**
         * Returns the element stored at the node.
         * 
         * @return the stored element
         * @throws IllegalStateException if node not currently linked to others
         */
        public E getElement() throws IllegalStateException {
            // If node is not currently linked to others, a defunct node
            if (next == null) {
                throw new IllegalStateException("Position no longer valid");
            }
            return element;
        }

        /**
         * Returns the node that precedes this one (or null if no such node).
         * 
         * @return the preceding node
         */
        public Node<E> getPrev() {
            return prev;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         * 
         * @return the following node
         */
        public Node<E> getNext() {
            return next;
        }

        /**  Update Methods **/ 
        /**
         * Sets the node's element to the given element e.
         * 
         * @param e the node's new element
         */
        public void setElement(E e) {
            element = e;
        }

        /**
         * Sets the node's previous reference to point to Node n.
         * 
         * @param p the node that should precede this one
         */
        public void setPrev(Node<E> p) {
            prev = p;
        }

        /**
         * Sets the node's next reference to point to Node n.
         * 
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            next = n;
        }
    } //  end of nested Node class 

    /** Instance Variables of LinkedPositionalList **/
    private Node<E> head;   // Sentinel Head Node
    private Node<E> tail;   // Sentinel Tail Node
    private int size = 0;   // The number of elements within the list

    private final static String ILLEGAL_POS = "Invalid Position";
    private final static String NULL_NODE = "Node at Position is no longer in the list";

    /** Construct an empty PositionalList */
    public LinkedPositionalList(){
        head = new Node<>(null,null,null); // 1. Create the Head
        tail = new Node<>(null,head,null);   // 2. Create the tail with prev link to head
        head.setNext(tail);                       // 3. Set Head next link to tail
    }

    /** Private Utility Methods **/

    /**
     * Veriies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. NOTE: Does not verify if position
     * belongs to this particular list instance.
     * 
     * @param p - A Position
     * @return the underlying Node instance at that position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    private Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException(ILLEGAL_POS);
        Node<E> node = (Node<E>) p; // safe cast
        if(node.getNext() == null) {
            throw new IllegalArgumentException(NULL_NODE);
        }
        return node; 
    }
    
    /**
     * Returns the given node as a Position, unless it is a sentinel node, in
     * which case null is returned (user must not be exposed to sentinels)
     * @param node The node to be returned as a Position
     * @return The node as a Position, otherwise null if it is a sentinel node
     */
    private Position<E> position(Node<E> node) {
        // If node is either one of the sentinel nodes
        if (node == head || node == tail) { return null; }
        return node;
    }

    /**
     * Adds an element to the linked list between the given nodes.
     * The given predecessor and successor should be neighboring each
     * other prior to the call. This updates the links to the node.
     *
     * @param e        The element to add
     * @param pred     node just before the location where the new element is inserted
     * @param succ     node just after the location where the new element is inserted
     * @return the new element's node
     */
    private Position<E> addBetween(E e, Node<E> pred, Node<E> succ) {
        Node<E> newest = new Node<>(e, pred, succ);  // create a new node
        // Update the links of predecessor and successor nodes to new node
        pred.setNext(newest);
        succ.setPrev(newest);
        size++;
        return newest;
    }

    /** Public Access Methods **/
    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */
    public int size() { return size; }

    /**
     * Tests whether the list is empty.
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() { return size == 0; }

    /**
     * Returns the first Position in the list.
     *
     * @return the first Position in the list (or null, if empty)
     */
    public Position<E> first() {
        return position(head.getNext());
    }

    /**
     * Returns the last Position in the list.
     *
     * @return the last Position in the list (or null, if empty)
     */
    public Position<E> last() {
        return position(tail.getPrev());
    }

    /**
     * Returns the Position immediately before Position p.
     * @param p   a Position of the list
     * @return the Position of the preceding element (or null, if p is first)
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getPrev());
    }

    /**
     * Returns the Position immediately after Position p.
     * @param p   a Position of the list
     * @return the Position of the following element (or null, if p is last)
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getNext());
    }

    /** Public Update Methods **/

    /**
     * Inserts an element at the front of the list.
     *
     * @param e the new element
     * @return the Position representing the location of the new element
     */
    public Position<E> addFirst(E e) {
        return addBetween(e, head, head.getNext());      
    }

    /**
     * Inserts an element at the back of the list.
     *
     * @param e the new element
     * @return the Position representing the location of the new element
     */
    public Position<E> addLast(E e) {
        return addBetween(e, tail.getPrev(), tail);     
    }

    /**
     * Inserts an element immediately before the given Position.
     *
     * @param p the Position before which the insertion takes place
     * @param e the new element
     * @return the Position representing the location of the new element
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node.getPrev(), node);
    }

    /**
     * Inserts an element immediately after the given Position.
     *
     * @param p the Position after which the insertion takes place
     * @param e the new element
     * @return the Position representing the location of the new element
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node, node.getNext());
    }

    /**
     * Replaces the element stored at the given Position and returns the replaced element.
     *
     * @param p the Position of the element to be replaced
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E oldElement = node.getElement();
        node.setElement(e);
        return oldElement;
    }

    /**
     * Removes the element stored at the given Position and returns it.
     * The given position is invalidated as a result.
     *
     * @param p the Position of the element to be removed
     * @return the removed element
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p); // Validate the incoming position
        // Take the node's neighbors
        Node<E> before = node.getPrev();
        Node<E> after = node.getNext();
        // Update the neighbor's links to each other
        before.setNext(after);
        after.setPrev(before);
        size--; // Decrement the size
        E removed = node.getElement();
        // Assist Garbage Collection by setting links of removed node to null
        // This also allows us to detect whether a position is defunct later on
        node.setElement(null);           
        node.setNext(null);             
        node.setPrev(null);
        return removed;
    }

    /**
     * Produce a String Representation of the contents of this Positional List.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        Node<E> curr = head.getNext();
        while (curr != tail) {
          str.append(curr.getElement());
          curr = curr.getNext();
          if (curr != tail)
            str.append(", ");
        }
        str.append("]");
        return str.toString();
      }

    /**
     * A iterator class that is nonstatic, meaning that each instance of LinkedPositionalList
     * has its own iterator. The iterator can make an implicit reference to the outer list
     * allowing list methods to be called directly. 
     */
    private class PositionIterator implements Iterator<Position<E>> {
        // A Position pointer keeps track of position in the list, initialized as first position
        private Position<E> pointer = first(); 
        // A Position pointer that keeps the most recent element thats been called with next
        private Position<E> recent = null; 
        // Error Messages
        private static String NO_SUCH_ELEM = "There is no further elements to iterate on";
        private static String ILLEGAL_STATE = "There is nothing to remove";

        /**
         * Tests whether the iterator has a next object
         * @return true if there are more objects to call next on, false otherwise
         */
        public boolean hasNext() { return pointer != null; }

        public Position<E> next() throws NoSuchElementException {
            if (pointer == null) { throw new NoSuchElementException(NO_SUCH_ELEM); }
            recent = pointer;   // Save the current pointer element as it may be removed later
            pointer = after(pointer); // Reassign the pointer to position immediately after it
            return recent; 
        }

        /**
         * Removes the element returned by the most recent call to next
         * @throws IllegalStateException if next has not yet been called, or
         *                               if remove was already called since recent next
         */
        public void remove() throws IllegalStateException {
            if(recent == null ) { throw new IllegalStateException(ILLEGAL_STATE); }
            LinkedPositionalList.this.remove(recent); // Remove called from outer list class
            recent = null;  // Prevent removed to be called again until next is called
        }
    } // end of nested Iterator Class

    /**
     * Returns an iterator of the elements stored in the list.
     * 
     * @return iterator of the list's elements
     */
    @Override
    public Iterator<E> iterator() {
        return new PositionIterator(); // Create a new instance of the Iterator inner class 
    }
    private class PositionIterable implements Iterable<Position<E>> {
        public Iterator<Position<E>> iterator() { return new PositionIterator(); }
    }


}
