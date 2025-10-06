package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DietaryRequirementsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DietaryRequirements(null));
    }

    @Test
    public void constructor_invalidDietaryRequirements_throwsIllegalArgumentException() {
        String invalidDietaryRequirements = "";
        assertThrows(IllegalArgumentException.class, () -> new DietaryRequirements(invalidDietaryRequirements));
    }

    @Test
    public void isValidDietaryRequirements() {
        // null dietary requirements
        assertThrows(NullPointerException.class, () -> DietaryRequirements.isValidDietaryRequirements(null));

        // invalid dietary requirements
        assertFalse(DietaryRequirements.isValidDietaryRequirements("")); // empty string
        assertFalse(DietaryRequirements.isValidDietaryRequirements(" ")); // spaces only
        assertFalse(DietaryRequirements.isValidDietaryRequirements("   ")); // multiple spaces only

        // valid dietary requirements
        assertTrue(DietaryRequirements.isValidDietaryRequirements("No restrictions")); // simple text
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Vegetarian")); // single word
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Vegan")); // single word
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Halal")); // single word
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Kosher")); // single word
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Gluten-free")); // hyphenated
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Dairy-free")); // hyphenated
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Nut allergy")); // two words
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Seafood allergy")); // two words
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Multiple allergies")); // two words
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Vegetarian (no eggs)")); // with parentheses
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Halal and gluten-free")); // with conjunction
        assertTrue(DietaryRequirements.isValidDietaryRequirements("No restrictions, vegetarian preferred")); // with comma
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Lactose intolerant")); // medical condition
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Celiac disease")); // medical condition
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Diabetes - low sugar")); // with dash
        assertTrue(DietaryRequirements.isValidDietaryRequirements("High blood pressure - low sodium")); // with dash
        assertTrue(DietaryRequirements.isValidDietaryRequirements("A")); // single character
        assertTrue(DietaryRequirements.isValidDietaryRequirements("123")); // numbers
        assertTrue(DietaryRequirements.isValidDietaryRequirements("No restrictions!")); // with exclamation
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Vegetarian?")); // with question mark
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Halal & Kosher")); // with ampersand
        assertTrue(DietaryRequirements.isValidDietaryRequirements("Gluten-free, dairy-free, nut-free")); // multiple restrictions
    }

    @Test
    public void equals() {
        DietaryRequirements dietaryRequirements = new DietaryRequirements("Vegetarian");

        // same values -> returns true
        assertTrue(dietaryRequirements.equals(new DietaryRequirements("Vegetarian")));

        // same object -> returns true
        assertTrue(dietaryRequirements.equals(dietaryRequirements));

        // null -> returns false
        assertFalse(dietaryRequirements.equals(null));

        // different types -> returns false
        assertFalse(dietaryRequirements.equals(5.0f));

        // different values -> returns false
        assertFalse(dietaryRequirements.equals(new DietaryRequirements("Vegan")));
    }

    @Test
    public void testHashCode() {
        DietaryRequirements dietaryRequirements1 = new DietaryRequirements("Vegetarian");
        DietaryRequirements dietaryRequirements2 = new DietaryRequirements("Vegetarian");
        DietaryRequirements dietaryRequirements3 = new DietaryRequirements("Vegan");

        // same values -> returns same hash code
        assertEquals(dietaryRequirements1.hashCode(), dietaryRequirements2.hashCode());

        // different values -> returns different hash codes
        assertFalse(dietaryRequirements1.hashCode() == dietaryRequirements3.hashCode());
    }

    @Test
    public void toString_returnsCorrectString() {
        DietaryRequirements dietaryRequirements = new DietaryRequirements("Vegetarian");
        assertEquals("Vegetarian", dietaryRequirements.toString());
    }

    @Test
    public void value_returnsCorrectValue() {
        DietaryRequirements dietaryRequirements = new DietaryRequirements("No restrictions");
        assertEquals("No restrictions", dietaryRequirements.value);
    }

    @Test
    public void constructor_withLeadingAndTrailingSpaces_trimmed() {
        DietaryRequirements dietaryRequirements = new DietaryRequirements("  Vegetarian  ");
        assertEquals("Vegetarian", dietaryRequirements.value);
    }

    @Test
    public void constructor_withSpecialCharacters_success() {
        String[] specialCases = {
            "Vegetarian (no eggs)",
            "Halal & Kosher",
            "Gluten-free, dairy-free",
            "No restrictions!",
            "Diabetes - low sugar",
            "A1B2C3",
            "José María - Vegetarian"
        };

        for (String specialCase : specialCases) {
            DietaryRequirements dietaryRequirements = new DietaryRequirements(specialCase);
            assertEquals(specialCase, dietaryRequirements.value);
        }
    }
}
