package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ID_DESC_EVENT1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewAttendeesCommand;
import seedu.address.model.event.EventId;

public class ViewAttendeesCommandParserTest {

    private final ViewAttendeesCommandParser parser = new ViewAttendeesCommandParser();

    @Test
    public void parse_validArgs_success() {
        ViewAttendeesCommand expected = new ViewAttendeesCommand(new EventId("event1"));
        assertParseSuccess(parser, EVENT_ID_DESC_EVENT1, expected);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAttendeesCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " event1", expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAttendeesCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "preamble" + EVENT_ID_DESC_EVENT1, expectedMessage);
    }

    @Test
    public void parse_multipleEventPrefixes_failure() {
        String input = EVENT_ID_DESC_EVENT1 + " " + EVENT_ID_DESC_EVENT1;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAttendeesCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }
}
