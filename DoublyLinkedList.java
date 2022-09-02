import java.lang.StringBuilder;
import java.util.NoSuchElementException;
/**
 * Downside to SinglyLinkedLists is inefficiency in deleting arbitrary
 * node in the interior of the list, since one needs access to the node
 * that precedes the node to be deleted to have its links updated.
 * 
 * A DoublyLinkedList possess nodes with two explicit references to the
 * nodes before and after it. This allows for O(1) update operations 
 * such as insertion and deletion at arbritrary positions within the
 * list.
 * 
 * Usage of Sentinel nodes will help avoid special cases when operating
 * near the boundaries of the list. The tradeoff for slightly higher
 * memory usage is simplifying the logic of our operations. Two sentinel
 * nodes for both head and tail will be used.
 * 1. Only the nodes between head and tail change
 * 2. All insertions will always be placed between a pair of existing nodes
 * 3. All deletions will also guarantee to be in a node stored with neighbors
 *    on both sides
 * 4. Special case for empty lists, regarding head/tail, do not apply like it
 * does for SinglyLinkedLists
 * 
 * @author kendr
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

	/** Error Messages **/
	private static final String UNDERFLOW = "List Underflow: There is nothing to remove!";

	/** Default Constructor **/
	public DoublyLinkedList(){
		//When initializing sentinel nodes only the one link matters, the rest are null even data
		this.head = new Node<>(null,null,null); //Must initialize tail to set the reference to it
		this.tail = new Node<>(null,this.head,null);
		head.setNext(tail);
	}

	/** Access Methods **/
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
	 * @throws NoSuchElementException when list is empty 
	 */
	public E first() throws NoSuchElementException {
		if(isEmpty()) { throw new NoSuchElementException(UNDERFLOW); }
		return head.getNext().getData(); //First element is after the sentinel head node
	}

	/**
	 * Returns but does not remove the last node.data of the list
	 * @return The last element of the list
	 * @throws NoSuchElementException when list is empty
	 */
	public E last() throws NoSuchElementException {
		if(isEmpty()) { throw new NoSuchElementException(UNDERFLOW); }
		return tail.getPrev().getData(); //Last element is before the sentinel tail node
	}

	/** Private Helper Methods **/
	/**
	 * Private update method that adds element e in betwixt two given nodes
	 */
	private void insert(E e, Node<E> precursor, Node<E> successor){
		//Create a new node while updating both its links
		Node<E> latest = new Node<>(e, precursor, successor);
		precursor.setNext(latest);
		successor.setPrev(latest);
		size++; 
	}

	/**
	 * Removes the given node from the list, updates neighbor links, and return its data
	 * 
	 * @return The data of the removed Node
	 */
	private E remove(Node<E> node){
		//Acquire the two neighbors of the node to delete
		Node<E> before = node.getPrev();
		Node<E> after = node.getNext();
		before.setNext(after); //Set the node.previous.next link to that of successor
		after.setPrev(before); //Set the node.next.prev link to that of precursor
		size--; //Decrement number of nodes
		return node.getData();
	}

	/** Update Methods **/
	/**
	 * Adds node with element e to the front of the list
	 * @param e	The data of the node to add
	 */
	public void addFirst(E e){
		insert(e,head,head.getNext());
	}

	/**
	 * Adds node with element e to the end of the list
	 * @param e The data of the node to add
	 */
	public void addLast(E e){
		insert(e,tail.getPrev(),tail);
	}

	/**
	 * Removes and returns first element of the list
	 * @return The data of the node removed
	 * @throws NoSuchElementException when list is empty
	 */
	public E removeFirst() throws NoSuchElementException {
		if(isEmpty()) { throw new NoSuchElementException(UNDERFLOW); }
		return remove(head.getNext()); // Remember, first element is after sentinel head node
	}

	/**
	 * Removes and returns the last element of the list
	 * @return The data of the node removed
	 * @throws NoSuchElementException when list is empty
	 */
	public E removeLast() throws NoSuchElementException {
		if(isEmpty()) { throw new NoSuchElementException(UNDERFLOW); }
		return remove(tail.getPrev()); // Remember, last element is before sentinel tail node
	}

	/**
	 * Removes and returns first element of the list
	 * @return The data of the node removed, null if list is empty
	 */
	public E pollFirst() {
		if(isEmpty()) { return null; }
		return remove(head.getNext()); // Remember, first element is after sentinel head node
	}

	/**
	 * Removes and returns the last element of the list
	 * @return The data of the node removed, null if list is empty
	 */
	public E pollLast() {
		if(isEmpty()) { return null; }
		return remove(tail.getPrev()); // Remember, last element is before sentinel tail node
	}
	
	/**
     * @returns A String representation of SinglyLinkedList
     */
    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();
        list.append("LinkedList: ");

		//The first element of the list is after sentinel node 
		Node<E> curr = this.head.getNext(); 

        //Traverse through each node to add data to StringBuilder
        for(; curr.next != null; curr = curr.next){
            list.append("(" + String.valueOf(curr.getData()) + ")");
			if(curr.next != tail){
				list.append(" -> ");
			}
        }
        //Condition stops at the tail sentinel node since curr.next == null
        return list.toString();
    }

	/**
	 * Higher level notion of equivalence where we define two DoublyLinkedList
	 * as equivalent if:
	 * I) They have the same Length
	 * II) The contents that are element-by-element are equivalent
	 * 
	 * @param o - The DoublyLinkedList object parameter to compare with the
	 *          caller
	 * @return True, if both lists are the same size and the contents are
	 *         element-by-element equivalent, otherwise false
	 */
	@Override
	public boolean equals(Object o) {
		// 1. Null Treatment
		if (o == null) {
			return false;
		}
		/*
		 * 2. Class Equivalence - getClass() vs. instanceof
		 * getClass() only returns true if object is actually an instance of the
		 * specified class but instanceof operator returns true even if the object
		 * is a subclass of a specified class or interface in Java; allows implementation
		 * of equality between super and subclasses but does not satisfy symmetry: 
		 * x.equals(y) is true then y.equals(x) is also true, but if you swap x
		 * with a subclass then x instanceof y is true but y instanceof x will be false,
		 * hence equals is false. We do not consider a DoublyLinkedList to be 
		 * equivalent to SinglyLinkedList with with the same contents
		 */
		if (this.getClass() != o.getClass()) {
			return false;
		}
		// Although declared formal type parameter <E> cannot detect at runtime whether
		// other list has a matching type. See Type erasure.
		DoublyLinkedList<?> other = (DoublyLinkedList<?>) o; // Typecast and use unknown type 
		// 3. Size Check
		if (this.size != other.size) {
			return false;
		}
		Node<?> ptrA = this.head.getNext(); // Traverses through the primary list
		Node<?> ptrB = other.head.getNext(); // Traverse through the secondary list

		//Remember that we must check when our ptr (runner) becomes the sentinel tail node and stop,
		//otherwise when we call tail.getData() it will end up with NullPointerException. Therefore,
		//Our conditional will be checking if its next link reference is null rather than the node 
		//itself. Ex. ptr.next != null , and not ptr != null
		//Traversal through both lists
		for (;ptrA.getNext() != null; ptrA = ptrA.getNext()) {
			if (!ptrA.getData().equals(ptrB.getData())) {
				return false;
			}
			ptrB = ptrB.getNext();
		}
		return true; // When reached, every element matched successfuly
	}

	// Quick Test
	public static void main(String[] args) {
			DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
			list.addFirst(4);
			list.addFirst(3);
			list.addFirst(2);
			list.addFirst(1);
			list.addLast(5);
			System.out.println(list.toString()); //Expect List 1-5
			System.out.println("Size is: " + list.size());

			System.out.println("Removed: " + list.removeFirst());
			System.out.println(list.toString()); //Expect List 2-5
			System.out.println("Size is: " + list.size());

			System.out.println("Removed: " + list.removeLast());
			System.out.println(list.toString()); //Expect List 2-5
			System.out.println("Size is: " + list.size());

			DoublyLinkedList<Integer> list2 = new DoublyLinkedList<>();
			list2.addFirst(4);
			list2.addFirst(3);
			list2.addFirst(2);

			System.out.println(list.equals(list2));
	}

}