import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

/**
 * Implementation of FrequencyFilter using BST.
 * @author kendr
 */
public class FrequencyFilter {
	
	public static final String ILLEGAL_ARG_LENGTH = "List must be same length as"
			+ " number of trees";
	public static final String ILLEGAL_ARG_BAD_RANGE = "maxPrefixLen cannot be "
			+ "less than minPrefixLen";
	public static final String ILLEGAL_ARG_MIN_PREFIX = "maxPrefixLen must be "
			+ "positive";
	public static final String ILLEGAL_NULL_PARAM = "Passed in parameter cannot"
			+ "be null!";
	public static final String ILLEGAL_ARG_MIN_ZERO = "minPrefixLen cannot be 0";
	public boolean DONT_ALLOW_EMPTY_STRINGS = true; //if min = 0, empty Strings
	//False flag means we Do Allow Empty Strings
	
	// Array of BSTs that map prefixes to frequency (number of occurrences)
    private BST<String, Integer>[] frequencyTrees;
	//1st BST has minPrefixLen, 2nd is minPrefixLen+1... last is maxPrefixLen
    
    /**
     * Builds this FrequencyFilter's frequencyTrees array of BSTs. 
     * Each element in the array is a BST whose keys are the prefixes of a 
     * certain length and values are the frequency of each prefix
     * 
     * @param data 		   the data from which to parse prefixes
     * @param minPrefixLen the minimum prefix length, inclusive, equivalent to 
     * 					   the prefix length of the first BST in the this 
     * 					   FrequencyFilter's frequencyTrees array
     * @param maxPrefixLen the maximum prefix length, inclusive, equivalent to 
     * 					   the prefix length of the last BST in the this 
     * 					   FrequencyFilter's frequencyTrees array
     * 
     * @throws IllegalArgumentException if maxPrefixLen is less than 
     * 									minPrefixLen or minPrefixLen is not 
     * 									positive, or if data is null
     */
	@SuppressWarnings("unchecked")
	public void buildFrequencyTrees(String data, int minPrefixLen, 
			int maxPrefixLen) throws IllegalArgumentException {
		/* Parameter Checks */
		if (maxPrefixLen < minPrefixLen) {
			throw new IllegalArgumentException( ILLEGAL_ARG_BAD_RANGE );
		} else if(minPrefixLen < 0) {
			throw new IllegalArgumentException( ILLEGAL_ARG_MIN_PREFIX );
		} else if (data == null) { //No Data to Use
			throw new IllegalArgumentException( ILLEGAL_NULL_PARAM );
		} else if(minPrefixLen == 0 && DONT_ALLOW_EMPTY_STRINGS) {
			throw new IllegalArgumentException( ILLEGAL_ARG_MIN_ZERO );
		}
			
		/* local variables */
		int end, start = 0; //Used to reset our prefix
		StringBuilder prefix = new StringBuilder(); 
		String key; //stores prefix as a String
		int curr = minPrefixLen; //Starting from minPrefix, tracks prefix range
		int trees = 0; //Counter for how many BSTs to make
		
		//Trim data, split words by whitespace and into String Array
		String[] text = data.trim().split(" ");  
		
		//We want to create BST's (max - min+1) times to populate BST[]
		int treeCount = maxPrefixLen - minPrefixLen + 1;
		this.frequencyTrees = (BST<String,Integer>[]) new BST<?, ?>[treeCount];
		BST<String,Integer> currTree; //store reference to current BST
		
		while (trees < treeCount) {
			currTree = new BST<String,Integer>(); //initialize our BST
			
			//1. Iterate through data[] until we are at (text.length - currLen)
			for(int i = 0; i < text.length - curr; i++) {
				//2. Create the prefix 
				for(int j=0; j < curr; j++) { 
					prefix.append(text[i+j]); 
					if(j != curr-1) { //Unless at the end
						prefix.append(" "); //append whitespace between words
					}
				} //by this point, the prefix is made

				//3. Convert prefix to String, and store it as a key
				key = prefix.toString(); 

				this.updateTree(currTree,key); //4. Update key into the BST

				//5. Reset and Delete currWord/prefix until next iteration
				end = prefix.length();
				prefix.delete(start, end);

			}//By this point our Current Tree is Made with the currentLen prefix
		
			//6. Add tree to BST[], increment trees
			frequencyTrees[trees] = currTree;
			trees++; 
			
			//7. Increase prefix length for next iteration, until maxPrefixLen
			if(curr != maxPrefixLen) { curr++; }
		}

	}

	/**
	 * Updates the frequency of a given prefix within a tree, given the prefix 
	 * as the key
	 * 
	 * If the key exists: increments key's frequency value by 1 
	 * Otherwise put the new key in BST with a frequency value of 1 
	 * 
	 * @param bst 		The current Tree to update with the given prefix as key
	 * @param key		The prefix to use as the key for the wordMap
	 */
	private void updateTree(BST<String, Integer> bst, String key) {
		//Does the BST contain the given key?
		//Yes --> set() frequency, No --> put() the key
		if(bst.containsKey(key)) {
			bst.set(key,(bst.get(key)+1));
		} else {
			bst.put(key, 1); //Insert new prefix within the tree
		}
	}
	
