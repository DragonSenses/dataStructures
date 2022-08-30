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

    @Override
	public boolean equals (Object o){
        // Is argument a reference to this object?
		if(o == this) 
            return true;

        // 2. Check if argument has correct type 
		if (!(o instanceof Entry)) 
            return false;

        // 3. Cast argument o to correct type
        Entry<?,?> e = (Entry<?,?>)o;

        // 4. for each significant field in class, check if field of the argument
        // matches thhe corresponding field of this object
		return e.getKey() == key && e.getValue() == value;
	}
}
