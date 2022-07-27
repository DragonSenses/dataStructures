import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
 * 
 * Source: Goodrich, M. T., Tamassia, R., &amp; Goldwasser, M. H. (2015). 
 *          Data Structures and algorithms in Java. Wiley. 
 */
public class Graph <V,E>  {

    /************************* Nested Vertex Node class  ********************************/
    private class Node implements Vertex<V> {
        private V element;
        private Position<Vertex<V>> p;
        private HashMap<Vertex<V>, Edge<E>> out;    // Edges where Vertex is origin
        private HashMap<Vertex<V>, Edge<E>> in;     // Edges where Vertex is destination

        public Node(V data){
            this(data,isDigraph);
        }

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

        /**
         * String representation of a Node/Vertex
         */
        @Override
        public String toString(){
            return element.toString();
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
        public Vertex<V>[] getEndpoints() { return edge; }

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

        @Override
        public String toString(){
            StringBuilder e = new StringBuilder("Edge [");
            e.append(edge[0].getElement().toString());
            e.append(", ");
            e.append(edge[1].getElement().toString());
            e.append("] Already Exists");
            return e.toString();
        }
    } /************************* End of nested Edge Node class  ********************************/
    
    /** Graph Instance Variables **/
    private LinkedPositionalList<Vertex<V>> vertices = new LinkedPositionalList<>();
    private LinkedPositionalList<Edge<E>> edges = new LinkedPositionalList<>(); 
    private boolean isDigraph;

    // Error Messages
    private static final String ILLEGAL_NODE = "Invalid Vertex";
    private static final String ILLEGAL_EDGE = "Invalid Edge";

    // Default Constructor
    public Graph() {
        super();
        isDigraph = false;
    }

