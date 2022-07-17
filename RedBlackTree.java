import java.util.ArrayList;
import java.util.List;
// import java.util.Stack;
import java.util.NoSuchElementException;

/** WORK IN PROGRESS. Not yet implemented fully. May even start over, but use what I learned from 
 * attempting it here. Uploaded for documentation purposes. 
 * A self-balancing Red-Black Tree. 
 * @author kendr
 *
 * @param <K> The type of the keys of this BST. They need to be comparable by nature of the BST
 * "K extends Comparable" means that BST will only compile with classes that implement Comparable
 * interface. This is because our BST sorts entries by key. Therefore keys must be comparable.
 * @param <V> The type of the values of this BST. 
 * 
 */	
public class RedBlackTree<K extends Comparable<? super K>, V> {
	private Node<K,V> root;
	int size;	//tracks the number of Entries within BST
	public static final String ILLEGAL_ARG = "Argument is Null";
	
	/** 1-bit Field Flags that represent the color of a node*/
	private static final boolean RED = true;	
	private static final boolean BLACK = false; 

	/** Error Messages **/
	private static String UNDERFLOW = "Tree Underflow, there is nothing to remove!";

	/**
	 * Public Constructor of Binary Search Tree.
	 */
	public RedBlackTree() {
		super();
		this.size = 0;
	}

	/** Public Access Methods **/

	//@return The number of (key, value) pairs in this BST
	public int size() { //number of entries
		if (this.isEmpty()) { return 0; }
		return (this.size == root.size(root) ? this.size : root.size(root));
	}

	public boolean isEmpty() { //is BST empty --> root is null?
		return root == null;
	}

	/** Insertion Methods **/
	
