package edu.kit.informatik.view;

import edu.kit.informatik.core.*;
import edu.kit.informatik.model.escaperoutes.EscapeRouteAnalyzer;
import edu.kit.informatik.presenter.*;
import edu.kit.informatik.presenter.output.*;
import edu.kit.informatik.presenter.output.Result.ResultType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Julian Dieskau
 * @author Nils Pukropp
 * @version 1.0
 * 
 * This class is based on the CommandHandler-class my/our Programming Tutor Nils Pukropp has presented,
 * thus it is allowed to be used as a source for code in this exercise.
 */
public class CommandHandler {
    // private variables:
    private final List<Command> commandList;
    private final EscapeRouteAnalyzer analyzer;
    private final Input input;
    private final Output output;
    private final ErrorOutput errorOutput;
    // negative limits allow as many as possible arguments with the needed length of the array:
    // would have wrote "SPLIT_LIMIT" cause it is a constant but the checkstyle thinks otherwise
    private final int splitLimit = -1;
    // controls the execution of the application:
    private boolean executeApplication;
    
    /**
     * Constructor that sets all important parameters at the start of the application
     * and initializes the commandList.
     * 
     * @param a EscapeRouteAnalyzer Logic-Class to call in the commands
     * @param i Input               Input from the Application
     * @param o Output              Output that is passed to the Application
     * @param e ErrorOutput         Output that is passed but just used for error-messages
     */
    public CommandHandler(EscapeRouteAnalyzer a, Input i, Output o, ErrorOutput e) {
        commandList = new ArrayList<>();
        this.analyzer = a;
        
        // set application to a "running"-state
        executeApplication = true;
        
        // register the commands here:
        this.commandList.addAll(List.of(new Add(this), new Quit(this), 
                new Flow(this), new Print(this), new ListCMD(this)));
        
        this.input = i;
        this.output = o;
        this.errorOutput = e;
    }

    /**
     * Method that starts the execution of the program and is a placeholder if any other actions need to happen
     * at the start of the Application / the CommandHandler
     */
    public void sessionStart() {
        execute();
    }
    
    /**
     * The method used for all runtime operations of the program and used for user interaction
     * The program terminates if execute() is not running anymore; stopped by quit().
     */
    public void execute() {
        while (executeApplication) {
            String userInput = input.read();
            String[] splitUserInput = userInput.split(" ", splitLimit);
            
            // Check if the amount of arguments is at least one:
            // Through limited size of characters of the readLine() we do not need to check array's upper bounds
            if (splitUserInput.length < 1) {
                errorOutput.print(ErrorMessages.INVALID_ARGUMENT_AMOUNT);
            }
            
            // check if the command is valid and then execute it or if not mark as FAILURE
            Result r = getCommand(splitUserInput[0])
                    .map(cmd -> cmd.execute(splitUserInput))
                    .orElse(new Result(ErrorMessages.COMMAND_NOT_FOUND, ResultType.FAILURE));
            
            // output the result of the command
            String out = r.getOutput();
            if (!r.isQuit()) {
                if (r.wasSuccess()) {
                    output.print(out);
                } else {
                    errorOutput.print(out);
                }
            }
            // else do nothing, executeApplication was set to false
            // -> Application will terminate
        }
    }
    
    
    /**
     * Use Optional to search the commands after the name and avoiding returning null
     * 
     * @param name  String  of the command we are searching for in the commandList
     * @return      Optional of the command or empty Optional if not found
     */
    private Optional<Command> getCommand(String name) {
        for (Command c : commandList) {
            if (c.getRegex().matches(name)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
    
    
    /**
     * Terminates the application by stopping the execute() method.
     */
    public void quit() {
        executeApplication = false;
    }
    
    /**
     * Getter for the LogicClass for all Commands that call the command-handler
     * 
     * @return the EscapeRouteAnalyzer used by the CommandHandler
     */
    public EscapeRouteAnalyzer getAnalyzer() {
        return this.analyzer;
    }
}
