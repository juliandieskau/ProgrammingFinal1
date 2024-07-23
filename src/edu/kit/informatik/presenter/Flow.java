package edu.kit.informatik.presenter;

import java.util.List;

import edu.kit.informatik.model.escaperoutes.graph.Edge;
import edu.kit.informatik.model.escaperoutes.graph.Graph;
import edu.kit.informatik.presenter.input.Patterns;
import edu.kit.informatik.presenter.output.ErrorMessages;
import edu.kit.informatik.presenter.output.Result;
import edu.kit.informatik.presenter.output.Result.ResultType;
import edu.kit.informatik.view.CommandHandler;

/**
 * Command class for the command, that computes the maximum flow in a graph
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class Flow extends Command {

    /**
     * Constructor that sets the CommandHandler for this class
     * 
     * @param handler   CommandHandler to give to super-class
     */
    public Flow(CommandHandler handler) {
        super(handler);
    }

    @Override
    public String getRegex() {
        return "flow";
    }

    @Override
    public Result execute(String[] input) {
        if (input.length != 4) {
            return new Result(String.format(ErrorMessages.INVALID_ARGUMENT_AMOUNT, 4, input.length), 
                    ResultType.FAILURE);
        }
        // get all parameters as single String objects
        String graphID = input[1];
        String startVertex = input[2];
        String targetVertex = input[3];
        // test all parameters for correctness TODO test start and target vertices!
        if (!isValidGraphID(graphID)) {
            return new Result(String.format(ErrorMessages.INVALID_GRAPH_STRING, graphID), ResultType.FAILURE);
        } else if (graphNonExistent(graphID)) {
            return new Result(String.format(ErrorMessages.GRAPH_NOT_FOUND, graphID), ResultType.FAILURE);
        } 
        Graph g = this.analyzer.returnGraphWithID(graphID);
        if (!validStartVertex(startVertex, g)) {
            return new Result(String.format(ErrorMessages.INVALID_START_VERTEX, startVertex), ResultType.FAILURE);
        } else if (!validTargetVertex(targetVertex, g)) {
            return new Result(String.format(ErrorMessages.INVALID_TARGET_VERTEX, targetVertex), ResultType.FAILURE);
        } else if (startVertex.equals(targetVertex)) {
            // it is tested, that the 
            return new Result(ErrorMessages.VERTICES_EQUAL, ResultType.FAILURE);
        }
        // parameters are correct, compute flow
        long flow = this.analyzer.maximumFlow(g, startVertex, targetVertex);
        
        return new Result(String.valueOf(flow), ResultType.SUCCESS);
    }

    /**
     * Returns {@value true} if the String represents a valid identifier for an escape-route-network, 
     * else {@value false} 
     * 
     * @param inputOne  String  input[1]
     * @return          boolean as above
     */
    private boolean isValidGraphID(String inputOne) {
        if (inputOne.matches(Patterns.VALID_NETWORK)) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns {@value true} if the graph-ID is non-existent , 
     * else {@value false}
     * 
     * @param inputOne  String  input[1]
     * @return          boolean as above
     */
    private boolean graphNonExistent(String inputOne) {
        List<Graph> currentGraphs = this.analyzer.getEscapeRoutes();
        if (currentGraphs.isEmpty()) {
            return true;
        } 
        // implicit else, if one graph has the same ID as the given input, return {@value false}
        for (int i = 0; i < currentGraphs.size(); i++) {
            Graph g = currentGraphs.get(i);
            if (g.getID().equals(inputOne)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns {@value true} if the startVertex is valid, 
     * else {@value false}
     * 
     * @param startVertex String  input[2]
     * @return            boolean as above
     */
    private boolean validStartVertex(String startVertex, Graph g) {
        boolean exists = false;
        boolean to = false;
        for (Edge e : g.getEdgeList()) {
            // test if one of the vertices
            if (e.getFromAsString().equals(startVertex)) {
                exists = true;
            }
            // test if one of the edges points towards it
            if (e.getToAsString().equals(startVertex)) {
                to = true;
            }
        }
        if (exists && !to) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns {@value true} if the targetVertex is valid, 
     * else {@value false}
     * 
     * @param targetVertex  String  input[3]
     * @return              boolean as above
     */
    private boolean validTargetVertex(String targetVertex, Graph g) {
        boolean exists = false;
        boolean from = false;
        for (Edge e : g.getEdgeList()) {
            // test if one of the vertices
            if (e.getToAsString().equals(targetVertex)) {
                exists = true;
            }
            // test if one of the edges points towards it
            if (e.getFromAsString().equals(targetVertex)) {
                from = true;
            }
        }
        if (exists && !from) {
            return true;
        }
        return false;
    }
}
