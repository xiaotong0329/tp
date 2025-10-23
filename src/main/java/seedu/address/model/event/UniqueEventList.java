package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.UniqueList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * An event is considered unique by comparing using {@code Event#isSameEvent(Event)}. As such, adding and updating of
 * events uses Event#isSameEvent(Event) for equality so as to ensure that the event being added or updated is
 * unique in terms of identity in the UniqueEventList. However, the removal of an event uses Event#equals(Object) so
 * as to ensure that the event with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Event#isSameEvent(Event)
 */
public class UniqueEventList extends UniqueList<Event> {

    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the list.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the list.
     */
    public void setEvent(Event target, Event editedEvent) {
        setElement(target, editedEvent);
    }

    /**
     * Replaces the contents of this list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        setElements(events);
    }

    public void setEvents(UniqueEventList replacement) {
        requireNonNull(replacement);
        setAllFromOther(replacement);
    }

    @Override
    protected boolean isSameElement(Event event1, Event event2) {
        return event1.isSameEvent(event2);
    }

    @Override
    protected RuntimeException createDuplicateException() {
        return new DuplicateEventException();
    }

    @Override
    protected RuntimeException createNotFoundException() {
        return new EventNotFoundException();
    }
}