	/**
	 * Search BST for entry with given key, update value if found. Otherwise add
	 * new entry to BST. Duplicate keys are not allowed
	 * 
	 * Search is not affected by color of Nodes, stays the same. Most methods
	 * all benefit that the tree is more balanced, but do not need to changed.
	 * 
	 * During Insertion need to maintain the tree balance properties:
	 * 1. No Node has two Red Edges connected to it
	 * 2. Every Path [sequence of Nodes, where any two consecutive nodes form 
	 *    an edge] from Root Node to Null has the same number of Black edges.
	 * 3. Red edges "lean" left   
	 * 4. Root and External Nodes are Black
	 * 
	 * Due to 1), all Red nodes have Black children and a Black parent.
	 * Also due to 1) any given path should alternate between Red and Black 
	 * links
	 * 
	 * All nodes have one edge to its parent
	 * 
	 * @return true if the key value pair was added to BST, false otherwise
	 * @throws IllegalArgumentException if the key is null
	 */
	public boolean put(K key, V value) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException(ILLEGAL_ARG); }
		if (value == null) { //Don't allow null values 
			remove(key);
			return false;  //null value indicates removal of node with key
		} 
		
		root = add(root, key, value); //Recursive Search, starting from root
		root.color = BLACK; //when root is inserted turn it black
		return true;
	}
	
	/**
	 * Adds the specified node, but checks if conditions are met before insert,
	 * if not then remedy the node at that position and keep going.
	 * 
	 * If we are at an external Node at the bottom of tree, color it Red so its
	 * children are black (null leaves), and add it. Otherwise, check the next
	 * cases: right leaning, double red node violation, both child are red
	 * 
	 * 1) left BLACK, right RED --> turn left
	 * 2) left , and left.left both RED --> turn right 
	 * 3) Both children left,right are RED --> swap colors
	 * 
	 * Essentially, nodes balance out by swapping colors, and passing up the 
	 * red node to the parent node, where the recursive caller checks again if
	 * there is balance between the passed up parent and its sibling
	 * @param n
	 * @param key
	 * @param value
	 * @return
	 */
	private Node<K,V> add(Node<K,V> n, K key, V value) {
		//All new Nodes are red because we want Red to indicate where there is
		//imbalance within the tree
		if(n == null) { return new Node<K,V>(key,value,RED,1); }
		
		int go = key.compareTo((K) n.getKey()); //Compare the Key Values
		
		//The search, asks 3 questions about key @ each Node
		if(go < 0) {			//Less than --> go Left 
			n.left = add(n.left, key, value);
		} else if (go > 0) {	//Greater than --> go Right
			n.right = add(n.right, key, value);
		} else {				//equal --> update value
			n.setValue(value);
		}
		
		//Remedy anything that breaks the tree's balance properties
		Node<K,V> newRoot = this.remedy(n); //Maintain balance properties, new root
		if(newRoot != null) { n = newRoot; } //Internal node shouldn't be null
		
		//Update sizes
		n.nodes = n.size(n.left) + n.size(n.right) + 1; //update subtree size
		// this.size++; //increment BST Entries
		
		return n;
	}
	
	/**
	 * During insertion, this method ensures that the properties of the 
	 * Red Black Binary Search Tree are met so that that tree maintains balance.
	 * If it breaks any of the properties, it resets the link in the parent node
	 * parameter after adjusting. Returns a new node at the root or swaps colors
	 * 
	 * 1. No Node has two Red Edges connected to it
	 * 2. Every Path from Root Node to Null has the same number of Black Edges
	 * 3. Red edges are incoming nodes are red, they tend to lean left or should
	 * 	  be the left of the parent
	 * 4. Root and External Nodes are Black
	 * 
	 * The three cases that it checks for and its remedy is:
	 * 1) Left Child is Black, Right Child is Red --> turnLeft
	 * 2) If a left child and its left child are both red, we move it up the 
	 * 	  tree and turn it rightward
	 * 3) Swap colors if both children are black, this moves up a RED node up 
	 * 	  the tree.
	 * 
	 * @param n 	The parent node to check if conditions are met
	 * @return	If there are no breaking conditions then it returns the parent
	 * 			node passed in, otherwise returns a new parent node
	 */
	private Node<K,V> remedy(Node<K,V> n) {
		Node<K,V> left = n.left;
		Node<K,V> right = n.right;
		
		//If Node's right child is Red but its sibling is Black, this means we 
		//Have a Red edge leaning right, fix it so Red Node is to the left
		//We are switching the parent from a smaller key to a larger key.
		if(n.isBlack(left) && n.isRed(right)) { 
			n = turnLeft(n); //The right child becomes the parent
		}
		//If the parent's left child is Red, and left child's left is also red
		if(n.isRed(left.left) && n.isRed(left)) {
			n = turnRight(n); //Temporarily shift the red edge to lean right
		}//The left child becomes the parent, opposite of above
		
		//Double red violation, when a Node has two red edges where both its
		//children are both Red. This also fixes the case above
		if(n.isRed(left) && n.isRed(right)) {
			swapColors(n);
		}
		
		return n; //Either returns the original node, or the new parent Node
	}

	/** Deletion Methods **/
	
	/**
	 * Removes the smallest key and its associated value
	 * @throws NoSuchElementException when remove is called an empty tree
	 */
	public void removeMin() throws NoSuchElementException {
		if (isEmpty()) { throw new NoSuchElementException(UNDERFLOW); }

		// If Both Children of the root are Black, set root to Red
		if(root.bothBlack(root)) { root.color = RED; }

		// Private Utility method to delete the key-value pair at the root
		root = removeMin(root);
	}

	private Node<K,V> removeMin(Node<K,V> n) {
		if(n.left == null) { return null; }
		// Both Node's left child , and left child's left are both black
		// Make the Left Child or One of its Children red
		if(n.isBlack(n.left) && n.isBlack(n.left.left)){
			n = makeRedLeft(n);
		}

		n.left = removeMin(n.left);
		// At this point we must restore the properties of the Red Black Tree
		return null; 
	}

	
	/**
	 * Remove the entry corresponding to the given key
	 * 
	 * @return true if an entry for the given key was removed
	 * @throws IllegalArgumentException if the key is null
	 */
	public boolean remove(K key) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException(ILLEGAL_ARG); }
		if(!containsKey(key)) { return false; }
		
		//If Both Children are Black, change the color of root to Red
		if(root.bothBlack(root)) { this.root.color = RED; }
		
		this.root = cutAndTie(root,key);
		
		//BST still has entries and root exists, then revert root color to Black
		if(!isEmpty()) { this.root.color = BLACK; } 
		
		return true;
	}
	
	/**
	 * Removes the Node entry with corresponding key, then balances the tree.
	 * 
	 * Just when we always add new nodes as Red, we also want to always delete a
	 * red node, if not red then swap colors to make it red. 
	 * @param n		The rooted node to start the removal from
	 * @param key	The key to search for
	 * @return		The 
	 */
	private Node<K,V> cutAndTie(Node<K,V> n, K key){
		// if (key.compareTo(n.getKey()) < 0) { //key less than node's key
			
		// }
		return null;
	}

	/** Update Methods **/

	/**
	 * Replaces the value that maps to the key if it is present
	 * @param key The key whose mapped value is being replaced
	 * @param newValue The value to replace the existing value with
	 * @return true if the key was in this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		return false;
	}

	/**
	 * Adds the key, value pair to this DefaultMap if it is not present,
	 * otherwise, replaces the value with the given value
	 * @throws IllegalArgumentException if the key is null
	 */
	public void set(K key, V value) throws IllegalArgumentException {
		put(key,value); //put() updates value	
	}
	
	/** Search Methods **/

	/**
	 * @return the value corresponding to the specified key
	 * @throws IllegalArgumentException if the key is null
	 */
	public V get(K key) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException(ILLEGAL_ARG); }
		return search(root,key);
	}
	
	/**
	 * Searches through the BST, making a decision on which path to take by 
	 * asking 3 questions at each Node: is key less, greater, or equal?
	 * @param n			The node to start search from
	 * @param key		Key to check
	 * @return			Returns the Value associated with the given key,
	 * 					null otherwise
	 */
	private V search(Node<K,V> n, K key) {
		while (n != null) {  //Until we hit a null leaf
			int go = key.compareTo((K) n.getKey()); //compare keys with node
			if (go < 0) {
				n = n.left; 	//key is less than, go left
			} else if (go > 0) {
				n = n.right;	//key is greater than, go right
			} else {
				return (V) n.getValue(); //Keys are Equal, compareTo returns 0
			}
		}
		return null; //Failed search, no value returned
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
	 * Keys are in ascending sorted order through inorder traversal of the tree
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
	private void addInOrder(Node<K,V> n, ArrayList<K> list) {
		if (n == null) { return; } //Base Case
		
		//If Parent Node has left Child, traverse left subtree of N
		addInOrder(n.left, list); 
		
		list.add((K) n.getKey()); //Action: Visit --> Add Key to List
		
		//If Parent Node has right child, then traverse the right subtree of N
		addInOrder(n.right,list); 
	}
	
	/**
	 * Consider the case when a Parent node's right child is Red, which is a 
	 * Red Edge leaning right, we must adjust the Node's left, right 
	 * references such that the parent and right child swap places and switch
	 * colors (where the left child of the new parent is Red).
	 * 
	 * We are switching the parent from a smaller key to a larger key.
	 * 
	 * Example: Parent = 4, and rChild = 7, then rChild's left is btwn 4 and 7
	 * rChild's left is less than it, but greater than parent, BST holds true
	 * rChild left could be 5
	 * 
	 * References that remain the same: parent's left, right child's right
	 * References that differ: parent's right, right child's left, reference 
	 * node to parent (the caller)
	 * 
	 * @param parent	The current Node to rotate with respect to
	 * @return			Returns the right child as the new parent
	 */
	private Node<K,V> turnLeft(Node<K,V> parent) {
		//parent should not be null and right child is Red
		if(!parent.isValidRedRight(parent)) { return parent; } //De Morgan's Law
		
		Node<K,V> rChild = parent.right; //Hold a reference to the (red) Right Child
		
		//reassign parent's right as the redChild's left. 
		parent.right = rChild.left; 
		
		//reassign rightChild's left to parent, such that parent will move down  
		//a level down the tree
		rChild.left = parent; 
		
		//Ensure that this edge that now leans left
		rChild.color = parent.color; //The new parent (prev. rChild) turns BLACK
		parent.color = RED; //This is now the left child of rChild, we turn RED
		//Update the nodes field or size of subtree of each node
		rChild.nodes = parent.nodes;
		parent.nodes = parent.size(parent.left) + parent.size(parent.right) + 1;
		return rChild; //Here, the right child is now the new Parent (root)
		
	}
	
	/**
	 * Adjusts the a Parent node and it's (red) left child so that the 
	 * leftChild becomes the new parent going up the level in the tree and 
	 * change its color, while the parent becomes the new right child of it and
	 * changes RED. 
	 * 
	 * Essentially the opposite of turnLeft above. 
	 * 
	 * We are switching the parent from a larger key to a smaller key.
	 * 
	 * @param parent		The current Node to rotate with respect to
	 * @return				Returns the left child as the new parent
	 */
	private Node<K,V> turnRight(Node<K,V> parent) {
		//parent should not be null and left child is Red
		if(!parent.isValidRedLeft(parent)) { return parent; }
		
		Node<K,V> lChild = parent.left; //Hold a reference to the (red) Left Child

		//reassign parent's Left as the leftChild's right. 
		parent.left = lChild.right; 

		//reassign leftChild's right to parent, such that leftChild will move UP  
		//a level within the tree
		lChild.right = parent; 

		//leftChild was originally red now becomes the color of parent's right
		//child (or black) so that no two red edges are connected to one node
		lChild.color = parent.right.color;
		parent.color = RED; //parent is now the new Right Child, we turn RED
		//Update the nodes field or size of subtree of each node
		lChild.nodes = parent.nodes;
		parent.nodes = parent.size(parent.left) + parent.size(parent.right) + 1;
		return lChild; //Here, the left child is now the new Parent (root)

	}
	
	/**
	 * This method swaps the color of a node's children and itself. 
	 * 
	 * Property: Any red node must have both its children to be black, it 
	 * follows that if both children are red, but right shouldn't be red, so 
	 * swap the colors of parent and child
	 * 
	 * Consider the case when the Node has two red children, or two red edges
	 * connected to it, such that it violates the property that a Node can only
	 * have at most one red edge. 
	 * 
	 * Another Case is that this method may color the Root RED, but root is 
	 * always black so this method colors it black after every call to put()
	 * 
	 * @param parent 	The node to swap colors of, along with its children
	 */
	private void swapColors(Node<K,V> parent) {
		//Parent and Children are all not null, and opposite colors
		if(parent.nonNullFamily(parent) && parent.oppositeColor(parent)) {
		parent.color = RED; 
		parent.left.color = BLACK;
		parent.right.color = BLACK; 
		}
	}
	
	/**
	 * Flips the colors of the parent node and its children
	 * @param parent The node and its two children to flip colors with
	 */
	private void flipColors(Node<K,V> parent) {
		parent.color = !parent.color;
		parent.left.color = !parent.left.color;
		parent.right.color = !parent.right.color;
	}
	
	/**
	 * Node class within our Binary Search Tree. This will contain the Entries,
	 * or Key, Value pairs <K,V>. 
	 * 
	 * A Node is either Red or Black. Root and Leaves are Black. If Node is red,
	 * its children is black. All paths from node to its descendants contain
	 * same number of black nodes. 
	 * 
	 * A Red Node indicates imbalance within the tree, or new nodes in the tree
	 * 
	 * 
	 * ROOT: Root is Black
	 * External: Every External Node is Black
	 * Red Property: Children of a Red Node are Black
	 * Depth: All external nodes have the same black depth, or number of proper 
	 * ancestors that are black
	 * 
	 * No Node has two Red Links connected to it, or in a path you cannot have
	 * two red nodes in a row. You can have blank nodes in a row. 
	 * @param <K>
	 * @param <V>
	 */
	private static class Node<K extends Comparable<? super K>, V> {
		//instance variables
		private K key;				    //Key
		private V value;				//Value of key
		private Node<K,V> left, right;	//References left and right of Node
		private boolean color;		    //Red or Black color
		//All null nodes, root, and leaves are Black
		private int nodes; 			    //Number of Nodes in Subtree
		
		//Public Constructor
		public Node(K key, V value, boolean color, int nodes) {
			this.key = key;
			this.value = value;
			this.color = color;
			this.nodes = nodes;
			this.left = null;
			this.right = null;
		}
		
		//getter of key
		public K getKey() {
			return this.key;
		}

		//getter of value
		public V getValue() {
			return this.value;
		}

		//setter of value
		public void setValue(V value) {
			this.value = value;
		}
		
		//Determine if the color of Node is Red
		private boolean isRed(Node<K,V> node) {
			if (node == null) { return false; } //null Nodes are Black
			return this.color == RED;
		}
		
		//Determine if the color of Node is Black
		private boolean isBlack(Node<K,V> node) {
			return !isRed(node);
		}
		
		//Determine if both children of the current node are black
		private boolean bothBlack(Node<K,V> n) {
			return (n.isBlack(n.left) && n.isBlack(n.right));
		}
		
		//Determines the number of nodes in the subtree, 0 if node is null
		private int size(Node<K,V> node) {
			if (node == null) { return 0; }
			return node.nodes;
		}
		
		//Check if parent is not null and right child is Red
		private boolean isValidRedRight(Node<K,V> n) {
			return n != null && n.isRed(n.right);
		}
		
		//Check if parent is not null and left child is Red
		private boolean isValidRedLeft(Node<K,V> n) {
			return n != null && n.isRed(n.left);
		}
		
		//Determines if parent and its children are not null, false otherwise
		private boolean nonNullFamily(Node<K,V> n) {
			return n != null && n.left != null && n.right != null;
		}
		
		//Determine if parent has the opposite color of both its children
		private boolean oppositeColor(Node<K,V> n) {
			return (n.isBlack(n) && n.isRed(n.left) && n.isRed(n.right)) 
					|| (n.isRed(n) && n.isBlack(n.left) && n.isBlack(n.right));
		}
	}							
	/** BST only keys
	 * R after red Nodes
	 * put(8)				    2 Red Nodes:(18,8)    turnRight ,both child Red 
	 * Case 2: 		(5)				(5)			 	    (5)	
	 * 				/ \				/ \				   /  \
	 * 			  (3) (19)		  (3) (19)		  	(3)  (18)
	 * 			  /    / \	-->	 /     /  \  -->    /    /   \ 
	 * 			(1R) (18R)	   (1R)  (18R)		 (1R)  (8R) (19R)
	 * 								 /
	 * 							   (8R)
	 * 
	 * 		   flipColor,				turnLeft
	 * 	but right child is Red
	 * 			 (5)					 (18)
	 * 			/   \					/   \
	 * 		  (3)  (18R)	---> 	  (5R)  (19)
	 * 		  /    /   \ 			 /   \     
	 * 		(1R) (8)  (19)		    (3)  (8) 
	 * 								/
	 * 							  (1R)
	 */
	 
}