package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String title;
    private final String deadline;
    private final boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("title") String title,
                          @JsonProperty("deadline") String deadline,
                          @JsonProperty("isDone") boolean isDone) {
        this.title = title;
        this.deadline = deadline;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        title = source.getTitle();
        deadline = source.getDeadline() != null
                ? source.getDeadline().format(DateTimeFormatter.ofPattern(Task.DATE_TIME_FORMAT))
                : null;
        isDone = source.isDone();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "title"));
        }
        if (!Task.isValidTitle(title)) {
            throw new IllegalValueException(Task.MESSAGE_CONSTRAINTS);
        }

        LocalDateTime modelDeadline = null;
        if (deadline != null && !deadline.isEmpty()) {
            if (!Task.isValidDeadline(deadline)) {
                throw new IllegalValueException(Task.DEADLINE_CONSTRAINTS);
            }
            try {
                modelDeadline = Task.parseDeadline(deadline);
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(Task.DEADLINE_CONSTRAINTS);
            }
        }

        return new Task(title, modelDeadline, isDone);
    }
}
