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
    // private final static String NULL_NODE = "Node at Position is no longer in the list";

    /** Private Utility Methods **/
    /**
     * Populates the LinkedPositionalList with n integers in order of 1 to n, 
     * or in order order using addLast()
     * @param l The LinkedPositionalList to fill
     * @param n the number of integers to fill it with
     */
    private static void fill(LinkedPositionalList<Integer> l, int n) {
        for(int i = 1; i < n+1; i++){
            l.addLast(i);
        }
    }

    /**
     * Populates the LinkedPositionalList with n integers in order of n to 1, 
     * or reverse order using addFirst()
     * @param l The LinkedPositionalList to fill
     * @param n the number of integers to fill it with
     */
    private static void fillFirst(LinkedPositionalList<Integer> l, int n) {
        for(int i = 1; i < n+1; i++){
            l.addFirst(i);
        }
    }

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
            () -> assertEquals(1,list.first().getElement()),
            () -> assertEquals(1,list.last().getElement())
        );
    }

    @Test
    public void addLastOne(){
        list.addLast(1);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,list.first().getElement()),
            () -> assertEquals(1,list.last().getElement())
        );
    }

    // Exception Testing - Many methods validate() the position passed in
    // before, after, addBefore, addAfter, set(), remove()

    // if first/last returns a sentinel node, the node should return null
    @Test
    void beforeNull(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> list.before(null));
        assertEquals(ILLEGAL_POS, e.getMessage());
    }

    @Test
    void equalsTrue(){
        fill(list,3);
        LinkedPositionalList<Integer> list2 = new LinkedPositionalList<>();
        fill(list2,3);
        list.equals(list2);
    }

    @AfterEach
    void tearDown() {
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        list = null;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("PositionalList Unit Testing is complete.");
    }
}
