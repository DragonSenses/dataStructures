/**
 * Priority Queue implemented with a Sorted List. 
 * 
 * O(1) - size(), isEmpty(), min(), removeMin()
 * O(n) - insert()
 */
public class PriorityQueue <K,V> {
    private DoublyLinkedList<Entry<K,V>> list = new DoublyLinkedList<>();

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
