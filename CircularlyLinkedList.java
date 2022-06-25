import java.lang.StringBuilder; //To be used in toString() for mutability and concatenation within a loop

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
	private static boolean testing = true; //Allows the running of tests in output

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
		}else { //Case 2: non empty list, add new Node to head with link to previous head
			Node<E> newNode = new Node<>(e,tail.getNext()); 
			//Set the tail link to new Node
			tail.setNext(newNode);
		}
		size++; //Increment List size
	}

	/**
	 * Adds Node with element e to end of list
	 * @param e - The element to add
	 */
	public void addLast(E e) {
		//Can reuse method addFirst() to add the node to front of the list
		addFirst(e); 
		tail = tail.getNext(); //Then update the tail reference to refer to the head
	}

	/**
	 * Rotates the head node to the back of list, becoming the tail. Moves the tail reference up.
	 * (head) A  B  C (tail)  yields
	 * (head) C  B  A (tail)  
	 */
	public void rotate() {
		if(tail != null) { //Non Empty list?
			tail = tail.getNext();
		}
	}

	/**
	 * Removes and returns the first node's data
	 * @return	the first element
	 */
	public E removeFirst() {
		if(isEmpty()) { return null; }
		Node<E> head = tail.getNext(); //Store the head node temporarily
		if (head == tail) { //Case 1: Only One node (both head and tail)
			tail = null; 
		} else { //Case 2: More than One Node in the List
			//Set the tail.next to point to the head.next, skipping & removing the head
			tail.setNext(head.getNext()); 
		}
		size--; //Decrement after removal
		return head.getData();
	}

    /**
     * @returns A String representation of CircularlyLinkedList
     */
    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();
        list.append("LinkedList: ");

        //Traverse through each node to add data to String
		Node<E> curr = this.tail.getNext();
        for(; curr.next != tail; curr = curr.next){
            list.append(curr.getData() + "");
            list.append(" ");
        }
        //Condition stops at the penultimate node to tail since curr.next == tail
		list.append(curr.getData() + "");
		list.append(" ");
        //Still must print out the last Node's data
        list.append(this.tail.getData() + "");

        return list.toString();
    }

	//Quick test
	public static void main(String[] args) {
		if(testing){
			CircularlyLinkedList<Integer> list = new CircularlyLinkedList<>();
			list.addFirst(4);
			list.addFirst(3);
			list.addFirst(2);
			list.addFirst(1);
			list.addLast(5);
			System.out.println(list.toString()); //Expect List 1-5
			System.out.println("Size is: " + list.size());
			list.removeFirst();
			System.out.println(list.toString()); //Expect List 2-5
			System.out.println("Size is: " + list.size());
		}
	}
}
