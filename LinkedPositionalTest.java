import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Methods to test: size, isEmpty, first, last, before, after addFirst, addLast,
 * addBefore, addAfter, set, remove, toString, 
 * 
 * may override equals and hashcode
 */ 
public class LinkedPositionalTest {
    LinkedPositionalList<Integer> list;

    @BeforeAll
    public static void setup(){
        System.out.println("PositionalList Unit Testing has begun ...");
    }

    @BeforeEach
    public void init(){
        this.list = new LinkedPositionalList<Integer>();
    }

    @Test
    public void addOne(){
        list.addFirst(1);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,list.first()),
            () -> assertEquals(1,list.last())
        );
    }

    @AfterEach
    void tearDown() {
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            it.remove();
            it.next();
        }
        list = null;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("PositionalList Unit Testing is complete.");
    }
}
