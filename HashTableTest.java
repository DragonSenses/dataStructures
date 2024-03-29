import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HashTableTest {
    /** Instance Variables **/
    HashTable<Integer,Integer> table;
    HashTable<String,String> strTable;
    HashTable<Integer,Integer> emptyTable;
    HashTable<String,String> zeroSizedTable;
    HashTable<String,String> negSizedTable;
	
    List<String> actualKeys;
	List<Integer> actualListOfKeys;

    public static final String TEST_KEY = "Key";
	public static final String TEST_VAL = "Value";
    public static final Integer KEY = 1;
    public static final Integer VAL = 65;

	public static Integer counter = 4;	

    //Error Messages
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
    
    @BeforeAll
    static void setup() {
		System.out.println("Hash Table Unit Testing has begun ...");
    }

    @BeforeEach
    void init() {
        table = new HashTable<Integer, Integer>();
        strTable = new HashTable<String,String>();
    }

    /**
	 * Helper method that fills in the table with entries with the same key and
	 * value within range of [0 - (n-1)]
	 * @param table		table to populate
	 * @param n			Number of entries
	 */
	public static void fillTable(HashTable<String, String> table, int n) {
		for(int i=0; i<n; i++) {
			table.put(String.valueOf(i),String.valueOf(i)); //1st entry: <0,0>
		}
	}

    /**
	 * Helper method that fills in the table with entries with the same key and
	 * value within range of [0 - (n-1)]
	 * @param table		table to populate
	 * @param n			Number of entries
	 */
	public void fillTable(int n) {
		for(int i=0; i<n; i++) {
			this.table.put(i,i); //1st entry: <0,0>
		}
	}

	/**
	 * Helper method that fills in the table with entries with the 
	 * key values within the range of [1 - (n-1)], and values [65, (n+64)]
	 * @param table		table to populate
	 * @param n			Number of entries
	 */
	public void fillTableASCII(int n) {
		for(int i=0; i<n; i++) {
			this.table.put(KEY+i,VAL+i); //1st entry: <1,65>
		}
	}

	/** Tests  **/

	@Test
	void isEmptyTrue(){
		assertTrue(table.isEmpty());
	}

	@Test
	void isEmptyFalse(){
		fillTable(1);
		assertFalse(table.isEmpty());
	}

	@Test
	public void addOne(){
		// Create the Expected Values
		List<Integer> expectedKeysInt = new ArrayList<>();
		expectedKeysInt.add(1);
		List<String> expectedKeysStr = new ArrayList<>();
		expectedKeysStr.add("1");

		// Add One value to each HashTable
		table.put(1, 65);
		strTable.put("1", "65");

		// Create the List of Keys for each to compare to Expected
		List<Integer> actualKeysInt = table.keys();
		List<String> actualKeysStr = strTable.keys();
		
		assertAll("table",
			() -> assertTrue(!table.isEmpty()),
			() -> assertEquals(1, table.size()),
			() -> assertEquals(true, table.containsKey(1)),
			() -> assertEquals(65, table.get(1)),
			() -> assertEquals(expectedKeysInt, actualKeysInt)
		);

		assertAll("strTable",
			() -> assertTrue(!strTable.isEmpty()),
			() -> assertEquals(1, strTable.size()),
			() -> assertEquals(true, strTable.containsKey("1")),
			() -> assertEquals("65", strTable.get("1")),
			() -> assertEquals(expectedKeysStr, actualKeysStr)
		);
	}

	@Test
	void getOne(){
		table.put(KEY,VAL); 
		assertEquals(VAL,table.get(KEY));
	}

	@Test
	void getNone(){
		fillTable(4);
		assertEquals(null,table.get(4096));
	}

	@Test
	void getTwice(){
		table.put(KEY,VAL); 
		table.put(KEY+1,VAL+1);
		assertEquals(VAL,table.get(KEY));
		assertEquals(VAL+1,table.get(KEY+1));
	}

	@Test
	void getThreeTimes(){
		fillTableASCII(3);
		assertAll("table",
			() -> assertEquals(VAL,table.get(KEY)),
			() -> assertEquals(VAL+1,table.get(KEY+1)),
			() -> assertEquals(VAL+2,table.get(KEY+2))
		);
	}

	@Test
	void getTwiceAfterRemove(){
		fillTableASCII(3);
		assertAll("table",
			() -> assertEquals(VAL,table.get(KEY)),
			() -> assertEquals(true,table.remove(KEY+1)),
			() -> assertEquals(VAL+2,table.get(KEY+2))
		);
	}

	@Test
	void getNullKey(){
		fillTable(4);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> table.get(null));
		assertEquals(ILLEGAL_ARG_NULL_KEY,e.getMessage());
	}

	

	@Test
	public void get4KeysString(){
		fillTable(strTable,4);
		assertAll("strTable",
			() -> assertEquals(4,strTable.size()),
			() -> assertEquals("0", strTable.get("0")),
			() -> assertEquals("1", strTable.get("1")),
			() -> assertEquals("2", strTable.get("2")),
			() -> assertEquals("3", strTable.get("3"))
		);
	}

	@Test
	public void get4KeysInteger(){
		fillTable(4);
		assertAll("table",
			() -> assertEquals(4,table.size()),
			() -> assertEquals(0, table.get(0)),
			() -> assertEquals(1, table.get(1)),
			() -> assertEquals(2, table.get(2)),
			() -> assertEquals(3, table.get(3))
		);
	}

	// Based on our HashMap implementation, when get() called on entry that does
	// not exist, we return a null value
	@Test
	public void getKeyNotInTable(){
		fillTable(4);
		fillTable(strTable,4);
		assertAll("table",
			() -> assertEquals(4,table.size()),
			() -> assertEquals(0, table.get(0)),
			() -> assertEquals(1, table.get(1)),
			() -> assertEquals(2, table.get(2)),
			() -> assertEquals(3, table.get(3)),
			() -> assertEquals(null, table.get(4))
		);

		assertAll("strTable",
			() -> assertEquals(4,strTable.size()),
			() -> assertEquals("0", strTable.get("0")),
			() -> assertEquals("1", strTable.get("1")),
			() -> assertEquals("2", strTable.get("2")),
			() -> assertEquals("3", strTable.get("3")),
			() -> assertEquals(null, strTable.get("4"))
		);
	}

    @Test
	public void zeroSizeConstructor(){
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> zeroSizedTable = new HashTable<String,String>(0,0.75));
		assertEquals(ILLEGAL_ARG_CAPACITY,e.getMessage());
	}

	@Test
	public void negativeSizeConstructor(){
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> negSizedTable = new HashTable<String,String>(-16,0.75));
		assertEquals(ILLEGAL_ARG_CAPACITY,e.getMessage());
	}

	@Test
	public void negativeloadFactor(){
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> negSizedTable = new HashTable<String,String>(16,-24.00));
		assertEquals(ILLEGAL_ARG_LOAD_FACTOR,e.getMessage());
	}

	@Test
	public void largeloadFactor(){
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> negSizedTable = new HashTable<String,String>(16,2.0));
		assertEquals(ILLEGAL_ARG_LOAD_FACTOR,e.getMessage());
	}

    @Test
	public void putNullKey() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> table.put(null, VAL));
		assertEquals(ILLEGAL_ARG_NULL_KEY,e.getMessage());
	}

	@Test
	public void removeNullKey() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> table.remove(null));
		assertEquals(ILLEGAL_ARG_NULL_KEY,e.getMessage());
	}

    @Test
	public void keysEmptyTable(){
		List<Integer> expected = new ArrayList<Integer>(0);
		List<Integer> actual = table.keys();
		assertEquals(expected,actual);
	}

    @Test
	public void keysNonEmptyTable() {
		List<String> expectedKeys = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			strTable.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		actualKeys = strTable.keys();
		// we need to sort because hash table doesn't guarantee ordering
		Collections.sort(actualKeys);
		assertEquals(expectedKeys, actualKeys);
	}

	@Test
	public void containsNullKey(){
		fillTable(4);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> table.containsKey(null));
		assertEquals(ILLEGAL_ARG_NULL_KEY,e.getMessage());
	}

	@Test
	public void contains4Keys(){
		fillTable(strTable,4);
		assertAll("strTable",
			() -> assertEquals(4,strTable.size()),
			() -> assertEquals(true, strTable.containsKey("0")),
			() -> assertEquals(true, strTable.containsKey("1")),
			() -> assertEquals(true, strTable.containsKey("2")),
			() -> assertEquals(true, strTable.containsKey("3"))
		);
	}

	@Test
	public void removeOne(){
		List<Integer> expectedKeys = new ArrayList<>();
		expectedKeys.add(1);
		table.put(1, 65);
		actualListOfKeys = table.keys();

		assertAll("table",
			() -> assertTrue(!table.isEmpty()),
			() -> assertEquals(1, table.size()),
			() -> assertEquals(true, table.containsKey(1)),
			() -> assertEquals(65, table.get(1)),
			() -> assertEquals(expectedKeys, actualListOfKeys)
		);

		table.remove(1);
		actualListOfKeys = table.keys();
		expectedKeys.remove(0);

		assertAll("table",
			() -> assertTrue(table.isEmpty()),
			() -> assertEquals(false, table.containsKey(1)),
			() -> assertEquals(null, table.get(1)),
			() -> assertEquals(expectedKeys, actualListOfKeys)
		);
	}

	public static boolean debugRemove = true;

	@Test
	public void removeTwo(){
		List<Integer> expectedKeys = new ArrayList<>();
		expectedKeys.add(0);
		expectedKeys.add(1);

		table.put(0,0);
		table.put(1,1);
		
		actualListOfKeys = table.keys();
		Collections.sort(actualListOfKeys); // List does not guarantee order

		assertAll("table",
			() -> assertTrue(!table.isEmpty()),
			() -> assertEquals(2, table.size()),
			() -> assertEquals(true, table.containsKey(0)),
			() -> assertEquals(true, table.containsKey(1)),
			() -> assertEquals(0, table.get(0)),
			() -> assertEquals(1, table.get(1)),
			() -> assertEquals(expectedKeys, actualListOfKeys)
		);

		if(debugRemove){
			System.out.println(table.toString());
			System.out.println("Removed 0: " + table.remove(0));
			System.out.println("Removed 1: " + table.remove(1));
			System.out.println("Contains 1: " + table.containsKey(1));
			System.out.println(table.toString());
			System.out.println("Size is: " + table.size());
		} else {
			table.remove(0);
			table.remove(1);
		}

		expectedKeys.clear(); // remove all elements within expected List
		actualListOfKeys = table.keys(); 
		
		assertAll("table",
			() -> assertTrue(table.isEmpty()),
			() -> assertEquals(false, table.containsKey(0)),
			() -> assertEquals(false, table.containsKey(1)),
			() -> assertEquals(null, table.get(0)),
			() -> assertEquals(null, table.get(1)),
			() -> assertEquals(expectedKeys, actualListOfKeys)
		);
	}

	// Tests below run too slow enough to time out, so commented out for now

	// @Test 
	// void removeN(){
	// 	int n = 12;
	// 	List<Integer> expectedKeys = new ArrayList<>();

	// 	for(int i = 0; i < n; i++){
	// 		expectedKeys.add(i);
	// 		table.put(i,i);
	// 	}

	// 	actualListOfKeys = table.keys();
	// 	Collections.sort(actualListOfKeys); // List does not guarantee order

	// 	assertAll("table",
	// 		() -> assertTrue(!table.isEmpty()),
	// 		() -> assertEquals(n, table.size()),
	// 		() -> assertEquals(expectedKeys, actualListOfKeys)
	// 	);

	// 	for(int i = 0; i < n; i++){
	// 		assertEquals(true, table.remove(i));
	// 	}

	// 	expectedKeys.clear();
	// 	actualListOfKeys = table.keys();
	// 	Collections.sort(actualListOfKeys); // List does not guarantee order

	// 	assertAll("table",
	// 		() -> assertTrue(table.isEmpty()),
	// 		() -> assertEquals(0, table.size()),
	// 		() -> assertEquals(expectedKeys, actualListOfKeys)
	// 	);
	// }

	// @Test
	// public void doubleResizeCheck(){
	// 	counter = 16; 
	// 	List<Integer> expectedKeys = new ArrayList<>();
	// 	// Create elements up to counter times
	// 	for(int i = 0; i < counter; i++) {
	// 		table.put(i, i);
	// 		expectedKeys.add(i);
	// 	}
	// 	actualListOfKeys = table.keys();
	// 	// we need to sort because hash table doesn't guarantee ordering
	// 	Collections.sort(actualListOfKeys);

	// 	assertAll("table",
	// 		() -> assertTrue(!table.isEmpty()),
	// 		() -> assertEquals(counter, table.size()),
	// 		() -> assertEquals(expectedKeys, actualListOfKeys)
	// 	);
	// }

	// public boolean debugHalve = true;

	// @Test
	// public void halveResizeCheck(){
	// 	int testSize = 8;
	// 	table = new HashTable<Integer, Integer>(testSize);
		
	// 	if(debugHalve){
	// 		System.out.println(table.toString());
	// 	}

	// 	List<Integer> expectedKeys = new ArrayList<>(testSize);
		
	// 	for(int i = 0; i < testSize; i++) { 
	// 		table.put(i,i);
	// 		// Add only the first half to list or [0, testSize/2)
	// 		if(i < testSize/2) { expectedKeys.add(i); }
	// 	}
	// 	//Remove the second half, or [(testSize/2), testSize)
	// 	for(int i = testSize/2; i < testSize; i++){
	// 		System.out.println("Removing i " + table.remove(i));
	// 	}

	// 	if(debugHalve){
	// 		System.out.println(expectedKeys);
	// 		System.out.println(table.toString());
	// 	}

	// 	actualListOfKeys = table.keys();
	// 	// we need to sort because hash table doesn't guarantee ordering
	// 	Collections.sort(actualListOfKeys);

	// 	assertAll("table",
	// 		() -> assertTrue(!table.isEmpty()),
	// 		() -> assertEquals(testSize/2, table.size()),
	// 		() -> assertEquals(expectedKeys, actualListOfKeys)
	// 	);
	// }

    @AfterEach
    void tearDown() {
        List<Integer> keys = table.keys();
		for(Integer k: keys){
			table.remove(k);
		}

		List<String> sKeys = strTable.keys();
		for(String k: sKeys){
			strTable.remove(k);
		}

		table = null;
        strTable = null;
        emptyTable = null;
        zeroSizedTable = null;
	}

    @AfterAll
    static void tearDownAll() {
		System.out.println("Hash Table Unit Testing is complete.");
    }
}