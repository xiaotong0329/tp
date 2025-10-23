package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewAttendeesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains tests for {@code ViewAttendeesCommandParser}.
 */
public class ViewAttendeesCommandParserTest {

    private final ViewAttendeesCommandParser parser = new ViewAttendeesCommandParser();

    @Test
    public void parse_validArgs_returnsViewAttendeesCommand() throws Exception {
        String userInput = " " + CliSyntax.PREFIX_EVENT_ID.getPrefix() + "testEvent";
        assertTrue(parser.parse(userInput) instanceof ViewAttendeesCommand);
    }


    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, ViewAttendeesCommand.MESSAGE_USAGE);

        // Missing prefix
        assertThrows(ParseException.class, expectedMessage, () -> parser.parse("event1"));

        // Empty input
        assertThrows(ParseException.class, expectedMessage, () -> parser.parse(""));

        // Random invalid string
        assertThrows(ParseException.class, expectedMessage, () -> parser.parse("blah blah"));
    }
}
