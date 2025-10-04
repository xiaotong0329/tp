package seedu.address.testutil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event EVENT1 = new EventBuilder()
            .withEventId("event1")
            .withDate("2023-12-25")
            .withDescription("Christmas Event")
            .build();

    public static final Event EVENT2 = new EventBuilder()
            .withEventId("event2")
            .withDate("2024-01-01")
            .withDescription("New Year Event")
            .build();

    // Manually added
    public static final Event EVENT3 = new EventBuilder()
            .withEventId("event3")
            .withDate("2024-01-01")
            .withDescription("New Year Event")
            .build();

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT1, EVENT2));
    }
}
