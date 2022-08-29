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
    @BeforeAll
    public static void setup() {
        System.out.println("PriorityQueue Unit Testing has begun ...");
    }

    @BeforeEach
    public void init() {
        this.pq =  new PriorityQueue<>();
    }


    @AfterEach
    void tearDown() {
        while (!pq.isEmpty()) {
            pq.removeMin();
        }
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("PriorityQueue Unit Testing is complete.");
    }
}
