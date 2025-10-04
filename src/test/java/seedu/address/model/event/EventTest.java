package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventTest {

    @Test
    public void constructor_validParameters_success() {
        EventId eventId = new EventId("event1");
        LocalDate date = LocalDate.of(2023, 12, 25);
        String description = "Christmas Party";

        Event event = new Event(eventId, date, description);

        assertEquals(eventId, event.getEventId());
        assertEquals(date, event.getDate());
        assertEquals(description, event.getDescription());
    }

    @Test
    public void isSameEvent_nullEvent_returnsFalse() {
        Event event = new EventBuilder().build();
        assertFalse(event.isSameEvent(null));
    }

    @Test
    public void isSameEvent_sameEvent_returnsTrue() {
        Event event = new EventBuilder().build();
        assertTrue(event.isSameEvent(event));
    }

    @Test
    public void isSameEvent_sameEventId_returnsTrue() {
        Event event1 = new EventBuilder().withEventId("event1").build();
        Event event2 = new EventBuilder().withEventId("event1").build();

        assertTrue(event1.isSameEvent(event2));
    }

    @Test
    public void isSameEvent_differentEventId_returnsFalse() {
        Event event1 = new EventBuilder().withEventId("event1").build();
        Event event2 = new EventBuilder().withEventId("event2").build();

        assertFalse(event1.isSameEvent(event2));
    }

    @Test
    public void isValidEventId_validEventId_returnsTrue() {
        assertTrue(Event.isValidEventId("event1"));
        assertTrue(Event.isValidEventId("EVENT_1"));
        assertTrue(Event.isValidEventId("event_1"));
        assertTrue(Event.isValidEventId("123"));
    }

    @Test
    public void isValidEventId_invalidEventId_returnsFalse() {
        assertFalse(Event.isValidEventId("")); // Empty string
        assertFalse(Event.isValidEventId("event-1")); // Contains hyphen
        assertFalse(Event.isValidEventId("event 1")); // Contains space
        assertFalse(Event.isValidEventId("event@1")); // Contains special character
    }

    @Test
    public void isValidDateFormat_validDateFormat_returnsTrue() {
        assertTrue(Event.isValidDateFormat("2023-12-25"));
        assertTrue(Event.isValidDateFormat("2000-01-01"));
        assertTrue(Event.isValidDateFormat("2030-12-31"));
    }

    @Test
    public void isValidDateFormat_invalidDateFormat_returnsFalse() {
        assertFalse(Event.isValidDateFormat("")); // Empty string
        assertFalse(Event.isValidDateFormat("25-12-2023")); // Wrong format
        assertFalse(Event.isValidDateFormat("2023/12/25")); // Wrong separator
        assertFalse(Event.isValidDateFormat("2023-13-25")); // Invalid month
        assertFalse(Event.isValidDateFormat("2023-12-32")); // Invalid day
    }

    @Test
    public void isValidDescription_validDescription_returnsTrue() {
        assertTrue(Event.isValidDescription("Short description"));
        assertTrue(Event.isValidDescription("a".repeat(100))); // Max length
    }

    @Test
    public void isValidDescription_invalidDescription_returnsFalse() {
        assertFalse(Event.isValidDescription("")); // Empty string
        assertFalse(Event.isValidDescription("a".repeat(101))); // Exceeds max length
    }

    @Test
    public void equals_sameEvent_returnsTrue() {
        Event event = new EventBuilder().build();
        assertTrue(event.equals(event));
    }

    @Test
    public void equals_null_returnsFalse() {
        Event event = new EventBuilder().build();
        assertFalse(event.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Event event = new EventBuilder().build();
        assertFalse(event.equals("string"));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Event event1 = new EventBuilder().build();
        Event event2 = new Event(event1.getEventId(), event1.getDate(), event1.getDescription());
        assertTrue(event1.equals(event2));
    }

    @Test
    public void equals_differentEventId_returnsFalse() {
        Event event1 = new EventBuilder().withEventId("event1").build();
        Event event2 = new EventBuilder().withEventId("event2").build();
        assertFalse(event1.equals(event2));
    }

    @Test
    public void equals_differentDate_returnsFalse() {
        Event event1 = new EventBuilder().withDate("2023-12-25").build();
        Event event2 = new EventBuilder().withDate("2023-12-26").build();
        assertFalse(event1.equals(event2));
    }

    @Test
    public void equals_differentDescription_returnsFalse() {
        Event event1 = new EventBuilder().withDescription("Description 1").build();
        Event event2 = new EventBuilder().withDescription("Description 2").build();
        assertFalse(event1.equals(event2));
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        Event event1 = new EventBuilder().build();
        Event event2 = new Event(event1.getEventId(), event1.getDate(), event1.getDescription());
        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHashCode() {
        Event event1 = new EventBuilder().withEventId("event1").build();
        Event event2 = new EventBuilder().withEventId("event2").build();
        assertNotEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    public void toString_returnsCorrectFormat() {
        Event event = new EventBuilder().build();
        String expected = Event.class.getCanonicalName() + "{eventId=" + event.getEventId()
                + ", date=" + event.getDate() + ", description=" + event.getDescription() + "}";
        assertEquals(expected, event.toString());
    }
}
