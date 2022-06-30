import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.*;
/**
 * HashMap Tester class.
 * @author kendr
 *
 */
public class HashMapTest {
	
	private HashMap<String, String> testMap; // use this for basic tests
	private HashMap<String, String> mapWithCap; // use for testing proper rehashing
	public static final String TEST_KEY = "Test Key";
	public static final String TEST_VAL = "Test Value";
	
	@Before
	public void setUp() {
		testMap = new HashMap<>();
		mapWithCap = new HashMap<>(4, HashMap.DEFAULT_LOAD_FACTOR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPut_nullKey() {
		testMap.put(null, TEST_VAL);
	}

	@Test
	public void testKeys_nonEmptyMap() {
		// You don't have to use array list 
		// This test will work with any object that implements List
		List<String> expectedKeys = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		List<String> resultKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(resultKeys);
		assertEquals(expectedKeys, resultKeys);
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
}//EOF