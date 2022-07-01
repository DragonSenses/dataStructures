import static org.junit.Assert.*;

import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Collections;
import java.util.List;
// import java.util.Random;

import org.junit.*;
import org.junit.rules.ExpectedException;

public class BSTTest {
	
	@Rule //Allows us to see that certain Exceptions are Thrown
	public final ExpectedException ex = ExpectedException.none();
	
	/* Unit Tests */
	@Before
	public void initialize() {
	}
	
	@Test
	public void putOneGetOne() {
		BST bst = new BST();
		String expectedKey = "A";
		String expectedValue = "1";
		
		bst.put("A","1");
		
		String actual = (String) bst.get(expectedKey);
		assertEquals(expectedValue,actual);
	}
	
	@Test
	public void putTwoGetTwo() {
		BST bst = new BST();
		String expect1 = "1";
		String expect2 = "2";
		
		bst.put("A","1");
		bst.put("B","2");
		
		String actual1 = (String) bst.get("A");
		String actual2 = (String) bst.get("B");
		
		assertEquals(expect1,actual1);
		assertEquals(expect2,actual2);
	}
	
	@Test
	public void putThreeGetThree() {
		BST bst = new BST();
		String expect1 = "1";
		String expect2 = "2";
		String expect3 = "3";
		
		bst.put("A","1");
		bst.put("B","2");
		bst.put("C","3");
		
		String actual1 = (String) bst.get("A");
		String actual2 = (String) bst.get("B");
		String actual3 = (String) bst.get("C");
		
		assertEquals(expect1,actual1);
		assertEquals(expect2,actual2); 
		assertEquals(expect3,actual3); 
	}
	
	/**
	 * Populates the BST, using put(), with Alphabet entries: 
	 * Keys are Upper case, Values lower case
	 * @param bst	BST to populate
	 * @param l 	List of keys to populate with
	 */
	public static void populate(BST bst, List l, int n) {
		char c = 'a';
		for(int i = 0; i < n; i++) {
			c = (char) ( (i%26) + 'a');
			bst.put((String)l.get(i), String.valueOf(c));
		}
	}
	
	/**
	 * Populates the BST, using set(), with Alphabet entries: 
	 * Keys are Upper case, Values lower case
	 * @param bst	BST to populate
	 * @param l 	List of keys to populate with
	 */
	public static void populateSet(BST bst, List l, int n) {
		char c = 'a';
		for(int i = 0; i < n; i++) {
			c = (char) ( (i%26) + 'a');
			bst.set((String)l.get(i), String.valueOf(c));
		}
	}
	
	/**
	 * Make a List of Keys, which are Strings in the form of Capital Letters;
	 * @param n		Number of keys to make
	 */
	public static List<String> makeKeys(int n) {
		List<String> l = new ArrayList<String>();
		char c = 'A';
		for(int i = 0; i < n; i++) {
			c = (char)((i%26) + 'A');
			l.add(String.valueOf(c));
		}
		return l;
	}
	
	/**
	 * Make a List of Values, which are Strings of lower case letters;
	 * @param n		Up to how many values
	 */
	public static List<String> makeValues(int n) {
		List<String> l = new ArrayList<String>();
		char c = 'a';
		for(int i = 0; i < n; i++) {
			c = (char)((i%26) + 'a');
			l.add(String.valueOf(c));
		}
		return l;
	}
	
	@Test
	public void putTenGet10() {
		BST bst = new BST();
		int n = 10; // length
		List<String> l = makeKeys(n);
		List<String> values = makeValues(n);
		String expected, actual;
		populate(bst,l,n); // Populate BST with alphabet

		assertEquals("a",bst.get("A"));
		
		//We expect every Key to exist and every value as well
		for(int i=0; i<n; i++) {
			expected = values.get(i);
			actual = (String) bst.get(l.get(i));
			assertEquals(expected,actual);
		}
	}
	
