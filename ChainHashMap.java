import java.util.ArrayList;

/**
 * Separate Chaining HashMap implementation. 
 * @author kendr
 */
public class ChainHashMap <K,V> {
    /** Instance Variables **/
    public static final int DEFAULT_INITIAL_CAPACITY = 8;
    private int cap; // The underlying bucket capacity for the Hash Table
	private int size;     // Number of key-value pairs in the Hash Table.

    private ArrayList<Entry<K,V>>[] map;

    /** Constructors  **/
    public ChainHashMap(){
        this(DEFAULT_INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ChainHashMap(int capacity){
        this.cap = capacity;
        map = (ArrayList<Entry<K,V>>[]) new Object[cap];
        size = 0;
    }

    /**
	 * Entry class represents the Hash Map Entries of Key-Value pairs.
	 * @param <K>		Keys
	 * @param <V>		Values
	 */
	private static class Entry<K, V> {
		
		protected K key;
		private V value;
		
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

		@Override
		public String toString(){
			return "<" + String.valueOf(key) + " , " + String.valueOf(value) + ">";
		}
	}
}
