package edu.kit.informatik.model.escaperoutes.graph;

import java.util.ArrayList;

/**
 * Path-Class that is used to collect the order of edges and the path from one Vertex to another
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class Path {

    /**
     * List with order of the Edges to represent a path from one point to another
     */
    ArrayList<ResidualCapacityEdge> path;
    
    /**
     * public constructor, initiates an empty ArrayList for the edges
     */
    public Path() {
        path = new ArrayList<ResidualCapacityEdge>();
    }
    
    
    /**
     * add a new Edge to the Path
     * 
     * @param e Edge
     */
    public void addEdge(ResidualCapacityEdge e) {
        path.add(e);
    }
    
    /**
     * Get the number of edges in the path, equal to the size of a path that is defined as the number of
     * Vertices minus one
     * 
     * @return int  size of the ArrayList path
     */
    public int size() {
        return path.size();
    }
    
    /**
     * Return the reference to the edge at index i in path ArrayList, null if i out of bounds
     * or if the path is empty
     * 
     * @param i int index of the edge
     * @return ResidualCapacityEdge edge at index i
     */
    public ResidualCapacityEdge get(int i) {
        if (i >= 0 || i < this.size()) {
            return path.get(i);
        } else {
            return null;
        }
       
    }
    
    /**
     * Getter for the reference to the last Edge of the path
     * 
     * @return ResidualCapacityEdge if not empty
     */
    public ResidualCapacityEdge getLastEdge() {
        if (!path.isEmpty()) {
            return path.get(size() - 1);
        }
        return null;
    }
    
    /**
     * Getter for a Copy of the List of Edges of the Path
     * 
     * @return ArrayList<ResidualCapacityEdge> of this object
     */
    public ArrayList<ResidualCapacityEdge> getEdgeList() {
        ArrayList<ResidualCapacityEdge> result = new ArrayList<ResidualCapacityEdge>();
        for (ResidualCapacityEdge e : this.path) {
            result.add(e.getCopy()); 
        }
        return result;
    }
    
    /**
     * Copies all edges in this path into a new path and then returns the new path
     * 
     * @return  Path    new Path
     */
    public Path getCopy() {
        Path result = new Path();
        for (ResidualCapacityEdge e : this.path) {
            result.addEdge(e.getCopy()); 
        }
        return result;
    }
}
