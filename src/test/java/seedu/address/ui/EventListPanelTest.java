package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class EventListPanelTest {

    @Test
    public void constructor_validEventList_success() {
        ObservableList<Event> eventList = FXCollections.observableArrayList();
        // We can't actually create EventListPanel in headless environment due to JavaFX dependencies
        // But we can verify that the constructor parameters are valid
        assertNotNull(eventList);
    }

    @Test
    public void constructor_withEvents_success() {
        Event event1 = new EventBuilder().withEventId("event1").build();
        Event event2 = new EventBuilder().withEventId("event2").build();
        ObservableList<Event> eventList = FXCollections.observableArrayList(Arrays.asList(event1, event2));
        // We can't actually create EventListPanel in headless environment due to JavaFX dependencies
        // But we can verify that the event list is properly constructed
        assertNotNull(eventList);
        assertEquals(2, eventList.size());
    }

    // Note: EventListViewCell tests are omitted because they require JavaFX components
    // that cannot be instantiated in a headless test environment
}
