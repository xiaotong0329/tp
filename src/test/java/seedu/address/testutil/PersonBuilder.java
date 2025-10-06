package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.DietaryRequirements;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.StudentNumber;
import seedu.address.model.person.Year;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_YEAR = "2";
    public static final String DEFAULT_STUDENT_NUMBER = "A1234567X";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_DIETARY = "No allergies";
    public static final String DEFAULT_ROLE = "Member";

    private Name name;
    private Year year;
    private StudentNumber studentNumber;
    private Email email;
    private Phone phone;
    private DietaryRequirements dietaryRequirements;
    private Role role;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        year = new Year(DEFAULT_YEAR);
        studentNumber = new StudentNumber(DEFAULT_STUDENT_NUMBER);
        email = new Email(DEFAULT_EMAIL);
        phone = new Phone(DEFAULT_PHONE);
        dietaryRequirements = new DietaryRequirements(DEFAULT_DIETARY);
        role = new Role(DEFAULT_ROLE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        year = personToCopy.getYear();
        studentNumber = personToCopy.getStudentNumber();
        email = personToCopy.getEmail();
        phone = personToCopy.getPhone();
        dietaryRequirements = personToCopy.getDietaryRequirements();
        role = personToCopy.getRole();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code Person} that we are building.
     */
    public PersonBuilder withYear(String year) {
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code StudentNumber} of the {@code Person} that we are building.
     */
    public PersonBuilder withStudentNumber(String studentNumber) {
        this.studentNumber = new StudentNumber(studentNumber);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code DietaryRequirements} of the {@code Person} that we are building.
     */
    public PersonBuilder withDietaryRequirements(String dietaryRequirements) {
        this.dietaryRequirements = new DietaryRequirements(dietaryRequirements);
        return this;
    }

    /**
     * Sets the {@code DietaryRequirements} of the {@code Person} that we are building.
     * This is a legacy method for backward compatibility with address field.
     */
    public PersonBuilder withAddress(String address) {
        this.dietaryRequirements = new DietaryRequirements(address);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and sets it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Builds and returns the {@code Person}.
     */
    public Person build() {
        return new Person(name, year, studentNumber, email, phone, dietaryRequirements, role, tags);
    }
}


