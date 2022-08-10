import java.util.Objects;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Linear Probing Hash Table.
 * 
 * Note: No duplicate keys. Null values will delete the keys.
 * @author kendr
 */
public class HashTable<Key, Value> {
	/** Instance Variables **/
	public static final int DEFAULT_INITIAL_CAPACITY = 8;
	public static final double DEFAULT_LOAD_FACTOR = 0.75; 

	private double loadFactor; // = Entries/Buckets, Size/Capacity, N/C,Fullness
	private int capacity; // The underlying array capacity for the Hash Table
	private int size;     // Number of key-value pairs in the Hash Table.

	// Using an underlying generic array to store Key, Value pairs
	private Key[] keys;
	private Value[] values;

	// These instance variables will help in preventing a poor hash
	private long p, scale, shift; // Treats hash function like a math equation

	//Error Messages
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";

	/** Constructors **/
	public HashTable() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * HashTable Constructor.
	 * 
	 * @param initialCapacity the initial capacity of this Hashtable
	 * @throws IllegalArgumentException if initialCapacity is negative
	 */
	@SuppressWarnings("unchecked")
	public HashTable(int initialCapacity) throws IllegalArgumentException {
		if (initialCapacity <= 0) { // non-negative [0, infinity)
			this.capacity = DEFAULT_INITIAL_CAPACITY;
			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
		} else {
			this.capacity = initialCapacity;
		}
		keys = (Key[]) new Object[capacity];
		values = (Value[]) new Object[capacity];
		this.size = 0;
		this.loadFactor = DEFAULT_LOAD_FACTOR;

		// Set the prime, shift, scale to make a hashFunction later
		// Prime numbers to choose from: 29; 73; 5039; 314,159; 27644437 ;
		this.p = 131071; // 2^17 -1 = 131071 ; 1001001
		Random r = new Random(); // y = scale*f(x) + shift
		this.shift = r.nextInt((int) p); // a vertical shift
		this.scale = r.nextInt((int) p - 1) + 1; // a vertical scale, stretch
	}

