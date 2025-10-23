package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Money;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String eventId;
    private final String date;
    private final String description;
    private final String expense;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventId") String eventId, @JsonProperty("date") String date,
            @JsonProperty("description") String description,
            @JsonProperty("expense") String expense) {
        this.eventId = eventId;
        this.date = date;
        this.description = description;
        this.expense = expense; // may be null for backward compatibility
    }

    /**
     * Backward-compatible constructor without expense field.
     */
    public JsonAdaptedEvent(String eventId, String date, String description) {
        this(eventId, date, description, null);
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        eventId = source.getEventId().value;
        date = source.getDate().toString();
        description = source.getDescription();
        expense = source.getExpense().toString();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (eventId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, EventId.class.getSimpleName()));
        }
        if (!EventId.isValidEventId(eventId)) {
            throw new IllegalValueException(EventId.MESSAGE_CONSTRAINTS);
        }
        final EventId modelEventId = new EventId(eventId);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }
        if (!Event.isValidDateFormat(date)) {
            throw new IllegalValueException(Event.DATE_CONSTRAINTS);
        }
        final java.time.LocalDate modelDate = java.time.LocalDate.parse(date);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }
        if (!Event.isValidDescription(description)) {
            throw new IllegalValueException(Event.DESCRIPTION_CONSTRAINTS);
        }
        final String modelDescription = description;
        final Money modelExpense = (expense == null) ? Money.zero() : Money.parse(expense);

        return new Event(modelEventId, modelDate, modelDescription, modelExpense);
    }

}
