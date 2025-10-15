package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.common.Money;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    public static final String MESSAGE_CONSTRAINTS =
            "Event ID should only contain alphanumeric characters and underscores";
    public static final String DATE_CONSTRAINTS = "Date should be in YYYY-MM-DD format";
    public static final String DESCRIPTION_CONSTRAINTS = "Description should not exceed 100 characters";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_]+$";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final int MAX_DESCRIPTION_LENGTH = 100;

    private final EventId eventId;
    private final LocalDate date;
    private final String description;
    private final Money expense;

    /**
     * Every field must be present and not null.
     */
    public Event(EventId eventId, LocalDate date, String description) {
        requireAllNonNull(eventId, date, description);
        this.eventId = eventId;
        this.date = date;
        this.description = description;
        this.expense = Money.zero();
    }

    /**
     * Full constructor used when specifying expense.
     */
    public Event(EventId eventId, LocalDate date, String description, Money expense) {
        requireAllNonNull(eventId, date, description, expense);
        this.eventId = eventId;
        this.date = date;
        this.description = description;
        this.expense = expense;
    }

    public EventId getEventId() {
        return eventId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Money getExpense() {
        return expense;
    }

    /**
     * Returns true if both events have the same event ID.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventId().equals(getEventId());
    }

    /**
     * Returns true if a given string is a valid event ID.
     */
    public static boolean isValidEventId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid date format.
     */
    public static boolean isValidDateFormat(String test) {
        try {
            LocalDate.parse(test, DateTimeFormatter.ofPattern(DATE_FORMAT));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test != null && !test.isEmpty() && test.length() <= MAX_DESCRIPTION_LENGTH;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return eventId.equals(otherEvent.eventId)
                && date.equals(otherEvent.date)
                && description.equals(otherEvent.description)
                && expense.equals(otherEvent.expense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, date, description, expense);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventId", eventId)
                .add("date", date)
                .add("description", description)
                .add("expense", expense)
                .toString();
    }
}
