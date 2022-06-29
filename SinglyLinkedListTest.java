//import static org.junit.Assert.*;  // Needed for AssertTrue
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterEach;
// import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Package Visbility: Having no modifier in Java defaults to package visibility, regardless of
 * perantage, classes in the same package as class have access to the member. Will make test
 * methods public to prevent InvalidTestClassError.
 * @author kendr
 */
public class SinglyLinkedListTest<E> {
    
    SinglyLinkedList<Integer> singly;

    /** Helper Methods **/
    //This runs before all the tests, therefore must be static, previously @BeforeClass 
    @BeforeAll
    public static void setup(){
        System.out.println("Starting SinglyLinkedTest");
    }

    @BeforeEach
    public void init(){
        this.singly = new SinglyLinkedList<Integer>();
    }

    /**
     * Creates a SinglyLinkedList with Integers, of a given size. It populates
     * the list with numbers in sequential order, starting from 1
     * @param n - The size of the LinkedList
     * @return A SinglyLinkedList populated with integers from 1 to n
     */
    public SinglyLinkedList<Integer> makeList(int n){
        SinglyLinkedList<Integer> result = new SinglyLinkedList<Integer>();
        for (int i = n; i > 0; i--){
            result.addFirst(i);
        }
        return result;
    }

    /** Tests **/

    @Test
    public void emptyList(){ //Tests default constructor
        assumeTrue(singly.isEmpty()); // Expect an empty list
        assertEquals(0,singly.size()); // Expect the size to be 0
    }

    @Test
    public void addFirstOne(){
        singly.addFirst(1);
        assertAll("singly",
            () -> assertEquals(false,singly.isEmpty()),
            () -> assertEquals(1,singly.size()),
            () -> assertEquals(1,singly.first()),
            () -> assertEquals(1,singly.last())
        );
    }

    @Test
    public void addLastOne(){
        singly.addLast(1);
        assertAll("singly",
            () -> assertEquals(false,singly.isEmpty()),
            () -> assertEquals(1,singly.size()),
            () -> assertEquals(1,singly.first()),
            () -> assertEquals(1,singly.last())
        );
    }

    @Test
    public void removeFirstOneElement(){
        singly.addFirst(1);
        assertAll("singly",
            () -> assertEquals(false,singly.isEmpty()),
            () -> assertEquals(1,singly.size()),
            () -> assertEquals(1,singly.first()),
            () -> assertEquals(1,singly.last())
        );
        //After removal we expect empty, 0 size, and null first/last
        singly.removeFirst();
        assertAll("singly",
        () -> assertEquals(true,singly.isEmpty()),
        () -> assertEquals(0,singly.size()),
        () -> assertEquals(null,singly.first()),
        () -> assertEquals(null,singly.last())
    );
    }

    @Test
    public void removeLastOneElement(){
        singly.addFirst(1);
        assertAll("singly",
            () -> assertEquals(false,singly.isEmpty()),
            () -> assertEquals(1,singly.size()),
            () -> assertEquals(1,singly.first()),
            () -> assertEquals(1,singly.last())
        );
        //After removal we expect empty, 0 size, and null first/last
        singly.removeLast();
        assertAll("singly",
        () -> assertEquals(true,singly.isEmpty()),
        () -> assertEquals(0,singly.size()),
        () -> assertEquals(null,singly.first()),
        () -> assertEquals(null,singly.last())
    );
    }

    @Test
    public void equalsNull(){
        assertEquals(false,singly.equals(null));
    }

    @Test
    public void equalsItself(){
        assertEquals(true, singly.equals(singly));
    }

    @Test
    public void equalsItselfOneElement(){
        singly.addFirst(2);
        assertEquals(true, singly.equals(singly));
    }


    @Test
    public void equalsOneElement(){
        SinglyLinkedList<Integer> list2 = new SinglyLinkedList<Integer>();
        singly.addFirst(1);
        list2.addLast(1);
        assertEquals(true, singly.equals(list2));
    }

    @Test
    public void notEquals(){
        SinglyLinkedList<Integer> list2 = new SinglyLinkedList<Integer>();
        list2.addLast(2);
        assertEquals(false, singly.equals(list2));
    }
}
