package edu.kit.informatik.presenter;

import java.util.List;
import java.util.Collections;

import edu.kit.informatik.model.escaperoutes.graph.Edge;
import edu.kit.informatik.model.escaperoutes.graph.EdgeComparator;
import edu.kit.informatik.model.escaperoutes.graph.Graph;
import edu.kit.informatik.presenter.input.Patterns;
import edu.kit.informatik.presenter.output.ErrorMessages;
import edu.kit.informatik.presenter.output.Result;
import edu.kit.informatik.presenter.output.Result.ResultType;
import edu.kit.informatik.view.CommandHandler;

/**
 * Command class that is used to print all edges of a certain graph to the output.
 * Sorts the edges using the EdgeComparator.
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class Print extends Command {

    /**
     * Constructor that hands the CommandHandler to the super-class
     * 
     * @param handler CommandHandler to hand
     */
    public Print(CommandHandler handler) {
        super(handler);
    }

    @Override
    public String getRegex() {
        return "print";
    }

    @Override
    public Result execute(String[] input) {
        if (input.length != 2) {
            return new Result(String.format(ErrorMessages.INVALID_ARGUMENT_AMOUNT, 2, input.length), 
                    ResultType.FAILURE);
        }
        String identifier = input[1];
        // test identifier for correctness
        if (!isValidGraphID(identifier)) {
            return new Result(String.format(ErrorMessages.INVALID_GRAPH_STRING, identifier), ResultType.FAILURE);
        } else if (graphNonExistent(identifier)) {
            return new Result(String.format(ErrorMessages.GRAPH_NOT_FOUND, identifier), ResultType.FAILURE);
        }
        // get EdgeList of the graph and sort it alphabetically with the edgeComparator
        List<Edge> edgeList = this.analyzer.returnGraphWithID(identifier).getEdgeList();
        Collections.sort(edgeList, new EdgeComparator());
        // creates a String from the sorted edges, each in one line in the typical format:
        String output = generateString(edgeList);
        return new Result(output, ResultType.SUCCESS);
    }
    
    /**
     * Prints a list to the console
     * 
     * @param list  List<Edge>  list of all edges of a graph to iterate over
     * @return      String      formatted list of edges
     */
    private String generateString(List<Edge> list) {
        String str = "";
        for (Edge e : list) {
            str += e.getFromAsString();
            str += String.valueOf(e.getCapacity());
            str += e.getToAsString();
            if (!e.equals(list.get(list.size() - 1))) {
                str += "\n";
            }
        }
        return str;
    }
    
    /**
     * Returns {@value true} if the String represents a valid identifier for an escape-route-network, 
     * else {@value false} 
     * 
     * @param inputOne  String  input[1]
     * @return          boolean as above
     */
    private boolean isValidGraphID(String id) {
        if (id.matches(Patterns.VALID_NETWORK)) {
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
    private boolean graphNonExistent(String id) {
        List<Graph> currentGraphs = this.analyzer.getEscapeRoutes();
        if (currentGraphs.isEmpty()) {
            return true;
        } 
        // implicit else, if one graph has the same ID as the given input, return {@value false}
        for (int i = 0; i < currentGraphs.size(); i++) {
            Graph g = currentGraphs.get(i);
            if (g.getID().equals(id)) {
                return false;
            }
        }
        return true;
    }
}
