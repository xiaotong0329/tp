package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's dietary requirements in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDietaryRequirements(String)}
 */
public class DietaryRequirements {

    public static final String MESSAGE_CONSTRAINTS =
            "Dietary requirements should not be blank";

    /*
     * The first character of the dietary requirements must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = ".*\\S.*";

    public final String value;

    /**
     * Constructs a {@code DietaryRequirements}.
     *
     * @param dietaryRequirements A valid dietary requirements string.
     */
    public DietaryRequirements(String dietaryRequirements) {
        requireNonNull(dietaryRequirements);
        checkArgument(isValidDietaryRequirements(dietaryRequirements), MESSAGE_CONSTRAINTS);
        value = dietaryRequirements;
    }

    /**
     * Returns true if a given string is a valid dietary requirements.
     */
    public static boolean isValidDietaryRequirements(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DietaryRequirements)) {
            return false;
        }

        DietaryRequirements otherDietaryRequirements = (DietaryRequirements) other;
        return value.equals(otherDietaryRequirements.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
