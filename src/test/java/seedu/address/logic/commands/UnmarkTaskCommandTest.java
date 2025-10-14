package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnmarkTaskCommand}.
 */
public class UnmarkTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Find a done task to unmark
        Task doneTask = model.getFilteredTaskList().stream()
                .filter(Task::isDone)
                .findFirst()
                .orElse(null);

        if (doneTask == null) {
            // If no done task exists, create one
            doneTask = new TaskBuilder().withTitle("Done Task").withIsDone(true).build();
            model.addTask(doneTask);
        }

        int taskIndex = model.getFilteredTaskList().indexOf(doneTask) + 1;
        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(Index.fromOneBased(taskIndex));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Task unmarkedTask = doneTask.toggleDone();
        expectedModel.setTask(doneTask, unmarkedTask);

        String expectedMessage = String.format(UnmarkTaskCommand.MESSAGE_UNMARK_TASK_SUCCESS,
                Messages.format(doneTask));

        assertCommandSuccess(unmarkTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(outOfBoundIndex);

        assertCommandFailure(unmarkTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Find a done task to unmark
        Task doneTask = model.getFilteredTaskList().stream()
                .filter(Task::isDone)
                .findFirst()
                .orElse(null);

        if (doneTask == null) {
            // If no done task exists, create one
            doneTask = new TaskBuilder().withTitle("Done Task").withIsDone(true).build();
            model.addTask(doneTask);
        }

        int taskIndex = model.getFilteredTaskList().indexOf(doneTask) + 1;
        showTaskAtIndex(model, Index.fromOneBased(taskIndex));

        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(Index.fromOneBased(1));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Task unmarkedTask = doneTask.toggleDone();
        expectedModel.setTask(doneTask, unmarkedTask);
        showNoTask(expectedModel);

        String expectedMessage = String.format(UnmarkTaskCommand.MESSAGE_UNMARK_TASK_SUCCESS,
                Messages.format(doneTask));

        assertCommandSuccess(unmarkTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(outOfBoundIndex);

        assertCommandFailure(unmarkTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_taskAlreadyNotDone_throwsCommandException() {
        Task notDoneTask = new TaskBuilder().withTitle("Not Done Task").withIsDone(false).build();
        model.addTask(notDoneTask);

        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(
                Index.fromOneBased(model.getFilteredTaskList().size()));

        assertCommandFailure(unmarkTaskCommand, model, String.format(UnmarkTaskCommand.MESSAGE_TASK_ALREADY_NOT_DONE,
                Messages.format(notDoneTask)));
    }

    @Test
    public void equals() {
        UnmarkTaskCommand unmarkFirstCommand = new UnmarkTaskCommand(INDEX_FIRST_TASK);
        UnmarkTaskCommand unmarkSecondCommand = new UnmarkTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkTaskCommand unmarkFirstCommandCopy = new UnmarkTaskCommand(INDEX_FIRST_TASK);
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(targetIndex);
        String expected = UnmarkTaskCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unmarkTaskCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
