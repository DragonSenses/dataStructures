import java.lang.StringBuilder; //To be used in toString() for mutability and concatenation within a loop

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
    public boolean isEmpty() { return this.size == 0; }
    
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
            this.last = this.first;
        }
        this.size++;
    }

    //Adds an element to the end of the list
    public void addLast(E e){ 
        Node<E> newNode = new Node<>(e,null);
        //Special Case: empty list 
        if(this.size == 0){
            this.first = newNode;
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
    */
    public E removeLast(){
        E oldTailData = this.last.getData();
        if (this.isEmpty()) { return null; }
        else if (first == last){
            //When there is only one node, unlink both head and tail, and nullify
            this.first.setNext(null);
            this.first = null;
            this.last = null;
        } else{ 
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

    /**
     * Removes the first occurence of the element from the list. 
     * This method runs in O(n) as it traverses through the list until
     * a match is found. If no element is found, returns null
     * @param toRemove the element to remove from the list
     * @return the data of the node removed
     */
    public E remove(E toRemove){
        Node<E> curr = this.first;
        if(toRemove.equals(curr.getData())){
            return removeFirst(); 
        } 

        // Iterate through the list until we arrived at tail node
        while(curr != this.last){
            // if curr.next is the Node to remove
            if(curr.next.getData().equals(toRemove)){
                E removed = curr.next.getData(); // Hold curr.next data
                // Remove curr.next by updating curr's link
                curr.next = curr.next.next;
                return removed;
            }
            curr = curr.next;  // Iterate
        }

        // current node is now the last/tail node, check its data
        if(curr == last && curr.getData().equals(toRemove)){
            return removeLast();
        }

        return null; // Search Failed
    }

    /**
     * @returns A String representation of SinglyLinkedList
     */
    @Override
    public String toString() {
        StringBuilder list = new StringBuilder("LinkedList: ");

        //Traverse through each node to add data to StringBuilder
        for(Node<E> curr = this.first; curr.next != null; curr = curr.next){
            list.append("(");
            list.append(String.valueOf(curr.getData()));
            list.append(")");
            list.append(" -> ");
        }
        //Condition stops at the tail node since curr.next == null
        //Still must print out the last Node's data
        list.append("(" + String.valueOf(this.last.getData()) + ")");
        return list.toString();
    }

    /**
     * Higher level notion of equivalence where we define two SinglyLinkedLists as equivalent if:
     * I)   They have the same Length
     * II)  The contents that are element-by-element are equivalent
     * 
     * The equivalence relation:
     * 1. Null - For any nonnull reference X, the call x.equals(null) should return false
     * 2. Reflexivity - For any nonnull reference variable x, the call x.equals(x) should
     * return true (that is, an object should equal itself)
     * 3. Symmetry - For any nonnull reference variables x and y, the calls x.equals(y) and
     * y.equals(x) should return the same value.
     * 4. Transitivity - For any nonull reference variables x, y, and z, if both calls 
     * x.equals(y) and y.equals(z) returns true, then call x.equals(z) must return true as well
     * 
     * @param o - The SinglyLinkedList object parameter to compare with the caller
     * @return True, if both lists are the same size and the contents are element-by-element equivalent,
     * otherwise false
     */
    @Override
    public boolean equals(Object o){
        //1. Null Treatment
        if(o == null) { return false; } 
        /*2. Class Equivalence - getClass() vs. instanceof
         * getClass() only returns true if object is actually an instance of the specified class but
         * instanceof operator returns true even if the object is a subclass of a specified class or
         * interface in Java; allows implementation of equality between super and sub classes but does
         * not satisfy symmetry: x.equals(y) is true then y.equals(x) is also true, but if you swap x
         * with a subclass then x instanceof y is true but y instanceof x will be false, hence equals 
         * is false. We do not consider a SinglyLinkedList to be equivalent to DoublyLinkedList with 
         * with the same contents, a more stable approach 
         */
        if(this.getClass() != o.getClass()) { return false; }
        //Although declared formal type parameter <E> cannot detect at runtime whether other list has
        //a matching type. Type erasure, maps richer types at one level to less rich types at lower level
        // Typecast and use nonparameterized type; EDIT: Usage of wildcard ? is Unknown Type in Generics
        SinglyLinkedList<?> other = (SinglyLinkedList<?>) o; 
        //3. Size Check
        if(this.size != other.size) { return false; }
        Node<?> ptrA = this.first;  // Traverses through the primary list
        Node<?> ptrB = other.first; // Traverse through the secondary list
        while(ptrA != null){ //For every node within each list, check if element is equal
            if(!ptrA.getData().equals(ptrB.getData())) { return false; }
            ptrA = ptrA.getNext(); 
            ptrB = ptrB.getNext();
        }
        return true; // When reached, every element matched successfuly
    }

    // A quick test
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(4);
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(1);
        list.addLast(5);

        System.out.println(list.toString() + "\n");
        System.out.println("Removed node: (" + list.removeFirst() + ")");
        System.out.println(list.toString() + "\n");
        System.out.println("Removed node: (" + list.removeLast() + ")");
        System.out.println(list.toString() + "\n");
    }
}