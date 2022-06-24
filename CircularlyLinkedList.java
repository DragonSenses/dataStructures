/**
 * A LinkedList in which the next reference of the tail node is 
 * set to refer back to the head of the list rather than null.
 *
 * Adds new update method to SingularlyLinkedList, called rotate()
 * which moves the first element to the end of the list
 * 
 * Another way to optimize is no longer needing the head reference,
 * since we can locate the head using the tail.getNext() 
 * 
 * @author kendr
 *
 */
public class CircularlyLinkedList<E> {
	/** Nested Node Class */
    private static class Node <E> {
        private E data;             // Reference to data stored in node
        private Node<E> next;       // Reference to subsequent node in list
        
		public Node (E e, Node <E> n){ 
            this.data = e;
            this.next = n;
        }

        /** @return the reference to the element/data within Node */
        public E getData() { return this.data; }
        
        /** @return the reference or link to the next node of the list */
        public Node<E> getNext() { return this.next; }

        /** @param n - The next node to set the reference to */
        public void setNext(Node<E> n) { this.next = n;}
    } // end of Nested Node Class
	
	//Instance Variables or Member Fields of CircularlyLinkedList
	private Node<E> tail = null; // Only store tail, since head = tail.next
	private int size = 0; 		 // Number of nodes in the list

	//Default Constructor
	public CircularlyLinkedList(){}

	public int size() { return size; }
	public boolean isEmpty() { return size == 0; }

	//Access Methods
	/**
	 *  
	 * @return The first node data without removing, null if empty list
	 */
	public E first() {
		if(isEmpty()) { return null; }
		return tail.getNext().getData();
	}

	/**
	 * 
	 * @return The last node data without removing, null if empty list
	 */
	public E last() {
		if(isEmpty()) { return null; }
		return tail.getData();
	}

	//Update Methods
	/**
	 * Adds element E node to the front of the list.
	 * @param e - The element to add
	 */
	public void addFirst(E e) { 
		if(isEmpty()){ //Case 1, empty list set node as tail
			tail = new Node<>(e,null);
			tail.setNext(tail); //Set the tail to link to itself circularly
		}else {
			Node<E> newNode = new Node<>(e,tail.getNext());
		}
	}

	
	public void addLast(E e) {

	}

	public void rotate() {

	}

	public E removeFirst() {
		return null;
	}

	//Quick test
	public static void main(String[] args) {
		

	}
}
