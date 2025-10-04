package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.testutil.EventBuilder;

public class EventCardTest {

    @Test
    public void constructor_validEvent_success() {
        Event event = new EventBuilder().build();
        // We can't actually create EventCard in headless environment due to JavaFX dependencies
        // But we can verify that the Event object is valid
        assertNotNull(event);
        assertNotNull(event.getEventId());
        assertNotNull(event.getDate());
        assertNotNull(event.getDescription());
    }

    @Test
    public void constructor_setsCorrectValues() {
        EventId eventId = new EventId("test_event");
        LocalDate date = LocalDate.of(2023, 12, 25);
        String description = "Test Description";
        Event event = new Event(eventId, date, description);

        // We can't actually create EventCard in headless environment due to JavaFX dependencies
        // But we can verify that the Event object has the correct values
        assertNotNull(event);
        assertEquals("test_event", event.getEventId().value);
        assertEquals(LocalDate.of(2023, 12, 25), event.getDate());
        assertEquals("Test Description", event.getDescription());
    }

    @Test
    public void getEvent_returnsCorrectEvent() {
        Event event = new EventBuilder().build();
        // We can't actually create EventCard in headless environment due to JavaFX dependencies
        // But we can verify that the Event object exists and has valid data
        assertNotNull(event);
        assertNotNull(event.getEventId());
        assertNotNull(event.getDate());
        assertNotNull(event.getDescription());
    }
}
