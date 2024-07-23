package edu.kit.informatik.presenter;

import edu.kit.informatik.presenter.output.Result.ResultType;
import edu.kit.informatik.view.CommandHandler;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.model.escaperoutes.graph.*;
import edu.kit.informatik.presenter.output.ErrorMessages;
import edu.kit.informatik.presenter.output.Messages;
import edu.kit.informatik.presenter.output.Result;
import edu.kit.informatik.presenter.input.Patterns;
import edu.kit.informatik.presenter.input.Parser;

/**
 * Command class for the add-command
 * Checks the arguments and adds an edge to a graph or a graph to the list, given the input
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class Add extends Command {
    
    /**
     * Constructor, that sets the CommandHandler to be called, which also holds the EscapeRouteAnalyzer
     * 
     * @param h CommandHandler where the commands are called from and where the output is processed
     */
    public Add(CommandHandler h) {
        super(h);
    }

    @Override
    public String getRegex() {
        return "add";
    }

    @Override
    public Result execute(String[] input) {
        if (input.length != 3) {
            return new Result(String.format(ErrorMessages.INVALID_ARGUMENT_AMOUNT, 3, input.length), 
                    ResultType.FAILURE);
        }
        
        String one = input[1];
        String two = input[2];
        
        Result r;
        if (isOneSubargument(two)) {
            r = execAddEdgeToGraph(one, two);
        } else {
            r = execAddGraph(one, two);
        }
        return r;
    }

    /**
     * Addition to the execute method that handles the add-command 
     * in the case of adding a network/ graph to the memory
     * 
     * @param inputOne  String  ID of the new graph
     * @param inputTwo  String  list of all edges in String-format
     * @return          Result  of the command
     */
    private Result execAddGraph(String inputOne, String inputTwo) {
        if (!isValidGraphID(inputOne)) {
            return new Result(String.format(ErrorMessages.INVALID_GRAPH_STRING, inputOne), ResultType.FAILURE);
        } else if (!isNewGraphID(inputOne)) {
            return new Result(String.format(ErrorMessages.DUPLICATE_GRAPH, inputOne), ResultType.FAILURE);
        }
        // inputOne is a valid, not already existing id for a graph
        // following line is null if inputTwo is invalid or instead parses to a valid graph:
        List<Edge> edgeList = Parser.parseGraph(inputTwo);
        if (edgeList == null) {
            return new Result(String.format(ErrorMessages.INVALID_EDGE_LIST, inputTwo), ResultType.FAILURE);
        }
        // test if the graph meets the normal standards:
        if (!containsParallelEdge(edgeList)) {
            return new Result(ErrorMessages.CONTAINS_PARALLEL_CONTRADIRECTIONAL_EDGE, ResultType.FAILURE);
        } else if (!hasStartAndTarget(edgeList)) {
            return new Result(ErrorMessages.INVALID_START_OR_TARGET_VERTEX, ResultType.FAILURE);
        } 
        
        this.analyzer.addEscapeRoute(inputOne);
        for (Edge e : edgeList) {
            this.analyzer.addEdgeToGraph(inputOne, e);
        }
        return new Result(String.format(Messages.GRAPH_ADDED, inputOne), ResultType.SUCCESS);
    }
    
    /**
     * Addition to the execute method that handles the add command 
     * in the case of adding a single edge to an existing graph
     * 
     * @param inputOne  String  ID of the new graph
     * @param inputTwo  String  list of all edges in String-format
     * @return          Result  of the command
     */
    private Result execAddEdgeToGraph(String inputOne, String inputTwo) {
        Edge e = Parser.parseEdge(inputTwo);
        if (e == null) {
            return new Result(String.format(ErrorMessages.INVALID_EDGE_STRING, inputTwo), ResultType.FAILURE);
        }
        if (!isValidGraphID(inputOne)) {
            return new Result(String.format(ErrorMessages.INVALID_GRAPH_STRING, inputOne), ResultType.FAILURE);
        } else if (isNewGraphID(inputOne)) {
            return new Result(String.format(ErrorMessages.INVALID_GRAPH_STRING, inputOne), ResultType.FAILURE);
        }
        // => the graph with the given ID exists
        // copy the edgeList to tempList so that e is only added if the below tests are successful
        // test if the new graph still meets the standards:
        List<Edge> edgeList = this.analyzer.returnGraphWithID(inputOne).getEdgeList();
        List<Edge> tempList = new ArrayList<Edge>();
        for (Edge f : edgeList) {
            tempList.add(f);
        }
        tempList.add(e);
        if (!containsParallelEdge(tempList)) {
            return new Result(ErrorMessages.CONTAINS_PARALLEL_CONTRADIRECTIONAL_EDGE, ResultType.FAILURE);
        } else if (!hasStartAndTarget(tempList)) {
            return new Result(ErrorMessages.INVALID_START_OR_TARGET_VERTEX, ResultType.FAILURE);
        } 
        
        Result r = this.analyzer.addEdgeToGraph(inputOne, e);
        return r;
    }
    
    /**
     * Returns {@value true} if the String represents a valid identifier for an escape-route-network, 
     * else {@value false} 
     * 
     * @param inputOne  String  identifier to test
     * @return          boolean if valid
     */
    private boolean isValidGraphID(String inputOne) {
        if (inputOne.matches(Patterns.VALID_NETWORK)) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns {@value true} if the graph-ID is not existent , 
     * else {@value false}
     * 
     * @param inputOne  String  identifier to test
     * @return          boolean if id is not used in the memory
     */
    private boolean isNewGraphID(String inputOne) {
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
     * Returns {@value true} if inputTwo is only one edge (of right or wrong input)
     * 
     * @param inputTwo  String  to test
     * @return          boolean if one argument only
     */
    private boolean isOneSubargument(String inputTwo) {
        String arguments[] = inputTwo.split(";", -1);
        if (arguments.length == 1) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns {@value true} if the edge-list has a valid start- and target-vertex
     * 
     * @param edgeList  List<Edge>  list of all edges of the graph to test for
     * @return          boolean     if it has the vertices
     */
    private boolean hasStartAndTarget(List<Edge> edgeList) {
        // initialize variables
        boolean startExists = false;
        boolean targetExists = false;
        List<String> fromList = new ArrayList<String>();
        List<String> toList = new ArrayList<String>();
        for (Edge e : edgeList) {
            fromList.add(e.getFromAsString());
            toList.add(e.getToAsString());
        }
        
        // test if start or target vertex exist:
        // start: if for a vertex, that is a start vertex, no edges towards it exist, it is a valid start
        // target: opposite
        for (String s : fromList) {
            boolean to = false;
            for (Edge e : edgeList) {
                if (e.getToAsString().equals(s)) {
                    to = true;
                }
            }
            if (!to) {
                startExists = true;
            }
        }
        for (String s : toList) {
            boolean from = false;
            for (Edge e : edgeList) {
                if (e.getFromAsString().equals(s)) {
                    from = true;
                }
            }
            if (!from) {
                targetExists = true;
            }
        }
        if (startExists && targetExists) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns {@value true} if there is (at least) a pair of two edges that are parallel to each other
     * and one is the reversed other, {@value false} if not
     * 
     * @param edgeList  List<Edge>  list of all edges of the graph to test for
     * @return          boolean     if it contains a parallel edge pair
     */
    private boolean containsParallelEdge(List<Edge> edgeList) {
        for (Edge e : edgeList) {
            for (Edge f : edgeList) {
                if (e.getFromAsString().equals(f.getToAsString()) && e.getToAsString().equals(f.getFromAsString())) {
                    return false;
                }
            }
        }
        return true;
    }
}
