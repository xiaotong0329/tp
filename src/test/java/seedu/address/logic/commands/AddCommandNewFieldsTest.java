package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains comprehensive tests for AddCommand with the new club member fields.
 */
public class AddCommandNewFieldsTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    public void execute_allNewFieldsSpecified_success() throws Exception {
        Person validPerson = new PersonBuilder()
                .withName("John Doe")
                .withYear("3")
                .withStudentNumber("A1234567X")
                .withEmail("john@example.com")
                .withPhone("98765432")
                .withDietaryRequirements("Vegetarian")
                .withRole("President")
                .withTags("leadership")
                .build();

        CommandResult commandResult = new AddCommand(validPerson).execute(model);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasPerson(validPerson));
    }

    @Test
    public void execute_minimalRequiredFields_success() throws Exception {
        Person validPerson = new PersonBuilder()
                .withName("Jane Smith")
                .withYear("1")
                .withStudentNumber("B9876543Y")
                .withEmail("jane@example.com")
                .withPhone("87654321")
                .withDietaryRequirements("No restrictions")
                .withRole("Member")
                .build();

        CommandResult commandResult = new AddCommand(validPerson).execute(model);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasPerson(validPerson));
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = new PersonBuilder().build();
        model.addPerson(personInList);
        Person duplicatePerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(duplicatePerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(model));
    }

    @Test
    public void execute_duplicatePersonWithDifferentFields_throwsCommandException() {
        Person personInList = new PersonBuilder()
                .withName("Alice")
                .withYear("2")
                .withStudentNumber("A1111111X")
                .withEmail("alice@example.com")
                .withPhone("11111111")
                .withDietaryRequirements("Vegetarian")
                .withRole("Member")
                .build();
        model.addPerson(personInList);

        // Same name but different other fields should still be considered duplicate
        Person duplicatePerson = new PersonBuilder()
                .withName("Alice")
                .withYear("3")
                .withStudentNumber("A2222222Y")
                .withEmail("alice2@example.com")
                .withPhone("22222222")
                .withDietaryRequirements("Halal")
                .withRole("President")
                .build();
        AddCommand addCommand = new AddCommand(duplicatePerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(model));
    }

    @Test
    public void execute_personWithAllYearValues_success() throws Exception {
        // Test year 1
        Person personYear1 = new PersonBuilder()
                .withName("Student Year 1")
                .withYear("1")
                .withStudentNumber("A1111111X")
                .withEmail("year1@example.com")
                .withPhone("11111111")
                .withDietaryRequirements("No restrictions")
                .withRole("Member")
                .build();
        assertTrue(new AddCommand(personYear1).execute(model).getFeedbackToUser().contains("New person added"));

        // Test year 10
        Person personYear10 = new PersonBuilder()
                .withName("Student Year 10")
                .withYear("10")
                .withStudentNumber("A1010101X")
                .withEmail("year10@example.com")
                .withPhone("10101010")
                .withDietaryRequirements("No restrictions")
                .withRole("Member")
                .build();
        assertTrue(new AddCommand(personYear10).execute(model).getFeedbackToUser().contains("New person added"));
    }

    @Test
    public void execute_personWithVariousRoles_success() throws Exception {
        String[] roles = {"President", "Vice President", "Secretary", "Treasurer", "Member", "Committee Member"};

        for (int i = 0; i < roles.length; i++) {
            Person person = new PersonBuilder()
                    .withName("Person " + i)
                    .withYear("2")
                    .withStudentNumber("A" + String.format("%07d", i) + "X")
                    .withEmail("person" + i + "@example.com")
                    .withPhone(String.format("%08d", 10000000 + i))
                    .withDietaryRequirements("No restrictions")
                    .withRole(roles[i])
                    .build();

            CommandResult result = new AddCommand(person).execute(model);
            assertTrue(result.getFeedbackToUser().contains("New person added"));
        }
    }

    @Test
    public void execute_personWithVariousDietaryRequirements_success() throws Exception {
        String[] dietaryOptions = {
            "No restrictions", "Vegetarian", "Vegan", "Halal", "Kosher",
            "Gluten-free", "Dairy-free", "Nut allergy", "Seafood allergy"
        };

        for (int i = 0; i < dietaryOptions.length; i++) {
            Person person = new PersonBuilder()
                    .withName("Person " + i)
                    .withYear("2")
                    .withStudentNumber("A" + String.format("%07d", i) + "X")
                    .withEmail("person" + i + "@example.com")
                    .withPhone(String.format("%08d", 20000000 + i))
                    .withDietaryRequirements(dietaryOptions[i])
                    .withRole("Member")
                    .build();

            CommandResult result = new AddCommand(person).execute(model);
            assertTrue(result.getFeedbackToUser().contains("New person added"));
        }
    }

    @Test
    public void execute_personWithMultipleTags_success() throws Exception {
        Person personWithMultipleTags = new PersonBuilder()
                .withName("Multi Tag Person")
                .withYear("3")
                .withStudentNumber("A3333333X")
                .withEmail("multitag@example.com")
                .withPhone("33333333")
                .withDietaryRequirements("Vegetarian")
                .withRole("President")
                .withTags("leadership", "active", "volunteer", "mentor")
                .build();

        CommandResult commandResult = new AddCommand(personWithMultipleTags).execute(model);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(personWithMultipleTags)),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasPerson(personWithMultipleTags));
    }

    @Test
    public void execute_personWithComplexStudentNumber_success() throws Exception {
        String[] studentNumbers = {
            "A1234567X", "B9876543Y", "C1111111Z", "D9999999A",
            "E5555555B", "F7777777C", "G3333333D", "H8888888E"
        };

        for (int i = 0; i < studentNumbers.length; i++) {
            Person person = new PersonBuilder()
                    .withName("Student " + i)
                    .withYear("2")
                    .withStudentNumber(studentNumbers[i])
                    .withEmail("student" + i + "@example.com")
                    .withPhone(String.format("%08d", 30000000 + i))
                    .withDietaryRequirements("No restrictions")
                    .withRole("Member")
                    .build();

            CommandResult result = new AddCommand(person).execute(model);
            assertTrue(result.getFeedbackToUser().contains("New person added"));
        }
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        Person person = new PersonBuilder().withName("Test Person").build();
        AddCommand addCommand = new AddCommand(person);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + person + "}";
        assertEquals(expected, addCommand.toString());
    }

    @Test
    public void execute_personWithSpecialCharactersInFields_success() throws Exception {
        Person personWithSpecialChars = new PersonBuilder()
                .withName("Jose Maria")
                .withYear("4")
                .withStudentNumber("A1234567X")
                .withEmail("jose.maria@university.edu")
                .withPhone("98765432")
                .withDietaryRequirements("Vegetarian (no eggs)")
                .withRole("Vice President")
                .withTags("international", "multilingual")
                .build();

        CommandResult commandResult = new AddCommand(personWithSpecialChars).execute(model);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(personWithSpecialChars)),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasPerson(personWithSpecialChars));
    }

}
