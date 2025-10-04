package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_ID = "event1";
    public static final String DEFAULT_DATE = "2023-12-25";
    public static final String DEFAULT_DESCRIPTION = "Default Event";

    private EventId eventId;
    private LocalDate date;
    private String description;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        eventId = new EventId(DEFAULT_EVENT_ID);
        date = LocalDate.parse(DEFAULT_DATE);
        description = DEFAULT_DESCRIPTION;
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventId = eventToCopy.getEventId();
        date = eventToCopy.getDate();
        description = eventToCopy.getDescription();
    }

    /**
     * Sets the {@code EventId} of the {@code Event} that we are building.
     */
    public EventBuilder withEventId(String eventId) {
        this.eventId = new EventId(eventId);
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = LocalDate.parse(date);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Event build() {
        return new Event(eventId, date, description);
    }
}
