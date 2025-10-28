package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Shared helpers for attendance-related command parsers.
 */
public final class AttendanceParserUtil {

    public static final String MESSAGE_EMPTY_MEMBER_NAME =
            "Member names cannot be blank. Remove consecutive '/' or empty segments in m/…";
    public static final String MESSAGE_NO_MEMBER_SPECIFIED =
            "Please provide at least one member name after m/…";

    private AttendanceParserUtil() {
        // Utility class
    }

    /**
     * Returns true if all the specified prefixes have values present in the multimap.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        requireNonNull(argumentMultimap);
        requireNonNull(prefixes);
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if the prefix is associated with exactly one value.
     */
    public static boolean hasExactlyOneValue(ArgumentMultimap argumentMultimap, Prefix prefix) {
        requireNonNull(argumentMultimap);
        requireNonNull(prefix);
        return argumentMultimap.getAllValues(prefix).size() == 1;
    }

    /**
     * Parses a slash-delimited list of member names into {@link Name} objects.
     */
    public static List<Name> parseMemberNames(String rawMembers) throws ParseException {
        requireNonNull(rawMembers);
        String trimmedInput = rawMembers.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(MESSAGE_NO_MEMBER_SPECIFIED);
        }

        String[] parts = trimmedInput.split("/");
        List<Name> result = new ArrayList<>();

        for (String part : parts) {
            String trimmed = part.trim();
            if (trimmed.isEmpty()) {
                throw new ParseException(MESSAGE_EMPTY_MEMBER_NAME);
            }
            result.add(ParserUtil.parseName(trimmed));
        }
        return result;
    }

    /**
     * Wraps a {@link ParseException} with the command usage where appropriate.
     */
    public static ParseException propagateAttendanceParseException(ParseException pe, String usage) {
        requireNonNull(pe);
        requireNonNull(usage);
        if (MESSAGE_INVALID_COMMAND_FORMAT.equals(pe.getMessage())) {
            return new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, usage), pe);
        }
        return new ParseException(pe.getMessage(), pe);
    }
}
