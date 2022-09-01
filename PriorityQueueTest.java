import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

public class PriorityQueueTest {
    PriorityQueue<Integer,Integer> pq;
    Entry<Integer, Integer> expected; 

    /** Error Messages **/
    public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
    private static final String UNDERFLOW = "PriorityQueue Underflow: There is"
        + " no elements in the PriorityQueue.";

    @BeforeAll
    public static void setup() {
        System.out.println("PriorityQueue Unit Testing has begun ...");
    }

    @BeforeEach
    public void init() {
        this.pq =  new PriorityQueue<>();
    }

    /**
     * Populates the PriorityQueue with Key,Value pairs of integers, [1-n]
     * @param p The PriorityQueue to fill
     * @param n the number of integers to fill it with
     */
    private static void fill(PriorityQueue<Integer,Integer> p, int n) {
        for(int i = 1; i < n+1; i++){
            p.insert(i,i);
        }
    }

    private static boolean debug = false; 
    /**
     * Shuffles an array of integers for testing order property
     * @param arr array of integers, in order
     * @return a shuffled array of integers
     */
    private static void shuffle(int[] arr){
        int randomIndex;
        int temp;
        for(int i = 0; i < arr.length; i++){
            randomIndex = ThreadLocalRandom.current().nextInt(arr.length);
            temp = arr[randomIndex]; // Store the integer value at random index
            // Swap the Values
            arr[randomIndex] = arr[i];
            arr[i] = temp;  
        }
        if(debug) {System.out.println(Arrays.toString(arr)); }
    }

