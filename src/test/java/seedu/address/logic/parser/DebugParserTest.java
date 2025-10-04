package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;

import org.junit.jupiter.api.Test;

public class DebugParserTest {

    @Test
    public void debugArgumentTokenizer() {
        String userInput = "ev/event1 d/2023-12-25 desc/Christmas Event";

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                userInput, PREFIX_EVENT_ID, PREFIX_DATE, PREFIX_DESCRIPTION);
        System.out.println("Preamble: '" + argMultimap.getPreamble() + "'");
        System.out.println("Event ID: " + argMultimap.getValue(PREFIX_EVENT_ID));
        System.out.println("Date: " + argMultimap.getValue(PREFIX_DATE));
        System.out.println("Description: " + argMultimap.getValue(PREFIX_DESCRIPTION));
    }
}
