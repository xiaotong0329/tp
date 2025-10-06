package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class YearTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Year(null));
    }

    @Test
    public void constructor_invalidYear_throwsIllegalArgumentException() {
        String invalidYear = "";
        assertThrows(IllegalArgumentException.class, () -> new Year(invalidYear));
    }

    @Test
    public void isValidYear() {
        // null year
        assertThrows(NullPointerException.class, () -> Year.isValidYear(null));

        // invalid years
        assertFalse(Year.isValidYear("")); // empty string
        assertFalse(Year.isValidYear(" ")); // spaces only
        assertFalse(Year.isValidYear("0")); // below minimum
        assertFalse(Year.isValidYear("11")); // above maximum
        assertFalse(Year.isValidYear("12")); // above maximum
        assertFalse(Year.isValidYear("-1")); // negative
        assertFalse(Year.isValidYear("abc")); // non-numeric
        assertFalse(Year.isValidYear("1.5")); // decimal
        assertFalse(Year.isValidYear("01")); // leading zero
        assertFalse(Year.isValidYear("10.0")); // decimal representation of valid number

        // valid years
        assertTrue(Year.isValidYear("1")); // minimum valid
        assertTrue(Year.isValidYear("2"));
        assertTrue(Year.isValidYear("3"));
        assertTrue(Year.isValidYear("4"));
        assertTrue(Year.isValidYear("5"));
        assertTrue(Year.isValidYear("6"));
        assertTrue(Year.isValidYear("7"));
        assertTrue(Year.isValidYear("8"));
        assertTrue(Year.isValidYear("9"));
        assertTrue(Year.isValidYear("10")); // maximum valid
    }

    @Test
    public void equals() {
        Year year = new Year("3");

        // same values -> returns true
        assertTrue(year.equals(new Year("3")));

        // same object -> returns true
        assertTrue(year.equals(year));

        // null -> returns false
        assertFalse(year.equals(null));

        // different types -> returns false
        assertFalse(year.equals(5.0f));

        // different values -> returns false
        assertFalse(year.equals(new Year("4")));
    }

    @Test
    public void testHashCode() {
        Year year1 = new Year("3");
        Year year2 = new Year("3");
        Year year3 = new Year("4");

        // same values -> returns same hash code
        assertEquals(year1.hashCode(), year2.hashCode());

        // different values -> returns different hash codes
        assertFalse(year1.hashCode() == year3.hashCode());
    }

    @Test
    public void toString_returnsCorrectString() {
        Year year = new Year("3");
        assertEquals("3", year.toString());
    }

    @Test
    public void value_returnsCorrectValue() {
        Year year = new Year("5");
        assertEquals(5, year.year);
    }
}
