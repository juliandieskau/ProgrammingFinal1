package edu.kit.informatik.model.escaperoutes.graph;

/**
 * Represents an Edge that is used for the "residual-capacity network"-graph.
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class ResidualCapacityEdge extends Edge {

    private int flow;
    private int residualCapacity;
    
    /**
     * Public Constructor, calls the Edge-Constructor through super()
     * 
     * @param fromID    String  ID for the Vertex that the Edge starts from
     * @param toID      String  ID for the Vertex the from-Vertex is pointing towards
     * @param capacity  int     maximum number of persons that are able to use the edge/path in a minute 
     */
    public ResidualCapacityEdge(String fromID, String toID, int capacity) {
        super(fromID, toID, capacity);
        this.residualCapacity = capacity;
        this.flow = 0;
    }
    
    /**
     * Public Constructor (overload), calls the Edge-Constructor through super()
     * 
     * @param fromID    String  ID for the Vertex that the Edge starts from
     * @param toID      String  ID for the Vertex the from-Vertex is pointing towards
     * @param capacity  int     maximum number of persons that are able to use the edge/path in a minute 
     * @param flow      int     flow of the edge
     * @param resCapacity int   residual capacity of the edge (capacity minus flow..)
     */
    public ResidualCapacityEdge(String fromID, String toID, int capacity, int flow, int resCapacity) {
        super(fromID, toID, capacity);
        this.residualCapacity = resCapacity;
        this.flow = flow;
    }
    
    /**
     * Getter for the residual capacity
     * 
     * @return int residualCapacity
     */
    public int getResidualCapacity() {
        return this.residualCapacity;
    }
    
    /**
     * Setter for the residual capacity
     * 
     * @param newResCapacity int    new residual Capacity
     */
    public void setResidualCapacity(int newResCapacity) {
        this.residualCapacity = newResCapacity;
    }
    
    /**
     * Getter for the flow of the edge
     * 
     * @return  int     flow
     */
    public int getFlow() {
        return this.flow;
    }
    
    /**
     * Setter for the flow of the edge
     * 
     * @param amount    int     flow
     */
    public void changeFlowBy(int amount) {
        this.flow += amount;
    }
    
    /**
     * Edge with reversed parameters:
     * - rfrom = to; rto = from
     * - rflow = -this.flow
     * - rcapacity = 0
     * - residualCapacity = rcapacity - rflow = 0 + this.flow
     * 
     * @return ResidualCapacityEdge as above
     */
    public ResidualCapacityEdge getReversedEdge() {
        return new ResidualCapacityEdge(this.getToAsString(), this.getFromAsString(), 
                0, -this.getFlow(), this.getFlow());
    }
    
    /**
     * Returns a ResidualCapacityEdge-object with the same parameters as this one.
     * 
     * @return ResidualCapacityEdge as above
     */
    @Override
    public ResidualCapacityEdge getCopy() {
        return new ResidualCapacityEdge(this.getFromAsString(), this.getToAsString(), 
                this.getCapacity(), this.getFlow(), this.getResidualCapacity());
    }
    
    /**
     * Returns true, if obj is an ResidualCapacityEdge and if from and to of both Edges are equal;
     * capacity, flow of both doesn't matter
     * 
     * @param   obj Object that should be of type "ResidualCapacityEdge"
     * @return      boolean  if is equal
     */
    @Override
    public boolean equals(Object obj) {
        // basic tests
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        // if from and to of both are equal, the edge is equal
        final Edge e = (Edge) obj;
        if (this.getFromAsString() == e.getFromAsString() && this.getToAsString() == e.getToAsString()) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getFromAsString().hashCode();
        result = 31 * result + this.getToAsString().hashCode();
        return result;
    }
}
