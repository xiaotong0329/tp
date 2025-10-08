package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewAttendanceCommand.
 */
public class ViewAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_viewAttendance_success() {
        // Add an event to the model
        Event event = new seedu.address.testutil.EventBuilder()
                .withEventId("testEvent")
                .withDate("2023-12-25")
                .withDescription("Test Event")
                .build();
        model.addEvent(event);
        
        EventId eventId = event.getEventId();

        // Add some attendance records
        model.addAttendance(new Attendance(eventId, ALICE.getName()));
        model.addAttendance(new Attendance(eventId, BOB.getName()));

        ViewAttendanceCommand viewAttendanceCommand = new ViewAttendanceCommand(eventId);

        String expectedMessage = String.format(ViewAttendanceCommand.MESSAGE_SUCCESS, event.getDescription(),
                "• " + ALICE.getName() + "\n• " + BOB.getName());

        assertCommandSuccess(viewAttendanceCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        EventId nonExistentEventId = new EventId("NonExistentEvent");

        ViewAttendanceCommand viewAttendanceCommand = new ViewAttendanceCommand(nonExistentEventId);

        assertCommandFailure(viewAttendanceCommand, model, ViewAttendanceCommand.MESSAGE_EVENT_NOT_FOUND);
    }

    @Test
    public void execute_noAttendanceRecorded_showsNoAttendanceMessage() {
        // Add an event to the model
        Event event = new seedu.address.testutil.EventBuilder()
                .withEventId("testEvent")
                .withDate("2023-12-25")
                .withDescription("Test Event")
                .build();
        model.addEvent(event);
        
        EventId eventId = event.getEventId();

        ViewAttendanceCommand viewAttendanceCommand = new ViewAttendanceCommand(eventId);

        assertCommandSuccess(viewAttendanceCommand, model, ViewAttendanceCommand.MESSAGE_NO_ATTENDANCE, model);
    }

    @Test
    public void equals() {
        EventId eventId1 = new EventId("Event1");
        EventId eventId2 = new EventId("Event2");

        ViewAttendanceCommand viewAttendanceCommand1 = new ViewAttendanceCommand(eventId1);
        ViewAttendanceCommand viewAttendanceCommand2 = new ViewAttendanceCommand(eventId1);
        ViewAttendanceCommand viewAttendanceCommand3 = new ViewAttendanceCommand(eventId2);

        // same object -> returns true
        assertTrue(viewAttendanceCommand1.equals(viewAttendanceCommand1));

        // same values -> returns true
        assertTrue(viewAttendanceCommand1.equals(viewAttendanceCommand2));

        // different eventId -> returns false
        assertFalse(viewAttendanceCommand1.equals(viewAttendanceCommand3));

        // null -> returns false
        assertFalse(viewAttendanceCommand1.equals(null));

        // different type -> returns false
        assertFalse(viewAttendanceCommand1.equals(5));
    }
}
