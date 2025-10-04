package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEventId(String)}
 */
public class EventId {

    public static final String MESSAGE_CONSTRAINTS =
            "Event ID should only contain alphanumeric characters and underscores";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_]+$";

    public final String value;

    /**
     * Constructs a {@code EventId}.
     *
     * @param eventId A valid event ID.
     */
    public EventId(String eventId) {
        requireNonNull(eventId);
        checkArgument(isValidEventId(eventId), MESSAGE_CONSTRAINTS);
        value = eventId;
    }

    /**
     * Returns true if a given string is a valid event ID.
     */
    public static boolean isValidEventId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventId)) {
            return false;
        }

        EventId otherEventId = (EventId) other;
        return value.equals(otherEventId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
