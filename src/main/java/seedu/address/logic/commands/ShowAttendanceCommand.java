package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;

/**
 * Displays a summary of attendance for an event.
 */
public class ShowAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "showattendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a summary of attendance for an event. "
            + "Parameters: e/EVENTID\n"
            + "Example: " + COMMAND_WORD + " e/Orientation2023";

    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found";
    public static final String MESSAGE_SUCCESS =
        "Attendance summary for %1$s:\n"
        + "Attended (%2$d): %3$s\n"
        + "Absent (%4$d): %5$s";

    private final EventId eventId;

    /**
     * Creates a {@code ShowAttendanceCommand} to display the attendance list
     * of the event identified by the given {@code eventId}.
     *
     * @param eventId The ID of the event whose attendance list is to be shown.
     */
    public ShowAttendanceCommand(EventId eventId) {
        requireNonNull(eventId);
        this.eventId = eventId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Event event = model.getEventByEventId(eventId);
        if (event == null) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        List<Attendance> eventAttendances = model.getAddressBook().getAttendanceList().stream()
                .filter(attendance -> attendance.getEventId().equals(eventId))
                .collect(Collectors.toList());

        List<String> attendedNames = eventAttendances.stream()
                .filter(Attendance::hasAttended)
                .map(attendance -> attendance.getMemberName().toString())
                .collect(Collectors.toList());

        List<String> absentNames = eventAttendances.stream()
                .filter(attendance -> !attendance.hasAttended())
                .map(attendance -> attendance.getMemberName().toString())
                .collect(Collectors.toList());

        String attendedText = formatNames(attendedNames);
        String absentText = formatNames(absentNames);

        return new CommandResult(String.format(MESSAGE_SUCCESS, event.getDescription(),
                attendedNames.size(), attendedText, absentNames.size(), absentText));
    }

    private String formatNames(List<String> names) {
        if (names.isEmpty()) {
            return "None";
        }
        return String.join(", ", names);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ShowAttendanceCommand)) {
            return false;
        }

        ShowAttendanceCommand otherCommand = (ShowAttendanceCommand) other;
        return eventId.equals(otherCommand.eventId);
    }
}

