package edu.kit.informatik.model.escaperoutes.graph;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a graph that consists of ResidualCapacityEdges and is used inside the Edmonds-Karp-Algorithm
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class ResidualCapacityGraph {

    /**
     * List of ResidualCapacityEdges that represents the Graph
     */
    List<ResidualCapacityEdge> edges;
    
    /**
     * Public Constructor, calls super to Graph constructor
     */
    public ResidualCapacityGraph() {
        edges = new ArrayList<ResidualCapacityEdge>();
    }

    /**
     * Getter for the list of all edges
     * 
     * @return List<ResidualCapacityEdge> all edges of the graph object
     */
    public List<ResidualCapacityEdge> getEdgeList() {
        return this.edges;
    }
    
    /**
     * add a new Edge to the end of the Graph-List (order is unimportant)
     * 
     * @param e Edge to add
     */
    public void addEdge(ResidualCapacityEdge e) {
        edges.add(e);
    }
    
    /**
     * add a new Edge to the Graph with the parameters for normal Edges without flow etc
     * 
     * @param fromID    String  ID for the Vertex that the Edge starts from
     * @param toID      String  ID for the Vertex the from-Vertex is pointing towards
     * @param capacity  int     maximum number of persons that are able to use the edge/path in a minute  
     */
    public void addEdge(String fromID, String toID, int capacity) {
        edges.add(new ResidualCapacityEdge(fromID, toID, capacity));
    }
    
    /**
     * add a new Edge to the Graph with all parameters 
     * 
     * @param fromID            String  ID for the Vertex that the Edge starts from
     * @param toID              String  ID for the Vertex the from-Vertex is pointing towards
     * @param capacity          int     maximum number of persons that are able to use the edge/path in a minute
     * @param flow              int     number of persons that are able to go through the edge atm
     * @param residualCapacity  int     capacity-flow
     */
    public void addEdge(String fromID, String toID, int capacity, int flow, int residualCapacity) {
        edges.add(new ResidualCapacityEdge(fromID, toID, capacity, flow, residualCapacity));
    }
    
    /**
     * Changes the capacity of the edge in the graph with id edgeID by the given amount
     * 
     * @param edgeID String     id that has to equal the id of an edge (given by their from and to)
     * @param amount int        amount to change the flow by
     */
    public void addEdgeFlow(String edgeID, int amount) {
        for (ResidualCapacityEdge e : this.edges) {
            if (e.getID().equals(edgeID)) {
                e.changeFlowBy(amount);
                return;
            }
        }
    }
}
