import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.Assume.assumeFalse;

import java.util.NoSuchElementException;

public class StackTest {
    Stack<Integer> stack;

    private static final int repetitions = 4096;

    /** Error Messages **/
    private static final String UNDERFLOW = "Stack Underflow";
    public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";

    @BeforeAll
    public static void setup(){
        System.out.println("Stack Unit Testing has begun ...");
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
    public void emptyStack(){         //Tests default constructor
        assumeTrue(stack.isEmpty());  // Expect an empty list
        assertEquals(0,stack.size()); // Expect the size to be 0
    }

    @Test // Uninitialized Stack is empty
    public void isEmptyTrue(){
        assumeTrue(stack.isEmpty());
    }

    @Test
    public void isEmptyFalse(){
        stack.push(1024);
        assumeFalse(stack.isEmpty());
    }

    @Test
    public void pushOne(){
        stack.push(1);
        assertAll("stack",
            () -> assertEquals(false,stack.isEmpty()),
            () -> assertEquals(1,stack.size())
        );
    }

    // Tests the resizing mechanism
    @Test
    public void pushNine(){
        fill(stack, 9);
        assertAll("stack",
            () -> assertEquals(false, stack.isEmpty()),
            () -> assertEquals(9,stack.size())
        );
    }

    @Test
    public void pushNinePopNine(){
        fill(stack, 9);
        assertAll("stack",
            () -> assertEquals(9,stack.size()),
            () -> assertEquals(false, stack.isEmpty()),
            () -> assertEquals(9,stack.pop()),
            () -> assertEquals(8,stack.pop()),
            () -> assertEquals(7,stack.pop()),
            () -> assertEquals(6,stack.pop()),
            () -> assertEquals(5,stack.pop()),
            () -> assertEquals(4,stack.pop()),
            () -> assertEquals(3,stack.pop()),
            () -> assertEquals(2,stack.pop()),
            () -> assertEquals(1,stack.pop()),
            () -> assertEquals(true, stack.isEmpty()),
            () -> assertEquals(0,stack.size())
        );
    }

    @Test
    public void pushNinePopTen(){
        fill(stack, 9);
        assertAll("stack",
            () -> assertEquals(false, stack.isEmpty()),
            () -> assertEquals(9,stack.size()),
            () -> assertEquals(9,stack.pop()),
            () -> assertEquals(8,stack.pop()),
            () -> assertEquals(7,stack.pop()),
            () -> assertEquals(6,stack.pop()),
            () -> assertEquals(5,stack.pop()),
            () -> assertEquals(4,stack.pop()),
            () -> assertEquals(3,stack.pop()),
            () -> assertEquals(2,stack.pop()),
            () -> assertEquals(1,stack.pop())
        );
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> stack.pop());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    public void popOne(){
        stack.push(1);
        assertAll("stack",
            () -> assertEquals(false,stack.isEmpty()),
            () -> assertEquals(1,stack.size())
        );
        //After removal we expect empty and 0 size
        stack.pop();
        assertAll("stack",
            () -> assertEquals(true,stack.isEmpty()),
            () -> assertEquals(0,stack.size())
        );
    }

    @Test
    public void pushPopTwice(){
        stack.push(1);
        assertEquals(1,stack.peek());
        stack.push(2);
        assertEquals(2,stack.peek());
        assertAll("stack",
            () -> assumeFalse(stack.isEmpty()),
            () -> assertEquals(2,stack.size()),
            () -> assertEquals(2,stack.pop()),
            () -> assertEquals(1,stack.pop()),
            () -> assumeTrue(stack.isEmpty()),
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
    public void equalContentsDifferentCapacity(){
        fill(stack,8);
        Stack<Integer> stack16 = new Stack<>(16);
        fill(stack16,8);
        assertEquals(true, stack.equals(stack16));
    }

    @Test
    public void notEquals(){ // Empty Stack vs Non-Empty Stack
        Stack<Integer> stack2 = new Stack<Integer>();
        stack2.push(2);
        assertEquals(false, stack.equals(stack2));
    }

    @Test
    public void notEqualsDifferentSize(){
        fill(stack,4);
        Stack<Integer> stack2 = new Stack<Integer>();
        stack2.push(2);
        assertEquals(false, stack.equals(stack2));
    }

    @Test
    public void notEqualsOneElement(){
        stack.push(7);
        Stack<Integer> stack2 = new Stack<Integer>();
        stack2.push(2);
        assertEquals(false,stack.equals(stack2));
    }

    @Test
	public void zeroSizeConstructor(){
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> stack = new Stack<Integer>(0));
		assertEquals(ILLEGAL_ARG_CAPACITY,e.getMessage());
	}

    @Test
	public void negativeSizeConstructor(){
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			()-> stack = new Stack<Integer>(-1));
		assertEquals(ILLEGAL_ARG_CAPACITY,e.getMessage());
	}

    @Test
    public void popEmptyStack(){
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> stack.pop());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @Test
    public void peekEmptyStack(){
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
            () -> stack.peek());
        assertEquals(UNDERFLOW, e.getMessage());
    }

    @RepeatedTest(value = repetitions, 
        name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Repetition:")
    public void pushPopPeekRepeat(TestInfo testInfo){
        for(int i = 1; i < repetitions+1; i++){
            stack.push(i);
            assertEquals(i,stack.peek());
        }
        assertEquals(repetitions,stack.size());
        for(int k = repetitions; k != 0; k--){
            assertEquals(k,stack.pop());
        }
    }

    @Test   /** Testing Order Property **/
    public void reverseOrder(){
        fill(stack,65536); // Fill Stack with integers [1,2^16]
        // We expect the stack to return the values in reverse order
        for(int k=65536; k > 0; k--){
            assertEquals(k,stack.pop());
        }
    }

    // Note: Works when n = 2^28 (268435456) or less, n = 2^28 takes ~6.7 seconds 
    // when n = 2^29 (536870912) or  n = 2^30 (1073741824) or higher,
    // an java.lang.OutOfMemoryError occurs
    @Test
    public void reverseOrderN(){
        int n = 16777216; // 2^24  for quicker testing
        fill(stack,n); // Fill Stack with integers [1,2^16]
        // We expect the stack to return the values in reverse order
        for(int k=n; k > 0; k--){
            assertEquals(k,stack.pop());
        }
    }

    @AfterEach
    void tearDown() {
        while(!stack.isEmpty()){
            stack.pop();
        }
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Stack Unit Testing is complete.");
    }
}
