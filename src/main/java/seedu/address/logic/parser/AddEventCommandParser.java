package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_EVENT_ID, PREFIX_DATE, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ID, PREFIX_DATE, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ID, PREFIX_DATE, PREFIX_DESCRIPTION);

        EventId eventId = ParserUtil.parseEventId(argMultimap.getValue(PREFIX_EVENT_ID).get());
        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        String description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());

        Event event = new Event(eventId, date, description);

        return new AddEventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return java.util.stream.Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
