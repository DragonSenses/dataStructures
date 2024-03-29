import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
// import static org.junit.jupiter.api.Assertions.fail;
// import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * HashMap Tester class.
 * @author kendr
 *
 */
public class HashMapTest {
	
	private HashMap<String, String> testMap; // use this for basic tests
	private HashMap<String, String> mapWithCap; // use for testing proper rehashing
    protected HashMap<String, String> negSizedMap;
	protected HashMap<String, String> zeroSizedMap;
	List<String> actualKeys; //List of keys of our test map
	public static final String TEST_KEY = "Test Key";
	public static final String TEST_VAL = "Test Value";
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	
	@BeforeEach
	public void setUp() {
		testMap = new HashMap<>();
		mapWithCap = new HashMap<>(4, HashMap.DEFAULT_LOAD_FACTOR);
		actualKeys = new ArrayList<String>();
	}

	@AfterEach
	public void tearDown(){
		List<String> keys = testMap.keys();
		for(String k: keys){
			testMap.remove(k);
		}
		keys = mapWithCap.keys();
		for(String k: keys){
			mapWithCap.remove(k);
		}
	}

	/**
	 * Helper method that fills in the map with entries with the same key and
	 * value within range of [0 - (n-1)]
	 * @param map		Map to populate
	 * @param n			Number of entries
	 */
	public static void fillMap(HashMap<String, String> map, int n) {
		for(int i=0; i<n; i++) {
			map.put(String.valueOf(i),String.valueOf(i)); //1st entry: <0,0>
		}
	}

