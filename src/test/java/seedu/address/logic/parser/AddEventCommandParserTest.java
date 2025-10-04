package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ID_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ID_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EVENT1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder()
                .withEventId(VALID_EVENT_ID_EVENT1)
                .withDate(VALID_DATE_EVENT1)
                .withDescription(VALID_DESCRIPTION_EVENT1)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_ID_DESC_EVENT1 + DATE_DESC_EVENT1
                + DESCRIPTION_DESC_EVENT1, new AddEventCommand(expectedEvent));

        // multiple event IDs - last one accepted
        assertParseSuccess(parser, EVENT_ID_DESC_EVENT1 + EVENT_ID_DESC_EVENT1 + DATE_DESC_EVENT1
                + DESCRIPTION_DESC_EVENT1, new AddEventCommand(expectedEvent));

        // multiple dates - last one accepted
        assertParseSuccess(parser, EVENT_ID_DESC_EVENT1 + DATE_DESC_EVENT1 + DATE_DESC_EVENT1
                + DESCRIPTION_DESC_EVENT1, new AddEventCommand(expectedEvent));

        // multiple descriptions - last one accepted
        assertParseSuccess(parser, EVENT_ID_DESC_EVENT1 + DATE_DESC_EVENT1 + DESCRIPTION_DESC_EVENT1
                + DESCRIPTION_DESC_EVENT1, new AddEventCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing event ID prefix
        assertParseFailure(parser, VALID_EVENT_ID_EVENT1 + DATE_DESC_EVENT1 + DESCRIPTION_DESC_EVENT1,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, EVENT_ID_DESC_EVENT1 + VALID_DATE_EVENT1 + DESCRIPTION_DESC_EVENT1,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, EVENT_ID_DESC_EVENT1 + DATE_DESC_EVENT1 + VALID_DESCRIPTION_EVENT1,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_ID_EVENT1 + VALID_DATE_EVENT1 + VALID_DESCRIPTION_EVENT1,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event ID
        assertParseFailure(parser, INVALID_EVENT_ID_DESC + DATE_DESC_EVENT1 + DESCRIPTION_DESC_EVENT1,
                EventId.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, EVENT_ID_DESC_EVENT1 + INVALID_DATE_DESC + DESCRIPTION_DESC_EVENT1,
                "Date should be in YYYY-MM-DD format");

        // invalid description
        assertParseFailure(parser, EVENT_ID_DESC_EVENT1 + DATE_DESC_EVENT1 + INVALID_DESCRIPTION_DESC,
                "Description should not exceed 100 characters");

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_ID_DESC + INVALID_DATE_DESC + DESCRIPTION_DESC_EVENT1,
                EventId.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_ID_DESC_EVENT1 + DATE_DESC_EVENT1
                + DESCRIPTION_DESC_EVENT1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
