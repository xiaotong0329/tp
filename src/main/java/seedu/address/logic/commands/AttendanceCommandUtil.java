package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;
import java.util.Map;

import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.event.EventId;
import seedu.address.model.person.Name;

/**
 * Shared helpers for attendance-related commands.
 */
public final class AttendanceCommandUtil {

    private AttendanceCommandUtil() {
        // Utility class
    }

    /**
     * Collects attendance records for the given event and returns them mapped by member name.
     */
    public static Map<Name, Attendance> collectAttendanceByName(Model model, EventId eventId) {
        requireNonNull(model);
        requireNonNull(eventId);

        Map<Name, Attendance> result = new LinkedHashMap<>();
        model.getAddressBook().getAttendanceList().stream()
                .filter(attendance -> attendance.getEventId().equals(eventId))
                .forEach(attendance -> result.put(attendance.getMemberName(), attendance));
        return result;
    }
}
