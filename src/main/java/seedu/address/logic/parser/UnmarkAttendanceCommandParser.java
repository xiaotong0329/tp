package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.AttendanceParserUtil.arePrefixesPresent;
import static seedu.address.logic.parser.AttendanceParserUtil.hasExactlyOneValue;
import static seedu.address.logic.parser.AttendanceParserUtil.parseMemberNames;
import static seedu.address.logic.parser.AttendanceParserUtil.propagateAttendanceParseException;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.List;

import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new UnmarkAttendanceCommand object.
 */
public class UnmarkAttendanceCommandParser implements Parser<UnmarkAttendanceCommand> {

    @Override
    public UnmarkAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID, PREFIX_MEMBER);
        String usageError = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAttendanceCommand.MESSAGE_USAGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ID, PREFIX_MEMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(usageError);
        }

        if (!hasExactlyOneValue(argMultimap, PREFIX_EVENT_ID)
                || !hasExactlyOneValue(argMultimap, PREFIX_MEMBER)) {
            throw new ParseException(usageError);
        }

        String rawEventId = argMultimap.getValue(PREFIX_EVENT_ID).get().trim();
        String rawMembers = argMultimap.getValue(PREFIX_MEMBER).get();

        if (rawEventId.isEmpty()) {
            throw new ParseException(usageError);
        }

        try {
            EventId eventId = ParserUtil.parseEventId(rawEventId);
            List<Name> memberNames = parseMemberNames(rawMembers);
            return new UnmarkAttendanceCommand(eventId, memberNames);
        } catch (ParseException pe) {
            throw propagateAttendanceParseException(pe, UnmarkAttendanceCommand.MESSAGE_USAGE);
        }
    }
}