	@Test
	public void oneKeysTest() {
		BST bst = new BST();
		int n = 1; // length
		List<String> l = makeKeys(n);
		List<String> values = makeValues(n);
		String expected, actual;
		
		populate(bst,l,n); 

		assertEquals("a",bst.get("A"));
		
		//Test keys() method
		List<String> keys = bst.keys();
		
		for (int i = 0; i < keys.size(); i++) {
			assertEquals(l.get(i), keys.get(i));
		}
	}
	
	@Test
	public void twentySixKeysTest() {
		BST bst = new BST();
		int n = 26; // length
		List<String> l = makeKeys(n);
		List<String> values = makeValues(n);
		String expected, actual;
		
		populate(bst,l,n); 

		assertEquals("a",bst.get("A"));
		
		//Test keys() method
		List<String> keys = bst.keys();
		
		for (int i = 0; i < keys.size(); i++) {
			assertEquals(l.get(i), keys.get(i));
		}
	}
	
	@Test
	public void testSizeZero() {
		BST bst = new BST();
		int n = 0; // length
		List<String> l = makeKeys(n);
		List<String> values = makeValues(n);
		String expected, actual;
		populate(bst,l,n); // Populate BST with alphabet

		//Expect that String in values is same as actual
		for(int i=0; i<n; i++) {
			expected = values.get(i);
			actual = (String) bst.get(l.get(i));
			assertEquals(expected,actual);
		}
		
		assertEquals(n, bst.size());
	}
	
	@Test
	public void sizeCheck26() {
		BST bst = new BST();
		int n = 26; // length
		List<String> l = makeKeys(n);
		List<String> values = makeValues(n);
		String expected, actual;
		populate(bst,l,n); // Populate BST with alphabet

		
		//We expect every Key to exist and every value as well
		for(int i=0; i<n; i++) {
			expected = values.get(i);
			actual = (String) bst.get(l.get(i));
			assertEquals(expected,actual);
		}
		
		assertEquals(n, bst.size());
	}
	
	@Test
	public void testSizeThree() {
		BST bst = new BST();
		bst.put("X","1");
		bst.put("Y","2");
		bst.put("Z","3");
		
		//We expect the size of 3, all values remain
		assertEquals("1", bst.get("X"));
		assertEquals("2", bst.get("Y"));
		assertEquals("3", bst.get("Z"));
		
		assertEquals(3,bst.size());

	}
	
	@Test
	public void putThree2Duplicates() {
		BST bst = new BST();
		bst.put("X","1");
		bst.put("Y","2");
		bst.put("Z","3");
		assertEquals(3,bst.size()); //Confirm Size 3
		bst.put("X","4");
		assertEquals(3,bst.size()); //We expect size to remain as 3
		//Put two duplicate keys, different values
		assertEquals(false, bst.put("X", "4"));
		bst.put("Z", "6");
		
		//We expect no values have changed
		assertEquals("1", bst.get("X"));
		assertEquals("2", bst.get("Y"));
		assertEquals("3", bst.get("Z"));
		
		assertEquals(3,bst.size()); //Size remains the same
	}
	
	@Test
	public void set2DuplicateTest() {
		BST bst = new BST();
		bst.put("X","1");
		bst.put("Y","2");
		bst.put("Z","3");
		assertEquals(3,bst.size()); //Confirm Size 3
		
		bst.set("X","4");
		assertEquals(3,bst.size()); //We expect size to remain as 3
		assertEquals(false, bst.put("X", "4")); //Put the same duplicate, false
		
		bst.set("Z", "6");
		
		//We expect values of X and Z's to change
		assertEquals("4", bst.get("X"));
		assertEquals("2", bst.get("Y")); //Y value remains the same
		assertEquals("6", bst.get("Z"));
		
		assertEquals(3,bst.size()); //Size remains the same
		
	}
	
	@Test
	public void setOnlyTest() {
		BST bst = new BST();
		int n = 26; // length
		List<String> l = makeKeys(n);
		List<String> values = makeValues(n);
		String expected, actual;
		populateSet(bst,l,n); // Populate BST with alphabet
		
		
		assertEquals(n, bst.size());
		
		//We expect every Key to exist and every value as well
		for(int i=0; i<n; i++) {
			expected = values.get(i);
			actual = (String) bst.get(l.get(i));
			assertEquals(expected,actual);
		}
	}
	
