package edu.kit.informatik.presenter.input;

/**
 * Class that holds regex-patterns to use to test if input has the right form
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public final class Patterns {

    /** To test if a graph has a valid identifier: */
    public static final String VALID_NETWORK = "[A-Z]{1,6}";
    /** To test if a vertex has a valid identifier: */
    public static final String VALID_VERTEX = "[a-z]{1,6}";
    /** To test if a String is a capacity-representation: */
    public static final String IS_CAPACITY = "[0-9]";
    /** To test if a String is a vertex-representation: */
    public static final String IS_VERTEX = "[a-z]";
    
    private Patterns() {
        throw new IllegalAccessError();
    }

}