	@Test
	public void zeroSizeConstructor(){
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> zeroSizedMap = new HashMap<String,String>(0,0.75));
		assertEquals(ILLEGAL_ARG_CAPACITY,e.getMessage());
	}

	@Test
	public void negativeSizeConstructor(){
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> negSizedMap = new HashMap<String,String>(-16,0.75));
		assertEquals(ILLEGAL_ARG_CAPACITY,e.getMessage());
	}

	@Test
	public void negativeloadFactor(){
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> negSizedMap = new HashMap<String,String>(16,-24.00));
		assertEquals(ILLEGAL_ARG_LOAD_FACTOR,e.getMessage());
	}

	@Test
	public void largeloadFactor(){
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> negSizedMap = new HashMap<String,String>(16,2.0));
		assertEquals(ILLEGAL_ARG_LOAD_FACTOR,e.getMessage());
	}

	@Test
	public void putNullKey() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> testMap.put(null, TEST_VAL));
		assertEquals(ILLEGAL_ARG_NULL_KEY,e.getMessage());
	}

	@Test
	public void keysNonEmptyMap() {
		// You don't have to use array list 
		// This test will work with any object that implements List
		List<String> expectedKeys = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		actualKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(actualKeys);
		assertEquals(expectedKeys, actualKeys);
	}

	@Test
	public void keysEmptyMap(){
		List<String> expected = new ArrayList<String>(0);
		List<String> actual = testMap.keys();
		assertEquals(expected,actual);
	}
	

	@Test 
	public void mapSize4add5() {
		HashMap<String, String> map4 = mapWithCap;
		map4.put("A","A");
		map4.put("B","B");
		map4.put("C","C");
		map4.put("D","D");
		map4.put("E","E");
		List<String> actualKeys = map4.keys();
		
		//We expect 5 keys, since the HashMap must expand to accommodate
		assertEquals(5, actualKeys.size());
	}
	
	@Test 
	public void mapSize4addDuplicate() {
		HashMap<String, String> map4 = mapWithCap;
		
		//add duplicate entry 4 times
		for(int i=0; i<4; i++) {
			map4.put("A","A");
		}
		
		List<String> actualKeys = map4.keys();
		
		//We expect only 1 key since we don't want duplicates
		assertEquals(1, actualKeys.size());
	}
	

	@Test
	public void setAllTo16() {
		HashMap<String, String> map16 = testMap; //16 size, loadFactor 0.75
		
		String sameValue = "16";
		//add different entries 16 times
		fillMap(map16,16);
		
		//set the keys to all have the same value
		for(int i=0; i<16; i++) { 
			map16.set(String.valueOf(i), sameValue);
		}

		//All keys should return same value
		for(int i=0; i<16; i++) {
				assertEquals("16",map16.get(String.valueOf(i)));
		}
	}
	
	@Test
	public void put16replaceNone() {
		HashMap<String, String> map16 = testMap; //16 size, loadFactor 0.75

		String replacedValue = "16";
		boolean replaced = false;
		//add different entries 16 times
		fillMap(map16,16);

		//Replace but with different keys, shouldn't change any key's value
		for(int i=0; i<16; i++) { 
			map16.replace(String.valueOf(i+16), replacedValue);
		}

		//We expect no values to be a replacedValue
		for(int i=0; i<16; i++) {
			//We expect that it does not enter this loop
			if(map16.get(String.valueOf(i)) == replacedValue) { 
				replaced = true; 
				System.out.println(String.valueOf(i));
				System.out.println(map16.get(String.valueOf(i)));
			}
			//We expect this to always be false as no value is replaced
			assertEquals(false, replaced);
			replaced = false;
		}
	}

	@Test
	public void toStringOneEntry(){
		testMap.put("A","A");
		String expected = "[<A,A>]";
		assertEquals(expected,testMap.toString());
	}

	@Test
	public void toStringTwoEntries(){
		testMap.put("A","A");
		testMap.put("B","B");
		String expected = "[<A,A>, <B,B>]";
		String expected2 = "[<B,B>, <A,A>]";
		
		// Can expect one of out of two choices since
		// hashing does not guarantee the order
		assertTrue(expected.equals(testMap.toString())
			|| expected2.equals(testMap.toString()));
	}

	@Test
	public void getNull() {
		fillMap(testMap,4);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> testMap.get(null));
		assertEquals(ILLEGAL_ARG_NULL_KEY,e.getMessage());
	}

	@Test
	public void getEmptyMap(){
		assertEquals(null,testMap.get("1"));
	}

	@Test
	public void get4Keys(){
		fillMap(testMap,4);
		assertAll("testMap",
			() -> assertEquals(4,testMap.size()),
			() -> assertEquals("0", testMap.get("0")),
			() -> assertEquals("1", testMap.get("1")),
			() -> assertEquals("2", testMap.get("2")),
			() -> assertEquals("3", testMap.get("3"))
		);
	}

	// Based on our HashMap implementation, when get() called on entry that does
	// not exist, we return a null value
	@Test
	public void getKeyNotInMap(){
		fillMap(testMap,4);
		assertAll("testMap",
			() -> assertEquals(4,testMap.size()),
			() -> assertEquals("0", testMap.get("0")),
			() -> assertEquals("1", testMap.get("1")),
			() -> assertEquals("2", testMap.get("2")),
			() -> assertEquals("3", testMap.get("3")),
			() -> assertEquals(null, testMap.get("4"))
		);
	}

	@Test
	public void removeOneKey(){
		List<String> expectedKeys = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		actualKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(actualKeys);
		assertAll("testMap",
			() -> assertEquals(expectedKeys, actualKeys),
			() -> assertEquals(expectedKeys.size(),actualKeys.size()),
			() -> assertEquals(true,testMap.remove(TEST_KEY + "0"))
		);

		expectedKeys.remove(TEST_KEY + "0");
		actualKeys = testMap.keys();
		Collections.sort(actualKeys);
		assertEquals(expectedKeys, actualKeys);
	}

	@Test
	public void removeNone(){
		fillMap(testMap,8);
		assertEquals(false,testMap.remove(TEST_KEY));
	}

	@Test
	public void removeNull() {
		fillMap(testMap,4);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> testMap.remove(null));
		assertEquals(ILLEGAL_ARG_NULL_KEY,e.getMessage());
	}

	@Test
	public void removeOne(){
		List<String> expectedKeys = new ArrayList<>(0);
		// fillMap(testMap,8);
		// for(int i=0; i<8; i++){
		// 	assertEquals(true,testMap.remove(String.valueOf(i)));
		// }
		testMap.put("A", "65");
		testMap.remove("A");
		actualKeys = testMap.keys();
		Collections.sort(actualKeys);
		assertAll("testMap",
			() -> assertEquals(0,testMap.size()),
			() -> assertEquals(expectedKeys, actualKeys),
			() -> assertEquals(expectedKeys.size(),actualKeys.size()),
			() -> assertEquals(true,testMap.isEmpty())
		);
	}

	@Test		//Found a bug, after the first removal, the 2nd remove call doesn't work
	public void removeTwo(){
		List<String> expectedKeys = new ArrayList<>(0);
		testMap.put("A", "65");
		testMap.put("B","66");		
		
		assertEquals(true,testMap.remove("A"));
		assertEquals(true,testMap.remove("B"));
		
		actualKeys = testMap.keys();
		Collections.sort(actualKeys);
		assertAll("testMap",
			() -> assertEquals(0,testMap.size()),
			() -> assertEquals(expectedKeys, actualKeys),
			() -> assertEquals(expectedKeys.size(),actualKeys.size()),
			() -> assertEquals(true,testMap.isEmpty())
		);
	}

	// @Test		//Found a bug, after the first removal, the 2nd remove call doesn't work
	// public void removeThree(){
	// 	List<String> expectedKeys = new ArrayList<>(0);
	// 	testMap.put("A", "65");
	// 	testMap.put("B","66");
	// 	testMap.put("C","67");

	// 	assertEquals(true,testMap.remove("A"));
	// 	assertEquals(true,testMap.remove("B"));
	// 	assertEquals(true,testMap.remove("C"));

	// 	actualKeys = testMap.keys();
	// 	Collections.sort(actualKeys);
	// 	assertAll("testMap",
	// 		() -> assertEquals(0,testMap.size()),
	// 		() -> assertEquals(expectedKeys, actualKeys),
	// 		() -> assertEquals(expectedKeys.size(),actualKeys.size()),
	// 		() -> assertEquals(true,testMap.isEmpty())
	// 	);
	// }

	@Test
	public void replaceNone(){
		assertEquals(false,testMap.replace(TEST_KEY,TEST_VAL));
	}

	@Test
	public void replaceNull() {
		fillMap(testMap,4);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> testMap.replace(null, TEST_VAL));
		assertEquals(ILLEGAL_ARG_NULL_KEY,e.getMessage());
	}

	@Test
	public void replace4Entries(){
		fillMap(testMap,4);
		List<String> expectedKeys = new ArrayList<>(4);
		for(int i = 0; i < 4; i++){
			testMap.replace(String.valueOf(i), String.valueOf(i+60));
			expectedKeys.add(String.valueOf(i));
		}
		actualKeys  = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(actualKeys);

		assertAll("testMap",
			() -> assertEquals(4,testMap.size()),
			() -> assertEquals(expectedKeys, actualKeys),
			() -> assertEquals("60", testMap.get("0")),
			() -> assertEquals("61", testMap.get("1")),
			() -> assertEquals("62", testMap.get("2")),
			() -> assertEquals("63", testMap.get("3"))
		);
	}

	@Test
	public void setNull() {
		fillMap(testMap,4);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> testMap.set(null,"2"));
		assertEquals(ILLEGAL_ARG_NULL_KEY,e.getMessage());
	}

	@Test
	public void setEmptyMap(){
		testMap.set("1","2");
		actualKeys = testMap.keys();
		List<String> expected = new ArrayList<String>(0);
		assertAll("testMap",
			() -> assertEquals(0, testMap.size()),
			() -> assertEquals(expected, actualKeys),
			() -> assertEquals(null, testMap.get("1"))
		);
	}

	@Test
	public void set4Entries(){
		fillMap(testMap,4);
		List<String> expectedKeys = new ArrayList<>(4);
		for(int i = 0; i < 4; i++){
			testMap.set(String.valueOf(i), String.valueOf(i+60));
			expectedKeys.add(String.valueOf(i));
		}
		actualKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(actualKeys);

		assertAll("testMap",
			() -> assertEquals(4,testMap.size()),
			() -> assertEquals(expectedKeys, actualKeys),
			() -> assertEquals("60", testMap.get("0")),
			() -> assertEquals("61", testMap.get("1")),
			() -> assertEquals("62", testMap.get("2")),
			() -> assertEquals("63", testMap.get("3"))
		);
	}

	@Test	//Test if set adds 4 new entries to the HashMap
	public void setFourMoreEntries(){
		fillMap(testMap,4); // returns with HashMap of size 4
		List<String> expectedKeys = new ArrayList<>(4);
		for(int i = 0; i < 4; i++){
			testMap.set(String.valueOf(i+4), String.valueOf(i+60));
			expectedKeys.add(String.valueOf(i));
		}
		for(int i=4; i < 8; i++){	// Add 4 more keys to expected list
			expectedKeys.add(String.valueOf(i));
		}

		actualKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(actualKeys);

		assertAll("testMap",
			() -> assertEquals(8,testMap.size()),
			() -> assertEquals(expectedKeys, actualKeys),
			() -> assertEquals("0", testMap.get("0")),
			() -> assertEquals("1", testMap.get("1")),
			() -> assertEquals("2", testMap.get("2")),
			() -> assertEquals("3", testMap.get("3")),
			() -> assertEquals("60", testMap.get("4")),
			() -> assertEquals("61", testMap.get("5")),
			() -> assertEquals("62", testMap.get("6")),
			() -> assertEquals("63", testMap.get("7"))
		);
	}

	

	@Test
	public void containsKeyNull(){
		fillMap(testMap,4);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> testMap.containsKey(null));
		assertEquals(ILLEGAL_ARG_NULL_KEY,e.getMessage());
	}

	@Test
	public void containsKeys4(){
		fillMap(testMap,4);
		assertAll("testMap",
			() -> assertEquals(4,testMap.size()),
			() -> assertEquals(true, testMap.containsKey("0")),
			() -> assertEquals(true, testMap.containsKey("1")),
			() -> assertEquals(true, testMap.containsKey("2")),
			() -> assertEquals(true, testMap.containsKey("3"))
		);
	}

}//EOF