import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterEach;
// import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoublyLinkedListTest {
    DoublyLinkedList<Integer> list;

    @BeforeAll
    public static void setup(){
        System.out.println("Starting DoublyLinkedTest");
    }

    @BeforeEach
    public void init(){
        this.list = new DoublyLinkedList<Integer>();
    }
    
    @Test
    public void emptyList(){ //Tests default constructor
        assumeTrue(list.isEmpty()); // Expect an empty list
        assertEquals(0,list.size()); // Expect the size to be 0
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
    public void removeFirstOneElement(){
        list.addFirst(1);
        assertAll("list",
            () -> assertEquals(false,list.isEmpty()),
            () -> assertEquals(1,list.size()),
            () -> assertEquals(1,list.first()),
            () -> assertEquals(1,list.last())
        );
        //After removal we expect empty, 0 size, and null first/last
        list.removeFirst();
        assertAll("list",
        () -> assertEquals(true,list.isEmpty()),
        () -> assertEquals(0,list.size()),
        () -> assertEquals(null,list.first()),
        () -> assertEquals(null,list.last())
    );
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
        //After removal we expect empty, 0 size, and null first/last
        list.removeLast();
        assertAll("list",
        () -> assertEquals(true,list.isEmpty()),
        () -> assertEquals(0,list.size()),
        () -> assertEquals(null,list.first()),
        () -> assertEquals(null,list.last())
    );
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
}
