import java.util.HashMap;
import java.util.Map;

/**
 * TrieNode each represents a character or string belonging to the alphabet.
 * 
 * A standard trie S is an ordered tree T with the following properties:
 *  - each node of T, except the root, is labeled with a character of A
 *  - The children of an internal node of T have distinct labels
 *  - T has s leaves, each associated with a string of S, such that the
 * concatenation of the labels of the nodes on the path from the root to 
 * a leaf v of T yields the string of S associated with v.
 * 
 * A Standard trie storing a collection S of s strings of total length n from
 * an alphabet A has the following properties:
 *  - The height of T is equal to the lengh of the longest string in S
 *  - Every internal node of T has at most |A| children (26 for English Alphabet)
 *  - T has s leaves
 *  - The number of nodes of T is at most n + 1
 */
public class TrieNode {
    private Map<Character, TrieNode> children;  
    private boolean isLeaf; // Leaf represents the end of a word
    
    /* Zero-Arg Public Constructor */
    public TrieNode(){
        this.isLeaf = false;
        this.trie = new HashMap<Character, TrieNode>();
    }

    /**
     * @return the subtree containing the children of this TrieNode.
     */
    public Map<Character, TrieNode> getChildren(){
        return this.children;
    }

    /**
     * @return true if this TrieNode is a leaf node, false otherwise
     */
    public boolean isLeaf(){
        return this.isLeaf();
    }

    /*
     * @return true if this TrieNode is end of word, false otherwise
     */
    public boolean isEndOfWord(){
        return isLeaf();
    }

    /**
     * Sets this TrieNode isLeaf flag.
     * @param isLeaf whether TrieNode is leaf or not
     */
    public void setLeaf(boolean isLeaf){
        this.isLeaf = isLeaf;
    }

    /**
     * Sets this TrieNode if it is the end of word.
     * @param isEndOfWord determines whether TrieNode is end of word or not
     */
    public void setEndOfWord(boolean isEndOfWord){
        setLeaf(setLeaf);
    }
}
