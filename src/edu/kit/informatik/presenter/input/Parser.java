package edu.kit.informatik.presenter.input;

import java.util.ArrayList;
import java.util.List;

import edu.kit.informatik.model.escaperoutes.graph.Edge;

/**
 * Class that provides static methods to parse edges and graphs from the input-representation to the
 * form the program can work with 
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public final class Parser {

    private Parser() {
        throw new IllegalAccessError();
    }

    /**
     * Returns an Edge of the given String if matches a correct Edge, null if not
     * 
     * @param edge  String  argument that needs to be correct
     * @return      Edge    when edge is valid
     */
    public static Edge parseEdge(String edge) {
        // parse edge to a char-array
        char[] chars = edge.toCharArray();
        String w = "";
        String z = "";
        String v = "";
        
        // add the chars to either w or v if they are lowercase letters and and to z if number-Strings
        boolean isWordOne = true;
        for (char c : chars) {
            String s = String.valueOf(c);
            if (s.matches(Patterns.IS_VERTEX) && isWordOne) {
                w = w + s;
            } else if (s.matches(Patterns.IS_VERTEX) && !isWordOne) {
                v = v + s;
            } else if (s.matches(Patterns.IS_CAPACITY)) {
                z = z + s;
                isWordOne = false;
            } else {
                return null;
            }
        }
        // return an Edge with parsed arguments, if to and from are different and z is a valid int (!= 0)
        // amd if w and v are valid Vertex identifiers
        int i;
        try {
            i = Integer.parseInt(z);
        } catch (NumberFormatException e) {
            return null;
        }
        if (w.equals(v) || !w.matches(Patterns.VALID_VERTEX) 
                || !v.matches(Patterns.VALID_VERTEX) || i == 0) {
            return null;
        }
        return new Edge(w, v, i);
    }
    
    /**
     * Parses to a List of Edges if the given String matches a correct Graph, null if not
     * 
     * @param graph String  argument that needs to be a correct representation of a Graph
     * @return  List<Edge>  all Edges in a List
     */
    public static List<Edge> parseGraph(String graph) {
        String[] edges = graph.split(";", -1);
        List<Edge> edgeList = new ArrayList<Edge>();
        for (String e : edges) {
            Edge nextEdge = parseEdge(e);
            if (nextEdge == null) {
                return null;
            }
            // test if an edge is a duplicate in the list before adding
            for (Edge n : edgeList) {
                if (n.equals(nextEdge)) {
                    return null;
                }
            }
            edgeList.add(nextEdge);
        }
        return edgeList;
    }
}
