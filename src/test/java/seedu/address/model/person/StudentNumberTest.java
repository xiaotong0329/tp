package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentNumber(null));
    }

    @Test
    public void constructor_invalidStudentNumber_throwsIllegalArgumentException() {
        String invalidStudentNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentNumber(invalidStudentNumber));
    }

    @Test
    public void isValidStudentNumber() {
        // null student number
        assertThrows(NullPointerException.class, () -> StudentNumber.isValidStudentNumber(null));

        // invalid student numbers
        assertFalse(StudentNumber.isValidStudentNumber("")); // empty string
        assertFalse(StudentNumber.isValidStudentNumber(" ")); // spaces only
        assertFalse(StudentNumber.isValidStudentNumber("A123456")); // too short
        assertFalse(StudentNumber.isValidStudentNumber("A12345678")); // too long
        assertFalse(StudentNumber.isValidStudentNumber("A1234567")); // missing check character
        assertFalse(StudentNumber.isValidStudentNumber("1234567X")); // missing prefix letter
        assertFalse(StudentNumber.isValidStudentNumber("A1234567X1")); // too long
        assertFalse(StudentNumber.isValidStudentNumber("A1234567@")); // invalid character
        assertFalse(StudentNumber.isValidStudentNumber("A1234567#")); // invalid character
        assertFalse(StudentNumber.isValidStudentNumber("A1234567$")); // invalid character

        // valid student numbers
        assertTrue(StudentNumber.isValidStudentNumber("A1234567X")); // standard format
        assertTrue(StudentNumber.isValidStudentNumber("B9876543Y")); // different prefix and check
        assertTrue(StudentNumber.isValidStudentNumber("C1111111Z")); // all same digits
        assertTrue(StudentNumber.isValidStudentNumber("D9999999A")); // all 9s
        assertTrue(StudentNumber.isValidStudentNumber("E5555555B")); // all 5s
        assertTrue(StudentNumber.isValidStudentNumber("F7777777C")); // all 7s
        assertTrue(StudentNumber.isValidStudentNumber("G3333333D")); // all 3s
        assertTrue(StudentNumber.isValidStudentNumber("H8888888E")); // all 8s
        assertTrue(StudentNumber.isValidStudentNumber("A1B2C3D4E")); // mixed alphanumeric
        assertTrue(StudentNumber.isValidStudentNumber("Z9Y8X7W6V")); // mixed alphanumeric
        assertTrue(StudentNumber.isValidStudentNumber("M5N4P3Q2R")); // mixed alphanumeric
        assertTrue(StudentNumber.isValidStudentNumber("S1T2U3V4W")); // mixed alphanumeric
    }

    @Test
    public void equals() {
        StudentNumber studentNumber = new StudentNumber("A1234567X");

        // same values -> returns true
        assertTrue(studentNumber.equals(new StudentNumber("A1234567X")));

        // same object -> returns true
        assertTrue(studentNumber.equals(studentNumber));

        // null -> returns false
        assertFalse(studentNumber.equals(null));

        // different types -> returns false
        assertFalse(studentNumber.equals(5.0f));

        // different values -> returns false
        assertFalse(studentNumber.equals(new StudentNumber("B2345678Y")));
    }

    @Test
    public void testHashCode() {
        StudentNumber studentNumber1 = new StudentNumber("A1234567X");
        StudentNumber studentNumber2 = new StudentNumber("A1234567X");
        StudentNumber studentNumber3 = new StudentNumber("B2345678Y");

        // same values -> returns same hash code
        assertEquals(studentNumber1.hashCode(), studentNumber2.hashCode());

        // different values -> returns different hash codes
        assertFalse(studentNumber1.hashCode() == studentNumber3.hashCode());
    }

    @Test
    public void toString_returnsCorrectString() {
        StudentNumber studentNumber = new StudentNumber("A1234567X");
        assertEquals("A1234567X", studentNumber.toString());
    }

    @Test
    public void value_returnsCorrectValue() {
        StudentNumber studentNumber = new StudentNumber("B9876543Y");
        assertEquals("B9876543Y", studentNumber.value);
    }
}
