package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;
import seedu.address.testutil.EventBuilder;

public class ViewAttendeesCommandTest {

    private static final Event EVENT = new EventBuilder().withEventId("ViewEvent").withDescription("View")
            .withDate("2024-06-06").build();

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        model.addEvent(EVENT);
    }

    @Test
    public void execute_eventWithAttendees_success() {
        model.addAttendance(new Attendance(EVENT.getEventId(), new Name("Alice"), true));
        model.addAttendance(new Attendance(EVENT.getEventId(), new Name("Bob"), false));

        ViewAttendeesCommand command = new ViewAttendeesCommand(EVENT.getEventId());
        String expectedList = "• Alice (Attended)\n• Bob (Absent)";
        String expectedMessage = String.format(ViewAttendeesCommand.MESSAGE_SUCCESS, EVENT.getEventId(), expectedList);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_eventWithoutAttendees_showsEmptyMessage() {
        ViewAttendeesCommand command = new ViewAttendeesCommand(EVENT.getEventId());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(command, model, ViewAttendeesCommand.MESSAGE_NO_ATTENDANCE, expectedModel);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        ViewAttendeesCommand command = new ViewAttendeesCommand(new EventId("Missing"));
        assertCommandFailure(command, model, ViewAttendeesCommand.MESSAGE_EVENT_NOT_FOUND);
    }
}
