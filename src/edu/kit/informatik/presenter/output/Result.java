package edu.kit.informatik.presenter.output;

/**
 * Class that was presented by the tutor Nils Pukropp and is used inside the CommandHandler to
 * pass on the message as well as if an action was successful or not.
 * 
 * isQuit() is my addition to make sure that the quit command gives no output to the console, 
 * when the application terminates.
 * 
 * @author Nils Pukropp, Julian Dieskau
 * @version 1.0
 */
public class Result {
    private final String output;
    private final ResultType type;
    private boolean isQuit;

    /**
     * constructor for all normal commands
     * 
     * @param output message to be passed
     * @param type if the action was successful or not
     */
    public Result(String output, ResultType type) {
        this.output = output;
        this.type = type;
        this.isQuit = false;
    }

    /**
     * constructor for the quit command to state that the program should quit and give no output
     * 
     * @param output    String      message to be passed
     * @param type      ResultType  if the action was successful or not
     * @param isQuit    boolean     if the Result is the quit command
     */
    public Result(String output, ResultType type, boolean isQuit) {
        this.output = output;
        this.type = type;
        this.isQuit = isQuit;
    }
    
    /**
     * Is used as a sort of getter for the ResultType that equals
     * SUCCESS with true    and
     * FAILURE with false
     * 
     * @return {@value true} the action was successful and {@value false} not
     */
    public boolean wasSuccess() {
        return ResultType.SUCCESS.equals(type);
    }

    /**
     * Gives {@value true} if the command is the "quit" command and no further output shall be made
     * 
     * @return boolean  this.isQuit as above
     */
    public boolean isQuit() {
        return this.isQuit;
    }
    
    /**
     * Getter for the Output to be printed after the Result was returned
     * 
     * @return message that was conveyed within the result
     */
    public String getOutput() {
        return this.output;
    }

    /**
     * Getter for the ResultType
     * 
     * @return getter for the type of the Result
     */
    public ResultType getType() {
        return this.type;
    }
    
    /**
     * Nested enum that is used for saving the successfulness of an operation
     * 
     * @author Nils Pukropp
     * @version 1.0
     */
    public enum ResultType {
        /** If it was unsuccessful */
        FAILURE, 
        /** If it was successful*/
        SUCCESS
    }

}