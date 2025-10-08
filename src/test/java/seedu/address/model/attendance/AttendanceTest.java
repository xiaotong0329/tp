package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

/**
 * Test class for Attendance.
 */
public class AttendanceTest {

    @Test
    public void constructor_nullEventId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Attendance(null, new Name("John Doe")));
    }

    @Test
    public void constructor_nullMemberName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Attendance(new EventId("Event1"), null));
    }

    @Test
    public void constructor_validParameters_success() {
        EventId eventId = new EventId("Event1");
        Name memberName = new Name("John Doe");
        Attendance attendance = new Attendance(eventId, memberName);

        assertEquals(eventId, attendance.getEventId());
        assertEquals(memberName, attendance.getMemberName());
    }

    @Test
    public void isSameAttendance() {
        EventId eventId1 = new EventId("Event1");
        EventId eventId2 = new EventId("Event2");
        Name memberName1 = new Name("John Doe");
        Name memberName2 = new Name("Jane Doe");

        Attendance attendance1 = new Attendance(eventId1, memberName1);
        Attendance attendance2 = new Attendance(eventId1, memberName1);
        Attendance attendance3 = new Attendance(eventId2, memberName1);
        Attendance attendance4 = new Attendance(eventId1, memberName2);

        // same object -> returns true
        assertTrue(attendance1.isSameAttendance(attendance1));

        // same event ID and member name -> returns true
        assertTrue(attendance1.isSameAttendance(attendance2));

        // different event ID -> returns false
        assertFalse(attendance1.isSameAttendance(attendance3));

        // different member name -> returns false
        assertFalse(attendance1.isSameAttendance(attendance4));

        // null -> returns false
        assertFalse(attendance1.isSameAttendance(null));
    }

    @Test
    public void equals() {
        EventId eventId1 = new EventId("Event1");
        EventId eventId2 = new EventId("Event2");
        Name memberName1 = new Name("John Doe");
        Name memberName2 = new Name("Jane Doe");

        Attendance attendance1 = new Attendance(eventId1, memberName1);
        Attendance attendance2 = new Attendance(eventId1, memberName1);
        Attendance attendance3 = new Attendance(eventId2, memberName1);
        Attendance attendance4 = new Attendance(eventId1, memberName2);

        // same object -> returns true
        assertTrue(attendance1.equals(attendance1));

        // same values -> returns true
        assertTrue(attendance1.equals(attendance2));

        // different event ID -> returns false
        assertFalse(attendance1.equals(attendance3));

        // different member name -> returns false
        assertFalse(attendance1.equals(attendance4));

        // null -> returns false
        assertFalse(attendance1.equals(null));

        // different type -> returns false
        assertFalse(attendance1.equals(5));
    }

    @Test
    public void toString_returnsCorrectFormat() {
        EventId eventId = new EventId("Event1");
        Name memberName = new Name("John Doe");
        Attendance attendance = new Attendance(eventId, memberName);

        String expected = "Attendance{eventId=Event1, memberName=John Doe}";
        assertEquals(expected, attendance.toString());
    }
}
