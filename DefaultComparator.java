import java.util.Comparator;

/**
 * Default Comparator class that compares the values of two generic
 * elements. Uses the compareTo method of a Comparable element type
 */
public class DefaultComparator<T> implements Comparator<T> {
    /**
     * Compares two entries according to their key for order. Returns a negative
     * integer, zero, or positive integer as the first argument is less than, equal
     * to, or greather than the second argument.
     * @param k1 First entry to compare
     * @param k2 Second entry to compare
     * @return  The result of comparing two entries according to key
     */
    @SuppressWarnings("unchecked")
    public int compare(T a, T b) throws ClassCastException {
        return ((Comparable<T>) a).compareTo(b);
    }
}
