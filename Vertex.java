/**
 * A vertex of a graph. Also known as a Node.
 */
public class Vertex<V> {
    private V data;
    private Node<Vertex<V>> node;

    /**
     * Returns the data associated with the vertex. It
     * retrieves the data stored within the node.
     * 
     * @return the data associated with the vertex
     */
    public V getData() {
        return data;
    }

    /**
     * Node of a doubly linked list, which stores a reference to its
     * element and to both the previous and next node in the list. In
     * order to identify locations withn the linked list we set nodes
     * as the positions. 
     */
    private static class Node<E> {
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
                throw new IllegalStateException("Element no longer valid");
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
}