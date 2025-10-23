package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.MarkAttendanceCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

/**
 * Test class for MarkAttendanceCommandParser.
 */
public class MarkAttendanceCommandParserTest {

    private MarkAttendanceCommandParser parser = new MarkAttendanceCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = " e/Event1 m/John Doe";
        MarkAttendanceCommand expectedCommand = new MarkAttendanceCommand(
                new EventId("Event1"), List.of(new Name("John Doe")));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // missing event ID prefix
        assertParseFailure(parser, " m/John Doe", expectedMessage);

        // missing member name prefix
        assertParseFailure(parser, " e/Event1", expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event ID
        assertParseFailure(parser, " e/ e/Event1 m/John Doe", MESSAGE_INVALID_COMMAND_FORMAT);

        // invalid member name
        assertParseFailure(parser, " e/Event1 m/ m/John Doe", MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_extraPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
        assertParseFailure(parser, " extra e/Event1 m/John Doe", expectedMessage);
    }
}
