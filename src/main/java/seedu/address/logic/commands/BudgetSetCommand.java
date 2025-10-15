package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;
import seedu.address.model.common.Money;

/**
 * Sets the global budget.
 */
public class BudgetSetCommand extends Command {
    public static final String COMMAND_WORD = "budgetset";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set global budget. "
            + "Parameters: a/AMOUNT from/YYYY-MM-DD to/YYYY-MM-DD";
    public static final String MESSAGE_SUCCESS = "Budget set: %1$s$ from %2$s to %3$s";

    private final Money amount;
    private final LocalDate start;
    private final LocalDate end;

    /**
     * Constructs a command to set a global budget with amount and inclusive date range.
     * @param amount non-negative SGD amount with two decimals
     * @param start inclusive start date (yyyy-MM-dd)
     * @param end inclusive end date (yyyy-MM-dd), must be on/after start
     */
    public BudgetSetCommand(Money amount, LocalDate start, LocalDate end) {
        requireNonNull(amount);
        requireNonNull(start);
        requireNonNull(end);
        this.amount = amount;
        this.start = start;
        this.end = end;
    }

    /**
     * Applies the new budget to the model.
     * @return a CommandResult describing the outcome
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        Budget budget = new Budget(amount, start, end);
        model.setBudget(budget);
        return new CommandResult(String.format(MESSAGE_SUCCESS, amount.toString(), start, end));
    }
}


