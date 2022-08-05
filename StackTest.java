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

    @BeforeAll
    public static void setup(){
        System.out.println("Starting DoublyLinkedTest");
    }

    @BeforeEach
    public void init(){
        this.list = new DoublyLinkedList<Integer>();
    }
    
   
}
