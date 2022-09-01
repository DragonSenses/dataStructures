// JUnit imports
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import java.util.Iterator;

/**
 * Methods to test: size, isEmpty, first, last, before, after addFirst, addLast,
 * addBefore, addAfter, set, remove, toString, 
 * 
 * may override equals and hashcode
 */ 
public class LinkedPositionalTest {
    LinkedPositionalList<Integer> list;

    /** Error Messages **/
    private final static String ILLEGAL_POS = "Invalid Position";
    private final static String NULL_NODE = "Node at Position is no longer in the list";

    @BeforeAll
    public static void setup(){
        System.out.println("PositionalList Unit Testing has begun ...");
    }

    @BeforeEach
    public void init(){
        this.list = new LinkedPositionalList<Integer>();
    }

    @Test   // Uninitialized list should be empty
    public void isEmptyTrue(){
        assertTrue(list.isEmpty());
    }

    @Test
    public void isEmptyFalse(){
        list.addFirst(1);
        assertFalse(list.isEmpty());
    }

    @Test   
    public void addFirstOne(){
        list.addFirst(1);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,list.first()),
            () -> assertEquals(1,list.last())
        );
    }

    @Test
    public void addLastOne(){
        list.addLast(1);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,list.first()),
            () -> assertEquals(1,list.last())
        );
    }

    // Exception Testing - Many methods validate() the position passed in
    // before, after, addBefore, addAfter, set(), remove()

    // if first/last returns a sentinel node, the node should return null
    @Test
    void beforeNull(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> list.before(null));
        assertEquals(NULL_NODE, e.getMessage());
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
