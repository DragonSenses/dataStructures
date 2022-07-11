import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    public static final String TEST_KEY = "Key";
	public static final String TEST_VAL = "Value";
    public static final Integer KEY = 7;
    public static final Integer VAL = 16;

    //Error Messages
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
    
    @BeforeAll
    static void initAll() {
       
    }

    @BeforeEach
    void init() {
        table = new HashTable<Integer, Integer>();
        strTable = new HashTable<String,String>();
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
	public void keysEmptyTable(){
		List<Integer> expected = new ArrayList<Integer>(0);
		List<Integer> actual = emptyTable.keys();
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
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(actualKeys);
		assertEquals(expectedKeys, actualKeys);
	}


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
	}

    @AfterAll
    public void tearDownAll() {
        table = null;
        strTable = null;
        emptyTable = null;
        zeroSizedTable = null;
    }
}