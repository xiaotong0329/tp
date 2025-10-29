package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.AttendanceMessages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;
import seedu.address.testutil.EventBuilder;

public class ShowAttendanceCommandTest {

    private static final Event EVENT = new EventBuilder().withEventId("ShowEvent").withDescription("Summary")
            .withDate("2024-05-05").build();

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        model.addEvent(EVENT);
    }

    @Test
    public void execute_eventWithAttendance_success() {
        model.addAttendance(new Attendance(EVENT.getEventId(), new Name("Alice Tan"), true));
        model.addAttendance(new Attendance(EVENT.getEventId(), new Name("Bob Lim"), false));

        ShowAttendanceCommand command = new ShowAttendanceCommand(EVENT.getEventId());

        String expectedMessage = String.format(ShowAttendanceCommand.MESSAGE_SUCCESS, EVENT.getEventId(),
                1, AttendanceMessages.formatNames(java.util.List.of("Alice Tan")),
                1, AttendanceMessages.formatNames(java.util.List.of("Bob Lim")));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_eventWithNoAttendance_showsEmptySummary() {
        ShowAttendanceCommand command = new ShowAttendanceCommand(EVENT.getEventId());

        String expectedMessage = String.format(ShowAttendanceCommand.MESSAGE_SUCCESS, EVENT.getEventId(),
                0, AttendanceMessages.LABEL_NONE, 0, AttendanceMessages.LABEL_NONE);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        ShowAttendanceCommand command = new ShowAttendanceCommand(new EventId("Missing"));
        assertCommandFailure(command, model, ShowAttendanceCommand.MESSAGE_EVENT_NOT_FOUND);
    }
}
