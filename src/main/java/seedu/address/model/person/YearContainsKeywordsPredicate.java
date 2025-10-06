package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Year} matches any of the keywords given.
 */
public class YearContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public YearContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getYear().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof YearContainsKeywordsPredicate)) {
            return false;
        }

        YearContainsKeywordsPredicate otherYearContainsKeywordsPredicate = (YearContainsKeywordsPredicate) other;
        return keywords.equals(otherYearContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
