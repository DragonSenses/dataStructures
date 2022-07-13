import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChainHashMapTest {
    /** Instance Variables **/
    private ChainHashMap<String, String> testMap; // use this for basic tests

    List<String> actualKeys;

    public static final String TEST_KEY = "Test Key";
	public static final String TEST_VAL = "Test Value";
    // Error Messages
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";

    @BeforeAll
    static void initAll() {
    }

    @BeforeEach
    void init() {
        testMap = new ChainHashMap<String,String>();
    }

    @Test
    void succeedingTest() {
    }

  

    @AfterEach
    void tearDown() {
        List<String> keys = testMap.keys();
		for(String k: keys){
			testMap.remove(k);
	    }
    }

    @AfterAll
    static void tearDownAll() {
    }
}
