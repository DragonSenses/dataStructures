import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
/**
 * My HashMap implementation.
 * @author kendr
 *
 * @param <K>	Represents the Keys to be used to store our entries to an index
 * @param <V>	The value you store, associated with a key
 */
public class HashMap<K, V> {
	public static final boolean TESTING = true;
	/**
	 * HashMapEntry class represents the Hash Map Entries of Key-Value pairs.
	 * @param <K>		Keys
	 * @param <V>		Values
	 */
	private static class HashMapEntry<K, V> {
		
		K key;
		V value;
		
		//Constructor
		private HashMapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		
		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString(){
			return "<" + String.valueOf(key) + " , " + String.valueOf(value) + ">";
		}
	}
	//** HashMap Instance Variables  **/
	public static final double DEFAULT_LOAD_FACTOR = 0.75; 
	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";

	//Let N = Number of Entries, C = Size of Underlying Bucket Array
	private double loadFactor; // = Entries/Buckets, Size/Capacity, N/C,Fullness
	private int capacity;	// The underlying bucket's capacity
	private int size;		// Number of key-value pairs in the Hash Table
	
	// Use this instance variable for Linear Probing
	private HashMapEntry<K, V>[] entries; 	

	//Marks an entry as a TOMBSTONE, signifying a location may have been filled
	private HashMapEntry<K,V> TOMBSTONE = new HashMapEntry<>(null,null);
	
	//These instance variables will help in preventing a poor hash
	private long p, scale, shift; //Treats hash function like a math equation
	
