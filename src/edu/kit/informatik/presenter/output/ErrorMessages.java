package edu.kit.informatik.presenter.output;

/**
 *  Class that holds constant String values that are used for error-message-output.
 * 
 *  @author Julian Dieskau
 *  @version 1.0
 */
public final class ErrorMessages {

    /** If a command was given, that is not valid: */
    public static final String COMMAND_NOT_FOUND 
            = "this command is unknown, please use another one.";
    /** If a command was given, that is valid but with the wrong amount of arguments: */
    public static final String INVALID_ARGUMENT_AMOUNT
            = "invalid amount of arguments, %s expected, %d given.";
    /** If a graph with the given identifier is not existent in the List: */
    public static final String GRAPH_NOT_FOUND 
            = "there is no graph called %s, please enter an existing one.";
    /** If a graph with the given identifier already exists: */
    public static final String DUPLICATE_GRAPH 
            = "the graph \"%s\" already exists.";
    /** If a given character is invalid for a certain operation: */
    public static final String INVALID_CHARACTER
            = "the given character %s is invalid!";
    /** If a given String does not match a certain rule for edges: */
    public static final String INVALID_EDGE_STRING 
            = "the given String \"%s\" is not a valid edge-representation!";
    /** If a given String does not match a certain rule for graphs: */
    public static final String INVALID_GRAPH_STRING 
            = "the given String \"%s\" is an invalid identifier for graphs, please enter a correct one!";
    /** If a given String does not match a certain rule for parsing to a Graph: */
    public static final String INVALID_EDGE_LIST 
            = "the given String \"%s\" is an invalid list of edges and could not be parsed to a graph.";
    /** If a given vertex-id does not match the valid start-vertices: */
    public static final String INVALID_START_VERTEX
            = "the given String \"%s\" is not a valid start-vertex for computing the flow.";
    /** If a given vertex-id does not match the valid target-vertices: */
    public static final String INVALID_TARGET_VERTEX 
            = "the given String \"%s\" is not a valid target-vertex for computing the flow.";
    /** If a given graph-String has an edge twice: */
    public static final String DOUBLE_EDGE 
            = "the given String \"%s\" is invalid: Edge %s is given twice!";
    /** If a given start-vertex and target-vertex are equal: */
    public static final String VERTICES_EQUAL 
            = "the start-vertex is equal to the target-vertex.";
    /** If a graph does not contain a valid start- and target-vertex: */
    public static final String INVALID_START_OR_TARGET_VERTEX 
            = "the graph does not contain a valid start- and target-vertex, action could not be performed.";
    /** If a graph has an edge that is parallel to another edge but with reversed direction: */
    public static final String CONTAINS_PARALLEL_CONTRADIRECTIONAL_EDGE 
            = "the graph contains an edge that is parallel and contradirectional to another edge of the graph.";
    
    private ErrorMessages() {
        throw new IllegalAccessError();
    }
}
