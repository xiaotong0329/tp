package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;
import seedu.address.model.common.Money;
import seedu.address.model.event.Event;

/**
 * Shows the budget report.
 */
public class BudgetReportCommand extends Command {
    public static final String COMMAND_WORD = "budgetreport";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View budget report.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Budget budget = model.getBudget().orElse(null);
        if (budget == null) {
            return new CommandResult("No budget set. Use budgetset to set one.");
        }
        List<Event> events = model.getEventsWithin(budget.getStartDate(), budget.getEndDate());
        Money spent = model.computeTotalExpensesWithin(budget.getStartDate(), budget.getEndDate());
        Money remaining = budget.remaining(spent);

        StringBuilder sb = new StringBuilder();
        sb.append("Budget Report\n\n");
        sb.append("Total budget: ").append(budget.getAmount().toString()).append("$\n");
        sb.append("Duration: ").append(budget.getStartDate()).append(" to ").append(budget.getEndDate()).append("\n\n");
        for (Event e : events) {
            sb.append(e.getDescription()).append(": ").append(e.getExpense().toString()).append("$\n");
        }
        sb.append("\nBudget remaining: ").append(remaining.toString()).append("$");
        return new CommandResult(sb.toString());
    }
}


