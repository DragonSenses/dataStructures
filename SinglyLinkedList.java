import java.lang.StringBuilder;

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
    private int size = 0;           // Number of Nodes in the List 
    //Design choice to have two node references to the first and last nodes
    private Node<E> first = null;    
    private Node<E> last = null; 

    //Default Constructor for SinglyLinkedList
    public SinglyLinkedList(){
        super(); //Initializes its superclass including Object
    }

    //Access Methods
    public int size() { return this.size; }
    public boolean isEmpty() { return this,size == 0; }
    
    public E first(){
        if(this.isEmpty() == true) { return null; } 
        return first.getData();
    }
    
    public E last(){
        if(this.isEmpty() == true) { return null; }
        return last.getData();
    }

    //Update Methods
    //Adds element to the front of the list
    public void addFirst(E e){
        //Assign the first node to the new Node, with a link to the old First
        this.first = new Node<>(e,first);
        //Special Case: if list was empty, then the head node becomes the tail
        if(this.size == 0){
            this.last == first;
        }
        this.size++;
    }

    //Adds an element to the end of the list
    public void addLast(E e){ 
        Node<E> newNode = new Node<>(e,null);
        //Special Case: empty list 
        if(this.size == 0){
            this.first == newNode;
        } else{
            last.setNext(newNode); //Change the link of old Tail
        }
        this.last = newNode; //Eventually make new node become the tail
        this.size++; //Increment
    }

    //Remove and Return the first Node's data
    public E removeFirst() {
        if (this.isEmpty()) { return null; }
        E data = first.getData();
        // Change the head node reference to its link to the next node
        // Special Case: will become null if list becomes empty
        this.first = first.getNext();  
        this.size--; //Decrement size
        //Also change the tail node reference to null if list is empty
        if (this.size == 0) {
            this.last = null;
        }
        return data;
    }

    /**
    * Removes the Last Node. This can be done in 3 steps:
    * 1. Update next link of the penultimate node, finding this by traversal
    * 2. Make the penultimate node the new tail, and set its link to null
    * 3. Dispose of the former tail node
    * @returns the data within the last node
    * @throws NoSuchElementException if tail is null and list is empty
    */
    public E removeLast(){
        if (this.isEmpty()) { return null; }
        if (this.last == null) { throw new NoSuchElementException();}
        else if (first == last){
            //When there is only one node, unlink both head and tail, and nullify
            this.first.setNext(null);
            this.first == null;
            this.last == null;
        } else{ 
            E oldTailData = this.tail.getData();
            //Traverse to find the penultimate node
            Node <E> penultimate = this.first; //Start from the head of the list
            while (penultimate.next != this.last){
                penultimate = penultimate.next;
            }
            this.last = penultimate;
            this.last.setNext(null);
        }
        this.size--; //Decrement the List
        return oldTailData;
    }

    public static void printList(SinglyLinkedList list){
        Node <E> currNode = list.first;
        System.out.print("LinkedList: ");

		// Traverse through the LinkedList
		while (currNode != null) {
			// Print the data at current node
			System.out.print(currNode.data + " ");

			// Go to next node
			currNode = currNode.next;
		}

		System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();
        list.append("LinkedList: ");

        //Traverse through each node to add data to String
        for(Node curr = this.head; curr.next != null; curr = curr.next){
            list.append(curr.getData() + "");
            list.append(" ");
        }

        list.append("\n");
        return list.toString();
    }
}