	@Test
	public void setEveryEven() {
		BST<String, String> bst = new BST();
		int N = 100; //Size
		String expected, actual;
		
		ArrayList<String> keys = new ArrayList(N);
		ArrayList<String> values = new ArrayList(N);
		
		//Add Keys and Values, put(i, i*10), set(i, i*2);
		for(int i = 0; i < N; i++) {
			keys.add(String.valueOf(i));
			values.add(String.valueOf(i*10));
			bst.put(keys.get(i), values.get(i));
			if( (i&1) != 1) { //Bit Wise AND on number, if its 1 its odd
				bst.set(keys.get(i), String.valueOf(i*2)); //Its Even so set the value
			}
		}
		
		//We expect the size to be N
		assertEquals(N, bst.size());
		
		//We expect every Key to exist and every value as well
		for(int i=0; i<N; i++) {
			expected = values.get(i);
			actual = (String) bst.get(keys.get(i));
			if( (i&1) == 1)  { //Odd
				assertEquals(expected,actual);
			} else { //Even
				expected = String.valueOf(i*2); //We expect a different value
				assertEquals(expected,actual);
			}
		}
	}
	
	@Test
	public void containsKeyTest() {
		BST<String, String> bst = new BST();
		int N = 50; //Size
		String expected, actual;
		
		ArrayList<String> keys = new ArrayList(N);
		ArrayList<String> values = new ArrayList(N);
		
		
		//Populate the BST with same logic as above test, *10 if odd, *2 if even
		for(int i = 0; i < N; i++) {
			keys.add(String.valueOf(i));
			values.add(String.valueOf(i*10));
			bst.put(keys.get(i), values.get(i));
			if( (i&1) != 1) { //Bit Wise AND on number, if its 1 its odd
				bst.set(keys.get(i), String.valueOf(i*2)); //Its Even so set the value
			}
		}
		
		//We expect the size to be N
		assertEquals(N, bst.size());
		//We expect every Key to exist and every value as well		
		for(int i=0; i<N; i++) {
			expected = values.get(i);
			actual = (String) bst.get(keys.get(i));
			if( (i&1) == 1)  { //Odd
				assertEquals(expected,actual);
			} else { //Even
				expected = String.valueOf(i*2); //We expect a different value
				assertEquals(expected,actual);
			}
			
			//We expect every key to be both within BST and the keys() it makes
			assertEquals(true,bst.containsKey(String.valueOf(i)));
			
		}
	}
		
	@Test
	public void keysListContainsElementsTest() {
		BST<String, String> bst = new BST();
		int N = 50; //Size
		String expected, actual;

		List<String> keys = new ArrayList(N);
		List<String> values = new ArrayList(N);
		
		

		//Populate the keys and values
		for(int i = 0; i < N; i++) {
			keys.add(String.valueOf(i));
			values.add(String.valueOf(i));
		}

		Collections.shuffle(keys); //change the order of keys

		for(int i = 0; i < N; i++) {
			bst.put(keys.get(i), values.get(i)); //Populate BST
		}
		
		
		//We expect every Key to exist and every value as well
		List<String> actualKeys = bst.keys(); //so we can go ahead and keys()
		
		//Sort both key lists
		Collections.sort(keys);
		Collections.sort(actualKeys);

		for(int i=0; i<N; i++) {
			expected = keys.get(i);
			actual = actualKeys.get(i);
			
			//We expect that bst contains the keys, and both key lists match
			assertEquals(true,bst.containsKey(String.valueOf(i)));
			assertEquals(expected, actual);

		}
	}
	
