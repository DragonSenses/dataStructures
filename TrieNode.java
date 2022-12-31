import java.util.HashMap;
import java.util.Map;

/* *
 * Tries (a.k.a. Prefix Tree or Digital Tree) is a tree-based data structure for
 * storing strings in order to support fast pattern matching. 
 * 
 * A trie (also known as a digital tree) and sometimes even radix tree or 
 * prefix tree (as they can be searched by prefixes), is an ordered tree 
 * structure, which takes advantage of the keys that it stores – usually strings.
 * 
 * A node's position in the tree defines the key with which that node is 
 * associated, which makes tries different in comparison to binary search 
 * trees, in which a node stores a key that corresponds only to that node.
 * 
 * All descendants of a node have a common prefix of a String associated with 
 * that node, whereas the root is associated with an empty String.
 * 
 * ---- Main Application: Information Retrieval ----
 * 
 * The name "trie" comes from the word "reTRIEval". In an information retrieval 
 * application, such as a search for a certain DNA sequence in a genomic database,
 * we are given a collectiton of S of strings, all defined using the same alphabet.
 * 
 * The primary query operations that tries support are:
 *  1. Pattern Matching
 *  2. Prefix Matching
 *    - This operation involves being given a string X and looking for all the 
 *      strings in S that being with X.
 * 
 * ---- Properties of Trie Data Structure ----
 *  1. The Root node is always the null one
 *  2. Every child node will be sorted alphabetically
 *  3. Each node cannot have more than 26 children (because of the English Alphabet)
 *  4. Every node can store one letter from the alphabet
 * 
 * ---- Basic Operations of Trie Data Structure ----
 *  I) Insert Operation
 *      The first operation is to insert a node into the Trie.
 *   a) Conditions for Insertion
 *      - Every letter of the input word will be inserted as an individual in a 
 *      Trie node.
 *      - The character length will determine the length of the Trie.
 *      - The key character array will act as the children’s index.
 *      - If the current node has a reference to the current letter, set the 
 *        current node that referenced the node. 
 *          * Otherwise, a new node should be created.
 * 
 *  II) Search Operation
 *    - The second basic operation for Trie is searching a node. 
 *      This operation is also similar to the insertion. We only have to search 
 *      a node using the same approach.
 * 
 * III) Delete Operation
 *    - The third operation is to delete a node. This is also an easy operation. 
 *   a) Two Conditions before starting deletion:
 *      i) If the node key is not found while searching, the delete operation 
 *         will stop and exit.
 *      ii) If the node key is found while searching the Trie, the delete 
 *         operation will delete the key.
 * 
 * ---- Implementation Details ----
 * The underlying data structure to use is HashMap. We will represent each as
 * a TrieNode. 
 */
public class TrieNode {
    private Map<Character, TrieNode> Trie;
    private boolean isLeaf;
    
    
}
