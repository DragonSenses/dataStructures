import java.util.Comparator;

/**
 * Priority Queue implemented with a Sorted List. 
 * 
 * O(1) - size(), isEmpty(), min(), removeMin()
 * O(n) - insert()
 */
public class PriorityQueue <K,V extends Comparable<K>> {
    /** Instance Variables **/
    private DoublyLinkedList<Entry<K,V>> list = new DoublyLinkedList<>();
    Comparator<K> comp = (K k1, K k2) -> compare(k1,k2);

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
