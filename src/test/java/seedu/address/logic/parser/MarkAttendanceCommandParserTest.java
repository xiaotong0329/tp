package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ID_DESC_EVENT1;
import static seedu.address.logic.parser.AttendanceParserUtil.MESSAGE_EMPTY_MEMBER_NAME;
import static seedu.address.logic.parser.AttendanceParserUtil.MESSAGE_NO_MEMBER_SPECIFIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

public class MarkAttendanceCommandParserTest {

    private static final String MEMBER_AMY = " " + PREFIX_MEMBER + "Amy Bee";
    private final MarkAttendanceCommandParser parser = new MarkAttendanceCommandParser();

    @Test
    public void parse_validArgs_success() {
        MarkAttendanceCommand expectedCommand = new MarkAttendanceCommand(new EventId("event1"),
                List.of(new Name("Amy Bee")));
        assertParseSuccess(parser, EVENT_ID_DESC_EVENT1 + MEMBER_AMY, expectedCommand);
    }

    @Test
    public void parse_missingPrefixes_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE);
        assertParseFailure(parser, MEMBER_AMY, expectedMessage);
        assertParseFailure(parser, " e/event1", expectedMessage);
    }

    @Test
    public void parse_multipleMemberPrefixes_failure() {
        String input = EVENT_ID_DESC_EVENT1 + MEMBER_AMY + MEMBER_AMY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkAttendanceCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_emptyMemberValue_failure() {
        String input = EVENT_ID_DESC_EVENT1 + " " + PREFIX_MEMBER;
        assertParseFailure(parser, input, MESSAGE_NO_MEMBER_SPECIFIED);
    }

    @Test
    public void parse_blankMemberSegment_failure() {
        String input = EVENT_ID_DESC_EVENT1 + " " + PREFIX_MEMBER + "Amy Bee//Bob";
        assertParseFailure(parser, input, MESSAGE_EMPTY_MEMBER_NAME);
    }
}
