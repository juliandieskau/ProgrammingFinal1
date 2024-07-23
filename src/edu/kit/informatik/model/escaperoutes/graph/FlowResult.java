package edu.kit.informatik.model.escaperoutes.graph;

/**
 * Class that is used for the list of results for the max-flow-computation inside graphs as well as their
 * start-vertex and target-vertex
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class FlowResult {
    
    private final long maxFlow;
    private final String firstVertex;
    private final String targetVertex;
    
    /**
     * Constructor that initializes the constant three variables of FlowResults
     * 
     * @param maxFlow       int     maximum flow that was computed
     * @param firstVertex   String  first vertex of that computation
     * @param targetVertex  String  target vertex of that computation
     */
    public FlowResult(long maxFlow, String firstVertex, String targetVertex) {
        this.maxFlow = maxFlow;
        this.firstVertex = firstVertex;
        this.targetVertex = targetVertex;
    }

    /**
     * Getter for the result value
     * 
     * @return  int result of the max-flow-computation
     */
    public long getResult() {
        return this.maxFlow;
    }
    
    /**
     * Getter for the first-vertex-identifier
     * 
     * @return  String  id of the first vertex of the computation
     */
    public String getFirst() {
        return this.firstVertex;
    }
    
    /**
     * Getter for the target-vertex-identifier
     * 
     * @return  String  id of the target vertex of the computation
     */
    public String getTarget() {
        return this.targetVertex;
    }
    
    @Override
    public boolean equals(Object obj) {
        // basic tests
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        // test firstVertex und targetVertex
        final FlowResult res = (FlowResult) obj;
        if (this.getFirst().equals(res.getFirst()) && this.getTarget().equals(res.getTarget())) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getFirst().hashCode();
        result = 31 * result + this.getTarget().hashCode();
        return result;
    }
}
