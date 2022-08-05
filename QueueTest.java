import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

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

    @Test
    public void addOne(){
        queue.enqueue(1);
        assertAll("queue",
            () -> assertEquals(false,queue.isEmpty()),
            () -> assertEquals(1,queue.size())
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
    public void equalsNull(){
        assertEquals(false,queue.equals(null));
    }

    @Test
    public void equalsItself(){
        queue.enqueue(1);
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
    public void notEqualsEmptyAndNonEmpty(){
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
    public void pushPopPeekRepeat(TestInfo testInfo){
        for(int i = 1; i < repetitions+1; i++){
            queue.enqueue(i);
        }
        assertEquals(repetitions,queue.size());
        for(int k = 1; k <= repetitions+1; k++){
            assertEquals(k,queue.dequeue());
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

