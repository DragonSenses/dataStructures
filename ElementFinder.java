import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 *  return the kth largest or smallest element from a list of n elements,
 *  where n is several times larger than k. An obvious approach to finding the
 *  Kth largest/smallest element would be to sort them, which requires O(nlogn)
 *  operations. However, this approach becomes inefficient in both time and
 *  space with increase in input size. Heaps, on the other hand, provide a much
 *  better solution. The run-time complexity with heaps is O(nlogk). Find an
 *  algorithm to find the kth largest or smallest element
 *  using heaps in O(nlogk).
 *
 * Given a list of n non-negative integers in the form of a file, the Kth_finder
 * method would return the Kth largest/smallest number from the input file.
 * File name, value of K and the type of the task (“largest” or “smallest”)
 * are the arguments to the function. This function is present inside the
 * ElementFinder class.
 *
 * For example, input.txt is a file that contains 15 numbers with 5
 * space-separated numbers in each line. The method Kth_finder(“input.txt”, 4,
 * “largest”) would return 13.
 *
 * 1 4 6 8 9
 * 10 13 14 0 1
 * 98 96 5 3 2
 */
public class ElementFinder {

	/**
	 * Reading the file: You should read one line at the time, evaluate all the
	 * numbers in that line and then read the next line. You should not load
	 * the entire file at once. You can expect test cases where the file size
	 * is bigger than the available memory.
	 *
	 * Algorithm: There are four steps to this algorithm:
	 *
	 * First, figure out the type of heap (Min-Heap or Max-Heap) you will need
	 * depending upon the type of operation.
	 *
	 * Second, implement the comparators needed for each type of heap.
	 *
	 * Third step is to create a heap that allows you to find the required
	 * element in O(nlogk) complexity.
	 *
	 * Think of the input file as an infinite sequence of numbers. There’s
	 * no possible way to store all of them. But we can store up to K elements
	 * in the form of a heap. So far the complexity is only O(klogk).
	 * The last step is to use this heap in a way that for every element after
	 * the first k, you’d need at most O(logk) operations to figure out if this
	 * element should be discarded or stored in the heap.
	 * @param filename	String filename to read input data to populate heap
	 * @param K	the kth (smallest or largest) integer from file
	 * @param operation "largest" or "smallest" determines min or max heap
	 * @return the kth largest/smallest element if it exists. If no such
	 * element exists, the method should return -1.
	 */
	public static int Kth_finder(String filename, int K, String operation) {
		Heap<String, String> heap;

		// Check Arguments
		// 1. minHeap or maxHeap?
		boolean minHeap = false;
		// If operation is "largest" then maxHeap
		if(operation.equals("largest")){
			minHeap = false;
		} else if(operation.equals("smallest")){
			// minHeap is smallest
			minHeap = true;
		}

		// 2. Initialize Heap with Comparators based on minHeap or max Heap
		if(minHeap){
			heap = new Heap<>((x, y) -> x.compareTo(y));
		} else {
			heap = new Heap<>((x, y) -> y.compareTo(x));
		}

		/**  3. Verify filename argument with proper try/catch
		 * Then populate the heap.
		 */
		try {
			File f = new File(filename);
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				// Read line by line, cannot load entire file at once
				String[] data = sc.nextLine().split(" ");

				// Populate the Heap with data
				for(int i = 0; i < data.length; i++){
					heap.add(data[i],data[i]);
				}

			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			return -1;
		}

		// Now Keep polling "k" times to get kth integer
		// We can only poll up to how many entries there are, so use size
		int k = K;
		Entry<String, String> temp = heap.peek();
		for(int i = 0; i < K; i++){
			if(heap.size() <= 0) {
				// Failure Case
				return -1; // not enough elements to pop
			}

			temp = heap.poll();
		}

		return Integer.valueOf(temp.getKey()); // return kth integer
	}

}
