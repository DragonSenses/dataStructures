
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
public interface Graph<V, E> {

    /** Returns the number of vertices of the graph */
    int numVertices();

    /** Returns the number of edges of the graph */
    int numEdges();

    /** Returns the vertices of the graph as an iterable collection */
    Iterable<Vertex<V>> vertices();

    /** Returns the edges of the graph as an iterable collection */
    Iterable<Edge<E>> edges();

    /**
     * Returns the number of edges leaving vertex v.
     * For an undirected graph, this is the same result returned by inDegree
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    int outDegree(Vertex<V> v) throws IllegalArgumentException;

    /**
     * Returns the number of edges for which vertex v is the destination.
     * For an undirected graph, this is the same result returned by outDegree
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    int inDegree(Vertex<V> v) throws IllegalArgumentException;

    /**
     * Returns an iterable collection of edges for which vertex v is the origin.
     * For an undirected graph, this is the same result returned by incomingEdges.
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException;

    /**
     * Returns an iterable collection of edges for which vertex v is the
     * destination.
     * For an undirected graph, this is the same result returned by outgoingEdges.
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException;

    /** Returns the edge from u to v, or null if they are not adjacent. */
    Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException;

    /**
     * Returns the vertices of edge e as an array of length two.
     * If the graph is directed, the first vertex is the origin, and
     * the second is the destination. If the graph is undirected, the
     * order is arbitrary.
     */
    Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException;

    /** Returns the vertex that is opposite vertex v on edge e. */
    Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException;

    /** Inserts and returns a new vertex with the given element. */
    Vertex<V> insertVertex(V element);

    /**
     * Inserts and returns a new edge between vertices u and v, storing given
     * element.
     *
     * @throws IllegalArgumentException if u or v are invalid vertices, or if an
     *                                  edge already exists between u and v.
     */
    Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException;

    /** Removes a vertex and all its incident edges from the graph. */
    void removeVertex(Vertex<V> v) throws IllegalArgumentException;

    /** Removes an edge from the graph. */
    void removeEdge(Edge<E> e) throws IllegalArgumentException;

}