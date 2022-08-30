/**
 * Entry class that is used by PriorityQueue.
 */
public class Entry<K, V> { 
    public K key;
    private V value;
    
    //Constructor
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }

    public V getValue() { return value; }
    
    @Override
    public String toString(){
        return "<" + String.valueOf(key) + ", " + String.valueOf(this.getValue()) + ">";
    }
}
