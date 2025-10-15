package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.budget.Budget;
import seedu.address.model.common.Money;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Event> filteredEvents;
    private final FilteredList<Task> filteredTasks;
    private Budget budget; // nullable

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
        this.budget = addressBook.getBudget().orElse(null);
    }

    public ModelManager() {
        this(new VersionedAddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
        this.budget = addressBook.getBudget().orElse(null);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return addressBook.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) {
        addressBook.removeEvent(target);
    }

    @Override
    public void addEvent(Event event) {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        addressBook.setEvent(target, editedEvent);
    }

    @Override
    public Event getEventByEventId(EventId eventId) {
        requireNonNull(eventId);
        return addressBook.getEventByEventId(eventId);
    }

    //=========== Task Management ==================================================================================

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return addressBook.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        addressBook.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        addressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        addressBook.setTask(target, editedTask);
    }

    @Override
    public boolean hasAttendance(Attendance attendance) {
        requireNonNull(attendance);
        return addressBook.hasAttendance(attendance);
    }

    @Override
    public void addAttendance(Attendance attendance) {
        addressBook.addAttendance(attendance);
    }

    @Override
    public void setAttendance(Attendance target, Attendance editedAttendance) {
        requireAllNonNull(target, editedAttendance);
        addressBook.setAttendance(target, editedAttendance);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    //=========== Undo/Redo Operations ========================================================================

    @Override
    public void commit() {
        addressBook.commit();
    }

    @Override
    public boolean undo() {
        boolean result = addressBook.undo();
        if (result) {
            // Update filtered lists to reflect the restored state
            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
            updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        }
        return result;
    }

    @Override
    public boolean redo() {
        boolean result = addressBook.redo();
        if (result) {
            // Update filtered lists to reflect the restored state
            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
            updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        }
        return result;
    }

    @Override
    public boolean canUndo() {
        return addressBook.canUndo();
    }

    @Override
    public boolean canRedo() {
        return addressBook.canRedo();
    }

    @Override
    public void rollbackLastCommit() {
        addressBook.rollbackLastCommit();
    }

    //=========== Budget Operations ========================================================================

    @Override
    public Optional<Budget> getBudget() {
        return Optional.ofNullable(budget);
    }

    @Override
    public void setBudget(Budget budget) {
        this.budget = budget;
        // AddressBook persists budget for storage roundtrip
        this.addressBook.setBudget(budget);
    }

    @Override
    public void clearBudget() {
        this.budget = null;
        this.addressBook.clearBudget();
    }

    @Override
    public Money computeTotalExpensesWithin(LocalDate start, LocalDate end) {
        Money total = Money.zero();
        for (Event e : addressBook.getEventList()) {
            LocalDate d = e.getDate();
            if ((d.isEqual(start) || d.isAfter(start)) && (d.isEqual(end) || d.isBefore(end))) {
                total = total.plus(e.getExpense());
            }
        }
        return total;
    }

    @Override
    public List<Event> getEventsWithin(LocalDate start, LocalDate end) {
        List<Event> result = new ArrayList<>();
        for (Event e : addressBook.getEventList()) {
            LocalDate d = e.getDate();
            if ((d.isEqual(start) || d.isAfter(start)) && (d.isEqual(end) || d.isBefore(end))) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredEvents.equals(otherModelManager.filteredEvents);
    }

}
