package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.task.Task;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_TITLE = "Complete project report";
    public static final LocalDateTime DEFAULT_DEADLINE = LocalDateTime.of(2024, 12, 31, 23, 59);
    public static final boolean DEFAULT_IS_DONE = false;

    private String title;
    private LocalDateTime deadline;
    private boolean isDone;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        title = DEFAULT_TITLE;
        deadline = DEFAULT_DEADLINE;
        isDone = DEFAULT_IS_DONE;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        title = taskToCopy.getTitle();
        deadline = taskToCopy.getDeadline();
        isDone = taskToCopy.isDone();
    }

    /**
     * Sets the {@code title} of the {@code Task} that we are building.
     */
    public TaskBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the {@code deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
        return this;
    }

    /**
     * Sets the {@code deadline} of the {@code Task} that we are building using a string.
     */
    public TaskBuilder withDeadline(String deadline) {
        this.deadline = Task.parseDeadline(deadline);
        return this;
    }

    /**
     * Sets the {@code isDone} of the {@code Task} that we are building.
     */
    public TaskBuilder withIsDone(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    /**
     * Builds the {@code Task} with the given details.
     */
    public Task build() {
        return new Task(title, deadline, isDone);
    }
}
