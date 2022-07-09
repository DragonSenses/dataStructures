import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Comparator;

/**
 * A Sorted Map provides a total ordering on its keys. This map is 
 * ordered according to the natural ordering of keys by, default,
 * or it can be defined by providing an optional Comparator. 
 * 
 * Why use a Sorted Map? 
 * A traditional Map ADT (Abstract Data Type) allows users to look up
 * the value associated with a given key, but the search for that key
 * is known as an exact search. Consider the cases where one may need
 * to consider their decision based on the order of keys or multiple 
 * keys where each key is "near" each other. Consider pairs such as
 * <time stamps, events> or <cost, performance> where order matters.
 * An unsorted map does not provide a way to list all events ordered
 * by time. In fact, a Hash-Based implementations of Maps intentionally
 * scatter the keys that may seem "similar/near" to each other in the
 * original domain, so that they are uniformly distributed in a hash 
 * table.
 * 
 * Applications for A Sorted Map
 * - A Computer system that maintains information about events that
 * occured with a Time Stamp marking the occurrence of each event
 * - Modeling a trade-off problem through a key-value pair, consider
 * the trade off between performance measured against corresponding cost.
 * A customer may need to consider the the certain amount of money to 
 * query the database to find the best performing product they can afford.
 * (Example: (cost,speed) for the fastest car they can afford) 
 * - Flight Databases can represented as a sorted map where customers
 * may need to consider <origin, destination, date, and time> as parameters
 * to the key and <Flight Number, Number of Seats, Flight Duration, Fare> as
 * values. Where to satisfy users' queries a sorted map would do better than
 * a simple matter of finding an exact match for a requested query.
 * 
 * Implementation: Sorted Search Table, where we store the map's entries in
 * an Array List such that the keys are in increasing order. The table has
 * a space requirement of O(n). An advantage of using an array-based search
 * table is one can use Binary Search to determine whether a target key is 
 * found; in the case of an unsuccessful search, the algorithm can determine
 * a pair of indices that are just less than or just greater than the missing
 * target key - an inexact search. 
 */
public class SortedTableMap <K,V> {
    private ArrayList<Entry<K,V>> table = new ArrayList<>(); // Underlying Entry array
    private Comparator<K> comp;

    // Error Message
    public static final String ILLEGAL_KEY = "Incompatible Key"; 

    /** Constructors **/
    public SortedTableMap() { // Default SortedTableMap uses Natural Ordering of keys
        super(); 
        comp = new DefaultComparator<K,V>();
    }
    /**
     * Constructs a Sorted Table map with its own comparator
     * @param comp  The comparator to use to order keys by
     */
    public SortedTableMap(Comparator <K> comp) {
        if (comp == null ) { 
            comp = new DefaultComparator<K,V>();
        } else {
            this.comp = comp;
        }
    }

    /**
     * Private helper method that checks the validity of incoming parameter
     * key. Keys in this implementaion are not allowed to be null. Keys also
     * should be of the same type in order to be compared and sorted.
     * @param key Target key to check
     * @return True if key is invalid, false if key is valid
     * @throws IllegalArgumentException When incoming parameter key is invalid
     */
    private boolean checkKey(K key) throws IllegalArgumentException {
        // Try checking if key can be compared to itself
        try {
            return (comp.compare(key,key) == 0);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(ILLEGAL_KEY);
        }
    }

    /** Access Methods **/
    /**
     * Returns the number of entries in the map
     * @return  the number of entries in the map
     */
    public int size(){
        return table.size();
    }

    /**
     * Tests whether the map is empty
     * @return true if the map is empty, false otherwise
     */
    public boolean isEmpty(){
        return size() == 0;
    }

    /**
     * A recursive binary search algorithm which returns the index of the entry 
     * with target key (since keys are unique in a map). Otherwise, it returns 
     * the smallest index for a given search range within the table, [lo..hi] 
     * inclusive, storing an entry with a key greater than or equal to given k.
     * If no such element exists, returns index hi+1 (or index just beyond the
     * end of the search range). 
     * @param key the target key to search for
     * @param lo  the lowest index of the relevant table range
     * @param hi  the highest index of the relevant table range
     * @return the index of the target key, otherwise the lowest index i such that 
     * table[i] has key greater than or equal to given key (if no such entry exists
     * then index hi+1)
     */
    private int findIndex(K key, int lo, int hi){
        if (hi < lo) { return hi +1; } // No Entry qualifies
        int mid = (lo + hi) >>> 1; // Safe/Efficient way of finding mean of two large integers
        int index = comp.compare(key,table.get(mid).getKey());

        if (index == 0) { return mid; } // Exact Match found, return the index

        // if index is less than 0, answer is left of mid; Otherwise answer is right of mid
        return (index < 0) ? findIndex(key, lo, mid-1) : findIndex(key,mid+1, hi);
    }

    /**
     * Another findIndex with only one parameter, key, which initiates a
     * search throughout the entire table
     * @param key The key to search for
     * @return The index of the key if successful, otherwise index of leftmost
     * entry within the search range having key greater than or equal to key
     */
    private int findIndex(K key) { return findIndex(key,0, table.size()-1); }

    /** Update Methods **/
    /**
     * Returns the value associated with the specified key, or null if no such
     * entry exists.
     * @param key the key whose asscoiated value is to be returned
     * @return the asscociated value to key parameter, or null if no such entry exists
     * @throws IllegalArgumentException if key is invalid
     */
    public V get(K key) throws IllegalArgumentException {
        checkKey(key);
    }

    /**
     * Adds a new entry to the given map given a unique key and asccociated value. If
     * an entry with thhe key was already in the map, this replaces the previous value 
     * with the new value and returns the old value. Otherwise, a new entry is added 
     * and null is returned
     * @param key   The key of the entry to add
     * @param value The asscociated value to the key
     * @return the previous value asscociated with the key (or null if no such entry)
     * @throws IllegalArgumentException if key is invalid
     */
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);
    }

    /**
     * Removes the entry with the specified key, if present, and returns its associated
     * value. Otherwise does nothing and returns null
     * @param key The key whhose entry is to be removed 
     * @return The previous value of associated key, or null if no such entry exists
     * @throws IllegalArgumentException if key is invalid
     */
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);
    }

} // end of SortedTableMap Class

/** Comparator Class which compares Entries and Keys*/
class DefaultComparator<K,V> implements Comparator<K> {
    Comparator<K> comp;

       /** Method for comparing two entries according to key */
       public int compare(Entry<K,V> a, Entry<K,V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

     /** Method for comparing a key and an entry's key */
    public int compare(K a, Entry<K,V> b) {
        return comp.compare(a, b.getKey());
    }

    /** Method for comparing a key and an entry's key */
    public int compare(Entry<K,V> a, K b) {
        return comp.compare(a.getKey(), b);
    }

    /** Method for comparing two keys */
    public int compare(K a, K b) {
        return comp.compare(a, b);
    }
 
}
