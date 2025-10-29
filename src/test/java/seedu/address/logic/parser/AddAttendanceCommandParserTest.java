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

import seedu.address.logic.commands.AddAttendanceCommand;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

public class AddAttendanceCommandParserTest {

    private static final String MEMBER_AMY = " " + PREFIX_MEMBER + "Amy Bee";
    private static final String MEMBER_MULTIPLE = " " + PREFIX_MEMBER + "Amy Bee/Bob Choo";

    private final AddAttendanceCommandParser parser = new AddAttendanceCommandParser();

    @Test
    public void parse_validArgs_success() {
        AddAttendanceCommand expectedCommand = new AddAttendanceCommand(new EventId("event1"),
                List.of(new Name("Amy Bee")));
        assertParseSuccess(parser, EVENT_ID_DESC_EVENT1 + MEMBER_AMY, expectedCommand);
    }

    @Test
    public void parse_multipleMembers_success() {
        AddAttendanceCommand expectedCommand = new AddAttendanceCommand(new EventId("event1"),
                List.of(new Name("Amy Bee"), new Name("Bob Choo")));
        assertParseSuccess(parser, EVENT_ID_DESC_EVENT1 + MEMBER_MULTIPLE, expectedCommand);
    }

    @Test
    public void parse_missingPrefixes_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAttendanceCommand.MESSAGE_USAGE);
        assertParseFailure(parser, MEMBER_AMY, expectedMessage);
        assertParseFailure(parser, " e/event1", expectedMessage);
    }

    @Test
    public void parse_multipleMemberPrefixes_failure() {
        String userInput = EVENT_ID_DESC_EVENT1 + MEMBER_AMY + MEMBER_AMY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAttendanceCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyMemberValue_failure() {
        String userInput = EVENT_ID_DESC_EVENT1 + " " + PREFIX_MEMBER;
        assertParseFailure(parser, userInput, MESSAGE_NO_MEMBER_SPECIFIED);
    }

    @Test
    public void parse_blankMemberSegment_failure() {
        String userInput = EVENT_ID_DESC_EVENT1 + " " + PREFIX_MEMBER + "Amy Bee//Bob";
        assertParseFailure(parser, userInput, MESSAGE_EMPTY_MEMBER_NAME);
    }
}
