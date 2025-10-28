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
 * Views attendees for an event.
 */
public class ViewAttendeesCommand extends Command {

    public static final String COMMAND_WORD = "viewattendees";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found";
    public static final String MESSAGE_SUCCESS = "Attendees for %1$s:\n%2$s";
    public static final String MESSAGE_NO_ATTENDANCE = "No attendees recorded yet.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views attendees for an event. "
            + "Format: " + COMMAND_WORD + " e/EVENTID\n"
            + "Example: " + COMMAND_WORD + " e/Orientation2023";

    private final EventId eventId;

    /**
     * Creates a ViewAttendeesCommand to view attendees for the specified event.
     */
    public ViewAttendeesCommand(EventId eventId) {
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

        return new CommandResult(String.format(MESSAGE_SUCCESS, event.getEventId(), attendanceList));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewAttendeesCommand)) {
            return false;
        }

        ViewAttendeesCommand otherViewAttendeesCommand = (ViewAttendeesCommand) other;
        return eventId.equals(otherViewAttendeesCommand.eventId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventId", eventId)
                .toString();
    }
}
