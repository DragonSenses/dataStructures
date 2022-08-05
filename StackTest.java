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
            () -> assertEquals(1,stack.size())
        );
    }

    // Tests the resizing mechanism
    @Test
    public void addNine(){
        fill(stack, 9);
        assertAll("stack",
            () -> assertEquals(false, stack.isEmpty()),
            () -> assertEquals(9,stack.size())
        );
    }

    @Test
    public void removeOneElement(){
        stack.push(1);
        assertAll("stack",
            () -> assertEquals(false,stack.isEmpty()),
            () -> assertEquals(1,stack.size())
        );
        //After removal we expect empty, 0 size, and null first/last
        stack.pop();
        assertAll("stack",
        () -> assertEquals(true,stack.isEmpty()),
        () -> assertEquals(0,stack.size())
        );
    }

    @Test
    public void equalsNull(){
        assertEquals(false,stack.equals(null));
    }

    @Test
    public void equalsItself(){
        stack.push(1);
        assertEquals(true, stack.equals(stack));
    }

    @Test
    public void equalsItselfOneElement(){
        stack.push(2);
        assertEquals(true, stack.equals(stack));
    }

    // Check whether two different stacks with the same elemnt within are equivalent
    @Test
    public void equalsOneElement(){
        Stack<Integer> stack2 = new Stack<Integer>();
        stack.push(1);
        stack2.push(1);
        assertEquals(true, stack.equals(stack2));
    }

    @Test
    public void notEquals(){
        Stack<Integer> stack2 = new Stack<Integer>();
        stack2.push(2);
        assertEquals(false, stack.equals(stack2));
    }

    
}