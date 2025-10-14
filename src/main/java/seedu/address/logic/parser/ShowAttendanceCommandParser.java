package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;

import seedu.address.logic.commands.ShowAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventId;

/**
 * Parses input arguments and creates a new ShowAttendanceCommand object.
 */
public class ShowAttendanceCommandParser implements Parser<ShowAttendanceCommand> {

    @Override
    public ShowAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ShowAttendanceCommand.MESSAGE_USAGE));
        }

        if (hasNotExactlyOneValue(argMultimap, PREFIX_EVENT_ID)) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String rawEventId = argMultimap.getValue(PREFIX_EVENT_ID).get().trim();
        if (rawEventId.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        try {
            EventId eventId = ParserUtil.parseEventId(rawEventId);
            return new ShowAttendanceCommand(eventId);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap am, Prefix... prefixes) {
        for (Prefix prefix : prefixes) {
            if (am.getValue(prefix).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasNotExactlyOneValue(ArgumentMultimap am, Prefix prefix) {
        return am.getAllValues(prefix).size() != 1;
    }
}

