import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Intermediate data structure that uses the Binary Search Tree to reflect a
 * File System represents a file hierarchy of a directory with its data
 * stored from an input text file and 
 * in the format {name, directory, YYYY/MM/DD}
 * 
 * Two Instance variables nameTree and dateTree sort fileData objects
 * with the key as "name" or "date" respectively, in a Binary Search Tree
 * 
 * nameTree is the BST that stores fileData by name
 * 
 * dateTree is the BST that stores fileData by date 
 * 
 */
public class FileSystem {

    BST<String, FileData> nameTree;
    BST<String, ArrayList<FileData>> dateTree;
    
    // No-Arg Public Constructor of FileSystem()
    public FileSystem() {
    	this.nameTree = new BST<String,FileData>();
    	this.dateTree = new BST<String, ArrayList<FileData>>();
    }


    // Public Constructor with inputFile as parameter
    public FileSystem(String inputFile) {
    	this(); // Initializes FileSystem via No-Arg Public Constructor
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                // Create FileData input to store into BST
                FileData input = new FileData(data[0],
                		data[1],data[2]);
                
                // Add input into name BST
                nameTree.put(data[0],input); 
                
                // Add input into date BST
                
                // First, get the ArrayList that corresponds to the date
                // if empty (i.e. BST.get() returns null, create new ArrayList
                // otherwise put the data to existing List then add to dateTree

                ArrayList<FileData> list = dateTree.get(data[2]);
                if(list == null) {
                	list = new ArrayList<>();
                	list.add(input);
					this.dateTree.put(data[2],list);
                } else {
                	list.add(input);
                }
                
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);

        }
    }


    /**
     * Create a FileData object with the given file information and add it to 
     * the instance variables of FileSystem. If there is a duplicate file 
     * name, then the FileData with the most recent date should be used. 
     * 
     * For example, if the first FileData stored in the trees is test.txt, 
     * /home, 2021/01/01 and the next FileData is test.txt, /home, 2021/02/01, 
     * the second FileData should replace the first FileData stored in trees.
     * 
     * @param name
     * @param dir
     * @param date
     */
    public void add(String name, String dir, String date) {
    	FileData obj = this.nameTree.get(name);	// Get FileData
    	
    	/* If it is null, then there was no FileData object. 
    	 * So Add a NEW FileData object into both trees of FileSystem */
    	if(obj == null) {
    		addFileData(name,dir,date);
            
            // Early Return deals with case: FileData obj = null
            return; 
    	}
    	
    	// Here a FileData obj was found
    	// Compare Names, add if names are not the same
    	if(!obj.name.equals(name)) {
    		addFileData(name,dir,date);
    		return; 
    	}
    	
    	// At this point, names are the same, dir is irrelevant
    	
    	// Compare the dates
    	int comp = (obj.lastModifiedDate.compareTo(date));
    	
    	/* If comp is negative, the obj is older date (less than) 
    	 * If comp is positive, the obj is newest date (greater than)
    	 * If comp is 0, both dates are equals 
    	 * */
    	if(comp < 0) {
    		// Update the Trees with more recent
    		// WE Update new values in BST with set() method
    		this.nameTree.set(name, new FileData(name,dir,date));
    		
    		// for dateTree get the List
    		ArrayList<FileData> list = dateTree.get(obj.lastModifiedDate);
    		list.remove(obj); // remove object from list
    		
    		// Get the list for the most recent date
    		list = this.dateTree.get(date);
    		list.add(new FileData(name,dir,date));
    		
    	} else if(comp > 0) {
    		// Object is most recent date, do nothing
    	} else {
    		// both equal dates, do nothing, do not add
    	}
    }
    
    /**
     * Private helper method adds the fileData to FileSystem.
     */
    private void addFileData(String name, String dir, String date) {
    	FileData obj = new FileData(name,dir,date);
		
		this.nameTree.put(name, obj);
		
        ArrayList<FileData> list = this.dateTree.get(date);
        if(list == null) {
        	list = new ArrayList<>();
        	list.add(obj);
        } else {
        	list.add(obj);
        }
    }


    /**
     * Given a date (format: yyyy/mm/dd), return an ArrayList of file names 
     * that correspond to this date. This list should have the file names 
     * in the order that they were added.
     * 
     * If the date given is null, return null.
     * @param date to filter by
     * @return ArrayList of file names that correspond to this date
     */
    public ArrayList<String> findFileNamesByDate(String date) {
    	ArrayList<String> result = new ArrayList<>();
    	
    	// Retrieve list corresponding to the date
    	ArrayList<FileData> list = this.dateTree.get(date);
    	
    	if(list == null) { 
    		return null; 
    	}
    	
    	// For each FileData in List, add it to result by name
    	for(FileData fd: list) {
    		result.add(fd.name);
    	}
    	
    	return result;
    }


    /**
     * Given a startDate and an endDate (format: yyyy/mm/dd), return a new 
     * FileSystem that contains only the files that are within the range 
     * (startDate is inclusive, endDate is exclusive). Assume the given 
     * parameters are valid and non-null.
     * 
     * @return a new FileSystem that only contains data within the date range
     */
    public FileSystem filter(String startDate, String endDate) {
    	FileSystem fs = new FileSystem();
    	List<String> keys = this.dateTree.keys();
    	List<String> dateRange = new ArrayList<>();
    	
    	for(String date: keys) {
        	if(withinRange(date,startDate,endDate)) { 
    			// Add this to the result list
				dateRange.add(date);
    		} else {
    			// do nothing
    		}
    	}

		ArrayList<FileData> dateList;
    	// Add each FileData object into FileSystem by acceptable dates
    	for(String date: dateRange){
			dateList = dateTree.get(date); // get date list
			fs.dateTree.put(date,dateList); // add it to new dateTree

			// Populate the names list of new FileSystem from dateList
			for(FileData obj: dateList){
				fs.nameTree.put(obj.name, obj);
			}
		}

		// TODO: To help Garbage Collection, null out references of old
		// or "this" FileSystem

    	return fs;	// Return newly filtered FileSystem
    }
    
    /**
     * Check if date is within the range [startDate,endDate)
     * startDate inclusive, endDate exclusive. 
     * @return true if date within range, false otherwise
     */
    private boolean withinRange(String date, 
    		String startDate, String endDate) {
    	// Is date greater than or equal to start?
    	// And less than endDate?
    	return (startDate.compareTo(date) <= 0) 
    			&& (date.compareTo(endDate) < 0);
    }

	/**
	 * Give a string wildCard, return a new FileSystem that contains only the
	 * files with names that contain the wildCard string. Note that this
	 * wildcard can be found anywhere in the file name (if the wild card is
	 * test, then test.txt, thistest.txt and thistest would all be files that
	 * should be selected in the filter)
	 *
	 * Assume the given parameter is valid and non-null.
	 * @param wildCard
	 * @return new FileSystem containing only files of wildCard String
	 */
	public FileSystem filter(String wildCard) {
		FileSystem fs = new FileSystem();
		List<String> keys = this.nameTree.keys();

		// Result list stores all the filtered FileData by names
		List<FileData> result = new ArrayList<>();

		for(String name: keys){
			if(name.equals(wildCard)){
				// add FileData to the result list
				result.add(this.nameTree.get(name));
			}
		}

		// Populate the new FileSystem
		for(FileData data: result){
			fs.nameTree.put(data.name, data);

			ArrayList<FileData> list =
					dateTree.get(data.lastModifiedDate);

			if(list == null) {
				list = new ArrayList<>();
				list.add(data);
				fs.dateTree.put(data.lastModifiedDate,list);
			} else { // List in dateTree already exists, append FileData
				list.add(data);
			}
		}

		return fs;
    }


	/**
	 * Return a List that contains the nameTree
	 * where each entry is formatted as: ": <FileData toString()>"
	 *
	 * This list should be in alphabetical order. In the examples below, the
	 * quotations are there to indicate that the outputs are strings, thus “
	 * should not show up in the actual output.
	 * @return output of nameTree
	 */
	public List<String> outputNameTree(){

    }
    
    
    // TODO
    public List<String> outputDateTree(){

    }
    

}

