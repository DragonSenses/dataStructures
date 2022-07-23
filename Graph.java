import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

/** #################### WORK IN PROGRESS ########################### */

/**
 * A Graph is a way of representing relationships that exists betwen
 * pairs of objects. A graph is a set of objects, called vertices,
 * together withh a collection of pairwise connections between them,
 * called edges. In short, a graph is a collection of vertices and edges.
 * 
 * Abstractly, a graph G is a set of V vertices and a collection of
 * pairs of vertices from V, called edges. Vertices can also be referred
 * to as nodes, and edges referred to as arcs.
 * 
 * Graphs can either be Directed or Undirected. An edge (u,v) is directed
 * from u to v if the pair (u,v) is ordered where u precedes v. An edge
 * (u,v) is undirected if the pair (u,v) is not ordered. In the undirected
 * case, (u,v) is the same as (v,u).
 * 
 * Directed graphs, or digraphs, is a graph whose edges are all directed.
 * Undirected graphs is a graph where all edges within are undirected.
 * Mixed graphs is a graph with both directed and undirected edges. One
 * can make a directed graph by replacing every undirected edge by a pair
 * of directed edges.
 * 
 * Applications: A City Map can be modeled as a graph whose vertices are
 * intersections or dead ends, and edges are streets without intersections.
 * Undirected edges can correspond to two-way streets whereas directed
 * edges correspond to one-way streets. Therefore, this is a mixed graph.
 * 
 * - Another physical examples are graphs of electrical wiring and plumbing
 * networks, where each connector, fixture, or outlet can be viewed as a
 * vertex and each stretch of wire or pipe is viewed as an edge. Current
 * can flow in a wire, or water can flow in a pipe in either direction, so
 * we may considered their edges undirected or directed.
 * 
 * Implementation: Graph ADT, can be modeled with three data types:
 * Vertex, Edge, and Graph.
 * 
 * In case of undirected graph, methods outgoingEdges and incomingEdges
 * return the same collection, and outDegree and inDegree return the
 * same value.
 */
public class Graph <V,E>  {

    /************************* Nested Vertex Node class  ********************************/
    private class Node implements Vertex<V> {
        private V element;
        private Position<Vertex<V>> p;
        private HashMap<Vertex<V>, Edge<E>> out;
        private HashMap<Vertex<V>, Edge<E>> in;

        public Node(V data, boolean digraph) {
            element = data;
            out = new HashMap<Vertex<V>, Edge<E>>();
            if(digraph) {
                in = new HashMap<Vertex<V>, Edge<E>>();
            } else {
                in = out;   // If undirected graph, then in also refers to out map
            }
        }

        /** Returns the element associated with the vertex node. */
        public V getElement() { return element; }

        /** @returns the position of this vertex within the graphs list of vertexes */
        public Position<Vertex<V>> getPosition() { return p; }

        /** Set the position of this node vertex within the graphs list of vertexes */
        public void setPosition(Position<Vertex<V>> p) { this.p = p; }

        /** @return The reference to map of incoming edges */
        public HashMap<Vertex<V>, Edge<E>> getIncoming() { return in; }

        /** @return  The reference to map of outgoing edges*/
        public HashMap<Vertex<V>, Edge<E>> getOutgoing() { return out; }

        /**
         * Check whether this vertex instance belongs to the given graph. This will serve
         * as a safety check when updating the graphs.
         * @param graph the incoming graph to check in
         * @return true if this and graph point to the same instance, and position is not null;
         *         false otherwise
         */
        public boolean check(Graph<V,E> graph) {
            return (Graph.this == graph) && (p != null);
        }
    } /************************ End of Vertex Node class  ********************************/

    /************************* Nested Edge Node class  ********************************/
    private class EdgeNode implements Edge<E> {
        private E element;
        private Position<Edge<E>> p;
        // A Vertex array that contains two Vertexes to form the edge
        private Vertex<V>[] edge;  

