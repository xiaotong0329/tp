package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;

/**
 * Deletes an event identified by its event ID from the address book.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the event ID.\n"
            + "Parameters: e/EVENTID\n"
            + "Example: " + COMMAND_WORD + " e/Orientation";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Event %1$s deleted successfully.";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found: %1$s.";

    private final EventId targetEventId;

    public DeleteEventCommand(EventId targetEventId) {
        this.targetEventId = targetEventId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Event eventToDelete = model.getEventByEventId(targetEventId);
        if (eventToDelete == null) {
            throw new CommandException(String.format(MESSAGE_EVENT_NOT_FOUND, targetEventId.value));
        }

        model.deleteEvent(eventToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete.getEventId().value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEventCommand)) {
            return false;
        }

        DeleteEventCommand otherDeleteEventCommand = (DeleteEventCommand) other;
        return targetEventId.equals(otherDeleteEventCommand.targetEventId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetEventId", targetEventId)
                .toString();
    }
}
