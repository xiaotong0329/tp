package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.AttendanceParserUtil.arePrefixesPresent;
import static seedu.address.logic.parser.AttendanceParserUtil.hasExactlyOneValue;
import static seedu.address.logic.parser.AttendanceParserUtil.parseMemberNames;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.List;

import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object.
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    @Override
    public MarkAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID, PREFIX_MEMBER);

        // Must have no preamble and both prefixes present
        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ID, PREFIX_MEMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAttendanceCommand.MESSAGE_USAGE));
        }

        // Each prefix must appear exactly once
        if (!hasExactlyOneValue(argMultimap, PREFIX_EVENT_ID)
                || !hasExactlyOneValue(argMultimap, PREFIX_MEMBER)) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        // Values must be non-blank after trimming
        String rawEventId = argMultimap.getValue(PREFIX_EVENT_ID).get().trim();
        String rawMember = argMultimap.getValue(PREFIX_MEMBER).get().trim();
        if (rawEventId.isEmpty() || rawMember.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        // Parse into domain types
        try {
            EventId eventId = ParserUtil.parseEventId(rawEventId);
            List<Name> memberNames = parseMemberNames(rawMember);
            return new MarkAttendanceCommand(eventId, memberNames);
        } catch (ParseException pe) {
            // Tests expect a generic invalid-format message on invalid input
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }
}
