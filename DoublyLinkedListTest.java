// JUnit imports
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

public class DoublyLinkedListTest {
    DoublyLinkedList<Integer> list;

    /** Error Messages **/
	private static final String UNDERFLOW = "List Underflow: There is nothing to remove!";

    @BeforeAll
    public static void setup(){
        System.out.println("DoublyLinkedList Unit Testing has begun....");
    }

    @BeforeEach
    public void init(){
        this.list = new DoublyLinkedList<Integer>();
    }

    /**
     * Populates the DoublyLinkedList with n integers in order of n to 1, 
     * or reverse order since it uses addFirst
     * @param l The DoublyLinkedList to fill
     * @param n the number of integers to fill it with
     */
    private static void fillFirst(DoublyLinkedList<Integer> l, int n) {
        for(int i = 1; i < n+1; i++){
            l.addFirst(i);
        }
    }
    
    /**
     * Populates the DoublyLinkedList with n integers in order of 1 to n, 
     * or in order order since it uses addLast
     * @param l The DoublyLinkedList to fill
     * @param n the number of integers to fill it with
     */
    private static void fillLast(DoublyLinkedList<Integer> l, int n) {
        for(int i = 1; i < n+1; i++){
            l.addLast(i);
        }
    }
    
    @Test
    public void emptyList(){         // Tests default constructor
        assertTrue(list.isEmpty());  // Expect an empty list
        assertEquals(0,list.size()); // Expect the size to be 0
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

    @Test
    void firstEmptyList(){
        assertTrue(list.isEmpty());
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> list.first());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    void lastEmptyList(){
        assertTrue(list.isEmpty());
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> list.last());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    void peekEmptyList(){
        assertAll("list",
            () -> assertTrue(list.isEmpty()),
            () -> assertEquals(0,list.size()),
            () -> assertEquals(null,list.peekFirst()),
            () -> assertEquals(null,list.peekLast())
        );
    }

    @Test
    public void addAndPeek(){
        list.addFirst(1);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,list.peekFirst()),
            () -> assertEquals(1,list.peekLast())
        );
    }

    @Test
    void removeFirstEmptyList(){
        assertTrue(list.isEmpty());
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> list.last());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    void removeLastEmptyList(){
        assertTrue(list.isEmpty());
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> list.last());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    public void removeFirstOneElement(){
        list.addFirst(1);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,list.first()),
            () -> assertEquals(1,list.last())
        );
        //After removal we expect empty, 0 size
        list.removeFirst();
        assertAll("list",
            () -> assertEquals(true,list.isEmpty()),
            () -> assertEquals(0,list.size())
        );

        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> list.first());
        assertEquals(UNDERFLOW, e.getMessage());

        e = assertThrows(NoSuchElementException.class,
            () -> list.last());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    public void removeLastOneElement(){
        list.addFirst(1);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,list.first()),
            () -> assertEquals(1,list.last())
        );
        //After removal we expect empty, 0 size
        list.removeLast();
        assertAll("list",
            () -> assertEquals(true,list.isEmpty()),
            () -> assertEquals(0,list.size())
        );

        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> list.first());
        assertEquals(UNDERFLOW, e.getMessage());

        e = assertThrows(NoSuchElementException.class,
            () -> list.last());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    void pollEmptyList(){
        assertAll("list",
            () -> assertTrue(list.isEmpty()),
            () -> assertEquals(0,list.size()),
            () -> assertEquals(null,list.pollFirst()),
            () -> assertEquals(null,list.pollLast())
        );
    }

    @Test
    public void pollFirstOneElement(){
        list.addFirst(1);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,list.first()),
            () -> assertEquals(1,list.last())
        );
        //After removal we expect empty, 0 size, and first/last return null
        list.removeFirst();
        assertAll("list",
            () -> assertEquals(true,list.isEmpty()),
            () -> assertEquals(0,list.size()),
            () -> assertEquals(null,list.pollFirst()),
            () -> assertEquals(null,list.pollLast())
        );
    }

    @Test
    public void pollLastOneElement(){
        list.addFirst(1);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,list.first()),
            () -> assertEquals(1,list.last())
        );
        //After removal we expect empty, 0 size
        list.removeLast();
        assertAll("list",
            () -> assertEquals(true,list.isEmpty()),
            () -> assertEquals(0,list.size()),
            () -> assertEquals(null,list.pollFirst()),
            () -> assertEquals(null,list.pollLast())
        );
    }

    @Test   // Test Order Property of addFirst() and removeFirst()
    public void reverseOrderN(){
        int n = 1024;
        fillFirst(list,n); // Fill Stack with integers [1,2^16]
        // We expect the list to return the values in reverse order
        for(int k = n; k > 0; k--){
            assertEquals(k,list.removeFirst());
        }
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


    @Test
    public void equalsOneElement(){
        DoublyLinkedList<Integer> list2 = new DoublyLinkedList<Integer>();
        list.addFirst(1);
        list2.addLast(1);
        assertEquals(true, list.equals(list2));
    }

    @Test
    public void notEquals(){
        DoublyLinkedList<Integer> list2 = new DoublyLinkedList<Integer>();
        list2.addLast(2);
        assertEquals(false, list.equals(list2));
    }

    @AfterEach
    void tearDown() {
        while (!list.isEmpty()) {
            list.removeLast();
        }
        list = null;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("DoublyLinkedList Unit Testing is complete.");
    }
}
