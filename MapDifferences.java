import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/** HashMap vs. LinkedHashMap vs. TreeMap
 * This class considers the differences between these Maps
 * 
 * Summary: Which to use, and which scenarios do each excel at?
 *  HashMap - When Performance is Vital, and Ordering of Keys 
 *            Does not matter
 *  LinkedHashMap - If the insertion order of the keys should
 *                  be preserved, guarantees this order will
 *                  be maintained throught the life cycle of 
 *                  the map
 *  TreeMap - When keys need to be ordered by either their 
 *            natural ordering or by a Comparator
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
 * order which is the order in which the keys were inserted
 * into the map
 * 
 *  TreeMap is iterated according to the natural ordering of
 * its keys or according to the Comparator specified at the 
 * map's creation time
 * 
 * 3. Performance
 *  Assuming Hash function disperses the elements properly 
 * among the buckets, HashMap and LinkedHashMap offer O(1)
 * time performance for operations: get(), put(), containsKey(),
 * remove(), etc.
 *  TreeMap guarantees O(log(n)) time cost for these operations.
 * 
 * However, the added expense of maintaining the doubly-linked
 * list makes LinkedHashMap's performance slighly lower than 
 * that of HashMap. So opt for HashMap where performance is 
 * more a concern.
 * 
 *  Space Complexity wise, HashMap requires less memory than
 * TreeMap and LinkedHashMap since it uses a hash table to
 * store the mappings. LinkedHashMap has extra overhead of a
 * doubly-linked list, whereas TreeMap is implemented as a 
 * Red-Black tree, which takes up more memory.
 * 
 * 4. Treatment of Null Keys and Values
 *  HashMap and LinkedHashMap permits null keys and null values
 *  TreeMap permits only null values, but does not permit null
 * keys if thhe natural ordering of keys is used. It may support
 * null keys only if its Comparator supports comparison on null
 * keys.
 */
public class MapDifferences {

    /**
     * Here we demonstrate the iteration order of these Maps
     */
    public static void main(String[] args) {
        // Start with LinkedHashMap as it Iterates based on
        // The order the keys were inserted
        Map<String, String> map = new LinkedHashMap<>();

        map.put("USA", "California");
        map.put("Japan", "Kyoto");
        map.put("China", "Shanghai");
        map.put("UK", "Edinburgh");

        System.out.println("LinkedHashMap: " + map);

        map = new TreeMap<>(map);
        System.out.println("TreeMap: " + map);

        map = new HashMap<>(map);
        System.out.println("HashMap: " + map);

    }
}