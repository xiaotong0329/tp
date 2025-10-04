package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.testutil.EventBuilder;

public class UniqueEventListTest {

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        assertFalse(uniqueEventList.contains(event));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        uniqueEventList.add(event);
        assertTrue(uniqueEventList.contains(event));
    }

    @Test
    public void contains_eventWithSameIdentityInList_returnsTrue() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        Event sameEvent = new Event(event.getEventId(), event.getDate(), "Different description");
        uniqueEventList.add(event);
        assertTrue(uniqueEventList.contains(sameEvent));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        uniqueEventList.add(event);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(event));
    }

    @Test
    public void add_eventWithSameIdentity_throwsDuplicateEventException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        Event sameEvent = new Event(event.getEventId(), event.getDate(), "Different description");
        uniqueEventList.add(event);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(sameEvent));
    }

    @Test
    public void setEvent_targetNotInList_throwsEventNotFoundException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        Event differentEvent = new EventBuilder().withEventId("different").build();
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(event, differentEvent));
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        Event editedEvent = new Event(event.getEventId(), event.getDate(), "Edited description");

        uniqueEventList.add(event);
        uniqueEventList.setEvent(event, editedEvent);

        assertEquals(editedEvent, uniqueEventList.asUnmodifiableObservableList().get(0));
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        Event differentEvent = new EventBuilder().withEventId("different").build();

        uniqueEventList.add(event);
        uniqueEventList.setEvent(event, differentEvent);

        assertEquals(differentEvent, uniqueEventList.asUnmodifiableObservableList().get(0));
    }

    @Test
    public void setEvent_editedEventHasSameIdentityAsAnotherEvent_throwsDuplicateEventException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event1 = new EventBuilder().withEventId("event1").build();
        Event event2 = new EventBuilder().withEventId("event2").build();
        Event editedEvent = new Event(event1.getEventId(), event1.getDate(), "Edited description");

        uniqueEventList.add(event1);
        uniqueEventList.add(event2);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvent(event2, editedEvent));
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventNotInList_throwsEventNotFoundException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(event));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        uniqueEventList.add(event);
        uniqueEventList.remove(event);
        assertFalse(uniqueEventList.contains(event));
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_validUniqueEventList_setsEvents() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event1 = new EventBuilder().withEventId("event1").build();
        Event event2 = new EventBuilder().withEventId("event2").build();

        UniqueEventList otherList = new UniqueEventList();
        otherList.add(event1);
        otherList.add(event2);

        uniqueEventList.setEvents(otherList);

        assertEquals(2, uniqueEventList.asUnmodifiableObservableList().size());
        assertTrue(uniqueEventList.contains(event1));
        assertTrue(uniqueEventList.contains(event2));
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((List<Event>) null));
    }

    @Test
    public void setEvents_listWithDuplicates_throwsDuplicateEventException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        List<Event> eventsWithDuplicates = Arrays.asList(event, event);

        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(eventsWithDuplicates));
    }

    @Test
    public void setEvents_validList_setsEvents() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event1 = new EventBuilder().withEventId("event1").build();
        Event event2 = new EventBuilder().withEventId("event2").build();
        List<Event> events = Arrays.asList(event1, event2);

        uniqueEventList.setEvents(events);

        assertEquals(2, uniqueEventList.asUnmodifiableObservableList().size());
        assertTrue(uniqueEventList.contains(event1));
        assertTrue(uniqueEventList.contains(event2));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        uniqueEventList.add(event);

        assertThrows(UnsupportedOperationException.class, () ->
            uniqueEventList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator_returnsIterator() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        uniqueEventList.add(event);

        assertTrue(uniqueEventList.iterator().hasNext());
        assertEquals(event, uniqueEventList.iterator().next());
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertFalse(uniqueEventList.equals("string"));
    }

    @Test
    public void equals_null_returnsFalse() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertFalse(uniqueEventList.equals(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertTrue(uniqueEventList.equals(uniqueEventList));
    }

    @Test
    public void equals_sameEvents_returnsTrue() {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        Event event = new EventBuilder().build();

        uniqueEventList1.add(event);
        uniqueEventList2.add(event);

        assertTrue(uniqueEventList1.equals(uniqueEventList2));
    }

    @Test
    public void equals_differentEvents_returnsFalse() {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        Event event1 = new EventBuilder().withEventId("event1").build();
        Event event2 = new EventBuilder().withEventId("event2").build();

        uniqueEventList1.add(event1);
        uniqueEventList2.add(event2);

        assertFalse(uniqueEventList1.equals(uniqueEventList2));
    }

    @Test
    public void hashCode_sameEvents_returnsSameHashCode() {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        Event event = new EventBuilder().build();

        uniqueEventList1.add(event);
        uniqueEventList2.add(event);

        assertEquals(uniqueEventList1.hashCode(), uniqueEventList2.hashCode());
    }

    @Test
    public void toString_returnsInternalListToString() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = new EventBuilder().build();
        uniqueEventList.add(event);

        String expected = uniqueEventList.asUnmodifiableObservableList().toString();
        assertEquals(expected, uniqueEventList.toString());
    }
}
