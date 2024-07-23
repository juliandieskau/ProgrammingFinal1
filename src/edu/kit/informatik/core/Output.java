package edu.kit.informatik.core;

/**
 * Interface that is used to define how output is printed to the UI so the exact implementation is not needed
 * to be known.
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public interface Output {
    /**
     * Method that prints a message in the UI
     * 
     * @param message output that is printed to the UI
     */
    void print(String message);
}
