import java.util.Map.Entry;

/**
 * A Map is an abstract data type designed to efficiently store and retrieve
 * values
 * based upon a uniquely identifying search key for each. Specifically, a map
 * stores
 * key-value pairs <K,V> (which we call entries), where K is the key and V is
 * its
 * corressponding value. Keys are required to be unique, so that the association
 * of
 * keys to values defines a mapping.
 * 
 * Maps are also known as associative arrays, because the Entry's key serves
 * like an index into the map, in that it assists the map in efficiently location
 * the associated entry. Unlike a standard array, a key of a map does not need to
 * be numeric (example: index of an array), and does not directly designate a
 * position within the structure.
 * 
 * Examples of Maps:
 * - Web, whose entries are web pages. <Key = URL, Value = Page Content>
 * - Domain-Name System (DNS) maps a host name such as https://myanimelist.net
 *   to an Internet-Protocol (IP) address <Key = hostname, Value = IP Address>
 * - A customer base may be stored as a map, with 
 *          <Key = Customer Account Number, Value = Customer Information>
 * - Computer graphics may map a color name to RGB values <"Violet", 155,38,182>
 * 
 *                           NOTE: Treatment of Null
 * Operations get(k), put(k), remove(k) returns the value associated with the key 
 * if map has the entry but otherwise return null. In an application where null
 * is allowed as a natural value associated with a key k, that is 
 * entry <K, null> exists in a map, then get(k) will return null not because it
 * could not find the key within the map but because it found the key and returned
 * its associated value. Therefore some implementations such as java.util.Map 
 * explicitly forbid use of null value (and null keys). To assuage the ambiguity,
 * operation containsKey(k), a boolean method can check whether key k exists
 * 
 * @author kendr
 */
public interface Map<K, V> {
    /**
     * Returns the number of entries in the map.
     * 
     * @return number of entries in the map
     */
    int size();

    /**
     * Tests whether the map is empty.
     * 
     * @return true if the map is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the value associated with the specified key, or null if no such entry
     * exists.
     * 
     * @param key the key whose associated value is to be returned
     * @return the associated value, or null if no such entry exists
     */
    V get(K key);

    /**
     * Associates the given value with the given key. If an entry with
     * the key was already in the map, this replaced the previous value
     * with the new one and returns the old value. Otherwise, a new
     * entry is added and null is returned.
     * 
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the key (or null, if no such
     *         entry)
     */
    V put(K key, V value);

    /**
     * Removes the entry with the specified key, if present, and returns
     * its associated value. Otherwise does nothing and returns null.
     * 
     * @param key the key whose entry is to be removed from the map
     * @return the previous value associated with the removed key, or null if no
     *         such entry exists
     */
    V remove(K key);

    /**
     * Checkes whether the given map contains the specified key
     * 
     * @param key the key whose entry is to be found
     * @return true if this map contains a mapping for the specified key;
     *         false otherwise
     */
    boolean containsKey(K key);

    /**
     * Returns an iterable collection of the keys contained in the map.
     *
     * @return iterable collection of the map's keys
     */
    Iterable<K> keySet();

    /**
     * Returns an iterable collection of the values contained in the map.
     * Note that the same value will be given multiple times in the result
     * if it is associated with multiple keys.
     *
     * @return iterable collection of the map's values
     */
    Iterable<V> values();

    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    Iterable<Entry<K, V>> entrySet();
}