    @Test
    public void initInvalidComparator(){
        
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> pq = new PriorityQueue<>(null));
        assertEquals("Invalid Comparator!", e.getMessage());
    }

    @Test
    public void isEmptyTrue(){         //Tests default constructor
        assertTrue(pq.isEmpty());    // Expect an empty list
        assertEquals(0,pq.size());  // Expect the size to be 0
    }

    @Test
    public void isEmptyFalse(){
        pq.insert(1,1);
        assertFalse(pq.isEmpty());
    }

    @Test
    public void insertInvalidKey(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> pq.insert(null,null));
        assertEquals(ILLEGAL_ARG_NULL_KEY, e.getMessage());
    }

    
    @Test
    void minEmptyPQ(){
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> pq.min());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    void removeEmptyPQ(){
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> pq.removeMin());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    void addOne(){
        pq.insert(1,1);
        expected = new Entry<>(1,1);
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(1,pq.size()),
            () -> assertEquals(expected,pq.min())
        );
    }

    @Test
    void addZero(){
        expected = new Entry<>(0,0);       // expect 0 key
        fill(pq,9);        // fill pq with [1,9]
        pq.insert(0,0);   
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(10,pq.size()),
            () -> assertEquals(expected,pq.min())
        );
    }

    @Test
    void addSeven(){
        fill(pq,7);
        expected = new Entry<>(1,1);
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(7,pq.size()),
            () -> assertEquals(expected,pq.min())
        );
    }

    @Test
    void addNewMin(){
        int min = 31;
        int max = 73; // Insert Keys [31,73] 
        int size = max - min + 1;   // 73-31 = 42 +1 elements added
        for(int i = min; i < max+1; i++){
            pq.insert(i,i);
        }
        expected = new Entry<>(min,min);
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(size,pq.size()),
            () -> assertEquals(expected,pq.min())
        );

        int newMin = 2;
        expected = new Entry<>(newMin,newMin);   // new minimum
        pq.insert(newMin,newMin);   
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(size+1,pq.size()),
            () -> assertEquals(expected,pq.min())
        );
    }

    @Test
    void addMiddleKey(){
        int min = 31;
        int max = 73; // Insert Keys [31,73] 
        int size = max - min + 1;   // 73-31 = 42 +1 elements added
        for(int i = min; i < max+1; i++){
            pq.insert(i,i);
        }

        expected = new Entry<>(min,min);

        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(size,pq.size()),
            () -> assertEquals(expected,pq.min())
        );
        
        // add key 52, so minimum does not change
        pq.insert(52,52);   
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(size+1,pq.size()),
            () -> assertEquals(expected,pq.min())  
        );
    }

    @Test
    void addMax(){
        int min = 31;
        int max = 73; // Insert Keys [31,73] 
        int size = max - min + 1;   // 73-31 = 42 +1 elements added
        for(int i = min; i < max+1; i++){
            pq.insert(i,i);
        }
        
        expected = new Entry<>(min,min);

        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(size,pq.size()),
            () -> assertEquals(expected,pq.min())
        );
        
        // add key 74, so minimum does not change
        pq.insert(74,74);   
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(size+1,pq.size()),
            () -> assertEquals(expected,pq.min())  
        );
    }

    @Test
    void addNegative(){
        expected = new Entry<>(-1,-1);
        fill(pq,9);       // fill pq with [1,9]
        pq.insert(-1,-1);   // insert negative key
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(10,pq.size()),
            () -> assertEquals(expected,pq.min())
        );
    }

    @Test
    void addRemoveRoot(){
        fill(pq,7);
        int root = 1;
        expected = new Entry<>(root,root);
        for(int i = 0; i < 4; i++){
            assertEquals(expected,pq.removeMin());
            pq.insert(root,root);
            assertEquals(expected,pq.min());
        }
    }

    @Test
    void addTwoRemoveTwo(){
        pq.insert(1,1);
        pq.insert(2,2);
        expected = new Entry<>(1,1);

        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(2,pq.size()),
            () -> assertEquals(expected,pq.min()),
            () -> assertEquals(expected,pq.removeMin()),
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(1,pq.size()),
            () -> assertEquals(2,pq.removeMin().getValue())
        );
    }

    @Test
    void addRemoveTwice(){
        pq.insert(5,5);
        pq.insert(10,10);

        expected = new Entry<>(5,5);

        // Add and Remove Two Elements
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(2,pq.size()),
            () -> assertEquals(expected,pq.min()),
            () -> assertEquals(expected,pq.removeMin()),
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(1,pq.size()),
            () -> assertEquals(10,pq.removeMin().getKey())
        );

        // Add and Remove Two elements again
        fill(pq, 2);
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(2,pq.size()),
            () -> assertEquals(1,pq.min().getKey()),
            () -> assertEquals(1,pq.removeMin().getKey()),
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(1,pq.size()),
            () -> assertEquals(2,pq.removeMin().getKey())
        );
    }

    
    @Test
    void addNineRemoveOne(){
        fill(pq,9);
        int expected = 1;
        int nextExpected = 2;
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(9,pq.size()),
            () -> assertEquals(expected,pq.min().getValue()),
            () -> assertEquals(expected,pq.removeMin().getValue()),
            () -> assertEquals(8,pq.size()),
            () -> assertEquals(nextExpected,pq.min().getValue())
        );
    }

    @Test
    void add1024(){
        fill(pq,1024);

        for(int k = 0; k < 1024; k++){
            assertEquals(1024-k,pq.size());
            assertEquals(k+1,pq.min().getValue());
            assertEquals(k+1,pq.removeMin().getValue());
        }
        
        assertTrue(pq.isEmpty());
    }

    @Test   
    void inOrder(){
        int n = 65536;
        fill(pq,n); // Fill PriorityQueue with integers [1,2^16]
        // We expect the PriorityQueue to return the values in order of minimum
        for(int k=1; k < n+1; k++){
            assertEquals(k,pq.removeMin().getValue());
        }
    }

    @Test
    void inDifferentOrder(){
        // Insert Elements with a different insertion order
        pq.insert(5,5);
        pq.insert(3,3);
        pq.insert(1,1);
        pq.insert(7,7);
        pq.insert(2,2);
        pq.insert(4,4);
        pq.insert(6,6);

        for(int k = 1; k < 8; k++){
            assertEquals(k,pq.removeMin().getValue());
        }
    }

    @Test
    void shuffleAndSort(){
        int n = 10;
        int[] arr = IntStream.rangeClosed(1,n).toArray();
        shuffle(arr);
        // Insert the shuffled values
        for(int i : arr){
            pq.insert(i,i);
        }
        // Assert that the values are in order in pq
        for(int k = 1; k < n+1; k++){
            assertEquals(k,pq.removeMin().getValue());
        }
    }

    @Test
    void shuffleAndSortN(){
        int n = 8192;
        int[] arr = IntStream.rangeClosed(1,n).toArray();
        shuffle(arr);
        // Insert the shuffled values
        for(int i : arr){
            pq.insert(i,i);
        }
        // Assert that the values are in order in pq
        for(int k = 1; k < n+1; k++){
            assertEquals(k,pq.removeMin().getValue());
        }
    }

    @AfterEach
    void tearDown() {
        while (!pq.isEmpty()) {
            pq.removeMin();
        }
        expected = null;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("PriorityQueue Unit Testing is complete.");
    }
}