	@Test
	public void keysListRightOrderTest() {
		BST<String, String> bst = new BST();
		int N = 50; //Size
		String expected, actual;

		List<String> keys = new ArrayList(N);
		List<String> values = new ArrayList(N);
		
		List<String> newKeys = new ArrayList(N);

		//Populate the keys and values
		for(int i = 0; i < N; i++) {
			keys.add(String.valueOf(i));
			values.add(String.valueOf(i));
		}
		
		for(String k: keys) {
			newKeys.add(new String(k));
		}

		Collections.shuffle(newKeys); //change the order of keys

		for(int i = 0; i < N; i++) {
			bst.put(newKeys.get(i), values.get(i)); //Populate BST
		}
		
		Collections.sort(keys); //Collections sort Number Strings like 1, 10, etc
		//Test keys() method whether they print the same order as keys List 
		List<String> actualKeys = bst.keys(); 
		
		for(int i=0; i<N; i++) {
			expected = keys.get(i);
			actual = actualKeys.get(i);
			
			//We expect that BST contains the keys, and both key lists match
			assertEquals(true,bst.containsKey(String.valueOf(i)));
			assertEquals(expected, actual);
		}
	}
	

	@Test
	public void replaceNTest() {
		BST<String, String> bst = new BST();
		int N = 100; //Size
		String expected, actual;
		
		ArrayList<String> keys = new ArrayList(N);
		ArrayList<String> values = new ArrayList(N);
		
		//Add Keys and Values, put(i, i*10), set(i, i*2);
		for(int i = 0; i < N; i++) {
			keys.add(String.valueOf(i));
			values.add(String.valueOf(i*10));
			bst.put(keys.get(i), values.get(i));
		}
		
		//We expect the size to be N
		assertEquals(N, bst.size());
		
		//Now replace every value with same value as key
		for(int i = 0; i < N; i++) {
			bst.replace(keys.get(i), String.valueOf(i)); 
		}
		
		//We expect every Key to exist and every value as well
		for(int i=0; i<N; i++) {
			expected = String.valueOf(i);
			actual = (String) bst.get(keys.get(i));
			assertEquals(expected,actual);
		} //We expect every Value that has been replace by i to be same
	}
	
	@Test
	public void replaceBooleanTest() {
		BST<String, String> bst = new BST();
		int N = 32; //Size
		String expected, actual;
		
		ArrayList<String> keys = new ArrayList(N);
		ArrayList<String> values = new ArrayList(N);
		
		//Add Keys and Values, put(i, i*10), set(i, i*2);
		for(int i = 0; i < N; i++) {
			keys.add(String.valueOf(i));
			values.add(String.valueOf(i*10));
			bst.put(keys.get(i), values.get(i));
		}
		
		//We expect the size to be N
		assertEquals(N, bst.size());
		
		//Create Strings that are not expected to be within BST
		char c = 'a'; //Letters not expected to be within BST, only Numbers
		
		//Now try to replace values with keys that are not within BST
		for(int i = 0; i < N; i++) {
			c = (char) ( (i%26) + 'a');
			assertEquals(false,bst.containsKey(String.valueOf(c)));
			assertEquals(false,bst.replace(String.valueOf(c), 
					String.valueOf(i))); 
		}//We expect that this returns false at every iteration
		
		//We expect every Key to exist and every value as well
		for(int i=0; i<N; i++) {
			expected = values.get(i);
			actual = (String) bst.get(keys.get(i));
			assertEquals(expected,actual);
		} 
	}
	
	@Test
	public void putNullKey() {
		BST bst = new BST();
		bst.put("X","1");
		bst.put("Y","2");
		bst.put("Z","3");
		ex.expect(IllegalArgumentException.class);
		bst.put(null, "null");
		
	}
	
	@Test
	public void setNullKey() {
		BST bst = new BST();
		bst.put("X","1");
		bst.put("Y","2");
		bst.put("Z","3");
		ex.expect(IllegalArgumentException.class);
		bst.set(null, "null");
	}
	
	@Test
	public void replaceNullKey() {
		BST bst = new BST();
		bst.put("X","1");
		bst.put("Y","2");
		bst.put("Z","3");
		ex.expect(IllegalArgumentException.class);
		bst.replace(null, "null");
	}
	
	@Test
	public void getNullKey() {
		BST bst = new BST();
		bst.put("X","1");
		bst.put("Y","2");
		bst.put("Z","3");
		ex.expect(IllegalArgumentException.class);
		bst.get(null);
	}
	
