package seedu.address.model.attendance.exceptions;

/**
 * Signals that the operation will result in duplicate Attendance records
 * (Attendance records are considered duplicates if they have the same identity).
 */
public class DuplicateAttendanceException extends RuntimeException {
    public DuplicateAttendanceException() {
        super("Operation would result in duplicate attendance records");
    }
}
