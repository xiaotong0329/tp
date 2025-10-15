package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.budget.Budget;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import java.util.Optional;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

    /**
     * Returns an unmodifiable view of the attendance list.
     * This list will not contain any duplicate attendance records.
     */
    ObservableList<Attendance> getAttendanceList();

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();

    /**
     * Returns the optional global budget.
     */
    Optional<Budget> getBudget();

}
