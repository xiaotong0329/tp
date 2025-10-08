package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Marks a member as attended for an event.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "markattendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a member as attended for an event. "
            + "Parameters: e/EVENTID m/MEMBERNAME\n"
            + "Example: " + COMMAND_WORD + " e/Orientation2023 m/John Doe";

    public static final String MESSAGE_SUCCESS = "Marked %1$s as attended for %2$s.";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found";
    public static final String MESSAGE_MEMBER_NOT_FOUND = "Member not found";
    public static final String MESSAGE_ALREADY_ATTENDED = "Member is already marked as attended for this event";

    private final EventId eventId;
    private final Name memberName;

    /**
     * Creates a MarkAttendanceCommand to mark the specified member as attended for the specified event.
     */
    public MarkAttendanceCommand(EventId eventId, Name memberName) {
        requireNonNull(eventId);
        requireNonNull(memberName);
        this.eventId = eventId;
        this.memberName = memberName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if event exists
        Event event = model.getEventByEventId(eventId);
        if (event == null) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        // Find the member by name
        Person member = findMemberByName(model, memberName);
        if (member == null) {
            throw new CommandException(MESSAGE_MEMBER_NOT_FOUND);
        }

        // Create attendance record
        Attendance attendance = new Attendance(eventId, memberName);

        // Check if already attended (duplicate handling)
        if (model.hasAttendance(attendance)) {
            // Ignore duplicate as per requirements
            return new CommandResult(String.format(MESSAGE_SUCCESS, memberName, event.getDescription()));
        }

        // Add attendance record
        model.addAttendance(attendance);
        return new CommandResult(String.format(MESSAGE_SUCCESS, memberName, event.getDescription()));
    }

    /**
     * Finds a member by name in the address book.
     * Returns null if not found.
     */
    private Person findMemberByName(Model model, Name name) {
        return model.getFilteredPersonList().stream()
                .filter(person -> person.getName().equals(name))
                .findFirst()
                .orElse(null);
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
                && memberName.equals(otherMarkAttendanceCommand.memberName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventId", eventId)
                .add("memberName", memberName)
                .toString();
    }
}
