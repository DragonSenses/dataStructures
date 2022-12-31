import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private Map<Character, TrieNode> trie;
    private boolean isLeaf;
    
    /* Zero-Arg Public Constructor */
    public TrieNode(){
        this.isLeaf = false;
        this.trie = new HashMap<Character, TrieNode>();
    }
}
