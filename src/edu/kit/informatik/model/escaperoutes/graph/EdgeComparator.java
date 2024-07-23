package edu.kit.informatik.model.escaperoutes.graph;

import java.util.Comparator;

/**
 * EdgeComparator implements java.util.Comparator that is used to sort Edges alphabetically 
 * by their "from"- and "to"-components
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class EdgeComparator implements Comparator<Edge> {

    @Override
    public int compare(Edge e1, Edge e2) {
        int arg1 = e1.getFromAsString().compareTo(e2.getFromAsString());
        int arg2 = e1.getToAsString().compareTo(e2.getToAsString());
        
        if (arg1 < 0) {
            return -1;
        } else if (arg1 > 0) {
            return 1;
        } else {
            if (arg2 < 0) {
                return -1;
            } else if (arg2 > 0) {
                return 1;
            }
        }
        return 0;
    }

}
