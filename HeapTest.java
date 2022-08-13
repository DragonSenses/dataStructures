import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.Assume.assumeFalse;

public class HeapTest  {
    Heap<Integer,Integer> heap;

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
        assumeTrue(heap.isEmpty());  // Expect an empty list
        assertEquals(0,heap.size()); // Expect the size to be 0
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
