package edu.kit.informatik.model.escaperoutes;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

import edu.kit.informatik.model.escaperoutes.graph.*;
import edu.kit.informatik.presenter.output.ErrorMessages;
import edu.kit.informatik.presenter.output.Messages;
import edu.kit.informatik.presenter.output.Result;
import edu.kit.informatik.presenter.output.Result.ResultType;

/**
 * main class for the logic; calls all the other elements needed for analyzing escape routes and provides
 * public methods that can be called by the "IO system" to compute output
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public final class EscapeRouteAnalyzer {

    private List<Graph> escapeRoutes;
    
    /**
     * public constructor that initializes the list of all Graphs or escape-routes/ -networks
     * that exist in the runtime of the program
     */
    public EscapeRouteAnalyzer() {
        escapeRoutes = new ArrayList<Graph>();
    }
    
    // ######## private methods ########
    
    
    
    /**
     * Returns {@value true} if the given Edge is existing in the given Graph
     * 
     * @param g Graph   Graph to search in
     * @param e Edge    Edge to search for
     * @return  boolean 
     */
    private boolean isEdgeInGraph(Graph g, Edge e) {
        for (Edge edge : g.getEdgeList()) {
            if (edge.equals(e)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Set up a ResidualCapacityGraph that has the flow 0 for all its Edges
     * and the same capacity as the graph that is used as input
     * Also adds the reversed edges for the flow algorithm, that are not in the main-graph
     * 
     * @return graph with flow 0 for all edges
     */
    private ResidualCapacityGraph initiateRCGraph(Graph g) {
        ResidualCapacityGraph rcg = new ResidualCapacityGraph();
        for (Edge e : g.getEdgeList()) {
            // ResicualCapacityEdges are already instantiated with flow = 0!
            ResidualCapacityEdge rce = new ResidualCapacityEdge(e.getFromAsString(), 
                    e.getToAsString(), e.getCapacity());
            rcg.addEdge(rce);
            rcg.addEdge(rce.getReversedEdge());
        }
        return rcg;
    }
    
    /**
     * Breadth-First-Search for an Optimization-Path inside the ResidualCapacityGraph
     * Only use if the Graph is not empty
     * Please check if it returns null, then there is no Optimization-path and the Algorithm has to stop!
     * 
     * 
     * @param graph ResidualCapacityGraph   Graph to search for the path on
     * @param first Vertex                  Start Vertex to search from
     * @param last  Vertex                  Vertex the Search needs to stop on
     * @return      Path                    Optimization-Path
     */
    private Path findOptimizationPath(ResidualCapacityGraph graphIN, String firstVertex, String lastVertex) {
        // initiate Lists for use
        Queue<String> nextVertices = new LinkedList<String>();
        List<String> seenVertices = new ArrayList<String>();
        List<Path> pathList = new ArrayList<Path>();
        seenVertices.add(firstVertex);
        nextVertices.offer(firstVertex);
        
        // create a temporary graph, that has all edges of graphIN that have a residual capacity greater than zero
        ResidualCapacityGraph graph = new ResidualCapacityGraph();
        for (ResidualCapacityEdge e : graphIN.getEdgeList()) {
            if (e.getResidualCapacity() > 0) {
                graph.addEdge(e.getFromAsString(), e.getToAsString(), 
                        e.getCapacity(), e.getFlow(), e.getResidualCapacity());
            }
        }
        
        // run this loop until path is found or until there is no path to the last Vertex
        while (!nextVertices.isEmpty()) {
            
            // get the first Vertice from the next-Queue
            String v = nextVertices.poll();
            
            // search through all edges in the graph
            for (ResidualCapacityEdge edge : graph.getEdgeList()) {
                // only use those edges, that have v as the "from"-Vertice
                if (edge.getFromAsString().equals(v)) {
                    // if v is the firstVertex, add all edges in this loop as a path to the pathList! 
                    // => only done in the first while-iteration
                    if (v.equals(firstVertex)) {
                        Path newPath = new Path();
                        newPath.addEdge(edge.getCopy());
                        pathList.add(newPath);
                        if (edge.getToAsString().equals(lastVertex)) {
                            return newPath;
                        }
                    }
                    // If the edge has a "to"-Vertex that hasn't been on yet, add it to the list:
                    String w = edge.getToAsString();
                    if (!seenVertices.contains(w)) {
                        // mark as "been on" and add to the queue
                        seenVertices.add(w);
                        nextVertices.offer(w);
                        
                        // create temporary pathList to avoid ConcurrentModificationException
                        List<Path> tmpPaths = new ArrayList<Path>();
                        // Add edge to a path and that to the pathList if a path exists that 
                        // ends in the "from"-Vertex of the edge (equals v, checked beforehand)
                        for (Path path : pathList) {
                            if (path.getLastEdge().getToAsString().equals(v)) {
                                Path newPath = path.getCopy();
                                newPath.addEdge(edge);
                                // if the edge ends in the lastVertex, return the newPath;
                                // it starts in firstVertex and therefore is (on of) the fastest path(s)
                                if (edge.getToAsString().equals(lastVertex)) {
                                    return newPath;
                                }
                                tmpPaths.add(newPath);
                            }
                        }
                        // add all paths in tmpPaths to the pathList
                        for (Path p : tmpPaths) {
                            pathList.add(p);
                        }
                    }
                }
            }
        }
        // if no correct path is found, return null:
        return null;
    }
    
    /**
     * Used in the Edmonds-Karp-Algorithm to compute the Residual-Capacity-Network for the next loop iteration
     * doesn't remove edges with residualCapacity == 0, that is done inside the algorithm itself temporarily
     * 
     * @param graph ResidualCapacityGraph graph to be computed to be the return value
     * @return      ResidualCapacityGraph with new components
     */
    private ResidualCapacityGraph computeNextResidualCapacityGraph(ResidualCapacityGraph graph) {
        ResidualCapacityGraph nextGraph = new ResidualCapacityGraph();
        
        // might have edges with residualCapacity = 0 -> ignore those in the path finding
        for (ResidualCapacityEdge edge : graph.getEdgeList()) {
            int capacity = edge.getCapacity(); // stays the same -> final
            int flow = edge.getFlow(); // stays the same
            // residualCapacity = capacity - flow <-> might have changed in the process before
            int residualCapacity = capacity - flow;
            
            String from = edge.getFromAsString();
            String to = edge.getToAsString();
            ResidualCapacityEdge nextEdge = new ResidualCapacityEdge(from, to, capacity, flow, residualCapacity);
            nextGraph.addEdge(nextEdge);
        }
        
        return nextGraph;
    }
    
    /**
     * Search a path for the minimum residualCapacity of all edges
     * 
     * @param p Path    Path to search on
     * @return  int     minValue = result
     */
    private int findMinResidualCapacity(Path p) {
        int minValue = 0;
        List<ResidualCapacityEdge> pList = p.getEdgeList();
        for (ResidualCapacityEdge e : pList) {
            if (minValue == 0 || e.getResidualCapacity() < minValue) {
                minValue = e.getResidualCapacity();
            } 
        }
        return minValue;
    }
    
    // ######## public methods ########
    
    /**
     * Getter for the EscapeRoutes-List
     * 
     * @return List<Graph> List of all graphs/ networks
     */
    public List<Graph> getEscapeRoutes() {
        return this.escapeRoutes;
    }
    
    /**
     * Edmonds-Karp-Algorithm 
     * Computes the maximum Flow value of a given Graph from the defined first Vertex to the defined last Vertex
     * 
     * @param g             Graph   To compute the maximum flow for
     * @param firstVertex   String  id of the start-Vertex
     * @param lastVertex    String  id of the final-Vertex
     * @return maxFlow      int     maximum flow on g from first- to lastVertex
     */
    public long maximumFlow(Graph g, String firstVertex, String lastVertex) {
        // check if already computed and directly output it
        boolean resultExists = g.containsFlowResult(firstVertex, lastVertex);
        if (resultExists) {
            return g.getFlowResult(firstVertex, lastVertex);
        }
        // compute the maximum flow for given parameters
        ResidualCapacityGraph graph = initiateRCGraph(g);
        boolean repeat = true; // if p == null -> repeat = false
        do {
            graph = computeNextResidualCapacityGraph(graph);
            Path p = findOptimizationPath(graph, firstVertex, lastVertex);
            if (p != null) {
                int minCapacity = findMinResidualCapacity(p);
                // change flow (for all edges in path) of edges in graph
                // also change flow for all reversed edges
                for (ResidualCapacityEdge e : p.getEdgeList()) {
                    graph.addEdgeFlow(e.getID(), minCapacity);
                    graph.addEdgeFlow(e.getReversedID(), -minCapacity);
                }
            } else {
                // p == null <-> no optimization-path in the graph
                repeat = false;
            }
        } while (repeat);
        
        // maximum flow is equal to sum of flows of the edges coming out of firstVertex
        // which is the same as the sum of flows of the edges toward the lastVertex
        long maxFlow = 0;
        for (ResidualCapacityEdge e : graph.getEdgeList()) {
            if (firstVertex.equals(e.getFromAsString())) {
                maxFlow += (long) e.getFlow();
            }
        }
        // save max flow
        g.saveFlowResult(maxFlow, firstVertex, lastVertex);
        
        // return the sum; if there is no path from firstVertex to lastVertex maxFlow should be 0 by default
        return maxFlow;
    }
    
    /**
     * Search the List for a given graph-identifier and return it
     * 
     * @param  id   String  id of the graph to give back
     * @return      Graph   with given identifier String
     */
    public Graph returnGraphWithID(String id) {
        for (Graph g : escapeRoutes) {
            if (g.getID().equals(id)) {
                return g;
            }
        }
        return null;
    }
    
    /**
     * Add a new Graph that represents one EscapeRoute to the List of all.
     * Tests if a graph with the given identifier already exists and if not adds the new one.
     * 
     * @param identifier  String  Given Name for the Graph.
     * @return            Result  gives the output for the CommandSystem
     */
    public Result addEscapeRoute(String identifier) {
        // search if ID is already existent, if not add the new one
        if (returnGraphWithID(identifier) == null) {
            Graph g = new Graph(identifier);
            escapeRoutes.add(g);
            return new Result(String.format(Messages.GRAPH_ADDED, identifier), ResultType.SUCCESS);
        }
        return new Result(String.format(ErrorMessages.DUPLICATE_GRAPH, identifier), ResultType.FAILURE);
    }
    
    /** 
     * Add an Edge to a Graph with the given identifier or change its capacity if the edge already exists
     * Returns a failed result, if the graph with graphID does not exist
     * 
     * @param graphID   String  identifier of the graph to add an edge to
     * @param newEdge   Edge    edge to add to the graph
     * @return          Result  output for the CommandSystem
     */
    public Result addEdgeToGraph(String graphID, Edge newEdge) {
        String edgeOut = newEdge.getFromAsString() + newEdge.getCapacity() + newEdge.getToAsString();
        // check if the graph is existent
        Graph g = returnGraphWithID(graphID);
        if (g != null) {
            // check if the edge is already there and change its capacity
            if (isEdgeInGraph(g, newEdge)) {
                // cause isEdgeInGraph() is true, e can't be null!
                Edge e = g.getEdge(newEdge.getFromAsString(), newEdge.getToAsString());
                e.changeCapacity(newEdge.getCapacity());
                g.emptyFlowResults();
                return new Result(String.format(Messages.CAPACITY_CHANGED, edgeOut, graphID), ResultType.SUCCESS);
            }
            // add the new Edge
            g.addEdge(newEdge);
            g.emptyFlowResults();
            return new Result(String.format(Messages.EDGE_ADDED, edgeOut, graphID), ResultType.SUCCESS);
        }
        return new Result(String.format(ErrorMessages.GRAPH_NOT_FOUND, graphID), ResultType.FAILURE);
    }
    
    /**
     * Generate an edge with the given parameters to add to a Graph
     * 
     * @param fromID    String  ID for the Vertex that the Edge starts from
     * @param toID      String  ID for the Vertex the from-Vertex is pointing towards
     * @param capacity  int     maximum number of persons that are able to use the edge/path in a minute   
     * @return          Edge    with all three params as it's values
     */
    public Edge generateEdge(String fromID, String toID, int capacity) {
        return new Edge(fromID, toID, capacity);
    }
}
