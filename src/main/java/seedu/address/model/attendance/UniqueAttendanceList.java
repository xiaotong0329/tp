package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.UniqueList;
import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.DuplicateAttendanceException;
import seedu.address.model.person.Name;

/**
 * A list of attendance records that enforces uniqueness between its elements and does not allow nulls.
 * An attendance record is considered unique by comparing using {@code Attendance#isSameAttendance(Attendance)}.
 * As such, adding and updating of attendance records uses Attendance#isSameAttendance(Attendance) for equality
 * so as to ensure that the attendance record being added or updated is unique in terms of identity in the
 * UniqueAttendanceList. However, the removal of an attendance record uses Attendance#equals(Object) so
 * as to ensure that the attendance record with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Attendance#isSameAttendance(Attendance)
 */
public class UniqueAttendanceList extends UniqueList<Attendance> {

    /**
     * Replaces the attendance record {@code target} in the list with {@code editedAttendance}.
     * {@code target} must exist in the list.
     * The attendance record identity of {@code editedAttendance} must not be the same as another existing
     * attendance record in the list.
     */
    public void setAttendance(Attendance target, Attendance editedAttendance) {
        setElement(target, editedAttendance);
    }

    /**
     * Replaces the contents of this list with {@code attendances}.
     * {@code attendances} must not contain duplicate attendance records.
     */
    public void setAttendances(List<Attendance> attendances) {
        setElements(attendances);
    }

    public void setAttendances(UniqueAttendanceList replacement) {
        requireNonNull(replacement);
        setAllFromOther(replacement);
    }

    /**
     * Renames the member in all attendance records from {@code oldName} to {@code newName}.
     */
    public void renameMember(Name oldName, Name newName) {
        requireNonNull(oldName);
        requireNonNull(newName);
        List<Attendance> updatedAttendances = new ArrayList<>();
        for (Attendance attendance : internalList) {
            if (attendance.getMemberName().equals(oldName)) {
                updatedAttendances.add(new Attendance(attendance.getEventId(), newName, attendance.hasAttended()));
            } else {
                updatedAttendances.add(attendance);
            }
        }
        setElements(updatedAttendances);
    }

    /**
     * Removes all attendance records that belong to {@code memberName}.
     */
    public void removeAttendancesByMember(Name memberName) {
        requireNonNull(memberName);
        internalList.removeIf(attendance -> attendance.getMemberName().equals(memberName));
    }

    @Override
    protected boolean isSameElement(Attendance attendance1, Attendance attendance2) {
        return attendance1.isSameAttendance(attendance2);
    }

    @Override
    protected RuntimeException createDuplicateException() {
        return new DuplicateAttendanceException();
    }

    @Override
    protected RuntimeException createNotFoundException() {
        return new AttendanceNotFoundException();
    }
}
