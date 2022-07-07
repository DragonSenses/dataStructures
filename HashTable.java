import java.util.Random;

/**
 * Linear Probing Hash Table.
 * 
 * @author kendr
 */
public class HashTable<Key, Value> {
	/** Instance Variables **/
	public static final int DEFAULT_INITIAL_CAPACITY = 8;
	private int capacity; // The underlying array capacity for the Hash Table
	private int size; // Number of key-value pairs in the Hash Table

	// These instance variables will help in preventing a poor hash
	private long p, scale, shift; // Treats hash function like a math equation

	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";

	/** Constructors **/
	public HashTable() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * HashTable Constructor.
	 * 
	 * @param initialCapacity the initial capacity of this HashMap
	 * @throws IllegalArgumentException if initialCapacity is negative or loadFactor
	 *                                  not
	 *                                  positive
	 */
	@SuppressWarnings("unchecked")
	public HashTable(int initialCapacity) throws IllegalArgumentException {
		if (initialCapacity <= 0) { // non-negative [0, infinity)
			this.capacity = DEFAULT_INITIAL_CAPACITY;
			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
		} else {
			this.capacity = initialCapacity;
		}

		this.size = 0;

		// Set the prime, shift, scale to make a hashFunction later
		// Prime numbers to choose from: 29; 73; 5039; 314,159; 27644437 ;
		this.p = 131071; // 2^17 -1 = 131071 ; 1001001
		Random r = new Random(); // y = scale*f(x) + shift
		this.shift = r.nextInt((int) p); // a vertical shift
		this.scale = r.nextInt((int) p - 1) + 1; // a vertical scale, stretch
	}

	/**
	 * Provides the index on the entries array, given by the hash function.
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
		return hashCode % entries.length; // Map the hash code to Entries Array
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
}
