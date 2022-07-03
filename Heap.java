import java.util.ArrayList;
import java.util.Comparator;

/**
 * 
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

    public int size() { return heap.size(); }

    public boolean isEmpty() { return heap.isEmpty(); }

    protected int parent(int i) { return (i-1) / 2; }     // truncating division
    protected int left(int i) { return 2*i + 1; }
    protected int right(int i) { return 2*i + 2; }
    protected boolean hasLeft(int i) { return left(i) < heap.size(); }
    protected boolean hasRight(int i) { return right(i) < heap.size(); }
  
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
     */
    private boolean checkKey(K key) throws IllegalArgumentException {
        return(c.compare(key,key) == 0); // Can key be compared to itself?
    }
}