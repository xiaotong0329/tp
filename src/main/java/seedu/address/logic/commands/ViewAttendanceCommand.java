package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;

/**
 * Views attendance for an event.
 */
public class ViewAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "viewattendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views attendance for an event. "
            + "Parameters: e/EVENTID\n"
            + "Example: " + COMMAND_WORD + " e/Orientation2023";

    public static final String MESSAGE_SUCCESS = "Attendance for %1$s:\n%2$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found";
    public static final String MESSAGE_NO_ATTENDANCE = "No attendance recorded yet.";

    private final EventId eventId;

    /**
     * Creates a ViewAttendanceCommand to view attendance for the specified event.
     */
    public ViewAttendanceCommand(EventId eventId) {
        requireNonNull(eventId);
        this.eventId = eventId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if event exists
        Event event = model.getEventByEventId(eventId);
        if (event == null) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        // Get all attendance records for this event
        List<Attendance> eventAttendances = model.getAddressBook().getAttendanceList().stream()
                .filter(attendance -> attendance.getEventId().equals(eventId))
                .collect(Collectors.toList());

        if (eventAttendances.isEmpty()) {
            return new CommandResult(MESSAGE_NO_ATTENDANCE);
        }

        // Format the attendance list with their status
        String attendanceList = eventAttendances.stream()
                .map(attendance -> {
                    String status = attendance.hasAttended() ? "Attended" : "Absent";
                    return "• " + attendance.getMemberName() + " (" + status + ")";
                })
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_SUCCESS, event.getDescription(), attendanceList));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewAttendanceCommand)) {
            return false;
        }

        ViewAttendanceCommand otherViewAttendanceCommand = (ViewAttendanceCommand) other;
        return eventId.equals(otherViewAttendanceCommand.eventId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventId", eventId)
                .toString();
    }
}
