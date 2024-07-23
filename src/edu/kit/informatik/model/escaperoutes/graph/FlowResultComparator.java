package edu.kit.informatik.model.escaperoutes.graph;

import java.util.Comparator;

/**
 * FlowResultComparator implements java.util.Comparator for the FlowResult-class 
 * that compares by its three variables
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class FlowResultComparator implements Comparator<FlowResult> {

    @Override
    public int compare(FlowResult r1, FlowResult r2) {
        // compare the result int
        int out1 = Long.compare(r1.getResult(), r2.getResult());
        if (out1 != 0) {
            return out1;
        } else {
            // if equal compare the firstVertex-String alphabetically
            int out2 = r1.getFirst().compareTo(r2.getFirst());
            if (out2 != 0) {
                return out2;
            } else {
                // if equal compare the targetVertex-String alphabetically
                int out3 = r1.getTarget().compareTo(r2.getTarget());
                return out3;
            }
        }
    }

}
