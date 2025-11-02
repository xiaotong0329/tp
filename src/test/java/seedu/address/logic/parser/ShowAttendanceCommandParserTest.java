package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ID_DESC_EVENT1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowAttendanceCommand;
import seedu.address.model.event.EventId;

public class ShowAttendanceCommandParserTest {

    private final ShowAttendanceCommandParser parser = new ShowAttendanceCommandParser();

    @Test
    public void parse_validArgs_success() {
        ShowAttendanceCommand expected = new ShowAttendanceCommand(new EventId("event1"));
        assertParseSuccess(parser, EVENT_ID_DESC_EVENT1, expected);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowAttendanceCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " event1", expectedMessage);
    }

    @Test
    public void parse_multiplePrefixes_failure() {
        String userInput = EVENT_ID_DESC_EVENT1 + " " + PREFIX_EVENT_ID + "event2";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowAttendanceCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_blankEventId_failure() {
        String userInput = " " + PREFIX_EVENT_ID;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowAttendanceCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
