package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword - should return false with AND logic
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email, dietary requirements, and role - should return true with AND logic
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Vegetarian", "President"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").withEmail("alice@email.com")
                .withDietaryRequirements("Vegetarian").withRole("President").build()));
    }

    @Test
    public void test_yearContainsKeywords_returnsTrue() {
        // One keyword matching year
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("3"));
        assertTrue(predicate.test(new PersonBuilder().withYear("3").build()));

        // Multiple keywords with year match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("3", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withYear("3").build()));

        // Year keyword with other field matches
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("3", "Vegetarian"));
        assertTrue(predicate.test(new PersonBuilder().withYear("3").withDietaryRequirements("Vegetarian").build()));
    }

    @Test
    public void test_yearDoesNotContainKeywords_returnsFalse() {
        // Non-matching year
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("4"));
        assertFalse(predicate.test(new PersonBuilder().withYear("3").build()));

        // Keywords match other fields but not year
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Vegetarian", "President"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withYear("3").withDietaryRequirements("Vegetarian")
                .withRole("President").build()));
    }

    @Test
    public void test_studentNumberContainsKeywords_returnsTrue() {
        // One keyword matching student number
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("A1234567X"));
        assertTrue(predicate.test(new PersonBuilder().withStudentNumber("A1234567X").build()));

        // Partial match in student number
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("1234567"));
        assertTrue(predicate.test(new PersonBuilder().withStudentNumber("A1234567X").build()));

        // Multiple keywords with student number match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("A1234567X", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withStudentNumber("A1234567X").build()));
    }

    @Test
    public void test_studentNumberDoesNotContainKeywords_returnsFalse() {
        // Non-matching student number
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("B2345678Y"));
        assertFalse(predicate.test(new PersonBuilder().withStudentNumber("A1234567X").build()));

        // Keywords match other fields but not student number
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "3", "Vegetarian"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withYear("3").withStudentNumber("A1234567X")
                .withDietaryRequirements("Vegetarian").build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword matching email
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("alice@email.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Partial match in email
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Multiple keywords with email match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("alice@email.com", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withEmail("alice@email.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Non-matching email
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("bob@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Keywords match other fields but not email
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "3", "A1234567X"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withYear("3").withStudentNumber("A1234567X")
                .withEmail("alice@email.com").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword matching phone
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("12345"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));

        // Partial match in phone
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("123"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));

        // Multiple keywords with phone match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("12345", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Non-matching phone
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("67890"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345").build()));

        // Keywords match other fields but not phone
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "3", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withYear("3").withEmail("alice@email.com")
                .withPhone("12345").build()));
    }

    @Test
    public void test_dietaryRequirementsContainsKeywords_returnsTrue() {
        // One keyword matching dietary requirements
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Vegetarian"));
        assertTrue(predicate.test(new PersonBuilder().withDietaryRequirements("Vegetarian").build()));

        // Partial match in dietary requirements
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Vegetarian"));
        assertTrue(predicate.test(new PersonBuilder().withDietaryRequirements("Vegetarian (no eggs)").build()));

        // Multiple keywords with dietary requirements match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Vegetarian", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withDietaryRequirements("Vegetarian").build()));
    }

    @Test
    public void test_dietaryRequirementsDoesNotContainKeywords_returnsFalse() {
        // Non-matching dietary requirements
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Vegan"));
        assertFalse(predicate.test(new PersonBuilder().withDietaryRequirements("Vegetarian").build()));

        // Keywords match other fields but not dietary requirements
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "3", "12345"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withYear("3").withPhone("12345")
                .withDietaryRequirements("Vegetarian").build()));
    }

    @Test
    public void test_roleContainsKeywords_returnsTrue() {
        // One keyword matching role
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("President"));
        assertTrue(predicate.test(new PersonBuilder().withRole("President").build()));

        // Partial match in role
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("President"));
        assertTrue(predicate.test(new PersonBuilder().withRole("Vice President").build()));

        // Multiple keywords with role match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("President", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withRole("President").build()));
    }

    @Test
    public void test_roleDoesNotContainKeywords_returnsFalse() {
        // Non-matching role
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Secretary"));
        assertFalse(predicate.test(new PersonBuilder().withRole("President").build()));

        // Keywords match other fields but not role
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "3", "Vegetarian"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withYear("3").withDietaryRequirements("Vegetarian")
                .withRole("President").build()));
    }

    @Test
    public void test_tagsContainsKeywords_returnsTrue() {
        // One keyword matching tag
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple keywords with tag match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("friends", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("friends").build()));

        // Multiple tags with one matching
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));
    }

    @Test
    public void test_tagsDoesNotContainKeywords_returnsFalse() {
        // Non-matching tag
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Keywords match other fields but not tags
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "3", "President"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withYear("3").withRole("President")
                .withTags("friends").build()));
    }

    @Test
    public void test_yearMatchSpecialHandling_returnsTrue() {
        // Test "year 3" search - should match person with year "3"
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays.asList("year", "3"));
        assertTrue(predicate.test(new PersonBuilder().withYear("3").build()));

        // Test "3 year" search - should match person with year "3"
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("3", "year"));
        assertTrue(predicate.test(new PersonBuilder().withYear("3").build()));

        // Test "year 5" search - should match person with year "5"
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("year", "5"));
        assertTrue(predicate.test(new PersonBuilder().withYear("5").build()));

        // Test with additional keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("year", "3", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withYear("3").build()));
    }

    @Test
    public void test_yearMatchSpecialHandling_returnsFalse() {
        // Test "year 3" search - should not match person with year "4"
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays.asList("year", "3"));
        assertFalse(predicate.test(new PersonBuilder().withYear("4").build()));

        // Test "year" without number - should not match
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("year"));
        assertFalse(predicate.test(new PersonBuilder().withYear("3").build()));

        // Test number without "year" - should not match
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("3"));
        assertFalse(predicate.test(new PersonBuilder().withYear("3").build()));
    }

    @Test
    public void test_strictAndLogic_returnsTrue() {
        // All keywords must match - person has all matching fields
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "3", "Vegetarian"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withYear("3")
                .withDietaryRequirements("Vegetarian")
                .build()));
    }

    @Test
    public void test_strictAndLogic_returnsFalse() {
        // All keywords must match - person missing one matching field
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "3", "Vegan"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withYear("3")
                .withDietaryRequirements("Vegetarian") // Different from "Vegan"
                .build()));

        // All keywords must match - person missing multiple matching fields
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Bob", "4", "President", "Vegan"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice") // Different from "Bob"
                .withYear("3") // Different from "4"
                .withRole("Member") // Different from "President"
                .withDietaryRequirements("Vegetarian") // Different from "Vegan"
                .build()));
    }

    @Test
    public void test_mixedCaseKeywords_returnsTrue() {
        // Mixed case should work
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays.asList("aLICE", "3", "vEgEtArIaN"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withYear("3")
                .withDietaryRequirements("Vegetarian")
                .build()));
    }

    @Test
    public void test_partialWordMatches_returnsTrue() {
        // Partial word matches should work
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Ali", "Veg"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withDietaryRequirements("Vegetarian")
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
