package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

/**
 * Jackson-friendly version of {@link Attendance}.
 */
class JsonAdaptedAttendance {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Attendance's %s field is missing!";

    private final String eventId;
    private final String memberName;
    private final Boolean hasAttended;

    /**
     * Constructs a {@code JsonAdaptedAttendance} with the given attendance details.
     */
    @JsonCreator
    public JsonAdaptedAttendance(@JsonProperty("eventId") String eventId,
            @JsonProperty("memberName") String memberName,
            @JsonProperty("hasAttended") Boolean hasAttended) {
        this.eventId = eventId;
        this.memberName = memberName;
        this.hasAttended = hasAttended;
    }

    /**
     * Converts a given {@code Attendance} into this class for Jackson use.
     */
    public JsonAdaptedAttendance(Attendance source) {
        this.eventId = source.getEventId().value;
        this.memberName = source.getMemberName().fullName;
        this.hasAttended = source.hasAttended();
    }

    /**
     * Converts this Jackson-friendly adapted attendance object into the model's {@code Attendance} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attendance.
     */
    public Attendance toModelType() throws IllegalValueException {
        if (eventId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventId.class.getSimpleName()));
        }
        if (!EventId.isValidEventId(eventId)) {
            throw new IllegalValueException(EventId.MESSAGE_CONSTRAINTS);
        }
        final EventId modelEventId = new EventId(eventId);

        if (memberName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(memberName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(memberName);

        if (hasAttended == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "hasAttended"));
        }

        return new Attendance(modelEventId, modelName, hasAttended);
    }
}
