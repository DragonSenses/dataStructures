
import java.util.ArrayList;
import java.util.List;
// import java.util.Stack;

/**
 * @author kendr
 * @param <K> The type of the keys of this BST. They need to be comparable by nature of the BST
 * "K extends Comparable" means that BST will only compile with classes that implement Comparable
 * interface. This is because our BST sorts entries by key. Therefore keys must be comparable.
 * @param <V> The type of the values of this BST. 
 * 
 */	
public class BST<K extends Comparable<? super K>, V> {
	private Node<K,V> root; 
	private static final String ILLEGAL_ARG = "Argument is Null";
	//If we don't allow null values --> false, then we delete Nodes
	private boolean allowNullValues = true; //true --> update values with null
	/**
	 * Public Constructor of Binary Search Tree.
	 */
	public BST() {
		super();
	}
	
	/**
	 * Adds the specified key, value pair to this DefaultMap
	 * Note: duplicate keys are not allowed
	 * 
	 * @return true if the key value pair was added to BST, false otherwise
	 * @throws IllegalArgumentException if the key is null
	 */
	public boolean put(K key, V value) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException(ILLEGAL_ARG); }
		if (value == null && !allowNullValues) { //Don't allow null values 
			remove(key);
			return false;  //null values should mean we should the element
		} 
		int entries = size();
		this.root = add(root, key, value); //Recursive Search, start from root
		
		//Did the amount of entries in BST change? Duplicates won't change this
		return (entries == size()) ? false : true; 
	}
	
	/**
	 * Adds the specified node if not present within the map, if the keys match
	 * then just return n
	 * 
	 * @param n			Node to add
	 * @param key		Key to check for
	 * @param value		Associated Value to the key
	 * @return			A root node that reflects the new tree after insertion,
	 * 					null otherwise to represent that Entry was not added
	 */
	private Node<K,V> add(Node<K,V> n, K key, V value) {
		
		//Base case that finds empty area null, adds Leaf or Root to BST
		if(n == null) { 
			return new Node<K, V>(key,value,1); 
		} 
		
		int go = key.compareTo((K) n.getKey()); //Compare the Key Values
		
		//The search, asks 3 questions about key @ each Node
		if(go < 0) {			//Less than --> go Left 
			n.left = add(n.left, key, value);
		} else if (go > 0) {	//Greater than --> go Right
			n.right = add(n.right, key, value);
		} else {				//equal keys -- > duplicate
			//Just return the node we found
			return n; //Recursive call just sets n to right or left
		}
		updateSize(n);
		return n;
	}
	
	/**
	 * Recompute's the Node's size
	 * @param n		The node we wish to update
	 */
	private void updateSize(Node n) {
		//It assigns Node.size to equivalent to 1 (itself) + left + right
		n.size = 1 + size(n.left) + size(n.right); 
	}//If both children are null, or size = 0, then n size = 1, remains the same
	
	
	/**
	 * Replaces the value that maps to the key if it is present
	 * @param key The key whose mapped value is being replaced
	 * @param newValue The value to replace the existing value with
	 * @return true if the key was in this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException(ILLEGAL_ARG); }
		if (isEmpty()) { return false; } //nothing to Replace
		if (newValue == null && !allowNullValues) { //Don't allow null values 
			remove(key);
			return false;  //null values should mean we should the element
		} 
		if(!containsKey(key)) { return false; }
		
		set(key,newValue); //update the entry with new value
		return true;
	}
	
	/**
	 * Remove the entry corresponding to the given key
	 * 
	 * @return true if an entry for the given key was removed
	 * @throws IllegalArgumentException if the key is null
	 */
	public boolean remove(K key) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException(ILLEGAL_ARG); }
		if (isEmpty()) { return false; } //nothing to Remove
		
		int entries = size(); //Track the number of entries before operation
		
		this.root = searchAndDestroy(root,key); 
		
		//Did the amount of entries in BST change? Yes --> removal successful
		return (entries == size()) ? false : true; 
	}
	
	/**
	 * Search and Destroy is two-part operation that eliminates the 
	 * passed in Node, and fixes any connections that were severed.
	 * 
	 * Consider Cases: 
	 * 1) Node has no children, both left and right are empty, Node is a Leaf
	 * 2) Node has one child, let that child be its replacement
	 * 3) Node has two children, copy in order successor to N's position, then 
	 * 	  delete that successor. 
	 * 
	 * In Order Successor or closest descendant of N  is minimum element in the 
	 * right subtree of N's position
	 * 
	 * @param n		The found Node to delete
	 */
	private Node<K, V> searchAndDestroy(Node n, K key) {
		if (n == null) { return null; }
		
		int go = key.compareTo((K) n.key);
		
		//Begin Search, assignment fixes connections within the Tree
        if (go < 0) { 
        	n.left  = searchAndDestroy(n.left, key);
        } else if(go > 0) {
        	n.right = searchAndDestroy(n.right,key);
        } else { //Keys match, Search Complete, Begin Destroy Operation
        	
        	//Node has either One or No Child
        	if(n.right == null) { 
        		return n.left; 
        	} else if(n.left == null) { 
        		return n.right; 
        	}

        	//Case that Node has two children
			//We want to find the closest ancestor to the node to delete,
			//So the the leftmost child of the Node's right would be successor
        	Node toRemove = n; //Temporarily refer to current Node
        	
        	//n is assigned the new successor
        	//Transfer in-order successor's data to the current Node position
        	n = findMin(toRemove.right); //Finds LeftMost Node in Right Subtree
        	n.left = toRemove.left; //Fix the left connection 
        }
       
        updateSize(n); //Update sizes of every going up the Tree
        return n;
	}
	
	/**
	 * Find the leftmost node within the subtree, the In-Order Successor of N
	 * @param n		The Node to traverse
	 * @return		The Leftmost Node within the particular subtree
	 */
	private Node findMin(Node n) {
		Node curr = n;
		// loop to the leftmost leaf/child
		while (curr.left != null) {
			curr = curr.left;
		}
		return curr;
	}


	/**
	 * Adds the key, value pair to this DefaultMap if it is not present,
	 * otherwise, replaces the value with the given value
	 * @throws IllegalArgumentException if the key is null
	 */
	public void set(K key, V value) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException(ILLEGAL_ARG); }
		if (value == null && !allowNullValues) { //Don't allow null values 
			remove(key);
			return;  //null values should mean we should remove the element
		} 
		//Since setAdd returns the new root node, we set this to root
		root = this.setAdd(root, key, value); //Recursive set, start with root
	}
	
	/**
	 * Adds the specified node, if a matching entry is found then updates value
	 * Used in set() method
	 * 
	 * @param n			Node to add
	 * @param key		Key to check for
	 * @param value		Associated Value to the key
	 * @return			A root node that reflects the new tree after insertion				
	 */
	private Node<K,V> setAdd(Node<K,V> n, K key, V value) {
		
		if(n == null) { return new Node<K, V>(key,value,1); } //add Leaf or Root

		int go = key.compareTo((K) n.getKey()); //Compare the Key Values

		if(go < 0) {			//Less than --> go Left 
			n.left = setAdd(n.left, key, value);
		} else if (go > 0) {	//Greater than --> go Right
			n.right = setAdd(n.right, key, value);
		} else {				
			n.setValue(value);  //Update Value when keys match
			//return n;
		}
		
		updateSize(n);
		
		return n;
	}
	/**
	 * @return the value corresponding to the specified key
	 * @throws IllegalArgumentException if the key is null
	 */
	public V get(K key) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException(ILLEGAL_ARG); }
		Node found = search(root,key);
		if (found == null) { return null; } //If search failed
		return (V) found.value;
	}
	
	/**
	 * Search operation for BST, returns a Node to use by the caller, or null if
	 * not found. A decision tree that asks 3 questions at each position Node: 
	 * 1) is key less, 2) greater, 3) or equal?
	 * @param n			The node to start search from
	 * @param key		Key to check
	 * @return			Returns the found Node entry with the given key,
	 * 					null if search fails
	 */
	private Node<K,V> search(Node<K,V> n, K key) {
		while (n != null) {  //Until we hit a null leaf
			int go = key.compareTo((K) n.getKey()); //compare keys with node
			if (go < 0) {
				n = n.left; 	//key is less than, go left
			} else if (go > 0) {
				n = n.right;	//key is greater than, go right
			} else {
				return n; //Keys are Equal, compareTo returns 0
			}
		}
		return null; //Failed search, no value returned
	}
	

	/**
	 * @return the Total number of Key-Value pairs in this BST
	 */
	public int size() { 
		return size(root); //Helper method checks if root is null --> size 0
	}
	
	/**
	 * Finds the size of current Node, if null it signifies empty -- size 0
	 * Like a leaf at bottom of tree, otherwise return n.size
	 * @param n
	 * @return
	 */
	private int size(Node n) { 
		return (n == null) ? 0 : n.size; 
	}

	
	/**
	 * @return 	True if BST is empty, root is null or size is 0, false otherwise
	 */
	public boolean isEmpty() { 
		return size() == 0;
	}

	/**
	 * @return true if the specified key is in this BST, false otherwise
	 * @throws IllegalArgumentException if the key is null
	 */
	public boolean containsKey(K key) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException(ILLEGAL_ARG); }
		return this.get(key) != null; //Search BST for key, if non-null = found
	}

	/**
	 * List of keys in ascending sorted order, no sort(), in-order traversal
	 * @return an array containing the keys of this BST. If this BST is 
	 * empty, returns list of length zero. 
	 */
	public List<K> keys() {
		if(this.isEmpty()) { return new ArrayList<K>(0); }
		ArrayList<K> list = new ArrayList<K>();
		this.addInOrder(this.root, list);	//In Order Traversal, start w/ root
		return list;
	}
	
	/**
	 * An In Order traversal that goes through the BST in increasing order. 
	 * Recursively calls itself by traversing through left first, then action,
	 * then recursively go right. 
	 * @param n		The parent Node to check
	 * @param list	The ArrayList of Keys to add to
	 */
	private void addInOrder(Node n, ArrayList<K> list) {
		if (n == null) { return; } //Base Case
		
		//If Parent Node has left Child, traverse left subtree of N
		addInOrder(n.left, list); 
		
		list.add((K) n.getKey()); //Action: Visit --> Add Key to List
		
		//If Parent Node has right child, then traverse the right subtree of N
		addInOrder(n.right,list); 
	}
	
	/**
	 * Prints the Tree's Node Values. The integer determines the order
	 * @param n		Positive --> Post-Order
	 * 				Zero 	 --> In-Order
	 * 				Negative --> Pre-Order		
	 */
	public void printTree(int n) {
		if (n > 0) { printPostOrder(root); }
		if (n < 0) { printPreOrder(root); }
		if (n==0) { printInOrder(root); }
	}
	
	//Post-order traversal
	private void printPostOrder(Node n) {
		if (n == null) return;
		printPostOrder(n.left);
		printPostOrder(n.right);
		System.out.println(n.getValue() + " ");
	}
	
	//In-Order traversal
	private void printInOrder(Node n) {
		if (n == null) return;
		printInOrder(n.left);
		System.out.print("\"" + n.getKey() + "\"" + ": " );
		System.out.println(n.getValue() + " ");
		printInOrder(n.right);
	}
	
	//Pre-Order traversal
	private void printPreOrder(Node n) {
		if (n == null) return;
		System.out.println(n.getValue()+ " ");
		printInOrder(n.left);
		printInOrder(n.right);
	}
	
	/**
	 * Node class within our Binary Search Tree. This will contain the Entries,
	 * or Key, Value pairs <K,V>. 
	 * @param <K>	Keys
	 * @param <V>	Values
	 */
	private static class Node<K extends Comparable<? super K>, V> {
		//instance variables
		private K key;				
		private V value;			
		private Node left, right;	//References left and right children

		private int size; 			//Keep track of the number nodes below
		
		//Public Constructor
		public Node(K key, V value, int size) {
			this.key = key;
			this.value = value;
			this.left = null;
			this.right = null;
			this.size = size;
		}
		
		public K getKey() { 
			return this.key;
		}

		public V getValue() { 
			return this.value;
		}

		public void setValue(V value) {
			this.value = value;
		}
	
	}							
}