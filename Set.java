/**
 * Set interface of the Java Collections framework provides the features 
 * of a mathematical set in Java. Closely related to the map ADT.
 * 
 * 1. A Set is an unordered collection of elements, without duplicates. 
 * Elements of a set are like keys of a map, but without any auxiliary
 * values
 * 
 * 2. A Multiset (also known as Bag) is a set-like container that allows
 * duplicates
 * 
 * 3. A Multimap is similar to a traditional map, in that it associates 
 * values with keys; however, in a multimap the same key can be mapped
 * to multiple values. 
 * 
 * Implementations: from Java Collections Framework
 *  - HashSet provides an implementation of the Unordered set ADT with 
 *    a Hash Table
 *  - TreeSet implements the sorted set ADT using a balanced search tree
 *  - java.util.concurrent.ConcurrentSkipListSet implements a sorted set
 *    using a skip list
 * 
 * HashSet stores a group of individual objects but duplicate values are
 * not allowed. Using a hashtable, its hashing techinque improves 
 * performance by reducing the wait time. Does not guarantee insertion 
 * order of elements.
 * 
 * LinkedHashSet also stores a group of individual objects but duplicate 
 * values are not allowed. Internally implements a hashtable and a 
 * doubly linked list. It is an ordered Collection as it guarantees the 
 * insertion order of elements.
 * 
 * TreeSet also implements the Set interface, but duplicate values are 
 * not allowed. It does not support null values as it performs comparison
 * operations, it also does not store different types of objects as a result.
 * All elements are stored in a according to the natural ordering of
 * its keys or according to the Comparator specified at the set's creation
 * 
 */
public class Set {
    // Looking at java.util.Set interface
    /** Fundamental Methods of Set ADT */
    /**
     * add(e) - Adds the element e to Set S (if not already present)
     * remove(e) - Removes the element e from S (if it is present)
     * contains(e) - Returns whether e is an element of S
     * iterator() - Returns an iterator of the elements of S
     */

     /** Mathematical Set Operations */
     /**
      * Union ->  Element e is in Set S or e is in Set T
      * addAll(T) - Updates Set S to include all elements of set T, 
      *            replace S with S(Union)T 
      *
      * Intersection -> Element e is in Set S or e is in Set T
      * retainAll(T) - Updates Set S so thaht it only keeps those 
      *               elements that are also elements of set T, 
      *               replace S with S(Intersection)T
      *
      * Subtraction/Complement -> Element e is in S and e is not in T
      * removeAll(T) - Updates Set S by removing any of its elements
      *                that also occur in set T, replacing S by S-T
      *
      */
}
