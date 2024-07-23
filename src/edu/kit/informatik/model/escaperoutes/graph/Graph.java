package edu.kit.informatik.model.escaperoutes.graph;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a Graph that is used for escape routes and stores edges with their Vertex-IDs and capacity.
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class Graph {
    
    private List<Edge> edges;
    private String identifier;
    private List<FlowResult> maxFlowResults;
    
    /**
     * Public Constructor that takes an identifier and also initializes both Edge- an maxFlowResult-Lists
     * 
     * @param identifier    String  ID of the graph
     */
    public Graph(String identifier) {
        edges = new ArrayList<Edge>();
        maxFlowResults = new ArrayList<FlowResult>();
        this.identifier = identifier;
    }
    
    /**
     * Getter for identifier
     * 
     * @return String   identifier
     */
    public String getID() {
        return this.identifier;
    }
    
    /**
     * Getter for the list of all edges
     * 
     * @return List<Edge> all edges of the graph object
     */
    public List<Edge> getEdgeList() {
        return this.edges;
    }
    
    /**
     * Returns an Edge if it exists in the graph, null if not
     * 
     * @param from  
     * @param to    
     * @return  Edge with from and to
     */
    public Edge getEdge(String from, String to) {
        for (Edge e : edges) {
            if (e.getFromAsString().equals(from) && e.getToAsString().equals(to)) {
                return e;
            }
        }
        return null;
    }
    
    /**
     * Add a new Edge to the Graph and delete all FlowResults
     * 
     * @param e Edge
     */
    public void addEdge(Edge e) {
        edges.add(e);
    }
    
    /**
     * Add a new Edge to the Graph
     * 
     * @param fromID    String  ID for the Vertex that the Edge starts from
     * @param toID      String  ID for the Vertex the from-Vertex is pointing towards
     * @param capacity  int     maximum number of persons that are able to use the edge/path in a minute  
     */
    public void addEdge(String fromID, String toID, int capacity) {
        edges.add(new Edge(fromID, toID, capacity));
    }
    
    /**
     * Saves a max-flow-computation result in the list of results of this graph,
     * together with the first- and last-Vertex of the computation
     * 
     * @param result        long    max flow
     * @param firstVertex   String  ID of the first-/ start-Vertex of the flow-computation
     * @param targetVertex  String  ID of the last-/ target-Vertex of the flow-computation
     */
    public void saveFlowResult(long result, String firstVertex, String targetVertex) {
        FlowResult res = new FlowResult(result, firstVertex, targetVertex);
        maxFlowResults.add(res);
    }
    
    /**
     * Public method that clears the entire list of flow results.
     */
    public void emptyFlowResults() {
        this.maxFlowResults.clear();
    }
    
    /**
     * Tests if a computation for a part of a graph has already been computed
     * Checks the list of results if one with the ID of first- and last-Vertex is existing
     * 
     * @param firstVertex   String  ID of the first-/ start-Vertex of the flow-computation
     * @param targetVertex  String  ID of the last-/ target-Vertex of the flow-computation
     * @return              boolean {@value true} if is existing
     */
    public boolean containsFlowResult(String firstVertex, String targetVertex) {
        FlowResult res = new FlowResult(0, firstVertex, targetVertex);
        if (this.maxFlowResults.contains(res)) {
            return true;
        }
        return false;
    }
    
    /**
     * Getter for the max flow of a graph-part, accessed by first- and target-Vertex
     * 
     * @param firstVertex   String  ID of the first-/ start-Vertex of the flow-computation
     * @param targetVertex  String  ID of the last-/ target-Vertex of the flow-computation
     * @return flowResult   long    result of the computation
     */
    public long getFlowResult(String firstVertex, String targetVertex) {
        for (FlowResult f : this.maxFlowResults) {
            if (f.equals(new FlowResult(0, firstVertex, targetVertex))) {
                return f.getResult();
            }
        }
        return 0;
    }
    
    /**
     * Getter for the List of all flow-results of this graph-object
     * 
     * @return List<FlowResult> as above
     */
    public List<FlowResult> getFlowResults() {
        return this.maxFlowResults;
    }
}
