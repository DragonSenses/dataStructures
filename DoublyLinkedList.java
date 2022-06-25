
/**
 * @author kendr
 * Downside to SinglyLinkedLists is inefficiency in deleting arbitrary node in the interior of the list,
 * since one needs access to the node that precedes the node to be deleted to have its links updated.
 * 
 * A DoublyLinkedList possess nodes with two explicit references to the nodes before and after it.
 * This allows for O(1) update operations such as insertion and deletion at arbritrary positions within the
 * list. 
 * 
 * Usage of Sentinel nodes will help avoid special cases when operating near the boundaries of the list. The
 * tradeoff for slightly higher memory usage is simplifying the logic of our operations. Two sentinel nodes 
 * for both head and tail will be used. 
 * 	1. Only the nodes between head and tail change
 * 	2. All insertions will always be placed between a pair of existing nodes
 * 	3. All deletions will also guarantee to be in a node stored with neighbors on both sides
 * 	4. Special case for empty lists, regarding head/tail, do not apply like it does for SinglyLinkedLists
 * 		
 */
public class DoublyLinkedList {

	// Quick Test
	public static void main(String[] args) {

	}

}
