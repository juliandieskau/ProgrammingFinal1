package edu.kit.informatik.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.informatik.model.escaperoutes.graph.Edge;
import edu.kit.informatik.model.escaperoutes.graph.FlowResult;
import edu.kit.informatik.model.escaperoutes.graph.FlowResultComparator;
import edu.kit.informatik.model.escaperoutes.graph.Graph;
import edu.kit.informatik.model.escaperoutes.graph.GraphComparator;
import edu.kit.informatik.presenter.input.Patterns;
import edu.kit.informatik.presenter.output.ErrorMessages;
import edu.kit.informatik.presenter.output.Messages;
import edu.kit.informatik.presenter.output.Result;
import edu.kit.informatik.presenter.output.Result.ResultType;
import edu.kit.informatik.view.CommandHandler;

/**
 * Command class that is used to list graphs or in the other case all results of the 
 * maximum-flow-computations for a certain graph.
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class ListCMD extends Command {

    /**
     * Constructor that gives the CommandHandler to the super-class "Command"
     * 
     * @param handler CommandHandler to hand
     */
    public ListCMD(CommandHandler handler) {
        super(handler);
    }

    @Override
    public String getRegex() {
        return "list";
    }

    @Override
    public Result execute(String[] input) {
        // Find out which of the two list commands shall be called:
        Result r;
        if (input.length == 1) {
            r = list();
        } else if (input.length == 2) {
            r = list(input[1]);
        } else {
            return new Result(String.format(ErrorMessages.INVALID_ARGUMENT_AMOUNT, "1 or 2", input.length), 
                    ResultType.FAILURE);
        }
        
        return r;
    }

    /**
     * Extends the execute command an lists all the available graphs in the memory.
     * Sorts the graphs using the GraphComparator.
     * 
     * @return Result   if the list command was successful
     */
    private Result list() {
        List<Graph> escapeRoutes = this.analyzer.getEscapeRoutes();
        if (escapeRoutes.isEmpty()) {
            return new Result(Messages.EMPTY, ResultType.SUCCESS);
        }
        Collections.sort(escapeRoutes, new GraphComparator());
        String output = "";
        for (Graph g : escapeRoutes) {
            output += g.getID();
            output += " ";
            output += String.valueOf(getNumOfVertices(g.getEdgeList()));
            if (!g.equals(escapeRoutes.get(escapeRoutes.size() - 1))) {
                output += "\n";
            }
        }
        return new Result(output, ResultType.SUCCESS);
    }
    
    /**
     * Lists all computed max-flow values for a given graph with id arg
     * Sorts them with the FlowResultComparator
     * 
     * @param arg   String  identifier of the graph to print the list for
     * @return      Result  message and resulttype of the operation
     */
    private Result list(String arg) {
        // test if arg is valid
        if (!isValidGraphID(arg)) {
            return new Result(String.format(ErrorMessages.INVALID_GRAPH_STRING, arg), ResultType.FAILURE);
        } else if (graphNonExistent(arg)) {
            return new Result(String.format(ErrorMessages.GRAPH_NOT_FOUND, arg), ResultType.FAILURE);
        }
        // list:
        Graph g = this.analyzer.returnGraphWithID(arg);
        List<FlowResult> flowResults = g.getFlowResults();
        if (flowResults.isEmpty()) {
            return new Result(Messages.EMPTY, ResultType.SUCCESS);
        }
        String out = "";
        Collections.sort(flowResults, new FlowResultComparator());
        for (FlowResult f : flowResults) {
            out += f.getResult() + " ";
            out += f.getFirst() + " ";
            out += f.getTarget();
            if (!f.equals(flowResults.get(flowResults.size() - 1))) {
                out += "\n";
            }
        }
        return new Result(out, ResultType.SUCCESS);
    }
    
    /**
     * Returns the number of vertices that are present in a list of edges
     * 
     * @param list  List<Edge>  list of edges to count the vertices on
     * @return      int         number of vertices
     */
    private int getNumOfVertices(List<Edge> list) {
        List<String> strList = new ArrayList<String>();
        for (Edge e : list) {
            if (!strList.contains(e.getFromAsString())) {
                strList.add(e.getFromAsString());
            } 
            if (!strList.contains(e.getToAsString())) {
                strList.add(e.getToAsString());
            }
        }
        return strList.size();
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
}
