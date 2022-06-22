/**
 * 
 * @author kendr
 * A Linked List is a collection of nodes that form a linear sequence. 
 * A Singly linked List has each node store two values. 
 * 	1) Data: a reference to an object that is an element of the sequence
 *  2) Link: a reference to the next node of the list 
 * 
 * Generic LinkedList allows for variety of data types
 */
public class SinglyLinkedList <E> {
	/**
     * Nested Node Class
     */
    private static class Node <E> {
        private E data;             // Reference to data stored in node
        private Node<E> next;       // Reference to subsequent node in list
        public Node (E e, Node <E> n){
            this.data = e;
            this.next = n;
        }

        /**
         * 
         * @return the reference to the element/data within Node
         */
        public E getData() { return this.data; }
        
        /**
         * 
         * @return the reference or link to the next node of the list
         */
        public Node<E> getNext() { return this.next; }

        /**
         * 
         * @param n - The next node to set the reference to
         */
        public void setNext(Node<E> n) { this.next = n;}
    }

    //Instance variables of SinglyLinkedList

    //Access Methods

    //Update Methods
    
}
