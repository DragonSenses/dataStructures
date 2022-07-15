import java.util.Comparator;

/**
 * Priority Queue implemented with a Sorted List. 
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


    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);    // auxiliary key-checking method (could throw exception)
        Entry<K,V> newest = new Entry<>(key, value);
        Position<Entry<K,V>> curr = list.last();
        // curr advances backward in the list, looking for smaller key
        while (curr != null && compare(newest, curr.getElement()) < 0) {
          curr = list.before(curr);
        }
        if (curr == null) {
          list.addFirst(newest);                   // new key is the smallest
        } else {
          list.addAfter(curr, newest);             // newest goes after current
        }
        return newest;
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
		
		public void setValue(V value) { this.value = value; }

		@Override
		public String toString(){
			return "<" + String.valueOf(key) + " , " + String.valueOf(value) + ">";
		}
	}


}
