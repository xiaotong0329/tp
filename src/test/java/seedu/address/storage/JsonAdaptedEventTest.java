package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.testutil.EventBuilder;

public class JsonAdaptedEventTest {

    private static final String VALID_EVENT_ID = "event1";
    private static final String VALID_DATE = "2023-12-25";
    private static final String VALID_DESCRIPTION = "Christmas Party";

    @Test
    public void constructor_validParameters_success() throws Exception {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_EVENT_ID, VALID_DATE, VALID_DESCRIPTION);
        Event event = jsonEvent.toModelType();
        assertEquals(VALID_EVENT_ID, event.getEventId().value);
        assertEquals(LocalDate.parse(VALID_DATE), event.getDate());
        assertEquals(VALID_DESCRIPTION, event.getDescription());
    }

    @Test
    public void constructor_nullEventId_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(null, VALID_DATE, VALID_DESCRIPTION);
        assertThrows(IllegalValueException.class, jsonEvent::toModelType);
    }

    @Test
    public void constructor_invalidEventId_throwsIllegalValueException() {
        String invalidEventId = "event-1"; // Contains hyphen, not allowed
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(invalidEventId, VALID_DATE, VALID_DESCRIPTION);
        assertThrows(IllegalValueException.class, jsonEvent::toModelType);
    }

    @Test
    public void constructor_nullDate_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_EVENT_ID, null, VALID_DESCRIPTION);
        assertThrows(IllegalValueException.class, jsonEvent::toModelType);
    }

    @Test
    public void constructor_invalidDateFormat_throwsIllegalValueException() {
        String invalidDate = "25-12-2023"; // Wrong format
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_EVENT_ID, invalidDate, VALID_DESCRIPTION);
        assertThrows(IllegalValueException.class, jsonEvent::toModelType);
    }

    @Test
    public void constructor_nullDescription_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_EVENT_ID, VALID_DATE, null);
        assertThrows(IllegalValueException.class, jsonEvent::toModelType);
    }

    @Test
    public void constructor_descriptionTooLong_throwsIllegalValueException() {
        String longDescription = "a".repeat(101); // Exceeds max length
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_EVENT_ID, VALID_DATE, longDescription);
        assertThrows(IllegalValueException.class, jsonEvent::toModelType);
    }

    @Test
    public void constructor_fromEvent_success() throws Exception {
        Event event = new EventBuilder().build();
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(event);
        Event reconstructedEvent = jsonEvent.toModelType();
        assertEquals(event.getEventId().value, reconstructedEvent.getEventId().value);
        assertEquals(event.getDate(), reconstructedEvent.getDate());
        assertEquals(event.getDescription(), reconstructedEvent.getDescription());
    }

    @Test
    public void toModelType_validData_returnsEvent() throws Exception {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_EVENT_ID, VALID_DATE, VALID_DESCRIPTION);
        Event event = jsonEvent.toModelType();

        assertEquals(VALID_EVENT_ID, event.getEventId().value);
        assertEquals(LocalDate.parse(VALID_DATE), event.getDate());
        assertEquals(VALID_DESCRIPTION, event.getDescription());
    }
}
