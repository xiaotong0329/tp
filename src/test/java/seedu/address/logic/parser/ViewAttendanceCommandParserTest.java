package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains tests for {@code ViewAttendanceCommandParser}.
 */
public class ViewAttendanceCommandParserTest {

    private final ViewAttendanceCommandParser parser = new ViewAttendanceCommandParser();

    @Test
    public void parse_validArgs_returnsViewAttendanceCommand() throws Exception {
        String userInput = " " + CliSyntax.PREFIX_EVENT_ID.getPrefix() + "testEvent";
        assertTrue(parser.parse(userInput) instanceof ViewAttendanceCommand);
    }


    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewAttendanceCommand.MESSAGE_USAGE);

        // Missing prefix
        assertThrows(ParseException.class, expectedMessage, () -> parser.parse("event1"));

        // Empty input
        assertThrows(ParseException.class, expectedMessage, () -> parser.parse(""));

        // Random invalid string
        assertThrows(ParseException.class, expectedMessage, () -> parser.parse("blah blah"));
    }
}