	/**
	 * HashTable Constructor that allows one to set a loadFactor.
	 * @param initialCapacity the initial capacity of this HashTable
	 * @param loadFactor the load factor for rehashing this HashTable
	 * @throws IllegalArgumentException if initialCapacity is negative or loadFactor is 
	 * 									non positive
	 */
	@SuppressWarnings("unchecked")
	public HashTable(int initialCapacity, double loadFactor) throws IllegalArgumentException {
		if(initialCapacity <= 0) { //non-negative [0, infinity)
			this.capacity = DEFAULT_INITIAL_CAPACITY;
			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
		} else {
			this.capacity = initialCapacity;
		}

		//Load factor must be a positive value between (0,1]
		if(loadFactor < 0 || loadFactor > 1) { 
			this.loadFactor = DEFAULT_LOAD_FACTOR;
			throw new IllegalArgumentException(ILLEGAL_ARG_LOAD_FACTOR);
		} else {
			this.loadFactor = loadFactor;
		}
		
		keys = (Key[]) new Object[capacity];
		values = (Value[]) new Object[capacity];
		this.size = 0;
		
		//Set the prime, shift, scale to make a hashFunction later
		//Prime numbers to choose from: 29; 73;  5039; 314,159; 27644437 ; 
		this.p = 131071; //2^17 -1 = 131071 ; 1001001
		Random r = new Random();				//y = scale*f(x) + shift
		this.shift = r.nextInt((int)p); 		//a vertical shift
		this.scale = r.nextInt((int)p-1)+1; 	//a vertical scale, stretch
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
	private int hash(Key key) {
		int hashCode = this.hashValue(key); // positive hash % n = positive index
		return hashCode % keys.length; // Map the hash code to Keys[] Array
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
	private int hashValue(Key key) { // Use Java's Objects hashCode on the key
		int hashCode = Math.abs(Objects.hashCode(key));
		return (int) ((hashCode * scale + shift) % p); // Positive
	}

	/**
	 * Rehashes all of the keys 
	 * @param i	The starting index to begin rehash
	 */
	private void rehash(int i){
		Key k; 
		Value v;
		while(keys[i] != null){ 
			k = keys[i];
			v = values[i];

			// Remove the Key,Value pair 
			keys[i] = null;
			values[i] = null;

			//Reduce the size and re-put and rehash the pair into HashTable
			size--;
			put(k,v);
			i = (i+1) % capacity;
		}
	}

	 // resizes the hash table to the given capacity by re-hashing all of the keys
	 private void resize(int capacity) {
        HashTable<Key, Value> table = new HashTable<Key, Value>(capacity);
        for (int k = 0; k < keys.length; k++) {
            if (keys[k] != null) {
                table.put(keys[k], values[k]);
            }
        }
		// Relink the instance variables to the new table
        keys = table.keys;
        values = table.values;
        capacity = table.capacity;
    }



	/** Access Methods **/

	/**
     * Returns the number of key-value pairs in this hash table.
     *
     * @return the number of key-value pairs in this hash table
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this hash table is empty.
     *
     * @return True if this symbol table is empty;
     *         false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

	/**
     * Returns the value associated with the specified key.
     * @param key the key used to get the value
     * @return the Value associated with the key, null otherwise
     * @throws IllegalArgumentException if key is null
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
        for (int i = hash(key); keys[i] != null; i = (i + 1) % capacity) {
            if (keys[i].equals(key)) {
                return values[i];
			}
		}
        return null;
    }

	/**
	 * @return true if the specified key is in this HashMap; false otherwise
	 * @throws IllegalArgument exception if the key is null
	 */
	public boolean containsKey(Key key) throws IllegalArgumentException {
		if(key == null) { throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY); }
		if(isEmpty()) { return false; } //Empty Map -> no Keys
		return get(key) != null;
	}

	/**
	 * Adds the specified key, value pair to this HashTable. Overwrites the old value
	 * with a new value if the HashTable already contains the key. Deletes the key and
	 * value from the table if the value is null.
	 * 
	 * Note: duplicate keys are not allowed
	 * @param key the key to check for
	 * @param value the value associated with key
	 * @throws IllegalArgument exception if the key is null
	 */
	public void put(Key key, Value value) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY); }

		if(value == null) { 
			remove(key);
			return;
		}

		//Floating-point division on two integers, explicit/implicit cast
		if((double)size/capacity > loadFactor) { this.resize(capacity*2); }  
		
		//Search the array circularly for the next available index
		//If an open spot is found, put the key,value pair; Otherwise if a key matches
		//Set its value
		int index;
		for(index = hash(key); keys[index] != null; index = (index+1) % capacity){
			if (keys[index].equals(key)) {	// Does a matching key exist?
                values[index] = value;		// Set its value and return from the function
                return;	
            }
		}
		keys[index] = key;
        values[index] = value;
        size++;
	}

	/**
	 * Remove the entry corresponding to the given key
	 * 
	 * @throws IllegalArgument exception if the key is null
	 */
	public boolean remove(Key key) throws IllegalArgumentException {
		if(key == null) { throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY); }
		if(!containsKey(key)) { return false; } // Check if null key, empty map, or no key exists

		// Get the hashed index of the key
		int i = hash(key);
		while(!key.equals(keys[i])){
			i = (i+1) % size;
		}

		//Remove the key, value pair by setting them both to null
		keys[i] = null;
		values[i] = null;

		rehash((i+1)%capacity);

		size--;

		//If current loadFactor (Entries/ArrayLength) is 1/4loadFactor or less
		if( this.size > 0 && ((double)size/capacity) <= loadFactor/4) { 
			this.resize(capacity/2); //loadFactor|0.75*1/4 = .1875 = 18.75% full
		}

		return true;
	}

	/**
	 * @return A List containing the keys of this HashTable. If this HashTable is 
	 * empty, returns ArrayList of length zero. 
	 */
	public List<Key> keys() {
		if (isEmpty()) { return new ArrayList<Key>(0); }
		
		// Make an empty ArrayList of keys with length of the underlying array
		List<Key> ring = new ArrayList<>(keys.length);

		// Make a deep copy of the elements within keys[]
		for(int i = 0; i < keys.length; i++){
			ring.add(keys[i]);
		}
		
		// Iterate through the ArrayList , and remove null values
		for(int i = 0; i < keys.length; i++){
			if(keys[i] == null){
				ring.remove(keys[i]);
			}
		}
		// Return the list of keys
		return ring;
	}

	/**
	 * Prints a string representation of the hash table. 
	 * For Debugging purposes.
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder("Keys: [");
		for (int k = 0; k < keys.length; k++) {
            if (k > 0) { sb.append(", "); }
            sb.append(keys[k]);
        }
        sb.append("]\nValues: [");
		for (int v = 0; v < values.length; v++) {
            if (v > 0) { sb.append(", "); }
            sb.append(values[v]);
        }
		sb.append("]");
		return sb.toString();	
	}

}