	@Test
	public void removeNullKey() {
		BST bst = new BST();
		bst.put("X","1");
		bst.put("Y","2");
		bst.put("Z","3");
		ex.expect(IllegalArgumentException.class);
		bst.remove(null);
	}
	
	@Test
	public void sizeIsZero() {
		BST bst = new BST();
		assertEquals(0, bst.size());
	}
	
	@Test
	public void emptyIsTrue() {
		BST bst = new BST();
		assertEquals(true, bst.isEmpty());
	}
	
	
	@Test
	public void sizeAfterRemoveOneTest(){
		BST bst = new BST();
		bst.put("N","N");
		bst.put("T","T");
		bst.put("A","A");
		assertEquals(3,bst.size()); //Confirm Size 3
		
		bst.put("7", "7");
		bst.put("B", "B");
		bst.put("O", "O");
		bst.put("Z", "Z");
		assertEquals(7,bst.size()); //Confirm Size 7
		
		assertEquals(true,bst.remove("A")); //Removal
		
		assertEquals(6,bst.size()); //We expect a size of 6
	}
	
	@Test
	public void sizeAfterRemoveLeftMost(){
		BST bst = new BST();
		bst.put("T","T");
		bst.put("N","N");
		bst.put("A","A");
		assertEquals(3,bst.size()); //Confirm Size 3
		
		bst.put("7", "7");
		bst.put("B", "B");
		bst.put("O", "O");
		bst.put("Z", "Z");
		assertEquals(7,bst.size()); //Confirm Size 7
		
		assertEquals(true,bst.remove("7"));
		
		assertEquals(6,bst.size()); //We expect a size of 6
	}
	
	@Test
	public void sizeAfterRemoveMiddle(){
		BST bst = new BST();
		bst.put("T","T");
		bst.put("N","N");
		bst.put("A","A");
		assertEquals(3,bst.size()); //Confirm Size 3
		
		
		assertEquals(true,bst.remove("N"));
		
		assertEquals(2,bst.size()); //We expect a size of 6
	}
	
	@Test
	public void sizeAfterRemoveKeyNotExist(){
		BST bst = new BST();
		bst.put("T","T");
		bst.put("N","N");
		bst.put("A","A");
		assertEquals(3,bst.size()); //Confirm Size 3
		
		
		//We Expect that remove returns false for key, and size remains the same
		assertEquals(false,bst.remove("7"));
		
		assertEquals(3,bst.size()); 
	}
	
