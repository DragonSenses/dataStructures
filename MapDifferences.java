import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/** HashMap vs. LinkedHashMap vs. TreeMap
 * This class considers the differences between these Maps
 * 
 * 1. Implementation
 *   - Map Interface: HashMap, LinkedHashMap, TreeMap
 *   - NavigableMap & SortedMap Interface : TreeMap
 * HashMap is implemented as a Hash Table
 * LinkedHashMap is implemented as a doubly-linked list buckets 
 * TreeMap is implemented as a Red-Black Tree
 * 
 * 2. Iteration Order of Mappings
 * The most important difference between them lies in how the 
 * iterator returns the map's contents. A map's contents is a
 * set of keys, collection of values, or set of key-value 
 * mappings.
 * 
 *  HashMap makes no guarantees on the iteration order of the
 * map (as it intentionally scatter the keys that may seem 
 * "similar/near" to each other in the original domain, so
 * that they are uniformly distributed in a hash table.) Also
 * insertions and removal of any element might change its 
 * iteration order
 * 
 *  LinkedHashMap maintains a doubly-linked list through all 
 * of its entries. This linked list defines the iteration 
 * orderm which is the order in whichh the keys were inserted
 * into the map
 * 
 *  TreeMap is iterated according to the natural ordering of
 * its keys or according to the Comparator specified at the 
 * map's creation time
 */
public class MapDifferences {

    /**
     * 
     */
    public static void main(String[] args) {

    }
}