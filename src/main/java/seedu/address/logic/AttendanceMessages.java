package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Name;

/**
 * Centralises user-facing messages and formatting helpers for attendance-related commands.
 */
public final class AttendanceMessages {

    public static final String LABEL_NONE = "None";

    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found";
    public static final String MESSAGE_MEMBER_NOT_FOUND = "Member not found: %1$s";
    public static final String MESSAGE_MEMBER_NOT_FOUND_IN_LIST = "Member not found in attendance list: %1$s";

    public static final String MESSAGE_ALREADY_ADDED_SINGLE = "Member already added: %1$s";
    public static final String MESSAGE_ALREADY_ADDED_MULTIPLE = "Members already added: %1$s";

    public static final String MESSAGE_ADD_ATTENDANCE_RESULT = "Attendance list for %1$s updated.\nAdded: %2$s%3$s";
    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS =
            "Attendance for %1$s marked.\nNewly marked: %2$s\nAlready marked: %3$s";
    public static final String MESSAGE_UNMARK_ATTENDANCE_SUCCESS =
            "Attendance for %1$s updated.\nNow absent: %2$s\nAlready absent: %3$s";
    public static final String MESSAGE_REMOVE_ATTENDANCE_SUCCESS =
            "Removed from attendance list for %1$s: %2$s";

    private AttendanceMessages() {
        // Utility class
    }

    /**
     * Formats a collection of names as a comma-separated list. Returns {@link #LABEL_NONE} when empty.
     */
    public static String formatNames(Collection<?> names) {
        requireNonNull(names);
        if (names.isEmpty()) {
            return LABEL_NONE;
        }
        return names.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    /**
     * Formats a message describing members that were already present.
     */
    public static String formatAlreadyAddedMembers(List<Name> duplicateMembers) {
        requireNonNull(duplicateMembers);
        String nameText = formatNames(duplicateMembers);
        if (duplicateMembers.size() <= 1) {
            return String.format(MESSAGE_ALREADY_ADDED_SINGLE, nameText);
        }
        return String.format(MESSAGE_ALREADY_ADDED_MULTIPLE, nameText);
    }

    /**
     * Builds the user-facing result message for adding attendance entries.
     */
    public static String buildAddAttendanceResult(String eventDescription,
                                                  List<Name> addedMembers,
                                                  List<Name> duplicateMembers) {
        requireNonNull(eventDescription);
        requireNonNull(addedMembers);
        requireNonNull(duplicateMembers);

        String duplicateSection = duplicateMembers.isEmpty()
                ? ""
                : "\n" + formatAlreadyAddedMembers(duplicateMembers);

        return String.format(MESSAGE_ADD_ATTENDANCE_RESULT,
                eventDescription,
                formatNames(addedMembers),
                duplicateSection);
    }

    /**
     * Builds the user-facing result message for marking attendance entries.
     */
    public static String buildMarkAttendanceResult(String eventDescription,
                                                   List<Name> newlyMarked,
                                                   List<Name> alreadyMarked) {
        requireNonNull(eventDescription);
        requireNonNull(newlyMarked);
        requireNonNull(alreadyMarked);

        return String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS,
                eventDescription,
                formatNames(newlyMarked),
                formatNames(alreadyMarked));
    }

    /**
     * Builds the user-facing result message for unmarking attendance entries.
     */
    public static String buildUnmarkAttendanceResult(String eventDescription,
                                                     List<Name> newlyAbsent,
                                                     List<Name> alreadyAbsent) {
        requireNonNull(eventDescription);
        requireNonNull(newlyAbsent);
        requireNonNull(alreadyAbsent);

        return String.format(MESSAGE_UNMARK_ATTENDANCE_SUCCESS,
                eventDescription,
                formatNames(newlyAbsent),
                formatNames(alreadyAbsent));
    }

    /**
     * Builds the user-facing result message for removing attendance entries.
     */
    public static String buildRemoveAttendanceResult(String eventDescription,
                                                     List<Name> removedMembers) {
        requireNonNull(eventDescription);
        requireNonNull(removedMembers);

        return String.format(MESSAGE_REMOVE_ATTENDANCE_SUCCESS,
                eventDescription,
                formatNames(removedMembers));
    }
}
