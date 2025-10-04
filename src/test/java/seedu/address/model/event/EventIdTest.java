package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EventIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventId(null));
    }

    @Test
    public void constructor_invalidEventId_throwsIllegalArgumentException() {
        String invalidEventId = "event-1"; // Contains hyphen
        assertThrows(IllegalArgumentException.class, () -> new EventId(invalidEventId));
    }

    @Test
    public void constructor_validEventId_success() {
        String validEventId = "event1";
        EventId eventId = new EventId(validEventId);
        assertEquals(validEventId, eventId.value);
    }

    @Test
    public void isValidEventId_validEventIds_returnsTrue() {
        assertTrue(EventId.isValidEventId("event1"));
        assertTrue(EventId.isValidEventId("EVENT_1"));
        assertTrue(EventId.isValidEventId("event_1"));
        assertTrue(EventId.isValidEventId("123"));
        assertTrue(EventId.isValidEventId("a"));
        assertTrue(EventId.isValidEventId("A"));
        assertTrue(EventId.isValidEventId("event_id_123"));
    }

    @Test
    public void isValidEventId_invalidEventIds_returnsFalse() {
        assertFalse(EventId.isValidEventId("")); // Empty string
        assertFalse(EventId.isValidEventId(" ")); // Whitespace only
        assertFalse(EventId.isValidEventId("event-1")); // Contains hyphen
        assertFalse(EventId.isValidEventId("event 1")); // Contains space
        assertFalse(EventId.isValidEventId("event@1")); // Contains special character
        assertFalse(EventId.isValidEventId("event.1")); // Contains dot
    }

    @Test
    public void toString_returnsValue() {
        String eventIdString = "event1";
        EventId eventId = new EventId(eventIdString);
        assertEquals(eventIdString, eventId.toString());
    }

    @Test
    public void equals_sameEventId_returnsTrue() {
        EventId eventId1 = new EventId("event1");
        EventId eventId2 = new EventId("event1");
        assertTrue(eventId1.equals(eventId2));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        EventId eventId = new EventId("event1");
        assertTrue(eventId.equals(eventId));
    }

    @Test
    public void equals_null_returnsFalse() {
        EventId eventId = new EventId("event1");
        assertFalse(eventId.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        EventId eventId = new EventId("event1");
        assertFalse(eventId.equals("event1"));
    }

    @Test
    public void equals_differentEventId_returnsFalse() {
        EventId eventId1 = new EventId("event1");
        EventId eventId2 = new EventId("event2");
        assertFalse(eventId1.equals(eventId2));
    }

    @Test
    public void hashCode_sameEventId_returnsSameHashCode() {
        EventId eventId1 = new EventId("event1");
        EventId eventId2 = new EventId("event1");
        assertEquals(eventId1.hashCode(), eventId2.hashCode());
    }

    @Test
    public void hashCode_differentEventId_returnsDifferentHashCode() {
        EventId eventId1 = new EventId("event1");
        EventId eventId2 = new EventId("event2");
        assertNotEquals(eventId1.hashCode(), eventId2.hashCode());
    }
}
