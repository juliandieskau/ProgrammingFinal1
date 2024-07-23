package edu.kit.informatik.model.escaperoutes.graph;

/**
 * Represents an Edge of a Graph-Structure with a capacity
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class Edge {

    private final Vertex from;
    private final Vertex to;
    private int capacity;
    
    /**
     * Public Constructor for Edge
     * 
     * @param fromID    String  ID for the Vertex that the Edge starts from
     * @param toID      String  ID for the Vertex the from-Vertex is pointing towards
     * @param capacity  int     maximum number of persons that are able to use the edge/path in a minute
     */
    public Edge(String fromID, String toID, int capacity) {
        this.from = new Vertex(fromID);
        this.to = new Vertex(toID);
        this.capacity = capacity;
    }
    
    /**
     * Getter for a Copy of the Edge 
     * 
     * @return new Edge with same values as this object
     */
    public Edge getCopy() {
        return new Edge(getFromAsString(), getToAsString(), getCapacity());
    }
    
    /**
     * Getter for the ID of the From-Vertex
     * 
     * @return String   from-ID
     */
    public String getFromAsString() {
        return this.from.getID();
    }
    
    /**
     * Getter for the ID of the To-Vertex
     * 
     * @return String   to-ID
     */
    public String getToAsString() {
        return this.to.getID();
    }
    
    /**
     * Getter for the capacity of the Edge
     * 
     * @return capacity     int
     */
    public int getCapacity() {
        return this.capacity;
    }
    
    /** 
     * Setter for the capacity of the Edge
     * 
     * @param newCap  int    new capacity of this edge
     */
    public void changeCapacity(int newCap) {
        this.capacity = newCap;
    }
    
    /**
     * Return a string that generates an ID out of from- and to-Vertices-IDs
     * If your change this also change "getReverseID()"!
     * 
     * @return String   ID
     */
    public String getID() {
        return getFromAsString() + " " + getToAsString();
    }
    
    /**
     * Return a String that is equal to the ID of the reversed edge of e
     * If your change this also change "getID()"!
     * 
     * @return  String  ID of reversed edge
     */
    public String getReversedID() {
        return this.getToAsString() + " " + this.getFromAsString();
    }
    
    /**
     * Returns true, if obj is an Edge and if from and to of both Edges are equal;
     * capacity of both doesn't matter
     * 
     * @param obj   Object      that should be of type "Edge"
     * @return      boolean     if is equal
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
        if (this.getFromAsString().equals(e.getFromAsString())  && this.getToAsString().equals(e.getToAsString())) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return this.getFromAsString() + String.valueOf(this.getCapacity()) + this.getToAsString();
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getFromAsString().hashCode();
        result = 31 * result + this.getToAsString().hashCode();
        return result;
    }
}
