import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedPositionalTest {
    LinkedPositionalList<Integer> list;

    @BeforeAll
    public static void setup(){
        System.out.println("PositionalList Unit Testing has begun ...");
    }

    @BeforeEach
    public void init(){
        this.list = new LinkedPositionalList<Integer>();
    }

    @AfterEach
    void tearDown() {
        Iterator<Integer> it = list.iterator();
        while (!list.isEmpty()) {
            list.remove(list.before(list.last()));
        }
        list = null;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("PositionalList Unit Testing is complete.");
    }
}
