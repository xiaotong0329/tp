package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.AttendanceMessages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@link AddAttendanceCommand}.
 */
public class AddAttendanceCommandTest {

    private static final Event EVENT = new EventBuilder().withEventId("attendanceEvent").withDescription("Demo")
            .withDate("2024-10-10").build();

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        model.addEvent(EVENT);
        model.addPerson(new PersonBuilder(ALICE).build());
        model.addPerson(new PersonBuilder(BENSON).build());
    }

    @Test
    public void execute_addMembers_success() throws CommandException {
        List<Name> membersToAdd = List.of(ALICE.getName(), BENSON.getName());
        AddAttendanceCommand command = new AddAttendanceCommand(EVENT.getEventId(), membersToAdd);

        String expectedMessage = AttendanceMessages.buildAddAttendanceResult(EVENT.getEventId().toString(),
                membersToAdd, List.of());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAttendance(new Attendance(EVENT.getEventId(), ALICE.getName()));
        expectedModel.addAttendance(new Attendance(EVENT.getEventId(), BENSON.getName()));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_withExistingAttendance_reportsDuplicates() throws CommandException {
        model.addAttendance(new Attendance(EVENT.getEventId(), ALICE.getName()));

        List<Name> members = List.of(ALICE.getName(), BENSON.getName());
        AddAttendanceCommand command = new AddAttendanceCommand(EVENT.getEventId(), members);

        String expectedMessage = AttendanceMessages.buildAddAttendanceResult(EVENT.getEventId().toString(),
                List.of(BENSON.getName()), List.of(ALICE.getName()));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAttendance(new Attendance(EVENT.getEventId(), BENSON.getName()));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        EventId missingEventId = new EventId("MissingEvent");
        AddAttendanceCommand command = new AddAttendanceCommand(missingEventId, List.of(ALICE.getName()));

        assertCommandFailure(command, model, AddAttendanceCommand.MESSAGE_EVENT_NOT_FOUND);
    }

    @Test
    public void execute_memberNotFound_throwsCommandException() {
        Name unknownMember = new Name("Unknown Member");
        AddAttendanceCommand command = new AddAttendanceCommand(EVENT.getEventId(), List.of(unknownMember));

        assertCommandFailure(command, model,
                String.format(AddAttendanceCommand.MESSAGE_MEMBER_NOT_FOUND, unknownMember));
    }

    @Test
    public void equals() {
        AddAttendanceCommand command = new AddAttendanceCommand(EVENT.getEventId(), List.of(ALICE.getName()));
        AddAttendanceCommand sameCommand = new AddAttendanceCommand(EVENT.getEventId(), List.of(ALICE.getName()));
        AddAttendanceCommand differentMembers = new AddAttendanceCommand(EVENT.getEventId(),
                List.of(BENSON.getName()));
        AddAttendanceCommand differentEvent = new AddAttendanceCommand(new EventId("Other"),
                List.of(ALICE.getName()));

        assertTrue(command.equals(command));
        assertTrue(command.equals(sameCommand));
        assertFalse(command.equals(differentMembers));
        assertFalse(command.equals(differentEvent));
        assertFalse(command.equals(null));
        assertFalse(command.equals(5));
    }
}
