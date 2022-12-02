/**
 * In file system, a file is represented as a FileData object which 
 * contains the information of its name, directory, and last modified date.
 */
public class FileData {

    public String name;	// The name of the given file in string format.
    
    // directory of where the file is stored, represented in a string format
    public String dir;	
    
    // date of when the file is last modified, represented in a string format. 
    // Format: yyyy/mm/dd
    public String lastModifiedDate;

    /**
     * Public Constructor of FileData. Assumed arguments are non-null.
     */
    public FileData(String name, String directory, String modifiedDate) {
    	this.name = name;
    	this.dir = directory;
    	this.lastModifiedDate = modifiedDate;
    }

    /**
     * @return String representation of FileData in format of
     * {Name: file_name, Directory: dir_name, Modified Date: date}
     */
    public String toString() {
    	StringBuilder sb = new StringBuilder("{Name: ");
    	sb.append(this.name);
    	sb.append(", Directory: ");
    	sb.append(this.dir);
    	sb.append(", Modified Date: ");
    	sb.append(this.lastModifiedDate);
    	return sb.toString();
    }
}