        /**
         * Constructs an EdgeNode that contains the edge, or a pair of vertices
         * @param u The first vertex
         * @param v The second vertex
         * @param data  The data contained within the edge
         */
        @SuppressWarnings("unchecked")
        public EdgeNode(Vertex<V> u, Vertex<V> v, E data){
            element = data; 
            edge = (Vertex<V>[]) new Vertex[]{u,v}; // Array Length of 2
        }

        /** Returns the element associated with the edge. */
        public E getElement() { return element; }

        /** @return Reference to the edge, or array that stores the pair of vertices */
        public Vertex<V>[] getEdge() { return edge; }

        /** @returns the position of this edge within the graphs list of edges */
        public Position<Edge<E>> getPosition() { return p; }

        /** Set the position of this node edge within the graphs list of edges */
        public void setPosition(Position<Edge<E>> p) { this.p = p; }

        /**
         * Check whether this edge instance belongs to the given graph. This will serve
         * as a safety check when updating the graphs.
         * @param graph the incoming graph to check in
         * @return true if this and graph point to the same instance, and position is not null;
         *         false otherwise
         */
        public boolean check(Graph<V,E> graph) {
            return (Graph.this == graph) && (p != null);
        }
    } /************************* End of nested Edge Node class  ********************************/
    
    /** Graph Instance Variables **/
    private LinkedPositionalList<Vertex<V>> vertices = new LinkedPositionalList<>();
    private LinkedPositionalList<Edge<E>> edges = new LinkedPositionalList<>(); 
    HashMap<Vertex<V>, LinkedList<Vertex<V>>> adjMap = new HashMap<>();

    // Default Constructor
    public Graph() {
        super();
    }

    /** Returns the number of vertices of the graph */
    public int numVertices(){ return vertices.size(); }

    /** Returns the number of edges of the graph */
    public int numEdges(){ return edges.size(); }

    /** Returns the vertices of the graph as an iterable collection */
    public Iterator<Vertex<V>> vertices(){ return vertices.iterator(); }

    /** Returns the edges of the graph as an iterable collection */
    public Iterator<Edge<E>> edges(){ return edges.iterator(); }

    /**
     * Returns the number of edges leaving vertex v.
     * For an undirected graph, this is the same result returned by inDegree
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    public int outDegree(Vertex<V> v) throws IllegalArgumentException{
        Node vertex;
        return 0;
    }

    /**
     * Returns the number of edges for which vertex v is the destination.
     * For an undirected graph, this is the same result returned by outDegree
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    public int inDegree(Vertex<V> v) throws IllegalArgumentException{
        return 0;
    }

    /**
     * Returns an iterable collection of edges for which vertex v is the origin.
     * For an undirected graph, this is the same result returned by incomingEdges.
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException{
        return null;
    }

    /**
     * Returns an iterable collection of edges for which vertex v is the
     * destination.
     * For an undirected graph, this is the same result returned by outgoingEdges.
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException{
        return null;
    }

    /** Returns the edge from u to v, or null if they are not adjacent. */
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException{
        return null;
    }

    /**
     * Returns the vertices of edge e as an array of length two.
     * If the graph is directed, the first vertex is the origin, and
     * the second is the destination. If the graph is undirected, the
     * order is arbitrary.
     */
    public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException{
        return null;
    }

    /** Returns the vertex that is opposite vertex v on edge e. */
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException{
        return null;
    }

    /** Inserts and returns a new vertex with the given element. */
    public Vertex<V> insertVertex(V element){
        return null;
    }

    /**
     * Inserts and returns a new edge between vertices u and v, storing given
     * element.
     *
     * @throws IllegalArgumentException if u or v are invalid vertices, or if an
     *                                  edge already exists between u and v.
     */
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException{
        return null;
    }

    /** Removes a vertex and all its incident edges from the graph. */
    public void removeVertex(Vertex<V> v) throws IllegalArgumentException{

    }

    /** Removes an edge from the graph. */
    public void removeEdge(Edge<E> e) throws IllegalArgumentException{

    }

}