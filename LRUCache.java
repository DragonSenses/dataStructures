import java.util.HashMap;

/**
 * A custom-built LRU (Least Recently Used) Cache using a
 * doubly linked list and a HashMap.
 */
class LRUCache {

  /**
   * Node class representing each entry in the LRU Cache.
   */
  public class Node {
    int key; // Key for the cache entry
    int val; // Value for the cache entry
    Node next; // Pointer to the next node in the linked list
    Node prev; // Pointer to the previous node in the linked list

    /**
     * Constructs a new Node with the specified key and value.
     * 
     * @param key The key for the cache entry.
     * @param val The value for the cache entry.
     */
    public Node(int key, int val) {
      this.key = key;
      this.val = val;
      this.next = null;
      this.prev = null;
    }
  }

  private int capacity;
  private HashMap<Integer, Node> map;
  private Node head;
  private Node tail;

  /**
   * Constructs an LRUCache with the specified capacity.
   * 
   * @param capacity The maximum number of items the cache can hold.
   */
  public LRUCache(int capacity) {
    this.capacity = capacity;
    this.map = new HashMap<>();
    this.head = new Node(0, 0);
    this.tail = new Node(0, 0);
    head.next = tail;
    tail.prev = head;
  }

  /**
   * Adds a node to the end of the doubly linked list.
   * 
   * @param node The node to be added.
   */
  public void add(Node node) {
    Node previousEnd = tail.prev;
    previousEnd.next = node;
    node.prev = previousEnd;
    node.next = tail;
    tail.prev = node;
  }

  /**
   * Removes a node from the doubly linked list.
   * 
   * @param node The node to be removed.
   */
  public void remove(Node node) {
    node.prev.next = node.next;
    node.next.prev = node.prev;
  }

  /**
   * Retrieves the value associated with the specified key.
   * 
   * @param key The key whose associated value is to be returned.
   * @return The value associated with the specified key, or -1 if the key does
   *         not exist.
   */
  public int get(int key) {
    if (!map.containsKey(key)) {
      return -1;
    }

    Node node = map.get(key);
    remove(node);
    add(node);
    return node.val;
  }

  /**
   * Adds a key-value pair to the cache.
   * 
   * @param key   The key with which the specified value is to be associated.
   * @param value The value to be associated with the specified key.
   */
  public void put(int key, int value) {
    if (map.containsKey(key)) {
      Node oldNode = map.get(key);
      remove(oldNode);
    }

    Node node = new Node(key, value);
    map.put(key, node);
    add(node);

    if (map.size() > capacity) {
      Node nodeToDelete = head.next;
      remove(nodeToDelete);
      map.remove(nodeToDelete.key);
    }
  }
}

// ## **Complexity Analysis**

// ### **Time Complexity**: `O(1)` for both `get` and `put`
// - **For `get`**:
//   - Check if a key is in the hash map: `O(1)`
//   - Retrieve the node associated with the key: `O(1)`
//   - Call `remove(node)` and `add(node)`: Both are `O(1)`
// - **For `put`**:
//   - Check if a key is in the hash map: `O(1)`
//   - If the key exists, remove the node: `O(1)`
//   - Create a new node and insert it into the hash map: `O(1)`
//   - Call `add(node)`: `O(1)`
//   - If capacity is exceeded, remove the least recently used node and delete from the hash map: Both are `O(1)`

// ### **Space Complexity**: `O(capacity)`
// - **Hash Map**: Stores up to `capacity` key-value pairs.
// - **Linked List**: Holds up to `capacity` nodes, each with references to previous and next nodes.
// - **Overall Data Structure**: The combined space usage of the hash map and the linked list will not exceed `O(capacity)`.
