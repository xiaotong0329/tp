package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_TITLE_ALICE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTasks.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(ALICE).build();

        // whitespace only preamble
        assertParseSuccess(parser, " " + VALID_TASK_TITLE_ALICE + " " + VALID_DEADLINE_ALICE,
                new AddTaskCommand(expectedTask));

        // multiple deadlines - last deadline accepted
        assertParseSuccess(parser, " " + VALID_TASK_TITLE_ALICE + VALID_DEADLINE_BOB + " " + VALID_DEADLINE_ALICE,
                new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero deadlines
        Task expectedTask = new TaskBuilder(ALICE).withDeadline((String) null).build();
        assertParseSuccess(parser, " " + VALID_TASK_TITLE_ALICE,
                new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // empty title
        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid deadline format
        assertParseFailure(parser, " " + VALID_TASK_TITLE_ALICE + " dl/invalid-date",
                "Deadline should be in YYYY-MM-DD HH:mm format or YYYY-MM-DD format");

        // empty title
        assertParseFailure(parser, " " + VALID_DEADLINE_ALICE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }
}
