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
 *
 * 10 13 14 0 1
 * 98 96 5 3 2
 */
public class ElementFinder {

	/**
	 *Reading the file: You should read one line at the time, evaluate all the
	 * numbers in that line and then read the next line. You should not load
	 * the entire file at once. You can expect test cases where the file size
	 * is bigger than the available memory.
	 *
	 * Return value: The method should return the kth largest/smallest element
	 * if it exists. If no such element exists, the method should return -1.
	 *
	 * Algorithm: There are four steps to this algorithm:
	 *
	 * First, figure out the type of heap (Min-Heap or Max-Heap) you will need
	 * depending upon the type of operation. There’s no programming involved
	 * in this step.
	 * Second, implement the comparators needed for each type of heap. Take a
	 * look at the unit test for help.
	 * The third step is to create a heap that allows you to find the required
	 * element in O(nlogk) complexity. What it means is that the
	 * bubbleUp/bubbleDown operations should only take about O(logk).
	 * Think of the input file as an infinite sequence of numbers. There’s
	 * no possible way to store all of them. But we can store upto K elements
	 * in the form of a heap. So far the complexity is only O(klogk).
	 * The last step is to use this heap in a way that for every element after
	 * the first k, you’d need at most O(logk) operations to figure out if this
	 * element should be discarded or stored in the heap.
	 * @param filename	String
	 * @param K
	 * @param operation
	 * @return
	 */
	public static int Kth_finder(String filename, int K, String operation) {
		// Check Params
		/* Filename proper try/catch
		* K expect to always be smaller than size of input and greater
		* than 0
		* operation - either "largest" or "smallest"
		* */


		// Create a comparator depending upon the type of operation
		// Heap<Integer, Integer> heap = new Heap<Integer, Integer>(comparator);
		/** TODO **/
		return -1;
	}

		
}
