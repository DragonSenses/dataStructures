
/**
 * @author kendr
 *         Downside to SinglyLinkedLists is inefficiency in deleting arbitrary
 *         node in the interior of the list,
 *         since one needs access to the node that precedes the node to be
 *         deleted to have its links updated.
 * 
 *         A DoublyLinkedList possess nodes with two explicit references to the
 *         nodes before and after it.
 *         This allows for O(1) update operations such as insertion and deletion
 *         at arbritrary positions within the
 *         list.
 * 
 *         Usage of Sentinel nodes will help avoid special cases when operating
 *         near the boundaries of the list. The
 *         tradeoff for slightly higher memory usage is simplifying the logic of
 *         our operations. Two sentinel nodes
 *         for both head and tail will be used.
 *         1. Only the nodes between head and tail change
 *         2. All insertions will always be placed between a pair of existing
 *         nodes
 *         3. All deletions will also guarantee to be in a node stored with
 *         neighbors on both sides
 *         4. Special case for empty lists, regarding head/tail, do not apply
 *         like it does for SinglyLinkedLists
 * 
 */
public class DoublyLinkedList <E> { // Generics allows for variety of data types
	/** Nested Node Class */
	private static class Node<E> {
		private E data; // Reference to data stored in node
		private Node<E> next; // Reference to subsequent node in list
		private Node<E> prev; // Reference to the previous node in the list

		public Node(E e, Node<E> p, Node<E> n) {
			this.data = e;
			this.prev = p;
			this.next = n;
		}

		/** @return the reference to the element/data within Node */
		public E getData() {
			return this.data;
		}

		/** @return the reference to the previous node of the list */
		public Node<E> getPrev() {
			return this.prev;
		}

		/** @return the reference to the next node of the list */
		public Node<E> getNext() {
			return this.next;
		}

		/** @param p - The next node to set the reference to */
		public void setPrev(Node<E> p) {
			this.prev = p;
		}

		/** @param n - The next node to set the reference to */
		public void setNext(Node<E> n) {
			this.next = n;
		}
	} // end of Nested Node Class
	//Instance Variables of DoublyLinkedList
	private Node<E> head; // Sentinel node head
	private Node<E> tail; // Sentinel node tail
	private int size = 0;

	/** Default Constructor **/
	public DoublyLinkedList(){
		//When initializing sentinel nodes only the one link matters, the rest are null even data
		this.head = new Node<>(null,null,null); //Must initialize tail to set the reference to it
		this.tail = new Node<>(null,this.head,null);
		head.setNext(tail);
	}

	//Access Methods
	/**
	 * @return the number of nodes within the list
	 */
    public int size() { return this.size; } 

	/**
	 * @return true if list is empty, false otherwise
	 */
    public boolean isEmpty() { return this.size == 0; }

	/**
	 * Returns but does not remove the first node.data of the list
	 * @return The first element of the list
	 */
	public E first(){
		if(isEmpty()) { return null; }
		return head.getNext().getData(); //First element is after the sentinel head node
	}

	/**
	 * Returns but does not remove the last node.data of the list
	 * @return The last element of the list
	 */
	public E last(){
		if(isEmpty()) { return null; }
		return tail.getPrev().getData(); //Last element is before the sentinel tail node
	}

	
	// Quick Test
	public static void main(String[] args) {

	}

}
