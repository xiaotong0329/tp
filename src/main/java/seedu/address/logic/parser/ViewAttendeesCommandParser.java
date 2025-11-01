package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;

import java.util.Arrays;

import seedu.address.logic.commands.ViewAttendeesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventId;

/**
 * Parses input arguments and creates a new {@code ViewAttendeesCommand} object.
 */
public class ViewAttendeesCommandParser implements Parser<ViewAttendeesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ViewAttendeesCommand}
     * and returns a {@code ViewAttendeesCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ViewAttendeesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ID)
                || !argMultimap.getPreamble().isEmpty()
                || argMultimap.getAllValues(PREFIX_EVENT_ID).size() != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAttendeesCommand.MESSAGE_USAGE));
        }

        EventId eventId = ParserUtil.parseEventId(argMultimap.getValue(PREFIX_EVENT_ID).get());
        return new ViewAttendeesCommand(eventId);
    }

    /**
     * Returns true if all the specified prefixes are present in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
