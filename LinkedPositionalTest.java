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
import java.util.NoSuchElementException;

/**
 * Methods to test: first, last, before, after addFirst, addLast,
 * addBefore, addAfter, set, remove, toString, 
 */ 
public class LinkedPositionalTest {
    LinkedPositionalList<Integer> list;

    /** Error Messages **/
    private final static String ILLEGAL_POS = "Invalid Position";
    private static String NO_SUCH_ELEM = "There is no further elements to iterate on";
    private static String ILLEGAL_STATE = "There is nothing to remove";
    private final static String SENTINEL_NODE = "Position out of Bounds";

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
    void sizeZero(){
        assertEquals(0,list.size());
    }

    @Test
    void sizeOne(){
        list.addFirst(1);
        assertEquals(1,list.size());
    }

    @Test
    void sizeN(){
        int n = 2047;
        fill(list,n);
        assertEquals(n,list.size());
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

    // first() and last() return the position node, if sentinel node then null
    @Test
    void firstEmpty(){
        assertEquals(null,list.first());
    }

    @Test
    void lastEmpty(){
        assertEquals(null,list.last());
    }

    @Test
    void precedeOne(){
        list.addFirst(1);
        Position<Integer> p = list.first();
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,p.getElement())
        );
    }

    // Test Positions - before(), after()

    /**
     * position() is private utility method is used in before/after that 
     * checks the node if is either a sentinel node or not, then returns
     * the node as a position. If node is a sentinel node, originally
     * returns null which exposes the user to a runtime Exception : 
     * NullPointerException when attempting to access the sentinel node.
     * The code has been modified to make a small check for sentinel nodes,
     * if true then instead of returning null we throw an IndexOutOfBounds
     * exception instead. This reduces overhead on the JVM. 
     */
    @Test
    void beforeOne(){
        list.addFirst(1);
        Position<Integer> p = list.first();
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,p.getElement())
        );

        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class,
            () -> list.before(p).getElement());
        assertEquals(SENTINEL_NODE, e.getMessage());
    }

    @Test
    void afterOne(){
        list.addFirst(1);
        Position<Integer> p = list.first();
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,p.getElement())
        );

        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class,
            () -> list.after(p).getElement());
        assertEquals(SENTINEL_NODE, e.getMessage());
    }

    @Test
    void beforeTwo(){
        fill(list,2);
        Position<Integer> p = list.last();
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(2,list.size()),
            () -> assertEquals(2,p.getElement()),
            () -> assertEquals(1,list.before(p).getElement())
        );
    }

    @Test
    void afterTwo(){
        fill(list,2);
        Position<Integer> p = list.first();
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(2,list.size()),
            () -> assertEquals(1,p.getElement()),
            () -> assertEquals(2,list.after(p).getElement())
        );
    }

    @Test
    void beforeMiddle(){
        fill(list,7);
        // Start from 7, using list.last()
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(7,list.size()),
            () -> assertEquals(7,list.last().getElement())
        );

        // Starting from 7, reach 4 using before()
        Position<Integer> p = list.last();
        for(int i = 7; i > 4; i--){
            assertEquals(i, p.getElement());
            p = list.before(p);
        }

        assertEquals(4,p.getElement()); // Position is at middle point
    }

    @Test
    void afterMiddle(){
        fill(list,7);
        // Start from 1, using list.first()
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(7,list.size()),
            () -> assertEquals(1,list.first().getElement())
        );

        // Starting from 1, reach 4 using after()
        Position<Integer> p = list.first();
        for(int i = 1; i < 4; i++){
            assertEquals(i, p.getElement());
            p = list.after(p);
        }

        assertEquals(4,p.getElement()); // Position is at middle point
    }

    @Test
    void beforeIterate(){
        int n = 1023; 
        fill(list,n);
        Position<Integer> p = list.last();

        for(int k = n; k > 0; k--){
            assertEquals(k,p.getElement());
            p = list.before(p);
        }
    }

    @Test
    void afterIterate(){
        int n = 1023; 
        fill(list,n);
        Position<Integer> p = list.first();

        for(int k = 1; k < n+1; k++){
            assertEquals(k,p.getElement());
            p = list.after(p);
        }
    }

    @Test // Iterate through the list twice using before and after()
    void traverseTwice(){
        int n = 1023; 
        fill(list,n);
        Position<Integer> p = list.first();

        for(int k = 1; k < n+1; k++){
            assertEquals(k,p.getElement());
            p = list.after(p);
        }
        // At this point position is at sentinel node
        p = list.before(p);
        for(int k = n; k > 0; k--){
            assertEquals(k,p.getElement());
            p = list.before(p);
        }
    }

    @Test
    void traverseByPosition(){
        int n = 1023; 
        fill(list,n);
        Position<Integer> p;
        int i = 1;
        for(p = list.first(); p != null; p = list.after(p)){
            assertEquals(i++,p.getElement());
        }
        for(p = list.last(); p != null; p = list.before(p)){
            assertEquals(--i,p.getElement());
        }
    }

    // Test insert positions - addBefore(), addAfter()


    // Exception Testing - Many methods validate() the position passed in
    // before, after, addBefore, addAfter, set(), remove()

    // if first/last returns a sentinel node, the node should return null
    @Test
    void beforeNull(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> list.before(null));
        assertEquals(ILLEGAL_POS, e.getMessage());
    }

    // Add More Exception Tests
    @Test
    void nodeAlreadyRemoved(){
        list.addFirst(1);
        Iterator<Integer> it = list.iterator();
        if(it.hasNext()){
            it.next();
            it.remove();
        }

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> list.after(list.first()));
        assertEquals(ILLEGAL_POS, e.getMessage());
    }
    
    @Test
    void illegalStateRemoveWithoutNext(){
        list.addFirst(1);
        Iterator<Integer> it = list.iterator();
        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> it.remove());
        assertEquals(ILLEGAL_STATE, e.getMessage());
    }

    @Test
    void nextNoSuchElement(){
        list.addFirst(1);
        Iterator<Integer> it = list.iterator();
        if(it.hasNext()){
            it.next();
            it.remove();
        }
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> it.next());
        assertEquals(NO_SUCH_ELEM, e.getMessage());
    }

    @Test
    void equalsTrue(){
        list.addLast(1);
        LinkedPositionalList<Integer> list2 = new LinkedPositionalList<>();
        list2.addLast(1);
        assertTrue(list.equals(list2));
    }

    @Test
    void equalsTrue1023(){
        int n = 1023;
        fill(list,n);
        LinkedPositionalList<Integer> list2 = new LinkedPositionalList<>();
        fill(list2,n);
        assertTrue(list.equals(list2));
    }

    @Test
    void equalsFalse(){
        list.addFirst(2);
        LinkedPositionalList<Integer> list2 = new LinkedPositionalList<>();
        list.addFirst(4);
        assertFalse(list.equals(list2));
    }

    @Test
    void equalsFalseOrder(){
        fill(list,8); //addLast, list = {1,2,3,4,5,6,7,8}
        LinkedPositionalList<Integer> list2 = new LinkedPositionalList<>();
        fillFirst(list2,8); //addFirst, list2 = {8,7,6,5,4,3,2,1}
        assertFalse(list.equals(list2)); // Same elements but different order
    }

    @Test
    void equalsFalseType(){
        fill(list,4); 
        LinkedPositionalList<String> list2 = new LinkedPositionalList<>();
        for(int i = 1; i < 5; i++){
            list2.addLast(String.valueOf(i));
        }
        assertFalse(list.equals(list2));
    }

    @Test
    void equalsFalseLastElement(){
        int n = 1023;
        fill(list,n);
        LinkedPositionalList<Integer> list2 = new LinkedPositionalList<>();
        fill(list2,n);
        // Remove 1023 from list2, and addLast a different element
        list2.remove(list2.last());
        list2.addLast(1024);
        assertFalse(list.equals(list2));
    }

    @Test
    public void equalsNull(){
        assertEquals(false,list.equals(null));
    }

    @Test
    public void equalsItself(){
        list.addFirst(1);
        assertEquals(true, list.equals(list));
    }

    @Test
    public void equalsItselfOneElement(){
        list.addFirst(2);
        assertEquals(true, list.equals(list));
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
