import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Separate Chaining HashMap implementation.
 * 
 * @author kendr
 */
public class ChainHashMap<K, V> {
	/** Instance Variables **/
	public static final int DEFAULT_INITIAL_CAPACITY = 8;
	public static final double DEFAULT_LOAD_FACTOR = 0.75;
	private int numBuckets; // The number of buckets for the Hash Table
	private int size; // Number of key-value pairs in the Hash Table.
	// Let N = Number of Entries, C = Size of Underlying Bucket Array
	private double loadFactor; // = Entries/Buckets, Size/Capacity, N/C,Fullness

	private ArrayList<Entry<K, V>> bucketArray;// Underlying bucket array to store chains

	// These instance variables will help in preventing a poor hash
	private long p, scale, shift; // Treats hash function like a math equation

	// Error Messages
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";

	/** Constructors **/
	public ChainHashMap() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	public ChainHashMap(int capacity, double loadFactor) {
		this(capacity);
		// Load factor must be a positive value between (0,1]
		if (loadFactor < 0 || loadFactor > 1) {
			this.loadFactor = DEFAULT_LOAD_FACTOR;
			throw new IllegalArgumentException(ILLEGAL_ARG_LOAD_FACTOR);
		} else {
			this.loadFactor = loadFactor;
		}
	}

	/**
	 * Constructor that allows flexible capacity
	 * 
	 * @param capacity The number of buckets that hashMap contains
	 */
	public ChainHashMap(int capacity) {
		if (capacity <= 0) { // non-negative [0, infinity)
			this.numBuckets = DEFAULT_INITIAL_CAPACITY;
			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
		} else {
			this.numBuckets = capacity;
		}

		this.numBuckets = capacity;
		bucketArray = new ArrayList<>();
		size = 0;
		loadFactor = DEFAULT_LOAD_FACTOR;

		// Create empty chains as placeholders
		for (int i = 0; i < numBuckets; i++) {
			bucketArray.add(null);
		}

		// Set the prime, shift, scale to make a hashFunction later
		// Prime numbers to choose from: 29; 73; 5039; 314,159; 27644437 ;
		this.p = 131071; // 2^17 -1 = 131071 ; 1001001
		Random r = new Random(); // y = scale*f(x) + shift
		this.shift = r.nextInt((int) p); // a vertical shift
		this.scale = r.nextInt((int) p - 1) + 1; // a vertical scale, stretch
	}

	/**
	 * HashValue is the function that hashes the key, important that Object
	 * must return same HashCode by using Java's method. This implements the
	 * M.A.D. (Multiple, Add, Divide) method to hash. Needed this because:
	 * 
	 * One issue that occurred when using Obect.hashCode() directly was that
	 * if a String was long enough, the method returns negative! This skews all
	 * the other methods like findIndex(). Since its hashCode can be bigger than
	 * the largest integer we can store: MAX_INT of 32 bits, since Java stores
	 * negative numbers as 2's complement, there may be integer overflow, so
	 * the value that Object.hashCode() returns could be negative.
	 * 
	 * To offset this we can still use hashCode() but we need to get its
	 * absolute value, and to make the hashTable more evenly spaced, we should
	 * use a prime value, while also modifying the function with mathematics
	 * translations of shifts and scales. Also since prime number can be set to
	 * a huge value we should modulo it with the prime number itself.
	 * 
	 * In short our function H() --> (Scale*h(k) + shift) % prime
	 * 
	 * @param key The key to apply the hash function
	 * @return A hashCode we can then compress into a proper index
	 */
	private int hashValue(K key) { // Use Java's Objects hashCode on the key
		int hashCode = Math.abs(Objects.hashCode(key));
		return (int) ((hashCode * scale + shift) % p); // Positive
	}

	/**
	 * Provides the index on the underlying array, given by the hash function.
	 * 
	 * Assume that key object's hash code will not change. Preferably immutable
	 * objects such as Strings, primitive wrappers, LocalDate/Time, etc.
	 * 
	 * We have Bucket Array B[], and Key k, we do two steps to arrive to B[h(k)]
	 * 1) hashFunction h() -> h(k)
	 * 2)compressionFunction -> B[h(k)], this helps us map h(k) to B[0] - B[N-1]
	 * 
	 * It is in the hash function that we can claim average/amortized case for
	 * our data structure. Otherwise, if too many elements hash to the same key
	 * of course it will take O(n) time.
	 * 
	 * @param key Key to hash
	 * @return Returns index based off of hash value of key
	 */
	private int hash(K key) {
		int hashCode = this.hashValue(key); // positive hash % n = positive index
		return hashCode % numBuckets; // Map the hash code to Keys[] Array
	}

	/**
	 * Reszies the underlying bucket array to fit the amount of entries
	 * 
	 * @param capacity The new capacity to scale the bucketArray by
	 */
	private void resize(int capacity) {
		ArrayList<Entry<K, V>> temp = bucketArray;
		bucketArray = new ArrayList<>();
		numBuckets = capacity;
		size = 0; // Reset the size, to rehash all values back into HashMap
		for (int i = 0; i < numBuckets; i++) { // Create empty chains
			bucketArray.add(null);
		}

		// Rehash all the entries back into buckets
		for (Entry<K, V> entry : temp) {
			while (entry != null) {
				put(entry.getKey(), entry.getValue());
				entry = entry.next;
			}
		}
	}

