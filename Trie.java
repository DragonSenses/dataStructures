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
 * ---- Properties of Standard Trie ----
 * Let S be a set of s strings from alphabet A such that no string in S is a
 * prefix of another string
 * 
 * A standard trie S is an ordered tree T with the following properties:
 *  - each node of T, except the root, is labeled with a character of A
 *  - The children of an internal node of T have distinct labels
 *  - T has s leaves, each associated with a string of S, such that the
 * concatenation of the labels of the nodes on the path from the root to 
 * a leaf v of T yields the string of S associated with v.
 * 
 * Thus, a trie T represents the strings of S with paths from the root to the 
 * leaves of T. Note the importance of assuming that no string in S is a prefix
 * of another string. This ensures that each string of S is uniquely 
 * associated with a leaf of T.
 * 
 * A Standard trie storing a collection S of s strings of total length n from
 * an alphabet A has the following properties:
 *  - The height of T is equal to the lengh of the longest string in S
 *  - Every internal node of T has at most |A| children (26 for English Alphabet)
 *  - T has s leaves
 *  - The number of nodes of T is at most n + 1
 *  
 * ---- Properties of Trie Data Structure ----
 *  1. There is one root node in each Trie 
 *  2. Each node of a Trie represents a string and each edge represents a character
 *  3. Every node consists of hashmaps or an array of pointers, with each index
 *  representing a character and a flag to indicate if any string ends at the
 *  current node (isLeaf boolean)
 *  4. Tries can contain any number of characters including alphabets, numbers, 
 * and special characters. But for this implementation, only strings with characters 
 * 'a-z'. Therefore, only 26 pointers need for every node, where the 0th index 
 * represents ‘a’ and the 25th index represents ‘z’ characters.
 *  5. Each path from the root to any node represents a word or string.
 *  6. Every child node will be sorted alphabetically
 *  7. Each node cannot have more than 26 children (because of the English Alphabet)
 *  8. Every node can store one letter from the alphabet
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
 * a Trie with a root node, and delegate the TrieNode in a separate class.
 */
public class Trie {
    private TrieNode root;

    public Trie(){
        this.root = new TrieNode();
    }

    /**
     * @return true if trie is empty (root node is null), false otherwise
     */
    public boolean isEmpty(){
        return root == null;
    }

    /* Insertion */
    /**
     * Inserts the given string within the Trie, by updating each TrieNode
     * for each character of the word.
     * @param word String to insert in trie
     */
    public void insert(String word){
        // Set current node to Root Node
        TrieNode curr = root;

        // For each character in the string. Populate the trie.
        for(char c: word.toCharArray()){
            // If the edge/path does not exist, create a new TrieNode
            curr.getChildren().computeIfAbsent(c, ch -> new TrieNode());

            // Go to next node by moving up the current node
            curr = curr.getChildren().get(c); 
        }

        // Reaching this point means we are at the end of trie, a leaf node
        node.setLeaf(true);
    }

    /**
     * Adds the given string within the Trie
     * @param word string to add
     */
    public void add(String word){
        insert(word);
    }

    /* Retrieval - Searching / Finding */
    /**
     * Finds the given word within the Trie
     * @param word string to find
     * @return true if present, false otherwise
     */
    public boolean find(String word){
        TrieNode curr = root;   
        TrieNode node;
        char c;

        // For each character in the word
        for(int k = 0; k < word.length(); k++){
            c = word.charAt(i); 
            node = curr.getChildren().get(c);

            if(node == null) {
                return false;
            }

            curr = node;    // Move up the Trie
        }

        return curr.isEndOfWord(); // current node is leaf node
    }

    /**
     * Searches the given word within the Trie
     * @param word string to find
     * @return true if present, false otherwise
     */
    public boolean search(String word){
        return find(word); 
    }

    /**
     * Retrieves the given word within the Trie
     * @param word string to find
     * @return true if present, false otherwise
     */
    public boolean retrieve(String word){
        return find(word);
    }

    /* Deletion */
    public boolean delete(String word){
        return searchAndDestroy(root, word, 0);
    }


    /**
     * Private utility method that retrieves the given word, then if
     * found will delete it.
     * i) If the node key is not found while searching, the delete operation 
     * will stop and exit.
     * ii) If the node key is found while searching the Trie, the delete 
     * operation will delete the key.
     * @param curr TrieNode to search and delete word from
     * @param word word to retrieve
     * @param index to start search and destroy
     * @return true if given word is deleted, false otherwise
     */
    private boolean searchAndDestroy(TrieNode curr, String word, int index){
        // Bounds checking for index parameter
        if(index == word.length()) {
            if(!curr.isLeaf()) {
                return false;
            }
            curr.setEndOfWord(false);
            return curr.getChildren().isEmpty();
        }

        // Get the TrieNode that contains the current character of the word
        char c = word.charAt(index);
        TrieNode node = curr.getChildren().get(c);
        
        // No Children found for given character
        if(node == null) {
            return false; // exit delete operation
        }

        // Check if current node is ready to be deleted
        // Ensure that curr is not a leaf node (end of word) and recursively
        // call searchAndDestroy for each of its child nodes (index + 1)
        boolean canDelete = searchAndDestroy(node, word, index + 1) && 
                            !node.isLeaf();

        // If we can delete the current node then:
        if(canDelete){
            curr.getChildren().remove(c);
            return curr.getChildren().isEmpty();
        }

        return false;
    }

}
