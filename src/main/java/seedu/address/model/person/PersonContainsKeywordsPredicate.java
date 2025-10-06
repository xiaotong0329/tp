package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s fields match ALL of the keywords given.
 * Searches across name, year, student number, email, phone, dietary requirements, role, and tags.
 * Uses AND logic - all keywords must be found in the person's fields.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .allMatch(keyword -> 
                    StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                    || StringUtil.containsWordIgnoreCase(person.getYear().toString(), keyword)
                    || StringUtil.containsWordIgnoreCase(person.getStudentNumber().value, keyword)
                    || StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword)
                    || StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                    || StringUtil.containsWordIgnoreCase(person.getDietaryRequirements().value, keyword)
                    || StringUtil.containsWordIgnoreCase(person.getRole().value, keyword)
                    || person.getTags().stream().anyMatch(tag -> 
                        StringUtil.containsWordIgnoreCase(tag.tagName, keyword))
                    || isYearMatch(keyword, person.getYear().toString())
                );
    }

    /**
     * Special handling for year-related searches.
     * If keyword is "year" and there are numeric keywords, or if keyword is numeric and there's a "year" keyword,
     * then match against the person's year field.
     */
    private boolean isYearMatch(String keyword, String personYear) {
        // If the keyword is "year", check if there are any numeric keywords in the search
        if (keyword.equalsIgnoreCase("year")) {
            return keywords.stream().anyMatch(k -> k.matches("\\d+"));
        }
        
        // If the keyword is numeric, check if there's a "year" keyword in the search
        if (keyword.matches("\\d+")) {
            return keywords.stream().anyMatch(k -> k.equalsIgnoreCase("year")) 
                   && StringUtil.containsWordIgnoreCase(personYear, keyword);
        }
        
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPersonContainsKeywordsPredicate = (PersonContainsKeywordsPredicate) other;
        return keywords.equals(otherPersonContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
