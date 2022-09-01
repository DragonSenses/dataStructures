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

public class HeapTest  {
    Heap<Integer,Integer> heap;

    // Error Message
    private static final String ILLEGAL_ARG = "Incompatible Key";
    private static final String UNDERFLOW = "Heap Underflow: There is no elements in the Heap.";

    @BeforeAll
    public static void setup() {
        System.out.println("Heap Unit Testing has begun ...");
    }

    @BeforeEach
    public void init() {
        this.heap = new Heap<Integer,Integer>();
    }

    
    /**
     * Populates the Heap with Key,Value pairs of integers, [1-n]
     * @param h The Heap to fill
     * @param n the number of integers to fill it with
     */
    private static void fill(Heap<Integer,Integer> h, int n) {
        for(int i = 1; i < n+1; i++){
            h.insert(i,i);
        }
    }

    @Test
    public void emptyHeap(){         //Tests default constructor
        assertTrue(heap.isEmpty());  // Expect an empty list
        assertEquals(0,heap.size()); // Expect the size to be 0
    }

    @Test
    public void isEmptyFalse(){
        heap.insert(1,1);
        assertFalse(heap.isEmpty());
    }

    @Test
    public void insertInvalidKey(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> heap.insert(null,null));
        assertEquals(ILLEGAL_ARG, e.getMessage());
    }

    @Test
    void removeEmptyHeap(){
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> heap.removeMin());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    void minEmptyHeap(){
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> heap.min());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    public void initInvalidComparator(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> heap = new Heap<>(null));
        assertEquals("Invalid Comparator!", e.getMessage());
    }

    @Test
    void addOne(){
        heap.insert(1,1);
        int expected = 1;
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(1,heap.size()),
            () -> assertEquals(expected,heap.min())
        );
    }

    @Test
    void addNewMin(){
        int min = 31;
        int max = 73; // Insert Keys [31,73] 
        int size = max - min + 1;   // 73-31 = 42 +1 elements added
        for(int i = min; i < max+1; i++){
            heap.insert(i,i);
        }
        
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(size,heap.size()),
            () -> assertEquals(min,heap.min())
        );

        int expected = 2;   // new minimum
        heap.insert(expected,expected);   
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(size+1,heap.size()),
            () -> assertEquals(expected,heap.min())
        );
    }

    @Test
    void addMiddleKey(){
        int min = 31;
        int max = 73; // Insert Keys [31,73] 
        int size = max - min + 1;   // 73-31 = 42 +1 elements added
        for(int i = min; i < max+1; i++){
            heap.insert(i,i);
        }
        
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(size,heap.size()),
            () -> assertEquals(min,heap.min())
        );
        
        // add key 52, so minimum does not change
        heap.insert(52,52);   
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(size+1,heap.size()),
            () -> assertEquals(min,heap.min())  
        );
    }

    @Test
    void addMax(){
        int min = 31;
        int max = 73; // Insert Keys [31,73] 
        int size = max - min + 1;   // 73-31 = 42 +1 elements added
        for(int i = min; i < max+1; i++){
            heap.insert(i,i);
        }
        
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(size,heap.size()),
            () -> assertEquals(min,heap.min())
        );
        
        // add key 74, so minimum does not change
        heap.insert(74,74);   
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(size+1,heap.size()),
            () -> assertEquals(min,heap.min())  
        );
    }

    @Test
    void addZero(){
        int expected = 0;       // expect 0 key
        fill(heap,9);        // fill heap with [1,9]
        heap.insert(expected,expected);   
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(10,heap.size()),
            () -> assertEquals(expected,heap.min())
        );
    }

    @Test
    void addRemoveRoot(){
        fill(heap,7);
        int root = 1;

        for(int i = 0; i < 4; i++){
            assertEquals(root,heap.removeMin());
            heap.insert(root,root);
            assertEquals(root,heap.min());
        }
    }

    @Test
    void addNegative(){
        int expected = -1;
        fill(heap,9);       // fill heap with [1,9]
        heap.insert(expected,expected);   // insert negative key
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(10,heap.size()),
            () -> assertEquals(expected,heap.min())
        );
    }

    @Test
    void removeOne(){
        heap.insert(1,1);
        int expected = 1;
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(1,heap.size()),
            () -> assertEquals(expected,heap.min()),
            () -> assertEquals(expected,heap.removeMin()),
            () -> assertTrue(heap.isEmpty()),
            () -> assertEquals(0,heap.size())
        );
    }

    @Test
    void addTwoRemoveTwo(){
        heap.insert(5,5);
        heap.insert(10,10);
        int expected = 5;

        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(2,heap.size()),
            () -> assertEquals(expected,heap.min()),
            () -> assertEquals(expected,heap.removeMin()),
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(1,heap.size()),
            () -> assertEquals(10,heap.removeMin())
        );
    }

    @Test
    void addRemoveTwice(){
        heap.insert(5,5);
        heap.insert(10,10);
        int expected = 5;

        // Add and Remove Two Elements
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(2,heap.size()),
            () -> assertEquals(expected,heap.min()),
            () -> assertEquals(expected,heap.removeMin()),
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(1,heap.size()),
            () -> assertEquals(10,heap.removeMin())
        );

        // Add and Remove Two elements again
        fill(heap, 2);
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(2,heap.size()),
            () -> assertEquals(1,heap.min()),
            () -> assertEquals(1,heap.removeMin()),
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(1,heap.size()),
            () -> assertEquals(2,heap.removeMin())
        );
    }

    @Test
    void addNineRemoveOne(){
        fill(heap,9);
        int expected = 1;
        int nextExpected = 2;
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(9,heap.size()),
            () -> assertEquals(expected,heap.min()),
            () -> assertEquals(expected,heap.removeMin()),
            () -> assertEquals(8,heap.size()),
            () -> assertEquals(nextExpected,heap.min())
        );
    }

    
    @Test
    void add1024(){
        fill(heap,1024);
        int expected = 1;
        int nextExpected = 2;
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(1024,heap.size()),
            () -> assertEquals(expected,heap.min()),
            () -> assertEquals(expected,heap.removeMin()),
            () -> assertEquals(1023,heap.size()),
            () -> assertEquals(nextExpected,heap.min())
        );
    }

    @Test
    void addAndRemove1024(){
        int n = 1024;
        fill(heap,n);
        int expected = 1;
        assertAll("heap",
            () -> assertFalse(heap.isEmpty()),
            () -> assertEquals(1024,heap.size()),
            () -> assertEquals(expected,heap.min())
        );

        for(int i = 1; i < n+1; i++){
            assertEquals(i, heap.removeMin());
        }

        assertAll("heap",
            () -> assertTrue(heap.isEmpty())
        );
    }

    @AfterEach
    void tearDown() {
        while (!heap.isEmpty()) {
            heap.removeMin();
        }
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Heap Unit Testing is complete.");
    }
}
