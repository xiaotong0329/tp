package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        Event event = new EventBuilder().build();
        assertFalse(addressBook.hasEvent(event));
    }

    @Test
    public void hasEvent_eventInAddressBook_returnsTrue() {
        Event event = new EventBuilder().build();
        addressBook.addEvent(event);
        assertTrue(addressBook.hasEvent(event));
    }

    @Test
    public void hasEvent_eventWithSameIdentityInAddressBook_returnsTrue() {
        Event event = new EventBuilder().build();
        Event sameEvent = new Event(event.getEventId(), event.getDate(), "Different description");
        addressBook.addEvent(event);
        assertTrue(addressBook.hasEvent(sameEvent));
    }

    @Test
    public void addEvent_validEvent_success() {
        Event event = new EventBuilder().build();
        addressBook.addEvent(event);
        assertTrue(addressBook.hasEvent(event));
        assertEquals(1, addressBook.getEventList().size());
    }

    @Test
    public void setEvent_validEdit_success() {
        Event event = new EventBuilder().build();
        Event editedEvent = new Event(event.getEventId(), event.getDate(), "Edited description");

        addressBook.addEvent(event);
        addressBook.setEvent(event, editedEvent);

        // Both event and editedEvent should have the same event ID, so hasEvent should return true for both
        assertTrue(addressBook.hasEvent(event));
        assertTrue(addressBook.hasEvent(editedEvent));
        assertEquals(1, addressBook.getEventList().size());
        // The event in the list should be the editedEvent
        assertEquals(editedEvent, addressBook.getEventList().get(0));
    }

    @Test
    public void removeEvent_validEvent_success() {
        Event event = new EventBuilder().build();
        addressBook.addEvent(event);
        addressBook.removeEvent(event);

        assertFalse(addressBook.hasEvent(event));
        assertEquals(0, addressBook.getEventList().size());
    }

    @Test
    public void getEventByEventId_existingEventId_returnsEvent() {
        Event event = new EventBuilder().withEventId("test_event").build();
        addressBook.addEvent(event);

        Event foundEvent = addressBook.getEventByEventId(new EventId("test_event"));
        assertEquals(event, foundEvent);
    }

    @Test
    public void getEventByEventId_nonExistingEventId_returnsNull() {
        Event foundEvent = addressBook.getEventByEventId(new EventId("non_existing"));
        assertEquals(null, foundEvent);
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getEventList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons="
                + addressBook.getPersonList() + ", events=" + addressBook.getEventList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return FXCollections.observableArrayList();
        }
    }

}
