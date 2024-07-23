package edu.kit.informatik.model.escaperoutes.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * GraphComparator implements java.util.Comparator for Graphs to be able to sort them by their number of vertices
 * as well as alphabetically by their ID for equal number of vertices
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class GraphComparator implements Comparator<Graph> {

    @Override
    public int compare(Graph g1, Graph g2) {
        int l1 = getNumOfVertices(g1.getEdgeList());
        int l2 = getNumOfVertices(g2.getEdgeList());
        int out1 = Integer.compare(l2, l1);
        if (out1 != 0) {
            return out1;
        } else {
            int out2 = g1.getID().compareTo(g2.getID());
            return out2;
        }
    }

    /**
     * Returns the number of vertices that are in a List of Edges
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
}
