/**
 * A vertex of a graph. Also known as a Node.
 */
public interface Vertex <V> {

    /**
     * Returns the element associated with the vertex. It
     * retrieves the data stored within the node.
     * @return the element asscoaited with the vertex
     */
    V getElement();
}