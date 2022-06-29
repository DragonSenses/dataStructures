import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author kendr
 */
public class CircularlyLinkedListTest {
    CircularlyLinkedList<Integer> list;

    @BeforeAll
    public static void setup(){
        System.out.println("Starting CircularlyLinkedTest");
    }

    @BeforeEach
    public void init(){
        this.list = new CircularlyLinkedList<Integer>();
    }

    @Test
    public void emptyList(){ //Tests default constructor
        assumeTrue(list.isEmpty()); // Expect an empty list
        assertEquals(0,list.size()); // Expect the size to be 0
    }
}
