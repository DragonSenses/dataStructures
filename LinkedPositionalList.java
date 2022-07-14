/**
 * Implementation of a positional list using a Doubly Linked List
 */
public class LinkedPositionalList<E> {

    /**
     * The position interface provides a general abstraction for the
     * location of an element within a structure. A position acts as 
     * a marker or token within a broader list. A position p, which is 
     * associated with some element e in a list L, does not change, 
     * even if the index of e changes in L due to insertions or 
     * deletions elsewhere in the list. The only way in which a 
     * position is invalid is if that position is removed. Even when
     * we replace the element e stored at p with another element, p
     * does not change. This allows a position type that serves as 
     * parameters to some methods and returns values from other
     * methods of a list
     */
    public interface Position<E> {

        /**
         * Returns the element stored at the position
         * @return the stored element
         * @throws IllegalStateException if it is invalid position
         */
        E getElement() throws IllegalStateException;
    }

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

}
