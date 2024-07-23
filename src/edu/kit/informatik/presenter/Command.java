package edu.kit.informatik.presenter;

import edu.kit.informatik.model.escaperoutes.EscapeRouteAnalyzer;
import edu.kit.informatik.presenter.output.Result;
import edu.kit.informatik.view.CommandHandler;

/**
 * Interface for all command-classes to set needed methods for them to work properly.
 * The same as in the solution in our tut.
 * 
 * @author Julian Dieskau
 * @author Nils Pukropp
 * @version 1.0
 */
public abstract class Command {
    
    /**
     * The CommandHandler that calls all the Commands that inherit from this class
     * and is needed to pass the output as a Result to.
     */
    protected final CommandHandler handler;
    
    /**
     * The logic class that is called to give the processed input to and which provides all methods that
     * are needed to compute the output.
     */
    protected final EscapeRouteAnalyzer analyzer;
    
    /**
     * constructor, protected so the sub classes have access to it but nothing else
     * 
     * @param handler CommandHandler used
     */
    protected Command(CommandHandler handler) {
        this.handler = handler;
        this.analyzer = handler.getAnalyzer();
    }
    
    /**
     * Getter for the name of the Command class <-> what the Input in the console has to be
     * 
     * @return a String representing the name of the command
     */
    public abstract String getRegex();
    
    /**
     * Executes the command => calls all needed methods for the command to achieve what is needed
     * 
     * @param input String[], containing all arguments that were passed from the input
     * @return Result of the command getting executed, if it was successful or not
     */
    public abstract Result execute(String[] input);
}
