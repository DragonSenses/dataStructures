
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
 */
public interface Graph<V,E> {

}