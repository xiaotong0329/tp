package seedu.address.model.attendance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

/**
 * Represents an Attendance record in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Attendance {

    private final EventId eventId;
    private final Name memberName;
    private final boolean hasAttended;

    /**
     * Every field must be present and not null.
     */
    public Attendance(EventId eventId, Name memberName) {
        this(eventId, memberName, false);
    }

    /**
     * Creates an {@code Attendance} with the specified attendance status.
     */
    public Attendance(EventId eventId, Name memberName, boolean hasAttended) {
        requireAllNonNull(eventId, memberName);
        this.eventId = eventId;
        this.memberName = memberName;
        this.hasAttended = hasAttended;
    }

    public EventId getEventId() {
        return eventId;
    }

    public Name getMemberName() {
        return memberName;
    }

    public boolean hasAttended() {
        return hasAttended;
    }

    /**
     * Returns a copy of this attendance record marked as attended.
     */
    public Attendance markAttended() {
        if (hasAttended) {
            return this;
        }
        return new Attendance(eventId, memberName, true);
    }

    /**
     * Returns true if both attendance records have the same event ID and member name.
     * This defines a weaker notion of equality between two attendance records.
     */
    public boolean isSameAttendance(Attendance otherAttendance) {
        if (otherAttendance == this) {
            return true;
        }

        return otherAttendance != null
                && otherAttendance.getEventId().equals(getEventId())
                && otherAttendance.getMemberName().equals(getMemberName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;
        return eventId.equals(otherAttendance.eventId)
                && memberName.equals(otherAttendance.memberName)
                && hasAttended == otherAttendance.hasAttended;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, memberName, hasAttended);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventId", eventId)
                .add("memberName", memberName)
                .add("hasAttended", hasAttended)
                .toString();
    }
}
