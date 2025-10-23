package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

/**
 * Adds members to the attendance list of an event.
 */
public class AddAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "addattendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds members to an event's attendance list. "
            + "Parameters: e/EVENTID m/MEMBER[/MEMBER]...\n"
            + "Example: " + COMMAND_WORD + " e/Orientation2023 m/John Doe/Jane Smith";

    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found";
    public static final String MESSAGE_MEMBER_NOT_FOUND = "Member not found: %1$s";
    public static final String MESSAGE_RESULT = "Attendance list for %1$s updated.\nAdded: %2$s%3$s";

    private final EventId eventId;
    private final List<Name> memberNames;
    /**
     * Creates an {@code AddAttendanceCommand} to add the given members to the specified event.
     *
     * @param eventId The ID of the event to which members are added.
     * @param memberNames The list of member names to add to the event's attendance.
     */
    public AddAttendanceCommand(EventId eventId, List<Name> memberNames) {
        requireNonNull(eventId);
        requireNonNull(memberNames);
        this.eventId = eventId;
        this.memberNames = List.copyOf(memberNames);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Event event = model.getEventByEventId(eventId);
        if (event == null) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        Set<Name> uniqueNames = new LinkedHashSet<>(memberNames);
        List<Name> addedMembers = new ArrayList<>();
        List<Name> duplicateMembers = new ArrayList<>();

        for (Name name : uniqueNames) {
            if (!memberExists(model, name)) {
                throw new CommandException(String.format(MESSAGE_MEMBER_NOT_FOUND, name));
            }

            Attendance attendance = new Attendance(eventId, name);
            if (model.hasAttendance(attendance)) {
                duplicateMembers.add(name);
                continue;
            }

            model.addAttendance(attendance);
            addedMembers.add(name);
        }

        String addedText = formatNames(addedMembers);
        String duplicateMessage = duplicateMembers.isEmpty()
                ? ""
                : "\n" + formatAlreadyAddedMessage(duplicateMembers);
        return new CommandResult(String.format(MESSAGE_RESULT, event.getDescription(), addedText, duplicateMessage));
    }

    private boolean memberExists(Model model, Name name) {
        return model.getAddressBook().getPersonList().stream()
                .anyMatch(person -> person.getName().equals(name));
    }

    private String formatNames(List<Name> names) {
        if (names.isEmpty()) {
            return "None";
        }
        return names.stream().map(Name::toString).collect(Collectors.joining(", "));
    }

    private String formatAlreadyAddedMessage(List<Name> names) {
        String nameText = formatNames(names);
        if (names.size() == 1) {
            return "Member already added: " + nameText;
        }
        return "Members already added: " + nameText;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddAttendanceCommand)) {
            return false;
        }

        AddAttendanceCommand otherCommand = (AddAttendanceCommand) other;
        return eventId.equals(otherCommand.eventId)
                && memberNames.equals(otherCommand.memberNames);
    }
}
