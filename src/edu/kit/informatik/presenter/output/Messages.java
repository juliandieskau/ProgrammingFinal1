package edu.kit.informatik.presenter.output;

/**
 * Defines all Messages that are used as Output for successful operations
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public final class Messages {

    /** When an edge was added to a graph: */
    public static final String EDGE_ADDED = "Added new section %s to escape network %s.";
    /** When a graph was added to the list of graphs: */
    public static final String GRAPH_ADDED = "Added new escape network with identifier %s.";
    /** When the capacity of an exisiting edge was changed: */
    public static final String CAPACITY_CHANGED = "Added new section %s to escape network %s.";
    /** When a given String as graph-identifier has not been used before: */
    public static final String GRAPH_ID_NEW = "The given graph-identifier is new.";
    /** When a given String as graph-identifier already exists: */
    public static final String GRAPH_ID_USED = "a graph with the given identifier already exists";
    /** When the Application shall be quit: */
    public static final String QUIT = "Quit.";
    /** When there is no content to be printed: */
    public static final String EMPTY = "EMPTY";
    
    private Messages() {
        throw new IllegalAccessError();
    }

}
