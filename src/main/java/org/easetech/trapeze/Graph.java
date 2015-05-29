/**
 * 
 */
package org.easetech.trapeze;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anuj
 *
 */
public class Graph<T> {

	  /** Vector<Vertex> of graph verticies */
	  private List<Vertex<T>> verticies;

	  /** Vector<Edge> of edges in the graph */
	  private List<Edge<T>> edges;

	  /** The vertex identified as the root of the graph */
	  private Vertex<T> rootVertex;

	  /**
	   * Construct a new graph without any vertices or edges
	   */
	  public Graph() {
	    verticies = new ArrayList<Vertex<T>>();
	    edges = new ArrayList<Edge<T>>();
	  }

	  /**
	   * Are there any verticies in the graph
	   * 
	   * @return true if there are no verticies in the graph
	   */
	  public boolean isEmpty() {
	    return verticies.size() == 0;
	  }

	  /**
	   * Add a vertex to the graph
	   * 
	   * @param v
	   *          the Vertex to add
	   * @return true if the vertex was added, false if it was already in the graph.
	   */
	  public boolean addVertex(Vertex<T> v) {
	    boolean added = false;
	    if (verticies.contains(v) == false) {
	      added = verticies.add(v);
	    }
	    return added;
	  }

	  /**
	   * Get the vertex count.
	   * 
	   * @return the number of verticies in the graph.
	   */
	  public int size() {
	    return verticies.size();
	  }

	  /**
	   * Get the root vertex
	   * 
	   * @return the root vertex if one is set, null if no vertex has been set as
	   *         the root.
	   */
	  public Vertex<T> getRootVertex() {
	    return rootVertex;
	  }

	  /**
	   * Set a root vertex. If root does no exist in the graph it is added.
	   * 
	   * @param root -
	   *          the vertex to set as the root and optionally add if it does not
	   *          exist in the graph.
	   */
	  public void setRootVertex(Vertex<T> root) {
	    this.rootVertex = root;
	    if (verticies.contains(root) == false)
	      this.addVertex(root);
	  }

	  /**
	   * Get the given Vertex.
	   * 
	   * @param n
	   *          the index [0, size()-1] of the Vertex to access
	   * @return the nth Vertex
	   */
	  public Vertex<T> getVertex(int n) {
	    return verticies.get(n);
	  }

	  /**
	   * Get the graph verticies
	   * 
	   * @return the graph verticies
	   */
	  public List<Vertex<T>> getVerticies() {
	    return this.verticies;
	  }

	  /**
	   * Insert a directed, weighted Edge<T> into the graph.
	   * 
	   * @param from -
	   *          the Edge<T> starting vertex
	   * @param to -
	   *          the Edge<T> ending vertex
	   * @param cost -
	   *          the Edge<T> weight/cost
	   * @return true if the Edge<T> was added, false if from already has this Edge<T>
	   * @throws IllegalArgumentException
	   *           if from/to are not verticies in the graph
	   */
	  public boolean addEdge(Vertex<T> from, Vertex<T> to, int cost) throws IllegalArgumentException {
	    if (verticies.contains(from) == false)
	      throw new IllegalArgumentException("from is not in graph");
	    if (verticies.contains(to) == false)
	      throw new IllegalArgumentException("to is not in graph");

	    Edge<T> e = new Edge<T>(from, to, cost);
	    if (from.findEdge(to) != null)
	      return false;
	    else {
	      from.addEdge(e);
	      to.addEdge(e);
	      edges.add(e);
	      return true;
	    }
	  }

	  /**
	   * Insert a bidirectional Edge<T> in the graph
	   * 
	   * @param from -
	   *          the Edge<T> starting vertex
	   * @param to -
	   *          the Edge<T> ending vertex
	   * @param cost -
	   *          the Edge<T> weight/cost
	   * @return true if edges between both nodes were added, false otherwise
	   * @throws IllegalArgumentException
	   *           if from/to are not verticies in the graph
	   */
	  public boolean insertBiEdge(Vertex<T> from, Vertex<T> to, int cost)
	      throws IllegalArgumentException {
	    return addEdge(from, to, cost) && addEdge(to, from, cost);
	  }

	  /**
	   * Get the graph edges
	   * 
	   * @return the graph edges
	   */
	  public List<Edge<T>> getEdges() {
	    return this.edges;
	  }

	  /**
	   * Remove a vertex from the graph
	   * 
	   * @param v
	   *          the Vertex to remove
	   * @return true if the Vertex was removed
	   */
	  public boolean removeVertex(Vertex<T> v) {
	    if (!verticies.contains(v))
	      return false;

	    verticies.remove(v);
	    if (v == rootVertex)
	      rootVertex = null;

	    // Remove the edges associated with v
	    for (int n = 0; n < v.getOutgoingEdgeCount(); n++) {
	      Edge<T> e = v.getOutgoingEdge(n);
	      v.remove(e);
	      Vertex<T> to = e.getTo();
	      to.remove(e);
	      edges.remove(e);
	    }
	    for (int n = 0; n < v.getIncomingEdgeCount(); n++) {
	      Edge<T> e = v.getIncomingEdge(n);
	      v.remove(e);
	      Vertex<T> predecessor = e.getFrom();
	      predecessor.remove(e);
	    }
	    return true;
	  }

	  /**
	   * Remove an Edge<T> from the graph
	   * 
	   * @param from -
	   *          the Edge<T> starting vertex
	   * @param to -
	   *          the Edge<T> ending vertex
	   * @return true if the Edge<T> exists, false otherwise
	   */
	  public boolean removeEdge(Vertex<T> from, Vertex<T> to) {
	    Edge<T> e = from.findEdge(to);
	    if (e == null)
	      return false;
	    else {
	      from.remove(e);
	      to.remove(e);
	      edges.remove(e);
	      return true;
	    }
	  }

	  /**
	   * Clear the mark state of all verticies in the graph by calling clearMark()
	   * on all verticies.
	   * 
	   * @see Vertex#clearMark()
	   */
	  public void clearMark() {
	    for (Vertex<T> w : verticies)
	      w.clearMark();
	  }

	  /**
	   * Clear the mark state of all edges in the graph by calling clearMark() on
	   * all edges.
	   */
	  public void clearEdges() {
	    for (Edge<T> e : edges)
	      e.clearMark();
	  }

}
