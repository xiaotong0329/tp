package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.UniqueList;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 * A task is considered unique by comparing using {@code Task#isSameTask(Task)}. As such, adding and updating of
 * tasks uses Task#isSameTask(Task) for equality so as to ensure that the task being added or updated is
 * unique in terms of identity in the UniqueTaskList. However, the removal of a task uses Task#equals(Object) so
 * as to ensure that the task with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#isSameTask(Task)
 */
public class UniqueTaskList extends UniqueList<Task> {

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setTask(Task target, Task editedTask) {
        setElement(target, editedTask);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        setElements(tasks);
    }

    public void setTasks(UniqueTaskList replacement) {
        requireNonNull(replacement);
        setAllFromOther(replacement);
    }

    @Override
    protected boolean isSameElement(Task task1, Task task2) {
        return task1.isSameTask(task2);
    }

    @Override
    protected RuntimeException createDuplicateException() {
        return new DuplicateTaskException();
    }

    @Override
    protected RuntimeException createNotFoundException() {
        return new TaskNotFoundException();
    }
}
