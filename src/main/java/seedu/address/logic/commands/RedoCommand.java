package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redoes the previously undone command by restoring the next state of the address book.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes the previously undone command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Previous undo has been redone.";
    public static final String MESSAGE_NO_REDO = "There are no commands to redo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canRedo()) {
            throw new CommandException(MESSAGE_NO_REDO);
        }

        model.redo();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof RedoCommand; // All RedoCommand instances are equal
    }

    @Override
    public String toString() {
        return "RedoCommand";
    }
}
