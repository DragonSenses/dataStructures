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
    public Heap() {
        super();
    }
    public Heap(Comparator<K> c) { 
        this.c = c;
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
}