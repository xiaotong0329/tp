package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ViewAttendanceCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewAttendanceCommand;
import seedu.address.model.event.EventId;

/**
 * Test class for ViewAttendanceCommandParser.
 */
public class ViewAttendanceCommandParserTest {

    private ViewAttendanceCommandParser parser = new ViewAttendanceCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = " e/Event1";
        ViewAttendanceCommand expectedCommand = new ViewAttendanceCommand(new EventId("Event1"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // missing event ID prefix
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event ID
        assertParseFailure(parser, " e/ e/Event1", MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_extraPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
        assertParseFailure(parser, " extra e/Event1", expectedMessage);
    }
}
