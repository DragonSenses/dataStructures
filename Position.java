/**
 * The position interface provides a general abstraction for the
 * location of an element within a structure. A position acts as
 * a marker or token within a broader list. A position p, which is
 * associated with some element e in a list L, does not change,
 * even if the index of e changes in L due to insertions or
 * deletions elsewhere in the list. The only way in which a
 * position is invalid is if that position is removed. Even when
 * we replace the element e stored at p with another element, p
 * does not change. This allows a position type that serves as
 * parameters to some methods and returns values from other
 * methods of a list
 */
public interface Position<E> {

    /**
     * Returns the element stored at the position
     * 
     * @return the stored element
     * @throws IllegalStateException if it is invalid position
     */
    E getElement() throws IllegalStateException;
}
