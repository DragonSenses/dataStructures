import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TrieTest {
    Trie t;

    @BeforeAll
    static void initAll() {
        System.out.println("Trie Unit Testing has begun ...");
    }

    @BeforeEach
    void init() {
        t =  = new Trie();
    }

    /**
     * Populates the newly intialized trie, with strings 
     * {bear, bell, bid, bull, buy, sell, stock, stop}.
     */
    public void populateTrie(){
        t.insert("bear");
        t.insert("bell");
        t.insert("bid");
        t.insert("bull");
        t.insert("buy");
        t.insert("sell");
        t.insert("stock");
        t.insert("stop");
    }

    @Test
    void isEmptyTrue(){
        assertEquals(true, t.isEmpty());
    }

    @Test
    void isEmptyFalse() {
        t.add("k");
        assertEquals(false, t.isEmpty());
    }

    @Test
    void addOneWord(){
        t.insert("w");
        t.insert("o");
        t.insert("r");
        t.insert("d");

        assertEquals(false, t.isEmpty());
        assertEquals(true, t.find("word"));
    }

    @Test
    void deleteOneWord()[
        t.insert("w");
        t.insert("o");
        t.insert("r");
        t.insert("d");

        assertEquals(true, t.delete("word"));
    ]

    @Test
    void searchWordTrue(){
        populateTrie();
        assertEquals(true, t.find("bear"));
    }

    @Test
    void searchWordFalse(){
        populateTrie();
        assertEquals(false, t.find("bird"));
    }

    @Test
    void searchWordTwice(){
        populateTrie();
        assertEquals(true, t.find("bear"));
        assertEquals(true, t.find("bear"));
    }

    @Test
    void searchTwoWords(){
        populateTrie();
        assertEquals(true, t.find("bull"));
        assertEquals(true, t.find("stock"));
    }


    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Trie Unit Testing is complete.");
    }

}