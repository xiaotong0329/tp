package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.DietaryRequirements;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.StudentNumber;
import seedu.address.model.person.Year;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Year("2"), new StudentNumber("A1234567X"),
                new Email("alexyeoh@example.com"), new Phone("87438807"),
                new DietaryRequirements("No allergies"), new Role("Member"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Year("3"), new StudentNumber("B2345678Y"),
                new Email("berniceyu@example.com"), new Phone("99272758"),
                new DietaryRequirements("Vegetarian"), new Role("Vice President"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Year("1"), new StudentNumber("C3456789Z"),
                new Email("charlotte@example.com"), new Phone("93210283"),
                new DietaryRequirements("Halal"), new Role("Treasurer"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Year("4"), new StudentNumber("D4567890A"),
                new Email("lidavid@example.com"), new Phone("91031282"),
                new DietaryRequirements("No restrictions"), new Role("Secretary"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Year("2"), new StudentNumber("E5678901B"),
                new Email("irfan@example.com"), new Phone("92492021"),
                new DietaryRequirements("Halal"), new Role("Member"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Year("3"), new StudentNumber("F6789012C"),
                new Email("royb@example.com"), new Phone("92624417"),
                new DietaryRequirements("Vegetarian"), new Role("President"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }
}

