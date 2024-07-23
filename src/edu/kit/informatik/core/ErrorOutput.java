package edu.kit.informatik.core;

/**
 * interface that is implemented to define how error output is processed
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public interface ErrorOutput {
    /**
     * prints the error in an implementation that needs to be defined
     * 
     * @param message error message that is passed to the UI
     */
    void print(String message);
}
