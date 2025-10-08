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

    /**
     * Every field must be present and not null.
     */
    public Attendance(EventId eventId, Name memberName) {
        requireAllNonNull(eventId, memberName);
        this.eventId = eventId;
        this.memberName = memberName;
    }

    public EventId getEventId() {
        return eventId;
    }

    public Name getMemberName() {
        return memberName;
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
                && memberName.equals(otherAttendance.memberName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, memberName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventId", eventId)
                .add("memberName", memberName)
                .toString();
    }
}