	/** Access Methods **/
	/** @return Number of entries within the HashMap */
	public int size() {
		return size;
	}

	/** @return true if Hashmap is empty; false otherwise */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Retrieves the value for the given key
	 * 
	 * @param key to use to locate the entry value
	 * @return The value associated with the key, null otherwise
	 * @throws IllegalArgumentException if key is null
	 */
	public V get(K key) throws IllegalArgumentException {
		if (key == null)
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		// Find head of chain for given key
		int bucketIndex = hash(key);
		int hashCode = hashValue(key);

		Entry<K, V> head = bucketArray.get(bucketIndex);

		// Search chain for the given key
		for (; head != null; head = head.next) {
			// Both key and given hashCode must match within the chain
			if (head.getKey().equals(key) && head.getHashCode() == hashCode) {
				return head.value;
			}
		}

		// At this point, the given key was not found within the chain
		return null;
	}

	/** Update Methods **/
	/**
	 * Adds a Entry or Key, Value pair to the HashMap
	 * 
	 * @param key   The key to add
	 * @param value The value associated with the key
	 * @throws IllegalArgumentException if key is null
	 */
	public void put(K key, V value) throws IllegalArgumentException {
		if (key == null)
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		// Find head of chain for given key
		int bucketIndex = hash(key);
		int hashCode = hashValue(key);
		Entry<K, V> head = bucketArray.get(bucketIndex);

		// Check within the bucket if key is already present, then set its value
		while (head != null) {
			// Not only does it have to have the same key but also the same hashCode
			if (head.getKey().equals(key) && head.getHashCode() == hashCode) {
				head.setValue(value);
				return;
			}
			head = head.next; // Move up the chain with the next link
		}

		// Insert Key, Value pair within the chain

		head = bucketArray.get(bucketIndex);
		Entry<K, V> newEntry = new Entry<K, V>(key, value, hashCode);
		newEntry.next = head;
		bucketArray.set(bucketIndex, newEntry);

		size++;

		// If load factor goes beyond threshold, then
		// double hash table size if ((double)size/capacity > loadFactor)
		if ((double) size / numBuckets >= loadFactor) {
			resize(2 * numBuckets);
		}
	}

	/**
	 * Removes the entry given the corressponding key
	 * 
	 * @param key The key to locate entry with
	 * @return The value associated to the key, null otherwise
	 * @throws IllegalArgumentException if key is null
	 */
	public V remove(K key) throws IllegalArgumentException {
		if (key == null)
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		int bucketIndex = hash(key); // Apply hash function to find index for given key
		int hashCode = hashValue(key); // Store its associated hashCode
		// Get head of chain
		Entry<K, V> curr = bucketArray.get(bucketIndex);
		Entry<K, V> prev = null;

		// Iterate through chain for the given key
		while (curr != null) {
			// Key Search success
			if (curr.getKey().equals(key) && hashCode == curr.getHashCode()) {
				break;
			}

			// Else keep moving in chain
			prev = curr;
			curr = curr.next;
		}

		// If key was not there
		if (curr == null) {
			return null;
		}

		// Successful removal, decrement size
		size--;

		// Remove key
		if (prev != null) {
			prev.next = curr.next;
		} else {
			bucketArray.set(bucketIndex, curr.next);
		}

		// If current loadFactor (Entries/ArrayLength) is 1/4loadFactor or less
		if (this.size > 0 && ((double) size / numBuckets) <= loadFactor / 4) {
			this.resize(numBuckets / 2); // loadFactor|0.75*1/4 = .1875 = 18.75% full
		}

		return curr.getValue();
	}

	/**
	 * @return a List containing the keys of this ChainHashMap. If this HashMap is 
	 * empty, returns array of length zero. 
	 */
	public List<K> keys(){
		List<K> ring;
		if (isEmpty()) { 
			ring = new ArrayList<K>(0); 
			return ring;
		}

		// Refers to the current Entry in the chain, start with head
		Entry<K,V> head;	
		ring = new ArrayList<>(numBuckets);

		// Iterate through each chain
		for (int i = 0; i < numBuckets; i++) {
			// Iterate through each node Entry within the chain
			for(head = bucketArray.get(i); head != null; head = head.next){
				// Perform an operation for each Entry
				ring.add(head.getKey());
			}
		}

		return ring;
	}

	/**
	 * Entry class represents the Hash Map Entries of Key-Value pairs.
	 * 
	 * @param <K> Keys
	 * @param <V> Values
	 */
	private static class Entry<K, V> {

		protected K key;
		private V value;
		private final int hashCode;
		Entry<K, V> next; // Reference to the next entry, like a node

		// Constructor
		private Entry(K key, V value, int hashCode) {
			this.key = key;
			this.value = value;
			this.hashCode = hashCode;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public int getHashCode() {
			return hashCode;
		}

		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "<" + String.valueOf(key) + " , " + String.valueOf(value) + ">";
		}
	}
}