	@Test
	public void sizeAfterRemoveRoot(){
		BST bst = new BST();
		bst.put("T","T");
		bst.put("N","N");
		bst.put("A","A");
		assertEquals(3,bst.size()); //Confirm Size 3
		
		bst.put("7", "7");
		bst.put("B", "B");
		bst.put("O", "O");
		bst.put("Z", "Z");
		assertEquals(7,bst.size()); //Confirm Size 7
		
		assertEquals(true,bst.remove("T"));
		
		List<String> keys = bst.keys(); //Get Keys after Removal
		assertEquals(6,bst.size()); //We expect a size of 6
		
		String[] expected = {"7","A","B", "N", "O", "Z"};
		
		//We expect that the Keys are also the same and in order
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i],keys.get(i));
		}
	}
	
	@Test
	public void buildTreesNegMaxTest(){
		FrequencyFilter f = new FrequencyFilter();		
		String trainingText = "I like apples. I do not like pears.";
		ex.expect(IllegalArgumentException.class);
		//We expect that this illegal parameter will throw an error
		f.buildFrequencyTrees(trainingText, 0, -4);
	}
	
	@Test
	public void buildTreesLowMaxTest(){
		FrequencyFilter f = new FrequencyFilter();		
		String trainingText = "I like apples. I do not like pears.";
		ex.expect(IllegalArgumentException.class);
		//We expect that this illegal parameter will throw an error
		f.buildFrequencyTrees(trainingText, 20, 2);
	}
	
	@Test
	public void filterPrefixLen1Freq10Test(){
		FrequencyFilter f = new FrequencyFilter();	
		String expected = "\ntree -> {\n"
				+ "\tAwesome: 12\n" + "\thappy: 11\n" + "\tAM: 10\n}";
		
		String trainingText = "I I I I I I I I I I AM AM AM AM AM AM AM AM AM"
				+ " AM happy happy happy happy happy happy happy happy happy"
				+ " happy happy Bored Bored Bored Bored Bored Bored Bored Bored"
				+ " Bored Bored Bored Bored Awesome Awesome Awesome Awesome"
				+ " Awesome Awesome Awesome Awesome Awesome Awesome Awesome "
				+ "Awesome Awesome";
		f.buildFrequencyTrees(trainingText, 1, 1);
		List<String> actual = f.filter(10);
		//Builds only one tree
		String actualTree = actual.get(0);
		
		//We expect that the filter will output the same as the Spec. Example
		assertEquals(expected, actualTree);
	}
	
	@Test
	public void filterPrefixLenTwoTreesTest() {
		FrequencyFilter f = new FrequencyFilter();
		String data = "Hello there, world. Hello there, everyone. "
				+ "Hello there, CSE students! Hello there, world.";
		f.buildFrequencyTrees(data, 1, 2);
		
		//We expect to have a total of two trees
		List<String> actual = f.filter(1);
		assertEquals(2, actual.size());

	}
	
	@Test
	public void filterWithArrayTest(){
		FrequencyFilter f = new FrequencyFilter();
		StringBuilder expect = new StringBuilder();	
		expect.append("\ntree -> {\n");
		expect.append("\t");
		expect.append("\"");
		expect.append("Awesome");
		expect.append("\"");
		//expect.append("\n");
		expect.append(": 12\n");
		expect.append("\t");
		expect.append("\"");
		expect.append("happy");
		expect.append("\"");
		//expect.append("\n");
		expect.append(": 11\n");
		expect.append("\t");
		expect.append("\"");
		expect.append("AM");
		expect.append("\"");
		//expect.append("\n");
		expect.append(": 10\n");
		expect.append("}");
		String expected = "\ntree -> {\n"
				+ "\tAwesome: 12\n" + "\thappy: 11\n" + "\tAM: 10\n}";

		expected = expect.toString();
		
		String trainingText = "I I I I I I I I I I AM AM AM AM AM AM AM AM AM"
				+ " AM happy happy happy happy happy happy happy happy happy"
				+ " happy happy Bored Bored Bored Bored Bored Bored Bored Bored"
				+ " Bored Bored Bored Bored Awesome Awesome Awesome Awesome"
				+ " Awesome Awesome Awesome Awesome Awesome Awesome Awesome "
				+ "Awesome Awesome";
		f.buildFrequencyTrees(trainingText, 1, 1);
		int[] arr = {10};
		List<String> actual = f.filter(arr);
		//Builds only one tree
		String actualTree = actual.get(0);
		
		//We expect that the filter will output the same as the Spec. Example
		assertEquals(expected, actualTree);
	}
	
	@Test
	public void filterWithEmptyArray(){
		FrequencyFilter f = new FrequencyFilter();	
		String expected = "\ntree -> {\n"
				+ "\tAwesome: 12\n" + "\thappy: 11\n" + "\tAM: 10\n}";
		
		String trainingText = "I I I I I I I I I I AM AM AM AM AM AM AM AM AM"
				+ " AM happy happy happy happy happy happy happy happy happy"
				+ " happy happy Bored Bored Bored Bored Bored Bored Bored Bored"
				+ " Bored Bored Bored Bored Awesome Awesome Awesome Awesome"
				+ " Awesome Awesome Awesome Awesome Awesome Awesome Awesome "
				+ "Awesome Awesome";
		f.buildFrequencyTrees(trainingText, 1, 1);
		int[] arr = {}; //Empty Array of Size 0
		
		ex.expect(IllegalArgumentException.class);
		
		//Expect that the following line will throw a certain exception
		List<String> actual = f.filter(arr);
	}
}