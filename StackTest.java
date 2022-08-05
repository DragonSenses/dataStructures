import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StackTest {
    Stack<Integer> stack;
    @BeforeAll
    public static void setup(){
        System.out.println("Starting Stack Test");
    }

    @BeforeEach
    public void init(){
        this.stack = new Stack<Integer>();
    }

    /**
     * Populates the Stack with integers, [1-n]
     * @param s The Stack to fill
     * @param n the number of integers to fill it with
     */
    private static void fill(Stack<Integer> s, int n) {
        for(int i = 1; i < n+1; i++){
            s.push(i);
        }
    }
    
    @Test
    public void emptyList(){         //Tests default constructor
        assumeTrue(stack.isEmpty()); // Expect an empty list
        assertEquals(0,stack.size()); // Expect the size to be 0
    }

    @Test
    public void addOne(){
        stack.push(1);
        assertAll("stack",
            () -> assertEquals(false,stack.isEmpty()),
            () -> assertEquals(1,stack.size()),

        );
    }

    @Test
    public void addNine(){

    }


    @Test
    public void removeOneElement(){
        stack.push(1);
        assertAll("stack",
            () -> assertEquals(false,stack.isEmpty()),
            () -> assertEquals(1,stack.size()),

        );
        //After removal we expect empty, 0 size, and null first/last
        stack.pop();
        assertAll("stack",
        () -> assertEquals(true,stack.isEmpty()),
        () -> assertEquals(0,stack.size()),
        );
    }

    @Test
    public void equalsNull(){
        assertEquals(false,stack.equals(null));
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
}
