import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;

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
        Entry<Integer,Integer> expected = new Entry<>(1,1);
        assertAll("pq",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(1,pq.size()),
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

        assertAll("heap",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(size,pq.size()),
            () -> assertEquals(expected,pq.min())
        );
        
        // add key 74, so minimum does not change
        pq.insert(74,74);   
        assertAll("heap",
            () -> assertFalse(pq.isEmpty()),
            () -> assertEquals(size+1,pq.size()),
            () -> assertEquals(expected,pq.min())  
        );
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