    // One-Arg Constructor that determines whether Graph is directed or undirected
    public Graph(boolean directed) {
        this.isDigraph = directed; 
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
     * Checks if the given vertex is a valid instance of a Node (vertex) and is
     * part of this instance of Graph
     * @param v - Vertex to Check for
     * @return the reference to the vertex wrapped as a Node
     */
    private Node check(Vertex<V> v) {
        if (!(v instanceof Node)) { throw new IllegalArgumentException(ILLEGAL_NODE); }
        Node node = (Node) v; // Safe Cast and wrap as a Node
        if(!node.check(this)) { throw new IllegalArgumentException(ILLEGAL_NODE); }
        return node;
    }

    /**
     * Checks if the given edge is a valid instance of a EdgeNode and is
     * part of this instance of Graph
     * @param e - Edge to Check for
     * @return the reference to the edge wrapped as a EdgeNode
     */
    private EdgeNode check(Edge<E> e) {
        if (!(e instanceof EdgeNode)) { throw new IllegalArgumentException(ILLEGAL_EDGE); }
        EdgeNode node = (EdgeNode) e; // Safe Cast and wrap as an EdgeNode
        if(!node.check(this)) { throw new IllegalArgumentException(ILLEGAL_EDGE); }
        return node;
    }

    /**
     * Returns the number of edges leaving vertex v.
     * For an undirected graph, this is the same result returned by inDegree
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    public int outDegree(Vertex<V> v) throws IllegalArgumentException{
        Node vertex = check(v);
        return vertex.getOutgoing().size(); // Node stores a Map of outgoing edges
    }

    /**
     * Returns the number of edges for which vertex v is the destination.
     * For an undirected graph, this is the same result returned by outDegree
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    public int inDegree(Vertex<V> v) throws IllegalArgumentException{
        Node vertex = check(v);
        return vertex.getIncoming().size();
    }

    /**
     * Returns an iterable collection of edges for which vertex v is the origin.
     * For an undirected graph, this is the same result returned by incomingEdges.
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException{
        Node vertex = check(v);               // Values in the HashMap are Edges
        return vertex.getOutgoing().values(); // Collection view of values contained in map
    }

    /**
     * Returns an iterable collection of edges for which vertex v is the
     * destination.
     * For an undirected graph, this is the same result returned by outgoingEdges.
     * 
     * @throws IllegalArgumentException if v is not a valid vertex
     */
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException{
        Node vertex = check(v); // Always check for parameter v
        return vertex.getIncoming().values(); // Collection view of edges contained in map
    }

    /** Returns the edge from u to v, or null if they are not adjacent. */
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException{
        Node origin = check(u);
        return origin.getOutgoing().get(v); // If no mapping for the key, returns null in HashMap
    }

    /**
     * Returns the vertices of edge e as an array of length two.
     * If the graph is directed, the first vertex is the origin, and
     * the second is the destination. If the graph is undirected, the
     * order is arbitrary.
     */
    public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException{
        EdgeNode edge = check(e);
        return edge.getEndpoints();
    }

    /** Returns the vertex that is opposite vertex v on edge e. */
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException{
       EdgeNode edge = check(e);    // Is Edge e valid within the graph?
       Vertex<V>[] endpoints = edge.getEndpoints(); // Get the set of vertices of the edge
       
       if(endpoints[0] == v) {
        return endpoints[1]; // if v is found in the first spot, return the other
       } else if (endpoints[1] == v) {
            return endpoints[0]; // if v is found in second spot, return the first
       } else {
        throw new IllegalArgumentException("Vertex v is not incident to this edge");
       }

    }

    /** Inserts and returns a new vertex with the given element. */
    public Vertex<V> insertVertex(V element){
        Node vertex = new Node(element); // Construct the new Node vertex
        vertex.setPosition(vertices.addLast(vertex));    // Add to end of PositionalList of Vertices
        return vertex;
    }

    /**
     * Inserts and returns a new edge between vertices u and v, storing given
     * element.
     *
     * @throws IllegalArgumentException if u or v are invalid vertices, or if an
     *                                  edge already exists between u and v.
     */
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException{
        // Check if the HashMap contains the edge, if null is returned from get() then create it
        if (getEdge(u,v) == null) {
            EdgeNode edge = new EdgeNode(u, v, element); // Construct the edge
            edge.setPosition(edges.addLast(edge));      // Update edges PositionalList
            // Check the Vertices u and v
            Node origin = check(u);
            Node destination = check(v);
            // Add the Vertex v, the origin, to the outgoing Map where it is the origin
            origin.getOutgoing().put(v,edge);
            // Add the Vertex u, the destination, the incoming Map where it is the destination
            destination.getIncoming().put(u,edge);
            return edge;
        } else { // Else it already exists therefore throw an Exception
            throw new IllegalArgumentException(getEdge(u,v).toString());
        }
    }

    /** Inserts and returns a new edge between vertices u and v, with given element */
    public Edge<E> insert(V u, V v, E e){
        return this.insertEdge(this.insertVertex(u), this.insertVertex(v), e);
    }

    /** Removes an edge from the graph. */
    public void removeEdge(Edge<E> e) throws IllegalArgumentException{
        EdgeNode edge = check(e);           // 0. Check Valid Edge
        // 1. Get the two Vertex Nodes that make up the edge, or endpoints
        Node[] endpoints = (Node[]) edge.getEndpoints(); 
        // 2. Remove the respective vertexes incident to each other within their maps
        // Since endpoints[0] is u, and endpoints[1] is v, we assume u is the origin
        // and v is the destination. Thus, we remove v from u's outgoing map and 
        // u from v's incoming list
        endpoints[0].getOutgoing().remove(endpoints[1]);
        endpoints[1].getIncoming().remove(endpoints[0]);

        // 3. Remove the edge from the list of edges
        edges.remove(edge.getPosition());   // Remove edge from positional list
        // 4. Invalidate the Edge, EdgeNode, and Position
        edge.setPosition(null);
    }

    /** Removes a vertex and all its incident edges from the graph. */
    public void removeVertex(Vertex<V> v) throws IllegalArgumentException{
        Node vertex = check(v); //0. Check valid vertex
        // 1. Remove all edges in which vertex is the origin
        for(Edge<E> e : vertex.getOutgoing().values()){
            removeEdge(e);
        }
        //2. Remove all edges in which vertex is the destination
        for(Edge<E> e : vertex.getIncoming().values()){
            removeEdge(e);
        }
        //3. Remove vertex from the list of vertices
        vertices.remove(vertex.getPosition());  // Remove vertex from positional list
        //4. Invalidate the vertex, node, and position
        vertex.setPosition(null);   
    }

    public static <V,E> void BreadthFirstSearch(Graph<V,E> g, Vertex<V> u) {

    }

    /**
     * A Depth-first search algorithm that searches the Graph. Maintains two auxiliary
     * data structures, 1) a set that marks all visited nodes and 2) map that contains
     * any paths that have been used to search
     * @param g - The Graph to search in
     * @param u - The vertex to search for if it exists
     * @param visited - A set auxiliary data structure that contains vertices that have
     *                  already been visited
     * @param map - A Map that associates any vertex v with the edge e of the graph
     *              that is used to discover v
     */
    public static <V,E> void DepthFirstSearch(Graph<V,E> g, Vertex<V> u,
        Set<Vertex<V>> visited, Map<Vertex<V>,Edge<E>> map) {
        Vertex<V> v; // Declared outside the for loop to save space
        // 1. Mark the incoming vertex u as visited
        visited.add(u);
        // 2. For every outgoing edge e of vertex u, where u is the origin
        for(Edge<E> e: g.outgoingEdges(u)){
            v = g.opposite(u,e); // Get the opposite vertex of u, using edge e
            // If the vertex v is not within the set of visited nodes/vertices
            if(!visited.contains(v)){
                // 3. Put this edge e and vertex v as a known path within the map
                map.put(v,e); 
                // 4. Recursively call to continue exploring the path of v
                DepthFirstSearch(g, u, visited, map);
            }
        }
    }

    

    /**
     * For debugging purposes
     * @return String representation of Graph
     */
    @Override
    public String toString() {
        StringBuilder g = new StringBuilder();
        for (Vertex<V> v : vertices.eIterable()) {
            g.append("Vertex " + v.getElement() + "\n");
            if (isDigraph)
                g.append(" [outgoing]");
            g.append(" " + outDegree(v) + " adjacencies:");
            for (Edge<E> e: outgoingEdges(v))
                g.append(String.format(" (%s, %s)", opposite(v,e).getElement(), e.getElement()));
            g.append("\n");
            if (isDigraph) {
                g.append(" [incoming]");
                g.append(" " + inDegree(v) + " adjacencies:");
                for (Edge<E> e: incomingEdges(v))
                g.append(String.format(" (%s, %s)", opposite(v,e).getElement(), e.getElement()));
                g.append("\n");
            }
        }

        return g.toString();
    }

    public static void main(String[] args){
        boolean digraph = true;
        Graph<String,String> graph = new Graph<>(digraph);
        // graph.insertEdge(new Node<String>("A", digraph), new Node<String>("B", digraph),digraph);
        // Cannot make static reference to non-static type Node, learned from the line above
        // Need to use insertVertex and insertEdge to instantiate vertexes and edges
       
        graph.insertEdge(graph.insertVertex("A"),
            graph.insertVertex("B"), "AB");

        graph.insertEdge(graph.insertVertex("B"),
            graph.insertVertex("C"), "BC");

        graph.insertEdge(graph.insertVertex("B"),
            graph.insertVertex("D"), "BD");

        graph.insertEdge(graph.insertVertex("C"),
            graph.insertVertex("E"), "CE");

        graph.insertEdge(graph.insertVertex("B"),
            graph.insertVertex("A"), "BA");
        /**
         *      
         * A -> B -> C - > E
         *      |
         *       -> D
         */

        // System.out.println(graph.toString());
        // System.out.println(graph.insertEdge(graph.insertVertex("A"),
        //     graph.insertVertex("B"), "AB"));

        /** Create a Larger Sample Graph **/
        graph = new Graph<>(digraph);
        /**
         * A - B - C - D
         * | \ |   | / | 
         * E - F   G   H
         * |  /  / | \ |
         * I - J - K   L
         * | \    /|   |
         * M - N   O   P
         */

         /** First Row **/
        graph.insertEdge(graph.insertVertex("A"),
            graph.insertVertex("B"), "AB");

        graph.insertEdge(graph.insertVertex("B"),
            graph.insertVertex("C"), "BC");

        graph.insertEdge(graph.insertVertex("C"),
            graph.insertVertex("D"), "CD");
        
        /** Second Row */

        graph.insertEdge(graph.insertVertex("A"),
            graph.insertVertex("E"), "AE");

        graph.insertEdge(graph.insertVertex("A"),
            graph.insertVertex("F"), "AF");

        graph.insertEdge(graph.insertVertex("E"),
            graph.insertVertex("A"), "EA");

        graph.insertEdge(graph.insertVertex("F"),
            graph.insertVertex("A"), "FA");

        graph.insertEdge(graph.insertVertex("C"),
            graph.insertVertex("G"), "CG");
        
        graph.insertEdge(graph.insertVertex("D"),
            graph.insertVertex("G"), "DG");

        graph.insertEdge(graph.insertVertex("D"),
            graph.insertVertex("H"), "DH");

        /** Third Row **/

        graph.insertEdge(graph.insertVertex("E"),
            graph.insertVertex("F"), "EF");

        /** Fourth Row  **/

        graph.insertEdge(graph.insertVertex("E"),
            graph.insertVertex("I"), "EI");

        graph.insertEdge(graph.insertVertex("F"),
            graph.insertVertex("I"), "FI");

        graph.insertEdge(graph.insertVertex("G"),
            graph.insertVertex("J"), "GJ");

        graph.insert("G","K","GK");
        graph.insert("G","L","GL");
        graph.insert("H","L","HL");

        /** Fifth Row **/
        graph.insert("I","J","IJ");
        graph.insert("J","K","JK");

        /** Sixth Row **/
        graph.insert("I","M","IM");
        graph.insert("I","N","IN");
        graph.insert("K","N","KN");
        graph.insert("K","O","KO");
        graph.insert("L","P","LP");

        /** Seventh Row **/
        graph.insert("M","N","MN");

        System.out.println(graph.toString());
    }
} // end of Graph Class