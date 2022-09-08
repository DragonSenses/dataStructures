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
        // Recall that node has element, prev, next
        // tail = null, head, null
        // head = null, null, tail 
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

    // Test insert positions - addBefore(), addAfter()
    @Test
    void addAfterFirst(){
        fill(list,7);
        Position<Integer> p = list.first();
        list.addAfter(p,31);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(8,list.size()),
            () -> assertEquals(1,p.getElement()),
            () -> assertEquals(31,list.after(p).getElement())
        );
    }

    @Test
    void addAfterLast(){
        fill(list,7);
        Position<Integer> p = list.last();
        list.addAfter(p,31);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(8,list.size()),
            () -> assertEquals(7,p.getElement()),
            () -> assertEquals(31,list.after(p).getElement()),
            () -> assertEquals(31,list.last().getElement())
        );
    }

    @Test
    void addAfterTwo(){
        fill(list,7);
        list.addAfter(list.after(list.first()),9);
        list.print();
        Position<Integer> p;
        Position<Integer> newPosition = list.after(list.after(list.first()));
        int i = 1;
        for(p = list.first(); p != null; p = list.after(p)){
            if(p == newPosition){
                assertEquals(9,p.getElement());
            } else{
                assertEquals(i++,p.getElement());
            }
        }
    }

    @Test
    void addAfterInBetween(){
        fill(list,7);
        Position<Integer> p = list.first();
        System.out.println(list.toString());
        for(int k = 0; k < 7/2; k++){
            p = list.after(p);
        }

        list.addAfter(p,31);

        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(8,list.size())
        );

        System.out.println(list.toString());

        p = list.first();
        for(int i = 1; i < 8; i++){
            if(i == 6) {
                assertEquals(31,p.getElement());
            } else {
                assertEquals(i,p.getElement());
                p = list.after(p);
            }
        }
    }

    @Test
    void addBeforeFirst(){
        fill(list,7);
        Position<Integer> p = list.first();
        list.addBefore(p,31);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(8,list.size()),
            () -> assertEquals(1,p.getElement()),
            () -> assertEquals(31,list.before(p).getElement()),
            () -> assertEquals(31,list.first().getElement())
        );
    }

    @Test
    void addBeforeLast(){
        fill(list,7);
        Position<Integer> p = list.last();
        list.addBefore(p,31);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(8,list.size()),
            () -> assertEquals(7,p.getElement()),
            () -> assertEquals(7,list.last().getElement()),
            () -> assertEquals(31,list.before(p).getElement())
        );
    }

    @Test
    void precedeOne(){
        list.addFirst(1);
        Position<Integer> p = list.first();
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,p.getElement()),
            () -> assertEquals(null,list.precede(p))
        );
    }

    @Test
    void succeedOne(){
        list.addFirst(1);
        Position<Integer> p = list.first();
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,p.getElement()),
            () -> assertEquals(null,list.succeed(p))
        );
    }

    // Test Positions - before(), after()

    /**
     * position() is private utility method is used in before/after that 
     * checks the node if is either a sentinel node or not, then returns
     * the node as a position. If node is a sentinel node, originally
     * returns null which exposes the user to a runtime Exception : 
     * NullPointerException when attempting to access the sentinel node.
     * 
     * List's method getData() emulates the Node's getElement() but provides
     * a small check whether the position is null or one of the sentinel
     * nodes. if true then instead of returning null we throw an IndexOutOfBounds
     * exception instead. This reduces overhead on the JVM. 
     */
    @Test
    void getDataBeforeOne(){
        list.addFirst(1);
        Position<Integer> p = list.first();
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,p.getElement())
        );

        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class,
            () -> list.getData(list.before(p))); // Instead of getElement(), we getData()
        assertEquals(SENTINEL_NODE, e.getMessage());
    }

    @Test
    void getDataAfterOne(){
        list.addFirst(1);
        Position<Integer> p = list.first();
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,p.getElement())
        );

        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class,
            () -> list.getData(list.after(p))); // Instead of getElement(), we getData()
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
        int n = 4; 
        fill(list,n);
        Position<Integer> p = list.first();

        // A DoublyLinked Positional list is not circular
        // so tail's next is null, and head's prev is null
        // Before reassigning position, check if their next links are null

        for(int k = 1; k < n+1; k++){
            assertEquals(k,p.getElement());
            if(list.next(p) != null ) p = list.next(p);
        }


        for(int k = n; k > 0; k--){
            assertEquals(k,p.getElement());
            if(list.before(p) != null ) p = list.before(p);
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
