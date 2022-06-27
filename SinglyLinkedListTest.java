import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SinglyLinkedListTest<E> {
    
    /** Helper Methods **/

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
        SinglyLinkedList<Integer> singly = new SinglyLinkedList<Integer>();
        assertTrue(singly.isEmpty()); // Expect an empty list
        assertEquals(0,singly.size()); // Expect the size to be 0
    }
}
