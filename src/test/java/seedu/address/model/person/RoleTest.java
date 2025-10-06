package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid roles
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only
        assertFalse(Role.isValidRole("   ")); // multiple spaces only

        // valid roles
        assertTrue(Role.isValidRole("President")); // single word
        assertTrue(Role.isValidRole("Vice President")); // two words
        assertTrue(Role.isValidRole("Secretary")); // single word
        assertTrue(Role.isValidRole("Treasurer")); // single word
        assertTrue(Role.isValidRole("Member")); // single word
        assertTrue(Role.isValidRole("Committee Member")); // two words
        assertTrue(Role.isValidRole("Event Coordinator")); // two words
        assertTrue(Role.isValidRole("Public Relations Officer")); // three words
        assertTrue(Role.isValidRole("Social Media Manager")); // three words
        assertTrue(Role.isValidRole("Volunteer Coordinator")); // two words
        assertTrue(Role.isValidRole("Fundraising Chair")); // two words
        assertTrue(Role.isValidRole("Academic Representative")); // two words
        assertTrue(Role.isValidRole("Sports Captain")); // two words
        assertTrue(Role.isValidRole("Cultural Representative")); // two words
        assertTrue(Role.isValidRole("A")); // single character
        assertTrue(Role.isValidRole("123")); // numbers
        assertTrue(Role.isValidRole("President!")); // with exclamation
        assertTrue(Role.isValidRole("Vice-President")); // with hyphen
        assertTrue(Role.isValidRole("Co-President")); // with hyphen
        assertTrue(Role.isValidRole("President & CEO")); // with ampersand
        assertTrue(Role.isValidRole("José María - President")); // with special characters
        assertTrue(Role.isValidRole("President (Interim)")); // with parentheses
        assertTrue(Role.isValidRole("President, Vice President")); // with comma
        assertTrue(Role.isValidRole("President?")); // with question mark
    }

    @Test
    public void equals() {
        Role role = new Role("President");

        // same values -> returns true
        assertTrue(role.equals(new Role("President")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("Vice President")));
    }

    @Test
    public void testHashCode() {
        Role role1 = new Role("President");
        Role role2 = new Role("President");
        Role role3 = new Role("Vice President");

        // same values -> returns same hash code
        assertEquals(role1.hashCode(), role2.hashCode());

        // different values -> returns different hash codes
        assertFalse(role1.hashCode() == role3.hashCode());
    }

    @Test
    public void toString_returnsCorrectString() {
        Role role = new Role("President");
        assertEquals("President", role.toString());
    }

    @Test
    public void value_returnsCorrectValue() {
        Role role = new Role("Vice President");
        assertEquals("Vice President", role.value);
    }

    @Test
    public void constructor_withLeadingAndTrailingSpaces_trimmed() {
        Role role = new Role("  President  ");
        assertEquals("President", role.value);
    }

    @Test
    public void constructor_withSpecialCharacters_success() {
        String[] specialCases = {
            "Vice-President",
            "Co-President",
            "President & CEO",
            "José María - President",
            "President (Interim)",
            "President, Vice President",
            "President!",
            "President?",
            "A1B2C3",
            "123"
        };

        for (String specialCase : specialCases) {
            Role role = new Role(specialCase);
            assertEquals(specialCase, role.value);
        }
    }

    @Test
    public void constructor_withCommonClubRoles_success() {
        String[] commonRoles = {
            "President",
            "Vice President",
            "Secretary",
            "Treasurer",
            "Member",
            "Committee Member",
            "Event Coordinator",
            "Public Relations Officer",
            "Social Media Manager",
            "Volunteer Coordinator",
            "Fundraising Chair",
            "Academic Representative",
            "Sports Captain",
            "Cultural Representative",
            "Webmaster",
            "Photographer",
            "Designer",
            "Mentor",
            "Tutor",
            "Ambassador"
        };

        for (String commonRole : commonRoles) {
            Role role = new Role(commonRole);
            assertEquals(commonRole, role.value);
        }
    }
}
