package edu.kit.informatik.presenter;

import edu.kit.informatik.presenter.output.ErrorMessages;
import edu.kit.informatik.presenter.output.Messages;
import edu.kit.informatik.presenter.output.Result;
import edu.kit.informatik.presenter.output.Result.ResultType;
import edu.kit.informatik.view.CommandHandler;

/**
 * Command that is used to terminate the Program.
 * 
 * @author Julian Dieskau
 * @version 1.0
 */
public class Quit extends Command {

    /**
     * Constructor that hands the CommandHandler to the super-class command
     * 
     * @param handler CommandHandler
     */
    public Quit(CommandHandler handler) {
        super(handler);
    }

    @Override
    public String getRegex() {
        return "quit";
    }

    @Override
    public Result execute(String[] input) {
        if (input.length != 1) {
            return new Result(String.format(ErrorMessages.INVALID_ARGUMENT_AMOUNT, 1, input.length), 
                    ResultType.FAILURE);
        }
        this.handler.quit();
        return new Result(Messages.QUIT, ResultType.SUCCESS, true);
    }

}
