package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TaskBuilder;

public class TaskTest {

    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Task(null, LocalDateTime.now(), false));
    }

    @Test
    public void constructor_emptyTitle_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Task("", LocalDateTime.now(), false));
    }

    @Test
    public void constructor_whitespaceTitle_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Task("   ", LocalDateTime.now(), false));
    }

    @Test
    public void constructor_titleTooLong_throwsIllegalArgumentException() {
        String longTitle = "a".repeat(101);
        assertThrows(IllegalArgumentException.class, () -> new Task(longTitle, LocalDateTime.now(), false));
    }

    @Test
    public void constructor_validTitle_success() {
        String validTitle = "Valid Task Title";
        Task task = new Task(validTitle, null, false);
        assertEquals(validTitle, task.getTitle());
    }

    @Test
    public void constructor_withDeadline_success() {
        LocalDateTime deadline = LocalDateTime.of(2024, 12, 31, 23, 59);
        Task task = new Task("Test Task", deadline, false);
        assertEquals(deadline, task.getDeadline());
    }

    @Test
    public void constructor_withoutDeadline_success() {
        Task task = new Task("Test Task", null, false);
        assertEquals(null, task.getDeadline());
    }

    @Test
    public void constructor_withIsDone_success() {
        Task doneTask = new Task("Test Task", null, true);
        assertTrue(doneTask.isDone());

        Task notDoneTask = new Task("Test Task", null, false);
        assertFalse(notDoneTask.isDone());
    }

    @Test
    public void isSameTask() {
        Task alice = new TaskBuilder().withTitle("Alice").build();
        Task aliceCopy = new TaskBuilder().withTitle("Alice").build();
        Task bob = new TaskBuilder().withTitle("Bob").build();

        // same object -> returns true
        assertTrue(alice.isSameTask(alice));

        // same title -> returns true
        assertTrue(alice.isSameTask(aliceCopy));

        // different title -> returns false
        assertFalse(alice.isSameTask(bob));

        // null -> returns false
        assertFalse(alice.isSameTask(null));
    }

    @Test
    public void toggleDone() {
        Task task = new TaskBuilder().withIsDone(false).build();
        Task toggledTask = task.toggleDone();

        assertTrue(toggledTask.isDone());
        assertFalse(task.isDone()); // original task unchanged

        Task toggledBack = toggledTask.toggleDone();
        assertFalse(toggledBack.isDone());
    }

    @Test
    public void isValidTitle() {
        // null title
        assertFalse(Task.isValidTitle(null));

        // empty title
        assertFalse(Task.isValidTitle(""));

        // whitespace only
        assertFalse(Task.isValidTitle("   "));

        // valid title
        assertTrue(Task.isValidTitle("Valid Task Title"));

        // title too long
        assertFalse(Task.isValidTitle("a".repeat(101)));

        // title at max length
        assertTrue(Task.isValidTitle("a".repeat(100)));
    }

    @Test
    public void isValidDeadline() {
        // null deadline
        assertTrue(Task.isValidDeadline(null));

        // empty deadline
        assertTrue(Task.isValidDeadline(""));

        // whitespace deadline
        assertTrue(Task.isValidDeadline("   "));

        // valid date-time format
        assertTrue(Task.isValidDeadline("2024-12-31 23:59"));

        // valid date format
        assertTrue(Task.isValidDeadline("2024-12-31"));

        // invalid format
        assertFalse(Task.isValidDeadline("invalid-date"));

        // invalid date
        assertFalse(Task.isValidDeadline("2024-13-32"));

        // invalid time
        assertFalse(Task.isValidDeadline("2024-12-31 25:00"));
    }

    @Test
    public void parseDeadline() {
        // null deadline
        assertEquals(null, Task.parseDeadline(null));

        // empty deadline
        assertEquals(null, Task.parseDeadline(""));

        // whitespace deadline
        assertEquals(null, Task.parseDeadline("   "));

        // valid date-time format
        LocalDateTime expectedDateTime = LocalDateTime.of(2024, 12, 31, 23, 59);
        assertEquals(expectedDateTime, Task.parseDeadline("2024-12-31 23:59"));

        // valid date format (should default to 23:59)
        LocalDateTime expectedDate = LocalDateTime.of(2024, 12, 31, 23, 59);
        assertEquals(expectedDate, Task.parseDeadline("2024-12-31"));

        // invalid format
        assertThrows(IllegalArgumentException.class, () -> Task.parseDeadline("invalid-date"));
    }

    @Test
    public void equals() {
        Task alice = new TaskBuilder().withTitle("Alice").build();
        Task aliceCopy = new TaskBuilder().withTitle("Alice").build();
        Task bob = new TaskBuilder().withTitle("Bob").build();

        // same object -> returns true
        assertTrue(alice.equals(alice));

        // same values -> returns true
        assertTrue(alice.equals(aliceCopy));

        // different types -> returns false
        assertFalse(alice.equals(1));

        // null -> returns false
        assertFalse(alice.equals(null));

        // different title -> returns false
        assertFalse(alice.equals(bob));

        // different deadline -> returns false
        Task aliceWithDeadline = new TaskBuilder().withTitle("Alice").withDeadline("2024-12-31 23:59").build();
        Task aliceWithoutDeadline = new TaskBuilder().withTitle("Alice").withDeadline((LocalDateTime) null).build();
        assertFalse(aliceWithoutDeadline.equals(aliceWithDeadline));

        // different isDone -> returns false
        Task aliceDone = new TaskBuilder().withTitle("Alice").withIsDone(true).build();
        assertFalse(alice.equals(aliceDone));
    }

    @Test
    public void toStringMethod() {
        Task task = new TaskBuilder().withTitle("Test Task").withDeadline((LocalDateTime) null).build();
        String expected = Task.class.getCanonicalName() + "{title=Test Task, deadline=null, isDone=false}";
        assertEquals(expected, task.toString());
    }
}
