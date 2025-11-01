package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.AttendanceMessages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnmarkAttendanceCommand.
 */
public class UnmarkAttendanceCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unmarkAttendance_success() {
        Event event = new seedu.address.testutil.EventBuilder()
                .withEventId("testEvent")
                .withDate("2023-12-25")
                .withDescription("Test Event")
                .build();
        model.addEvent(event);

        EventId eventId = event.getEventId();
        Name memberName = ALICE.getName();
        Attendance initialAttendance = new Attendance(eventId, memberName, true);
        model.addAttendance(initialAttendance);

        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(eventId, List.of(memberName));

        String expectedMessage = AttendanceMessages.buildUnmarkAttendanceResult(
                event.getEventId().toString(), List.of(memberName), List.of());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAttendance(new Attendance(eventId, memberName, true),
                new Attendance(eventId, memberName, false));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_memberAlreadyAbsent_success() {
        Event event = new seedu.address.testutil.EventBuilder()
                .withEventId("testEvent")
                .withDate("2023-12-25")
                .withDescription("Test Event")
                .build();
        model.addEvent(event);

        EventId eventId = event.getEventId();
        Name memberName = ALICE.getName();
        Attendance initialAttendance = new Attendance(eventId, memberName, false);
        model.addAttendance(initialAttendance);

        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(eventId, List.of(memberName));

        String expectedMessage = AttendanceMessages.buildUnmarkAttendanceResult(
                event.getEventId().toString(), List.of(), List.of(memberName));

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        EventId nonExistentEventId = new EventId("NonExistentEvent");
        Name memberName = ALICE.getName();

        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(nonExistentEventId, List.of(memberName));

        assertCommandFailure(command, model, UnmarkAttendanceCommand.MESSAGE_EVENT_NOT_FOUND);
    }

    @Test
    public void execute_memberNotFound_throwsCommandException() {
        Event event = new seedu.address.testutil.EventBuilder()
                .withEventId("testEvent")
                .withDate("2023-12-25")
                .withDescription("Test Event")
                .build();
        model.addEvent(event);

        EventId eventId = event.getEventId();
        Name nonExistentMember = new Name("Non Existent Member");

        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(eventId, List.of(nonExistentMember));

        assertCommandFailure(command, model,
                String.format(UnmarkAttendanceCommand.MESSAGE_MEMBER_NOT_FOUND, nonExistentMember));
    }

    @Test
    public void equals() {
        EventId eventId1 = new EventId("Event1");
        EventId eventId2 = new EventId("Event2");
        Name memberName1 = new Name("Member1");
        Name memberName2 = new Name("Member2");

        UnmarkAttendanceCommand command1 = new UnmarkAttendanceCommand(eventId1, List.of(memberName1));
        UnmarkAttendanceCommand command2 = new UnmarkAttendanceCommand(eventId1, List.of(memberName1));
        UnmarkAttendanceCommand command3 = new UnmarkAttendanceCommand(eventId2, List.of(memberName1));
        UnmarkAttendanceCommand command4 = new UnmarkAttendanceCommand(eventId1, List.of(memberName2));

        assertTrue(command1.equals(command2));
        assertTrue(command1.equals(command1));
        assertFalse(command1.equals(command3));
        assertFalse(command1.equals(command4));
        assertFalse(command1.equals(null));
        assertFalse(command1.equals(5));
    }
}
