package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.AddAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddAttendanceCommand object.
 */
public class AddAttendanceCommandParser implements Parser<AddAttendanceCommand> {

    @Override
    public AddAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID, PREFIX_MEMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ID, PREFIX_MEMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAttendanceCommand.MESSAGE_USAGE));
        }

        if (hasNotExactlyOneValue(argMultimap, PREFIX_EVENT_ID)
                || hasNotExactlyOneValue(argMultimap, PREFIX_MEMBER)) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String rawEventId = argMultimap.getValue(PREFIX_EVENT_ID).get().trim();
        String rawMembers = argMultimap.getValue(PREFIX_MEMBER).get().trim();

        if (rawEventId.isEmpty() || rawMembers.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        try {
            EventId eventId = ParserUtil.parseEventId(rawEventId);
            List<Name> memberNames = parseMemberNames(rawMembers);
            return new AddAttendanceCommand(eventId, memberNames);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    private List<Name> parseMemberNames(String rawMembers) throws ParseException {
        String[] parts = rawMembers.split("/");
        List<Name> result = new ArrayList<>();

        for (String part : parts) {
            String trimmed = part.trim();
            if (trimmed.isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
            result.add(ParserUtil.parseName(trimmed));
        }

        return result;
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
        List<String> values = am.getAllValues(prefix);
        return values.size() != 1;
    }
}

