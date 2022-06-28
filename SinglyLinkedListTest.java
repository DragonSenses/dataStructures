import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterEach;
// import static org.junit.jupiter.api.Assertions.fail;
// import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Package Visbility: Having no modifier in Java defaults to package visibility, regardless of
 * perantage, classes in the same package as class have access to the member. Will make test
 * methods public to prevent InvalidTestClassError.
 * @author kendr
 */
public class SinglyLinkedListTest<E> {
    
    SinglyLinkedList<Integer> singly = new SinglyLinkedList<Integer>();

    /** Helper Methods **/
    //This runs before all the tests, therefore must be static, previously @BeforeClass 
    @BeforeAll
    public static void init(){
        System.out.println("Starting SinglyLinkedTest");
    }

    @BeforeEach
    public void setup(){
        
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
    public void testNewList(){ //Tests default constructor
        assertTrue(singly.isEmpty()); // Expect an empty list
        assertEquals(0,singly.size()); // Expect the size to be 0
    }

    @Test
    public void testAddFirst(){
        singly.addFirst(1);
        assertAll("singly",
            () -> assertEquals(false,singly.isEmpty()),
            () -> assertEquals(1,singly.size()),
            () -> assertEquals(1,singly.first()),
            () -> assertEquals(1,singly.last())
        );
    }
}
