import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChainHashMapTest {
    /** Instance Variables **/
    private ChainHashMap<String, String> testMap; // use this for basic tests
    protected HashMap<String, String> negSizedMap;
	protected HashMap<String, String> zeroSizedMap;

    List<String> actualKeys;

    public static final String TEST_KEY = "Test Key";
	public static final String TEST_VAL = "Test Value";
    // Error Messages
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";

    /**
	 * Helper method that fills in the map with entries with the same key and
	 * value within range of [0 - (n-1)]
	 * @param map		Map to populate
	 * @param n			Number of entries
	 */
	public static void fillMap(ChainHashMap<String, String> map, int n) {
		for(int i=0; i<n; i++) {
			map.put(String.valueOf(i),String.valueOf(i)); //1st entry: <0,0>
		}
	}

    @BeforeAll
    static void initAll() {
		System.out.println("ChainHashMap Unit Testing has started...");
    }

    @BeforeEach
    void init() {
        testMap = new ChainHashMap<String,String>();
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
	public void putFourDuplicates() {
		//add duplicate entry 4 times
		for(int i=0; i<4; i++) {
			testMap.put("A","A");
		}
		
		List<String> actualKeys = testMap.keys();
		
		//We expect only 1 key since we don't want duplicates
		assertEquals(1, actualKeys.size());
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
		String expectedValue = "Test Value0";
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
			() -> assertEquals(expectedValue,testMap.remove(TEST_KEY + "0"))
		);

		expectedKeys.remove(TEST_KEY + "0");
		actualKeys = testMap.keys();
		Collections.sort(actualKeys);
		assertEquals(expectedKeys, actualKeys);
	}

	@Test
	public void removeNone(){
		assertAll("testMap",
			() -> assertEquals(0,testMap.size()),
			() -> assertEquals(true,testMap.isEmpty()),
			() -> assertEquals(null,testMap.remove(TEST_KEY))
		);
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

    @Test	
	public void removeTwo(){
		List<String> expectedKeys = new ArrayList<>(0);
		String expectedValueOfA = "65";
		String expectedValueOfB = "66";

		testMap.put("A", expectedValueOfA);
		testMap.put("B",expectedValueOfB);		

		assertEquals(expectedValueOfA,testMap.remove("A"));
		assertEquals(expectedValueOfB,testMap.remove("B"));
		
		actualKeys = testMap.keys();
		Collections.sort(actualKeys);
		assertAll("testMap",
			() -> assertEquals(0,testMap.size()),
			() -> assertEquals(expectedKeys, actualKeys),
			() -> assertEquals(expectedKeys.size(),actualKeys.size()),
			() -> assertEquals(true,testMap.isEmpty())
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

    /** End of Tests **/

    @AfterEach
    void tearDown() {
        List<String> keys = testMap.keys();
		for(String k: keys){
			testMap.remove(k);
	    }
    }

    @AfterAll
    static void tearDownAll() {
		System.out.println("ChainHashMap Unit Testing is Complete.");
    }
}
