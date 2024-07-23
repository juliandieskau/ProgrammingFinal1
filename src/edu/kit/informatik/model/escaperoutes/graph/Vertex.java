package edu.kit.informatik.model.escaperoutes.graph;

/**
 * Represents a vertex for implementing graphs.
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class Vertex {

    private String identifier;
    
    /**
     * public constructor, sets the id of the Vertex
     * 
     * @param id String, identifier for the Vertex
     */
    public Vertex(String id) {
        this.identifier = id;
    }
    
    /**
     * getter for ID of the vertex
     * 
     * @return identifier of the Vertex-object
     */
    public String getID() {
        return this.identifier;
    }
}