    /**
     * Filters every BST in this FrequencyFilter's frequencyTrees array, 
     * removing any elements whose frequency falls below the cutoff frequency. 
     * That is, if a prefix occurs less than the cutoff frequency, it is removed 
     * @param freq the cutoff frequency
     * @return a modified String representation of each tree in this 
     * 		   FrequencyFilter, as specified in the PA7 spec. 
     */
    public List<String> filter(int freq) {
    	/** Parameter Check **/
    	if (freq < 0) { freq = 0; } //Negative Frequency --> Allow all Entries
    	
    	/** local variables **/
    	int end, start = 0; //Resets the tree, String representation
    	int trees = frequencyTrees.length; //Perform operation for each Tree
    	List<String> output = new ArrayList<>(trees);
    	BST<String,Integer> bst; //will hold reference to bst
    	
    	//Each element within the list is a Representation of a BST
    	StringBuilder tree = new StringBuilder();
    	for(int i=0; i < trees; i++) {
    		bst = frequencyTrees[i];
    		//1. Format the BST String \ntree -> {\n\t"key": ocurrences\n }
    		tree.append("\ntree -> {\n"); 
    		
    		//2. Traverse the Tree (In-Order), and add the contents
    		makeTreeContents(bst,tree,freq);

    		tree.append("}");
    		//3. Add the tree String representation to the List
    		output.add(tree.toString());
    		
    		//4. Reset and Delete tree String for next iteration
			end = tree.length();
			tree.delete(start, end);
    	}
    	return output;
    }

    /**
     * Filters every BST as in filter(int freq) above, except that each BST is 
     * filtered with its own cutoff frequency. 
     * @param freqs the cutoff frequencies to filter the BSTs with. 
     * 				Each element in freqs is applied to one corresponding BST 
     * 				(same index).
     * @return a modified String representation of each tree in this 
     * 			FrequencyFilter, as specified in the PA7 spec. 
     * @throws IllegalArgumentException   if the length of freqs and 
     * 									  frequencyTrees is not the same OR
     * 									  parameter is null
     */
    public List<String> filter(int[] freqs) throws IllegalArgumentException {
        /* Parameter Check */
    	if (freqs == null) { 
    		throw new IllegalArgumentException( ILLEGAL_NULL_PARAM);
    	}
    	if(freqs.length != frequencyTrees.length) {
    		throw new IllegalArgumentException( ILLEGAL_ARG_LENGTH);
    	} 
    	
    	/** local variables **/
    	int end, start = 0; //Resets the tree, String representation
    	int trees = frequencyTrees.length; //Perform operation for each Tree
    	List<String> output = new ArrayList<>(trees);
    	BST<String,Integer> bst; //will hold reference to bst
    	
    	//Each element within the list is a Representation of a BST
    	StringBuilder tree = new StringBuilder();
    	for(int i=0; i < trees; i++) {
    		//1. Format the BST String \ntree -> {\n\t"key": ocurrences\n }
    		tree.append("\ntree -> {\n"); 

    		bst = frequencyTrees[i];
    		//2. Traverse the Tree (In-Order), and add the contents
    		makeTreeContents(bst,tree, freqs[i]);

    		tree.append("}");
    		//3. Add the tree String representation to the List
    		output.add(tree.toString());
    		
    		//4. Reset and Delete tree String for next iteration
			end = tree.length();
			tree.delete(start, end);
    	}
        return output; //return the output List
    }
    
    /**
     * Fills in the Tree String representation's contents within BST by 
     * traversing through it In Order, and populate a temporary BST that holds 
     * the entries that satisfy the cut-off frequency. Used by filter() methods.
     * 
     * Elements in auxiliary Tree will iterated through in descending order, 
     * only allowing for one prefix per frequency due to nature of DefaultMap 
     * ADT, which maps a key to only one value [One Frequency, One Prefix]
     * @param bst				The BST to traverse and represent as String
     * @param treeString		The StringBuilder to update contents of
     * @param freq				The cut-off frequency that modulates entries
     */
    private void makeTreeContents(BST<String,Integer> bst, 
    		StringBuilder treeString, int freq) {
    	if (freq < 0) { freq = 0; } //Negative Frequency --> Allow all Entries
    	//An auxiliary tree to flip the parameters in order to be sorted
    	BST<Integer,String> aux = new BST<Integer,String>(); 

    	List<String> keys = bst.keys(); //In-Order
    	int frequency;
    	String key;

    	//1. Go through every value within BST and add it to auxiliary Tree
    	for(int i=0; i < keys.size(); i++) {
    		key = keys.get(i);
    		frequency = bst.get(key);
    		if(frequency >= freq) { //Does it meet the frequency cut-off?
    			//2. Insert element and flip inputs within Auxiliary Tree
    			aux.put(frequency, key); 
    		}
    	}

    	//2. Using auxiliary tree, update contents of String, descending order
    	List<Integer> auxKeys = aux.keys(); //In-Order sort, increasing order

    	for(int j = auxKeys.size()-1; j >= 0; j--) { //Iterate backwards
    		frequency = auxKeys.get(j); //Get Frequency
    		key = aux.get(frequency);	//Get the Prefix

    		//By nature of Map, Each frequency is only mapped to 1 value Prefix
    		//Therefore, just update the treeString, with proper format
    		treeString.append("\t");
    		treeString.append("\"");
    		treeString.append(key);
    		treeString.append("\"");
    		treeString.append(": ");
    		treeString.append(frequency+"\n");
    	}
    }

}
