package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.Money;
import seedu.address.model.event.Event;

/**
 * Sets the expense for an event in the currently displayed list by index.
 */
public class SetExpenseCommand extends Command {
    public static final String COMMAND_WORD = "setexpense";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set expense of an event by index. "
            + "Parameters: INDEX a/AMOUNT";
    public static final String MESSAGE_SUCCESS = "Expense set for %1$s: %2$s$";

    private final Index targetIndex;
    private final Money amount;

    /**
     * Constructs a command to set an event's expense.
     * @param targetIndex index of the event in the current event list (1-based)
     * @param amount non-negative SGD amount with two decimals
     */
    public SetExpenseCommand(Index targetIndex, Money amount) {
        this.targetIndex = targetIndex;
        this.amount = amount;
    }

    /**
     * Updates the target event with the new expense.
     * @return a CommandResult describing the outcome
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        int zeroBased = targetIndex.getZeroBased();
        if (zeroBased < 0 || zeroBased >= model.getFilteredEventList().size()) {
            throw new CommandException("Invalid event index");
        }
        Event original = model.getFilteredEventList().get(zeroBased);
        Event updated = new Event(original.getEventId(), original.getDate(), original.getDescription(), amount);
        model.setEvent(original, updated);
        return new CommandResult(String.format(MESSAGE_SUCCESS, original.getDescription(), amount.toString()));
    }
}


