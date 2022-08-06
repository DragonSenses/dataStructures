import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.Assume.assumeFalse;

import java.util.NoSuchElementException;

public class QueueTest {
    Queue<Integer> queue;   // Default Capacity of 8

    private static final int repetitions = 64;

    /** Error Messages **/
    private static final String UNDERFLOW = "Queue Underflow";
    private static final String OVERFLOW = "Queue Overflow";
    public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";

    @BeforeAll
    public static void setup(){
        System.out.println("Queue Unit Testing has begun ...");
    }

    @BeforeEach
    public void init(){
        this.queue = new Queue<Integer>();
    }

    /**
     * Populates the Queue with integers, [1-n]
     * @param s The Queue to fill
     * @param n the number of integers to fill it with
     */
    private static void fill(Queue<Integer> q, int n) {
        for(int i = 1; i < n+1; i++){
            q.enqueue(i);
        }
    }
    
    @Test
    public void emptyQueue(){         //Tests default constructor
        assumeTrue(queue.isEmpty());  // Expect an empty queue
        assertEquals(0,queue.size()); // Expect the size to be 0
    }

    @Test // Uninitialized Queue is empty
    public void isEmptyTrue(){
        assumeTrue(queue.isEmpty());
    }

    @Test
    public void isEmptyFalse(){
        queue.enqueue(1024);
        assumeFalse(queue.isEmpty());
    }

    @Test
    public void addOne(){
        queue.enqueue(1);
        assertAll("queue",
            () -> assertEquals(false,queue.isEmpty()),
            () -> assertEquals(1,queue.size())
        );
    }

    @Test
    public void addRemoveTwice(){
        queue.enqueue(1);
        assertEquals(1,queue.first());
        queue.enqueue(2);
        assertEquals(1,queue.first());
        assertAll("queue",
            () -> assumeFalse(queue.isEmpty()),
            () -> assertEquals(2,queue.size()),
            () -> assertEquals(1,queue.dequeue()),
            () -> assertEquals(2,queue.dequeue()),
            () -> assumeTrue(queue.isEmpty()),
            () -> assertEquals(0,queue.size())
        );
    }

    @Test
    public void addEight(){
        fill(queue, 8);
        assertAll("stack",
            () -> assertEquals(false, queue.isEmpty()),
            () -> assertEquals(8,queue.size())
        );
    }

    @Test
    public void addEightRemoveEight(){
        fill(queue, 8);
        assertAll("queue",
            () -> assertEquals(8,queue.size()),
            () -> assertEquals(false, queue.isEmpty()),
            () -> assertEquals(1,queue.dequeue()),
            () -> assertEquals(2,queue.dequeue()),
            () -> assertEquals(3,queue.dequeue()),
            () -> assertEquals(4,queue.dequeue()),
            () -> assertEquals(5,queue.dequeue()),
            () -> assertEquals(6,queue.dequeue()),
            () -> assertEquals(7,queue.dequeue()),
            () -> assertEquals(8,queue.dequeue()),
            () -> assertEquals(true, queue.isEmpty()),
            () -> assertEquals(0,queue.size())
        );
    }

    @Test
    public void addEightRemoveNine(){
        fill(queue, 8);
        assertAll("queue",
            () -> assertEquals(8,queue.size()),
            () -> assertEquals(false, queue.isEmpty()),
            () -> assertEquals(1,queue.dequeue()),
            () -> assertEquals(2,queue.dequeue()),
            () -> assertEquals(3,queue.dequeue()),
            () -> assertEquals(4,queue.dequeue()),
            () -> assertEquals(5,queue.dequeue()),
            () -> assertEquals(6,queue.dequeue()),
            () -> assertEquals(7,queue.dequeue()),
            () -> assertEquals(8,queue.dequeue()),
            () -> assertEquals(true, queue.isEmpty()),
            () -> assertEquals(0,queue.size())
        );
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> queue.dequeue());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    public void addNineCapacityEight(){
        fill(queue, 8);

        // Max Default Capacity of Queue is 8, expect an exception after adding again
        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> queue.enqueue(9));
        assertEquals(OVERFLOW,e.getMessage());

