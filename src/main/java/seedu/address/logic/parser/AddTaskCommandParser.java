package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;

import java.time.LocalDateTime;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DEADLINE);

        // Extract title (everything before the first prefix)
        String title = argMultimap.getPreamble().trim();

        if (title.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        // Validate title
        if (!Task.isValidTitle(title)) {
            throw new ParseException(Task.MESSAGE_CONSTRAINTS);
        }

        // Parse deadline if present
        LocalDateTime deadline = null;
        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            String deadlineString = argMultimap.getValue(PREFIX_DEADLINE).get();
            if (!Task.isValidDeadline(deadlineString)) {
                throw new ParseException(Task.DEADLINE_CONSTRAINTS);
            }
            try {
                deadline = Task.parseDeadline(deadlineString);
            } catch (IllegalArgumentException e) {
                throw new ParseException(Task.DEADLINE_CONSTRAINTS);
            }
        }

        Task task = new Task(title, deadline);
        return new AddTaskCommand(task);
    }
}
