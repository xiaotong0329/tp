package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;

import seedu.address.logic.commands.ViewAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventId;

/**
 * Parses input arguments and creates a new ViewAttendanceCommand object
 */
public class ViewAttendanceCommandParser implements Parser<ViewAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewAttendanceCommand
     * and returns a ViewAttendanceCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewAttendanceCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ID);

        EventId eventId = ParserUtil.parseEventId(argMultimap.getValue(PREFIX_EVENT_ID).get());

        return new ViewAttendanceCommand(eventId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return java.util.Arrays.stream(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
