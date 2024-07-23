package edu.kit.informatik;

import edu.kit.informatik.core.ErrorOutput;
import edu.kit.informatik.core.Input;
import edu.kit.informatik.core.Output;
import edu.kit.informatik.model.escaperoutes.EscapeRouteAnalyzer;
import edu.kit.informatik.view.CommandHandler;

/**
 * Main class that includes the main() method and starts the program.
 * Implements Output, Input and ErrorOutput using Terminal to pass to the CommandHandler 
 * 
 * Although I've come along the solution using interfaces for those three myself, it looks similar to
 * a past solution presented to us through our Tutor, so I'll mention him here anyway.
 * 
 * @author Julian Dieskau, Nils Pukropp
 * @version 1.0
 */
public final class Application {
    // Implement Input, Output and ErrorOutput:
    private static final Input IN = new Input() {
        @Override
        public String read() {
            return Terminal.readLine();
        }
    };
    
    private static final Output OUT = new Output() {
        @Override
        public void print(String message) {
            Terminal.printLine(message);
        }
    };
    
    private static final ErrorOutput ERR = new ErrorOutput() {
        @Override
        public void print(String message) {
            Terminal.printError(message);
        }
    };
    
    /**
     * private constructor to block the class of being instantiated
     */
    private Application() {
        throw new IllegalAccessError();
    }
    
    /**
     * main() method that starts up the application and
     * 
     * @param args command line arguments, takes none
     */
    public static void main(String[] args) {
        EscapeRouteAnalyzer e = new EscapeRouteAnalyzer();
        CommandHandler c = new CommandHandler(e, IN, OUT, ERR);
        c.sessionStart();
    }

}
