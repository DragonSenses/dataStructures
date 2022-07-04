import java.util.ArrayList;
import java.util.Comparator;

/** 
 * Binary Heap data structure allows for both inserations and removals in logarithmic time,
 * by using the structure of a binary tree to find a compromise between elements being 
 * entirely unsorted or perfectly sorted. There is a trade off when using an unsorted list
 * to store entries as insertions take O(1) time but removing an element with minimal key 
 * takes O(n) time. But if using a sorted list, removing the minimal element takes O(1) time
 * but adding a new element may require O(n) time to restore the sorted order. 
 * 
 * A Binary Heap satisfies the Heap-Order property, in a given Heap H, for every position p
 * other than the root, the key stored at p is greater than or equal to the key stored at p's
 * parents. One consequence of this is that keys encountered on a path from the root to a leaf
 * of H are in nondecreasing order. A minimal key is always stored at the root of H, or top of
 * the heap. 
 * 
 * Another property that the Heap H satisfies is its structural property of being complete: a
 * binary tree has all its levels completely filled except possibly the last level, with the 
 * remaining nodes at the last level reside in the leftmost possible positions
 * @author kendr
 */
public class Heap<K,V> {
    /**
	 * Entry class represents the Heap Entries of Key-Value pairs.
	 * @param <K>		Keys
	 * @param <V>		Values
	 */
	private static class Entry<K, V> {
		K key;
		V value;
		
		//Constructor
		private Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		
		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
	}
    //Member Fields of Heap 
    protected ArrayList<Entry<K,V>> heap = new ArrayList<>();
    private Comparator<K> c; //Comparator allows us to compare the keys in varying orders
    
    /** Constructors **/
    public Heap() {
        super();
        //this.c = new Comparator<K>();
    }
    public Heap(Comparator<K> c) { 
        this.c = c;
    }

    /** Protected Helper Methods **/
    protected int parent(int i) { return (i-1) / 2; }     // truncating division
    protected int left(int i) { return 2*i + 1; }
    protected int right(int i) { return 2*i + 2; }
    protected boolean hasLeft(int i) { return left(i) < heap.size(); }
    protected boolean hasRight(int i) { return right(i) < heap.size(); }
    
    /**
     * Swaps the elements with the given indexes in the arraylist
     * @param i - index of first element
     * @param j - index of second element
     */
    protected void swap(int i, int j) {
        Entry<K,V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Compares two entries according to key
     * @param a First entry to compare
     * @param b Second entry to compare
     * @return  The result of comparing two entries according to key
     */
    protected int compare(Entry<K,V>a, Entry<K,V> b){
        return c.compare(a.getKey(), b.getKey());
    }

    /**
     * Check for valid keys, throws exception if arguments are invalid
     * @param key   Key value to check for
     * @return  true if key is valid
     * @throws IllegalArgumentException
     */
    protected boolean checkKey(K key) throws IllegalArgumentException {
        return(c.compare(key,key) == 0); // Can key be compared to itself?
    }

    /**
     * Restores the heap property by moving the entry at index k higher. A new entry
     * is added at the end of array-lsit then repositioned as needed here.
     * @param k the initial index of the entry to upheap
     */
    protected void upheap(int k) {  
        while(k > 0){ 
            int p = parent(k);
            // if Entries k >= p, then exit the loop, heap property is verified
            if(compare(heap.get(k),heap.get(p)) >=0) { break; } 
            swap(k,p); //Otherwise, fix heap property by swapping k with its parent
            k = p; //Update the index of k with that of its parent position
        }
    }

    /**
     * Restores the heap property by moving the entry at index k lower. To remove the
     * entry with minimal key (index 0), we move last entry of the array list from 
     * index n-1 to index 0, then use downhheap to reposition it.
     * @param k the initial index of the entry to downheap
     */
    protected void downheap(int k) {
        while(hasLeft(k)) { //Continue to bottom (or break)
            int leftIndex = left(k);
            int smallChildIndex = leftIndex; // Store the smallChild Index as left for now
            
            if(hasRight(k)){ 
                int rightIndex = right(k);
                // Check if the Right Child is smaller
                if(compare(heap.get(leftIndex),heap.get(rightIndex)) > 0){
                    smallChildIndex = rightIndex;
                }
            }
            if(compare(heap.get(smallChildIndex), heap.get(k)) >= 0){
                break;  // Heap Property is satisfied, since current node is smaller
            }
            //Otherwise, swap the current node with its smaller child node
            swap(k,smallChildIndex);
            k = smallChildIndex; 
            
        }
    }
    
    
    /** Access Methods **/

    public int size() { return heap.size(); }

    public boolean isEmpty() { return heap.isEmpty(); }

    /**
     * Returns, but does not remove, an entry with the minimal key
     * @return  Entry with the smallest key, null if list is empty
     */
    public Entry<K,V> min() {
        if (heap.isEmpty()) { return null; }
        return heap.get(0);
    }

    /** Update Methods **/
    public Entry<K,V> insert(K key, V value)  {
        return null;
    }

    public Entry<K,V> removeMin() {
        return null;
    }
}