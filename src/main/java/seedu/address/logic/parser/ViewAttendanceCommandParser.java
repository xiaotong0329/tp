package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;

import java.util.Arrays;

import seedu.address.logic.commands.ViewAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventId;

/**
 * Parses input arguments and creates a new {@code ViewAttendanceCommand} object.
 */
public class ViewAttendanceCommandParser implements Parser<ViewAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ViewAttendanceCommand}
     * and returns a {@code ViewAttendanceCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ViewAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAttendanceCommand.MESSAGE_USAGE));
        }

        EventId eventId = ParserUtil.parseEventId(argMultimap.getValue(PREFIX_EVENT_ID).get());
        return new ViewAttendanceCommand(eventId);
    }

    /**
     * Returns true if all the specified prefixes are present in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
