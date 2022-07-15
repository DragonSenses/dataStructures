import java.util.Comparator;

/**
 * Priority Queue implemented with a Sorted List. This maintains entries
 * sorted by nondecreasing keys. This ensures that the first element of
 * the list is an entry with the smallest key. An unsorted Priority Queue
 * on the other hand would have methods min() and removeMin() run in
 * linear time O(n) as opposed to a sorted list which only has one method
 * insert() run in linear time O(n). This is due to the Priority Queue 
 * being implemented as a DoublyLinkedList. 
 * 
 * Priority Queue is an ADT where a queue-like structure is used to manage
 * objects that must be process in some way but the 
 * "First-In, First-Out (FIFO)" principle would not suffice. This 
 * collection of prioritized elements that allows arbitrary element 
 * insertion, and allows the removal of the element that has the first 
 * priority. When an element is added to a priority queue, the user 
 * designates its priority with an associated key. The element with the
 * minimal key will be the next to be removed from the Queue. Any objects
 * (in Java) may be used as a key as long as there exists a way to compare
 * any two instances of x and y, that defines a natural order of the keys. 
 * Usually priorities are expressed numerically, otherwise applications
 * may develop their own notion of priority for each element. 
 * 
 * O(1) - size(), isEmpty(), min(), removeMin()
 * O(n) - insert()
 */
public class PriorityQueue <K,V extends Comparable<K>> {
    /** Instance Variables **/
    // private DoublyLinkedList<Entry<K,V>> list = new DoublyLinkedList<>();
    // A Positional List implemented via Doubly Linked List
    private LinkedPositionalList<Entry<K,V>> list = new LinkedPositionalList<>();
    Comparator<K> comp = (K k1, K k2) -> compare(k1,k2);
    public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";

    public PriorityQueue(){
        super();
    }

    public PriorityQueue(Comparator<K> c){
        this.comp = c;
    }

    /** Private Utility Methods **/
    /**
     * Compares Two Entries by natural ordering
     * @param x First Entry to compare
     * @param y Second Entry to compare
     * @return -1 if x < y, 0 if x==y, 1 if x > y
     */
    protected int compare(Entry<K,V> x, Entry<K,V> y){
        return comp.compare(x.getKey(), y.getKey());
    }

     /**
     * Compares Two Keys by natural ordering
     * @param x First key to compare
     * @param y Second key to compare
     * @return -1 if x < y, 0 if x==y, 1 if x > y
     */
    protected int compare(K x, K y){
        return comp.compare(x,y);
    }

    /**
     * Determines whether the incoming parameter key is valid
     * @param key to check
     * @return true if key is valid, false otherwise
     * @throws IllegalArgumentException when key is either null or invalid
     */
    private boolean checkKey(K key) throws IllegalArgumentException{
        if(key == null) {    
            throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
        }
        return true;
    }

    /** Update Methods **/
    /**
     * Inserts a Key-Value pair and returns the entry created. Take note that to
     * have better worst-case running times of O(1) for operations min() and 
     * removeMin() by ensuring that the underlying list in the queue is sorted, 
     * the cost is causing insert to have a worst-case running time of O(n) as
     * we now have to scan the list to find the appropriate Position to insert
     * the new entry. We start at the end of the list, advancing backward until
     * the new key is smaller than the existing entry. In the worst case, the
     * new key is the smallest entry and reaches the front of the list, performing
     * at O(n) where n is the number of entries in the priority queue during 
     * execution.
     * @param key   The key of the new entry
     * @param value The associated value of the new entry
     * @return  the entry storing the new key-value pair
     * @throws IllegalArgumentException If the key is invalid
     */
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);    // auxiliary key-checking method (could throw exception)
        Entry<K,V> newest = new Entry<>(key, value);
        Position<Entry<K,V>> curr = list.last();
        // curr advances backward in the list, looking for smaller key
        while (curr != null && compare(newest, curr.getElement()) < 0) {
          curr = list.before(curr);
        }
        if (curr == null) { // This operation runs in Linear Time to keep list sorted
          list.addFirst(newest);                   // new key is the smallest
        } else {
          list.addAfter(curr, newest);             // newest goes after current
        }
        return newest;
    }

    /** Access Methods **/
    /** @return The number of Entries within the Priority Queue
     */
    public int size() { return size(); }

    /** @return True if Priority Queue is empty, false otherwise */
    public boolean isEmpty() { return size() == 0; }

    /**
     * Returns (but does not remove) an entry with minimal key.
     * @return Entry with the smallest key (or null if empty)
     */
    public Entry<K,V> min() {
        if(list.isEmpty()) { return null; }
        return list.first().getElement();
    }

    /**
     * Returns and removes an entry with minimal key.
     * @return The removed entry ( or null if empty)
     */
    public Entry<K,V> removeMin() {
        if (list.isEmpty()) { return null; }
        return list.remove(list.first());
    }

    private static class Entry<K, V> { //inner Entry Class
		protected K key;
		private V value;
		
		//Constructor
		private Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() { return key; }

		public V getValue() { return value; }
		
		@Override
		public String toString(){
			return "<" + String.valueOf(key) + " , " + String.valueOf(this.getValue()) + ">";
		}
	}


}
