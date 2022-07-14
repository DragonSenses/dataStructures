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
    private Node<E> head;
    private Node<E> tail; 
    private int size = 0; 

    /** Construct an empty PositionalList */
    public LinkedPositionalList(){
        head = new Node<>(null,null,null); // 1. Create the Head
        tail = new Node<>(null,head,null);   // 2. Create the tail with prev link to head
        head.setNext(tail);                       // 3. Set Head next link to tail
    }
}