        // Queue should contain the elements and operate normally afterwards
        assertAll("queue",
            () -> assertEquals(8,queue.size()),
            () -> assertEquals(false, queue.isEmpty()),
            () -> assertEquals(1,queue.dequeue()),
            () -> assertEquals(2,queue.dequeue()),
            () -> assertEquals(3,queue.dequeue()),
            () -> assertEquals(4,queue.dequeue()),
            () -> assertEquals(5,queue.dequeue()),
            () -> assertEquals(6,queue.dequeue()),
            () -> assertEquals(7,queue.dequeue()),
            () -> assertEquals(8,queue.dequeue()),
            () -> assertEquals(true, queue.isEmpty()),
            () -> assertEquals(0,queue.size())
        );
    }

    @Test
    public void removeOne(){
        queue.enqueue(1);
        assertAll("queue",
            () -> assertEquals(false,queue.isEmpty()),
            () -> assertEquals(1,queue.size())
        );
        //After removal we expect empty and 0 size
        assertEquals(1,queue.dequeue());
        assertAll("queue",
            () -> assertEquals(true,queue.isEmpty()),
            () -> assertEquals(0,queue.size())
        );
    }

    @Test
    public void addFourRemoveTwo(){
        fill(queue,4);
        assertAll("queue",
            () -> assertEquals(4,queue.size()),
            () -> assertEquals(false,queue.isEmpty()),
            () -> assertEquals(4,queue.size()),
            () -> assertEquals(1,queue.dequeue()),
            () -> assertEquals(2,queue.dequeue()),
            () -> assertEquals(2,queue.size())
        );
    }

    @Test
    public void addFourRemoveTwoAddThreeRemoveFour(){
        fill(queue,4);
        assertAll("queue",
            () -> assertEquals(4,queue.size()),
            () -> assertEquals(false,queue.isEmpty()),
            () -> assertEquals(4,queue.size()),
            () -> assertEquals(1,queue.dequeue()),
            () -> assertEquals(2,queue.dequeue()),
            () -> assertEquals(2,queue.size())
        );
        // Queue should be [3, 4]
        // Add three elements, Queue should be [3, 4, 5, 6, 7]
        queue.enqueue(5);
        queue.enqueue(6);
        queue.enqueue(7);

        // Remove three elements
        assertAll("queue",
            () -> assertEquals(5,queue.size()),
            () -> assertEquals(false,queue.isEmpty()),
            () -> assertEquals(3,queue.dequeue()),
            () -> assertEquals(4,queue.dequeue()),
            () -> assertEquals(5,queue.dequeue()),
            () -> assertEquals(6,queue.dequeue()),
            () -> assertEquals(1,queue.size()),
            () -> assertEquals(7,queue.first())
        ); // Queue expected to be [7]
    }

    @Test
    public void equalsNull(){
        assertEquals(false,queue.equals(null));
    }

    @Test
    public void equalsItself(){
        assertEquals(true, queue.equals(queue));
    }

    @Test
    public void equalsItselfOneElement(){
        queue.enqueue(2);
        assertEquals(true, queue.equals(queue));
    }

    // Check whether two different queues with the same elemnt within are equivalent
    @Test
    public void equalsOneElement(){
        Queue<Integer> queue2 = new Queue<Integer>();
        queue.enqueue(1);
        queue2.enqueue(1);
        assertEquals(true, queue.equals(queue2));
    }

    @Test
    public void notEquals(){
        Queue<Integer> queue2 = new Queue<Integer>();
        queue2.enqueue(2);
        assertEquals(false,queue.equals(queue2));
    }

    @Test
    public void notEqualsDifferentSize(){
        fill(queue,4);
        Queue<Integer> queue2 = new Queue<Integer>();
        queue2.enqueue(2);
        assertEquals(false, queue.equals(queue2));
    }

    @Test
    public void notEqualsOneElement(){
        queue.enqueue(7);
        Queue<Integer> queue2 = new Queue<Integer>();
        queue2.enqueue(2);
        assertEquals(false,queue.equals(queue2));
    }

    @Test
	public void zeroSizeConstructor(){
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> queue = new Queue<Integer>(0));
		assertEquals(ILLEGAL_ARG_CAPACITY,e.getMessage());
	}

    @Test
	public void negativeSizeConstructor(){
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> queue = new Queue<Integer>(-1));
		assertEquals(ILLEGAL_ARG_CAPACITY,e.getMessage());
	}

    @Test
    public void removeEmptyQueue(){
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> queue.dequeue());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    public void peekEmptyQueue(){
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> queue.first());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @RepeatedTest(value = repetitions, 
        name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Repetition:")
    public void addRemoveRepeat(TestInfo testInfo){
        Queue<Integer> testQueue = new Queue<>(repetitions);
        for(int i = 1; i < repetitions+1; i++){
            testQueue.enqueue(i);
        }
        assertEquals(repetitions,testQueue.size());
        for(int k = 1; k < repetitions+1; k++){
            assertEquals(k,testQueue.dequeue());
        }
    }

    @Test   /** Testing Order Property **/
    public void inOrder(){
        int n = 65536;
        Queue<Integer> queue16 = new Queue<>(n);
        fill(queue16,n); // Fill Queue with integers [1,2^16]
        // We expect the queue to return the values in order of insertion
        for(int k=1; k < n+1; k++){
            assertEquals(k,queue16.dequeue());
        }
    }

    // Note: Works when n = 2^28 (268435456) or less, n = 2^28 takes ~6.7 seconds 
    // when n = 2^29 (536870912) or  n = 2^30 (1073741824) or higher,
    // an java.lang.OutOfMemoryError occurs
    @Test
    public void inOrderN(){
        int n = 16777216;
        Queue<Integer> nQueue = new Queue<>(n);
        fill(nQueue,n); // Fill Queue with integers [1,2^16]
        // We expect the queue to return the values in order of insertion
        for(int k=1; k < n+1; k++){
            assertEquals(k,nQueue.dequeue());
        }
    }

    @AfterEach
    void tearDown() {
        while(!queue.isEmpty()){
            queue.dequeue();
        }
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Queue Unit Testing is complete.");
    }
}

