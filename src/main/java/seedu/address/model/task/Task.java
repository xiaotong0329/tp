package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    public static final String MESSAGE_CONSTRAINTS =
            "Task title should not be empty and should not exceed 100 characters";
    public static final String DEADLINE_CONSTRAINTS =
            "Deadline should be in YYYY-MM-DD HH:mm format or YYYY-MM-DD format";
    public static final String VALIDATION_REGEX = "^.{1,100}$";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final int MAX_TITLE_LENGTH = 100;

    private final String title;
    private final LocalDateTime deadline;
    private final boolean isDone;

    /**
     * Every field must be present and not null.
     */
    public Task(String title, LocalDateTime deadline) {
        requireAllNonNull(title);
        if (!isValidTitle(title)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.title = title;
        this.deadline = deadline;
        this.isDone = false;
    }

    /**
     * Constructor for creating a task with all fields (used for loading from storage).
     */
    public Task(String title, LocalDateTime deadline, boolean isDone) {
        requireAllNonNull(title);
        if (!isValidTitle(title)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.title = title;
        this.deadline = deadline;
        this.isDone = isDone;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a new Task with the done status toggled.
     */
    public Task toggleDone() {
        return new Task(this.title, this.deadline, !this.isDone);
    }

    /**
     * Returns true if both tasks have the same title.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getTitle().equals(getTitle());
    }

    /**
     * Returns true if a given string is a valid task title.
     */
    public static boolean isValidTitle(String test) {
        return test != null && !test.trim().isEmpty() && test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid deadline format.
     */
    public static boolean isValidDeadline(String test) {
        if (test == null || test.trim().isEmpty()) {
            return true; // deadline is optional
        }

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

            // Try parsing as date-time first, then as date only
            try {
                LocalDateTime.parse(test.trim(), dateTimeFormatter);
                return true;
            } catch (DateTimeParseException e1) {
                try {
                    LocalDateTime.parse(test.trim() + " 23:59", dateTimeFormatter);
                    return true;
                } catch (DateTimeParseException e2) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Parses a deadline string into LocalDateTime.
     * If only date is provided, sets time to 23:59.
     */
    public static LocalDateTime parseDeadline(String deadlineString) {
        if (deadlineString == null || deadlineString.trim().isEmpty()) {
            return null;
        }

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            return LocalDateTime.parse(deadlineString.trim(), dateTimeFormatter);
        } catch (DateTimeParseException e1) {
            try {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
                return LocalDateTime.parse(deadlineString.trim() + " 23:59", dateTimeFormatter);
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("Invalid deadline format: " + deadlineString);
            }
        }
    }

    /**
     * Returns true if both tasks have the same identity and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return title.equals(otherTask.title)
                && Objects.equals(deadline, otherTask.deadline)
                && isDone == otherTask.isDone;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, deadline, isDone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("title", title)
                .add("deadline", deadline)
                .add("isDone", isDone)
                .toString();
    }
}
