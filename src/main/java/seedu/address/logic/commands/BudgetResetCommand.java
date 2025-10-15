package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Clears the global budget.
 */
public class BudgetResetCommand extends Command {
    public static final String COMMAND_WORD = "budgetreset";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reset global budget.";
    public static final String MESSAGE_SUCCESS = "Budget cleared.";

    @Override
    public CommandResult execute(Model model) {
        model.clearBudget();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}


