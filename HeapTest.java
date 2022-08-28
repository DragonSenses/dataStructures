import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

public class HeapTest  {
    Heap<Integer,Integer> heap;

    // Error Message
    private static final String ILLEGAL_ARG = "Incompatible Key";

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
