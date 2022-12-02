import static org.junit.Assert.*;

import org.junit.*;
import java.util.ArrayList;
public class FileSystemTest {
    FileSystem fs;
    private static String name = "test.txt";
    private static String dir = "/home";
    private static String date = "2022/08/20";

    // Creates a new FileSystem before every test
    @Before
    public void initialize() {
         fs = new FileSystem();
    }

    // 2 Tests for add()
    @Test
    void addOne(){
        FileData expected = new FileData(name,dir,date);
        fs.add(name,dir,date);
        FileData actual = fs.nameTree.get(name);

        assertEquals(actual.toString(),expected.toString());
    }

    // Should replace the FileData with most recent date
    @Test
    void addTwiceDuplicate(){
        String newDate = "2050/08/20";
        FileData expected = new FileData(name,dir,newDate);
        fs.add(name,dir,date);   // old date: "2022/08/20"
        fs.add(name,dir,newDate); // new date: "2050/08/20"
        FileData actual = fs.nameTree.get(name);
        assertEquals(actual.toString(),expected.toString());
    }

    // 2 Tests for findFileNamesByDate
    @Test
    void findByDateOne(){
        FileData expected = new FileData(name,dir,date);
        fs.add(name,dir,date);
        ArrayList<String> dateList = fs.findFileNamesByDate(date);
        // get first element in dateList
        FileData actual = fs.dateTree.get(dateList.get(0)).get(0);

        assertEquals(actual.toString(),expected.toString());
    }

    @Test
    void findFileNamesByDateMultiple(){
        String name2 = "user.txt";
        String name3 = "food.txt";
        FileData expected = new FileData(name,dir,date);
        fs.add(name,dir,date);
        fs.add(name2,dir,date);
        fs.add(name3,dir,date);
        ArrayList<String> names = fs.findFileNamesByDate(date);

        assertEquals(fs.nameTree.get(names.get(0)).toString(),
                expected.toString());
        assertEquals(fs.nameTree.get(names.get(1)).toString(),
                expected.toString());
        assertEquals(fs.nameTree.get(names.get(2)).toString(),
                expected.toString());
    }

    // 4 tests for filter
    @Test
    void filterDatesAll(){
        String startDate = "0000/01/01";
        String endDate = "9999/12/31";
        FileSystem files = fs.filter(startDate,endDate);
        System.out.println(files);
        assertEquals(1,1);
    }

    @Test
    void filterDatesSome(){
        assertEquals(1,1);

    }
    @Test
    void filterNameOne(){
        assertEquals(1,1);


    }

    @Test
    void filterNameNone(){
        assertEquals(1,1);

    }

    // 2 tests for output

    @Test
    void outputNameTreeOnce(){
        assertEquals(1,1);

    }

    @Test
    void outputNameTreeSimple(){
        assertEquals(1,1);

    }

    @Test
    void outputDateTreeSimple(){
        assertEquals(1,1);

    }

    @Test
    void outputDateTreeComplex(){
        assertEquals(1,1);

    }
}