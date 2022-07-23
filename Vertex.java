/**
 * A vertex of a graph. Also known as a Node.
 */
public class Vertex<V> {
    private V data;

    /**
     * Returns the data associated with the vertex. It
     * retrieves the data stored within the node.
     * 
     * @return the data associated with the vertex
     */
    public V getData() {
        return data;
    }
}