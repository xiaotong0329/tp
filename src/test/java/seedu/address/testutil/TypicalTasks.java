package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ALICE = new TaskBuilder()
            .withTitle("Complete Alice's project")
            .withDeadline(LocalDateTime.of(2024, 12, 15, 18, 0))
            .withIsDone(false)
            .build();

    public static final Task BOB = new TaskBuilder()
            .withTitle("Review Bob's code")
            .withDeadline(LocalDateTime.of(2024, 12, 20, 12, 0))
            .withIsDone(true)
            .build();

    public static final Task CARL = new TaskBuilder()
            .withTitle("Update documentation")
            .withIsDone(false)
            .build();

    public static final Task DANIEL = new TaskBuilder()
            .withTitle("Fix bug in login system")
            .withDeadline(LocalDateTime.of(2024, 12, 25, 23, 59))
            .withIsDone(false)
            .build();

    public static final Task ELLE = new TaskBuilder()
            .withTitle("Prepare presentation")
            .withDeadline(LocalDateTime.of(2024, 12, 30, 14, 30))
            .withIsDone(true)
            .build();

    public static final Task FIONA = new TaskBuilder()
            .withTitle("Write unit tests")
            .withIsDone(false)
            .build();

    public static final Task GEORGE = new TaskBuilder()
            .withTitle("Deploy to production")
            .withDeadline(LocalDateTime.of(2024, 12, 31, 9, 0))
            .withIsDone(false)
            .build();

    // Manually added
    public static final Task HOON = new TaskBuilder()
            .withTitle("Refactor database queries")
            .withDeadline(LocalDateTime.of(2024, 12, 28, 16, 0))
            .withIsDone(false)
            .build();

    public static final Task IDA = new TaskBuilder()
            .withTitle("Optimize performance")
            .withIsDone(true)
            .build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ALICE, BOB, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