	public HashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	/**
	 * HashMap Constructor.
	 * @param initialCapacity the initial capacity of this HashMap
	 * @param loadFactor the load factor for rehashing this HashMap
	 * @throws IllegalArgumentException if initialCapacity is negative or loadFactor not
	 * positive
	 */
	@SuppressWarnings("unchecked")
	public HashMap(int initialCapacity, double loadFactor) throws IllegalArgumentException {
		if(initialCapacity <= 0) { //non-negative [0, infinity)
			this.capacity = DEFAULT_INITIAL_CAPACITY;
			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
		} else {
			this.capacity = initialCapacity;
		}
		
		if(this.loadFactor > 0) { //positive (0, infinity)
			this.loadFactor = DEFAULT_LOAD_FACTOR;
			throw new IllegalArgumentException(ILLEGAL_ARG_LOAD_FACTOR);
		} else {
			this.loadFactor = loadFactor;
		}
		
		this.size = 0;

		// if you use Linear Probing | array type-casted to store Hash Entries
		entries = (HashMapEntry<K, V>[]) new HashMapEntry<?, ?>[initialCapacity];
		
		//Set the prime, shift, scale to make a hashFunction later
		//Prime numbers to choose from: 29; 73;  5039; 314,159; 27644437 ; 
		this.p = 131071; //2^17 -1 = 131071 ; 1001001
		Random r = new Random();				//y = scale*f(x) + shift
		this.shift = r.nextInt((int)p); 		//a vertical shift
		this.scale = r.nextInt((int)p-1)+1; 	//a vertical scale, stretch
	}

	
	/**
	 * Adds the specified key, value pair to this HashMap
	 * Note: duplicate keys are not allowed
	 * 
	 * @return true if the key value pair was added to this HashMap
	 * @throws IllegalArgument exception if the key is null
	 */
	public boolean put(K key, V value) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException("ILLEGAL_ARG_NULL_KEY"); }
		//Floating-point division on two integers, explicit/implicit cast
		if((double)size/capacity > loadFactor) { this.scale(capacity*2); }  
		
		int i = findIndex(key, hash(key));
		
		if (i >= 0) { return false; } //implies Key has a Duplicate Entry
		
		return addEntry(key,value,i);
	}
	
	/**
	 * Adds the Entry<K,V> pair to the next open address. Also reverses the
	 * given index since a negative value implies that method may add entry.
	 * 
	 * First must convert the index properly, we know that the returned index 
	 * will be between 0-(N-1) a positive number turned negative due to 
	 * findIndex() indicating negative values means an open index to add an 
	 * entry to. To undo this operation we add 1 to ensure i is positive since a
	 * negative number + 1 will reduce it so it will be within range of the 
	 * entries index [findIndex() adds 1, so we are off by 1], then multiply by 
	 * -1 to undo this operation.
	 * @param key		Key to add
	 * @param value		Value to add
	 * @param i			Index to convert first, should be negative
	 * @return			True if entry was added
	 */
	private boolean addEntry(K key, V value, int i) {
		if (key == null) { throw new IllegalArgumentException("ILLEGAL_ARG_NULL_KEY"); }
		
		this.entries[-(i+1)] = new HashMapEntry<K,V>(key,value);
		this.size++;
		return true;
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
	 * @param key		Key to hash
	 * @return			Returns index based off of hash value of key
	 */
	private int hash(K key) {
		int hashCode = this.hashValue(key); //positive hash % n = positive index
		return hashCode % entries.length; //Map the hash code to Entries Array
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
	 * @param key		The key to apply the hash function
	 * @return			A hashCode we can then compress into a proper index
	 */
	private int hashValue(K key) { //Use Java's Objects hashCode on the key
		int hashCode = Math.abs(Objects.hashCode(key)); 
		return (int) ((hashCode*scale + shift) % p); //Positive
	}
	
	/**
	 * Checks if the index at the Entries Array is an open address
	 * @param i		The index value to check at
	 * @return		True if location is NULL (empty) or "TOMBSTONE", else false
	 */
	private boolean isOpen(int i) {
		return (entries[i] == null || entries[i] == TOMBSTONE);
	}
	
	/**
	 * Checks if the Key at the Index and the Incoming parameter key are equal
	 * @param i		Index to check in entries array
	 * @param key	Key to compare
	 * @return		True if keys match (duplicate), false otherwise
	 */
	private boolean keysMatch(int i, K key) {
		if (TESTING){
			System.out.println("Checking if keys match @ index" + i);
			System.out.print("Key @ Index is: " + entries[i].getKey().toString());
			System.out.println(" compared to parameter key: " + key);
		}
		return entries[i].getKey().equals(key); //access array and compare
	}
	
	/**
	 * Searching algorithm with two functions: 1) searches along entries to find
	 * the matching Key, Value pair. Otherwise 2) return the next open address 
	 * that the key can occupy within the entries array (this will return as 
	 * a negative number, to provide the caller with more information rather 
	 * than just the index).
	 * 
	 * Method contains searching logic where we skip over TOMBSTONES since that 
	 * index may have been filled at one point. We consider these indexes to be
	 * "Open"
	 * 
	 * Consider the case that we could not find the open index across entries
	 * but the first hash index looked at was a TOMBSTONE, then if data will
	 * be overwritten and collision MUST occur then we prefer it be the first
	 * TOMBSTONE or open index. A case occurs when user inputs a loadFactor >= 1
	 * 
	 * A negative integer means that no entry exists for the given key,
	 * A positive integer represents the entry index where the key was found
	 * 
	 * The performance of the search is dependent on our hash() function, as it
	 * determines whether too many elements hash to the same index, or if it is
	 * evenly spread out then lookup can be average/amortized O(1).
	 * @param hash		The hash value of the Key
	 * @param key		The Key to use
	 * @return			If Key is a new entry then returns index -(open+1) , or
	 * 					the index at which Key is present within entries[]
	 */
	private int findIndex(K key, int hash) {
		int open = -1;		 //No index is open
		int i = hash; 		 //temp index to use while going through entries
		
		if(isOpen(i)) { //Is the first hash Index NULL(empty) or TOMBSTONE
			if(open == -1) { open = i; }  //Set the First Hash Index as Open
			if(entries[i] == null) { return -(open+1); } //null --> Its Empty
		} else if (keysMatch(i,key)) { //Occupied index, check if keys match
			return i; //return the index of the entry, end search
		}
		i = (i+1) % capacity; //Increment by 1, circularly across array
		
		//Continue the search until we are back @ Square 1: Hash Index
		for(;i != hash; i = (i+1) % capacity) { 
			if(isOpen(i)) {
				if(open == -1) { open = i; } //mark the first open index
				if(entries[i] == null) { break; } //Null-> Empty -> End Search 
			} else if (keysMatch(i,key)) { //An Entry is there --> check keys
				return i; //keys match! Return positive number i:between [0,N-1]
			}
		} //At this point we got no matches, keep looking

		/* If we arrive at this point, then: Search has FAILED because of
		 * 1) break; So we found an empty open address, the key did not exist
		 * or key might have existed at one point since we found TOMBSTONE, so
		 * 2) Iterated through entries but did not find the Key's index
		 * 
		 * Given 2), depending on loadFactor open can be -1 if no openings
		 * or more likely an open is an index between (0 <= i <= N-1), marking 
		 * the first TOMBSTONE we can overwrite, if collision must occur. 
		 * 
		 * if hash is 0, we want to add 1 to offset open=-1 and make it so that 
		 * negative numbers will have meaning to the caller. 
		 * 
		 * More often than not, its rare to map too many items to the same key,
		 * and with loadFactor we can ensure that there will more than likely 
		 * be empty addresses so event 1) occurs. Value of open = [0,N-1]
		 */
			
		return -(open+1); //Negative Multiply 1, + 1, will be reversed by caller
	} 
	
	
	/**
	 * Replaces the value that maps to the key if it is present
	 * @param key The key whose mapped value is being replaced
	 * @param newValue The value to replace the existing value with
	 * @return true if the key was in this DefaultMap
	 * @throws IllegalArgument exception if the key is null
	 */
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		if(key == null) { throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY); }
		if(isEmpty()) { return false; } //Empty Map -> no Keys to replace
		
		int i = findIndex(key,hash(key)); //search for index with given key
		
		if (i >= 0 && keysMatch(i,key)) { //Index was found, update the entry
			entries[i].setValue(newValue);
			return true;
		}
		return false; 
	}

	/**
	 * Remove the entry corresponding to the given key
	 * 
	 * @return true if an entry for the given key was removed
	 * @throws IllegalArgument exception if the key is null
	 */
	public boolean remove(K key) throws IllegalArgumentException {
		if(key == null) { throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY); }
		if(isEmpty()) { return false; } //Empty Map -> no Keys to remove
		
		int i = findIndex(key,hash(key)); 
		if (TESTING){
			System.out.println("Index found within Remove(): " + i);
			System.out.print("Key @ Index is: " + entries[i].getKey().toString());
			System.out.println(" compared to parameter key: " + key);
		}

		if (i < 0) { return false; } //Negative Index implies no Entry for Key
		entries[i] = TOMBSTONE; //Lay the Entry to rest
		this.size--; 			//decrement size and remove from keys
		
		//If current loadFactor (Entries/ArrayLength) is 1/4loadFactor or less
		if( this.size > 0 && ((double)size/capacity) <= loadFactor/4) { 
			this.scale(capacity/2); //loadFactor|0.75*1/4 = .1875 = 18.75% full
		} //Also need 1 entry or more, size > 0 so we don't halve unnecessarily
		
		return true; 
	}

	/**
	 * Adds the key, value pair to this HashMap if it is not present,
	 * otherwise, replaces the value with the given value
	 * @throws IllegalArgument exception if the key is null
	 */
	public void set(K key, V value) throws IllegalArgumentException {
		if(key == null) { throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY); }
		if(isEmpty()) { return; } //Empty Map -> no Keys to set
		
		int i = findIndex(key,hash(key)); //Search entries for index of Key
		if (TESTING){
			System.out.println("Index found within set(): " + i);
			System.out.print("Key @ Index is: " + entries[i].getKey().toString());
			System.out.println(" compared to parameter key: " + key);
		}
		if(i >= 0) { //non-negative index implies an entry was found 
			entries[i].setValue(value); //Replace value 
		} else if (i < 0) { //negative index means no matching entry for key
			this.addEntry(key,value,i); //Map the key to Hash Table
		}
	}
	
	/**
	 * @return the value corresponding to the specified key, null if key doesn't 
	 * exist in hash map
	 * @throws IllegalArgument exception if the key is null
	 */
	public V get(K key) throws IllegalArgumentException {
		if(key == null) { throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY); }
		if(isEmpty()) { return null; } //Empty Map -> no Keys to get
		int i = findIndex(key,hash(key));
		if (i < 0) { return null; } //negative Index implies no Entry for Key
		return entries[i].getValue(); //Entry found
	}
	
	/**
	 * @return The number of (key, value) pairs in this HashMap
	 */
	public int size() {
		return this.size;
	}

	/**
	 * @return true iff this.size() == 0 is true
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * @return true if the specified key is in this HashMap
	 * @throws IllegalArgument exception if the key is null
	 */
	public boolean containsKey(K key) throws IllegalArgumentException {
		if(key == null) { throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY); }
		if(isEmpty()) { return false; } //Empty Map -> no Keys
		int i = findIndex(key,hash(key));
		if (TESTING){
			System.out.println("Index found within containsKey(): " + i);
			System.out.print("Key @ Index is: " + entries[i].getKey().toString());
			System.out.println(" compared to parameter key: " + key);
		}
		if(i > 0 && keysMatch(i,key)) { 
			return true;
		}
		return false;
	}
	
	/**
	 * @return an array containing the keys of this HashMap. If this HashMap is 
	 * empty, returns array of length zero. 
	 */
	public List<K> keys() {
		if (isEmpty()) { return new ArrayList<K>(0); }
		
		List<K> ring = new ArrayList<K>();
		
		for (int j = 0; j < this.capacity; j++) {
			if (!isOpen(j)) { //if Entry isn't Null or TOMBSTONE
				ring.add(entries[j].getKey());
			}
		}
		
		return ring;
	}
	
	/**
	 * Scales the size of the underlying bucket array by rehashing all the keys,
	 * creating a fresh array without TOMBSTONES provides better performance.
	 * 
	 * Couple things to note is that the loadFactor = Size/Capacity, or
	 * Entries/Buckets, or Number of Entries/Size of the Array. 
	 * Let N = Number of entries, and C = capacity of the Array. Then the ACTUAL
	 * loadFactor is N/C.
	 * 
	 * Let us use DEFAULT loadFactor|0.75, and capacity|16
	 * In put(), we double the Array when N/C > 0.75, to allocate more space 
	 * In remove(), we halve the Array when N/C <= 0.1875 = (0.75*1/4)
	 * 
	 * On average then N/C represents the load that is on the HashMap. So with
	 * N entries, and C capacity of the array, we expect that for each index
	 * we have N/C entries. Or simply, loadFactor*capacity = entries.
	 * 
	 * So each for capacity of 16, each index would equal 0.75, 0.75*16 = 12.
	 * 12/16 is where we hit our threshold loadFactor. 
	 * 3/16 = 0.1875 = 0.75 * 1/4, this is the other threshold where we halve
	 * 
	 * @param newCapacity		A more relevant size of bucket array
	 */
	private void scale(int newCapacity) {
		//Create a New HashMap
		HashMap<K,V> newHashMap = new HashMap<K,V>(newCapacity,loadFactor);
		K key;
		V value;
		for(int i = 0; i < this.capacity; i++) {
			if (!isOpen(i)) { //Entry is Not Null or TOMBSTONE
				key = entries[i].getKey();
				value = entries[i].getValue();
				newHashMap.put(key,value); 		//use put to re-hash entries
			}
		}
		//set instance variables to that of the new HashMap
		this.capacity = newCapacity;
		this.size = newHashMap.size;
		this.entries = newHashMap.entries;
		
	}


	@Override
	public String toString(){
		StringBuilder map = new StringBuilder();
		for(HashMapEntry<K,V> E: entries){
			map.append(E.toString());
			map.append("\n");
		}
		return map.toString();
	}

	public void print(){
		System.out.println(this.toString());
	}

	public static void main(String[] args){
		HashMap<String, String> m = new HashMap<>(4, HashMap.DEFAULT_LOAD_FACTOR);
		//m.print();
		m.put("2", "2");
		m.print();
		
		// HashMap<String, String> map4 = new HashMap<>(4, HashMap.DEFAULT_LOAD_FACTOR);
		
		// //add different entries 16 times
		// for(int i=0; i<16; i++) {
		// 	map4.put(String.valueOf(i),String.valueOf(i)); //1st entry: <0,0>
		// }
		
		// for(int i=0; i<12; i++) {
		// 	map4.remove(String.valueOf(i));
		// }
		
		//List<String> actualKeys = map4.keys();
		
		//We expect 4 keys, 16-12 = 4
		// for(String s:actualKeys){
		// 	System.out.println(s);
		// }
		
		// System.out.println(map4.toString());

	}
} //end of HashMap class
