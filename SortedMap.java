
/**
 * A Sorted Map provides a total ordering on its keys. This map is 
 * ordered according to the natural ordering of keys by, default,
 * or it can be defined by providing an optional Comparator. 
 * 
 * Why use a Sorted Map? 
 * A traditional Map ADT (Abstract Data Type) allows users to look up
 * the value associated with a given key, but the search for that key
 * is known as an exact search. Consider the cases where one may need
 * to consider their decision based on the order of keys or multiple 
 * keys where each key is "near" each other. Consider pairs such as
 * <time stamps, events> or <cost, performance> where order matters.
 * An unsorted map does not provide a way to list all events ordered
 * by time. In fact, a Hash-Based implementations of Maps intentionally
 * scatter the keys that may seem "similar/near" to each other in the
 * original domain, so that they are uniformly distributed in a hash 
 * table.
 * 
 * Applications for A Sorted Map
 * - A Computer system that maintains information about events that
 * occured with a Time Stamp marking the occurrence of each event
 * - Modeling a trade-off problem through a key-value pair, consider
 * the trade off between performance measured against corresponding cost.
 * A customer may need to consider the the certain amount of money to 
 * query the database to find the best performing product they can afford.
 * (Example: (cost,speed) for the fastest car they can afford) 
 * - Flight Databases can represented as a sorted map where customers
 * may need to consider <origin, destination, date, and time> as parameters
 * to the key and <Flight Number, Number of Seats, Flight Duration, Fare> as
 * values. Where to satisfy users' queries a sorted map would do better than
 * a simple matter of finding an exact match for a requested query.
 */
public class SortedMap {
    
}
