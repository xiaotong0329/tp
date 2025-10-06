package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ID_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ID_EVENT1;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.model.event.EventId;
import seedu.address.logic.commands.CommandTestUtil;

public class DeleteEventCommandParserTest {

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteEventCommand() {
        assertParseSuccess(parser, EVENT_ID_DESC_EVENT1, new DeleteEventCommand(new EventId(VALID_EVENT_ID_EVENT1)));

        // whitespace
        assertParseSuccess(parser, " " + EVENT_ID_DESC_EVENT1 + "  ",
                new DeleteEventCommand(new EventId(VALID_EVENT_ID_EVENT1)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidEventId_throwsParseException() {
        assertParseFailure(parser, INVALID_EVENT_ID_DESC, EventId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_EVENT_ID_EVENT1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteEventCommand.MESSAGE_USAGE));
    }
}