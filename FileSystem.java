import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
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

    }
    

    
    
    // TODO
    public FileSystem filter(String wildCard) {

    }
    
    
    // TODO
    public List<String> outputNameTree(){

    }
    
    
    // TODO
    public List<String> outputDateTree(){

    }
    

}

