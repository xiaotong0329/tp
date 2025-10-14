package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

/**
 * Marks members as attended for an event.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "markattendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks members as attended for an event. "
            + "Parameters: e/EVENTID m/MEMBER[/MEMBER]...\n"
            + "Example: " + COMMAND_WORD + " e/Orientation2023 m/John Doe/Jane Smith";

    public static final String MESSAGE_SUCCESS = "Attendance for %1$s marked.";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found";
    public static final String MESSAGE_MEMBER_NOT_FOUND = "Member not found in attendance list: %1$s";

    private final EventId eventId;
    private final List<Name> memberNames;

    /**
     * Creates a MarkAttendanceCommand to mark the specified members as attended for the specified event.
     */
    public MarkAttendanceCommand(EventId eventId, List<Name> memberNames) {
        requireNonNull(eventId);
        requireNonNull(memberNames);
        this.eventId = eventId;
        this.memberNames = List.copyOf(memberNames);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if event exists
        Event event = model.getEventByEventId(eventId);
        if (event == null) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        Map<Name, Attendance> attendanceByName = collectAttendance(model);

        List<Name> targetNames = new ArrayList<>(new LinkedHashSet<>(memberNames));

        List<Name> newlyMarked = new ArrayList<>();
        List<Name> alreadyMarked = new ArrayList<>();

        for (Name name : targetNames) {
            Attendance attendance = attendanceByName.get(name);
            if (attendance == null) {
                throw new CommandException(String.format(MESSAGE_MEMBER_NOT_FOUND, name));
            }

            if (attendance.hasAttended()) {
                alreadyMarked.add(name);
                continue;
            }

            Attendance updatedAttendance = attendance.markAttended();
            model.setAttendance(attendance, updatedAttendance);
            newlyMarked.add(name);
        }

        String markedText = formatNames(newlyMarked);
        String alreadyMarkedText = formatNames(alreadyMarked);
        return new CommandResult(String.format(MESSAGE_SUCCESS, event.getDescription(), markedText, alreadyMarkedText));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAttendanceCommand)) {
            return false;
        }

        MarkAttendanceCommand otherMarkAttendanceCommand = (MarkAttendanceCommand) other;
        return eventId.equals(otherMarkAttendanceCommand.eventId)
                && memberNames.equals(otherMarkAttendanceCommand.memberNames);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventId", eventId)
                .add("memberNames", memberNames)
                .toString();
    }

    private Map<Name, Attendance> collectAttendance(Model model) {
        Map<Name, Attendance> result = new LinkedHashMap<>();
        model.getAddressBook().getAttendanceList().stream()
                .filter(attendance -> attendance.getEventId().equals(eventId))
                .forEach(attendance -> result.put(attendance.getMemberName(), attendance));
        return result;
    }

    private String formatNames(List<Name> names) {
        if (names.isEmpty()) {
            return "None";
        }
        return names.stream().map(Name::toString).collect(Collectors.joining(", "));
    }
}
