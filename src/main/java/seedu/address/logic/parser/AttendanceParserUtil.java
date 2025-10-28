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
}
