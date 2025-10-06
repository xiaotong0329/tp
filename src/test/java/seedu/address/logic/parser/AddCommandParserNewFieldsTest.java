package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DIETARY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DIETARY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIETARY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIETARY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIETARY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIETARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.DietaryRequirements;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.StudentNumber;
import seedu.address.model.person.Year;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains comprehensive tests for AddCommandParser with the new club member fields.
 */
public class AddCommandParserNewFieldsTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allNewFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB)
                .withYear(VALID_YEAR_BOB)
                .withStudentNumber(VALID_STUDENT_NUMBER_BOB)
                .withDietaryRequirements(VALID_DIETARY_BOB)
                .withRole(VALID_ROLE_BOB)
                .withTags(VALID_TAG_FRIEND)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB
                + EMAIL_DESC_BOB + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
                .withYear(VALID_YEAR_BOB)
                .withStudentNumber(VALID_STUDENT_NUMBER_BOB)
                .withDietaryRequirements(VALID_DIETARY_BOB)
                .withRole(VALID_ROLE_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB
                        + DIETARY_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNewFieldValues_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB
                + EMAIL_DESC_BOB + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple years
        assertParseFailure(parser, YEAR_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEAR));

        // multiple student numbers
        assertParseFailure(parser, STUDENT_NUMBER_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_NUMBER));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple dietary requirements
        assertParseFailure(parser, DIETARY_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DIETARY));

        // multiple roles
        assertParseFailure(parser, ROLE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + YEAR_DESC_AMY
                        + STUDENT_NUMBER_DESC_AMY + DIETARY_DESC_AMY + ROLE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_YEAR, PREFIX_STUDENT_NUMBER,
                        PREFIX_EMAIL, PREFIX_PHONE, PREFIX_DIETARY, PREFIX_ROLE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid year
        assertParseFailure(parser, INVALID_YEAR_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEAR));

        // invalid student number
        assertParseFailure(parser, INVALID_STUDENT_NUMBER_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_NUMBER));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid dietary requirements
        assertParseFailure(parser, INVALID_DIETARY_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DIETARY));

        // invalid role
        assertParseFailure(parser, INVALID_ROLE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid year
        assertParseFailure(parser, validExpectedPersonString + INVALID_YEAR_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_YEAR));

        // invalid student number
        assertParseFailure(parser, validExpectedPersonString + INVALID_STUDENT_NUMBER_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_NUMBER));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid dietary requirements
        assertParseFailure(parser, validExpectedPersonString + INVALID_DIETARY_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DIETARY));

        // invalid role
        assertParseFailure(parser, validExpectedPersonString + INVALID_ROLE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY)
                .withYear(VALID_YEAR_AMY)
                .withStudentNumber(VALID_STUDENT_NUMBER_AMY)
                .withDietaryRequirements(VALID_DIETARY_AMY)
                .withRole(VALID_ROLE_AMY)
                .withTags()
                .build();
        assertParseSuccess(parser, NAME_DESC_AMY + YEAR_DESC_AMY + STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_AMY + DIETARY_DESC_AMY + ROLE_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryNewFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB, expectedMessage);

        // missing year prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_YEAR_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB, expectedMessage);

        // missing student number prefix
        assertParseFailure(parser, NAME_DESC_BOB + YEAR_DESC_BOB + VALID_STUDENT_NUMBER_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + VALID_EMAIL_BOB
                + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + VALID_PHONE_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB, expectedMessage);

        // missing dietary requirements prefix
        assertParseFailure(parser, NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + VALID_DIETARY_BOB + ROLE_DESC_BOB, expectedMessage);

        // missing role prefix
        assertParseFailure(parser, NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + DIETARY_DESC_BOB + VALID_ROLE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_YEAR_BOB + VALID_STUDENT_NUMBER_BOB + VALID_EMAIL_BOB
                + VALID_PHONE_BOB + VALID_DIETARY_BOB + VALID_ROLE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidNewFieldValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid year
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_YEAR_DESC + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Year.MESSAGE_CONSTRAINTS);

        // invalid student number
        assertParseFailure(parser, NAME_DESC_BOB + YEAR_DESC_BOB + INVALID_STUDENT_NUMBER_DESC + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                StudentNumber.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + INVALID_EMAIL_DESC
                + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_PHONE_DESC + DIETARY_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid dietary requirements
        assertParseFailure(parser, NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + INVALID_DIETARY_DESC + ROLE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                DietaryRequirements.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + DIETARY_DESC_BOB + INVALID_ROLE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Role.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + INVALID_DIETARY_DESC + ROLE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + YEAR_DESC_BOB + STUDENT_NUMBER_DESC_BOB
                + EMAIL_DESC_BOB + PHONE_DESC_BOB + DIETARY_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validYearValues_success() {
        // Test all valid year values (1-10)
        for (int year = 1; year <= 10; year++) {
            String yearDesc = " " + PREFIX_YEAR + year;
            Person expectedPerson = new PersonBuilder(AMY)
                    .withYear(String.valueOf(year))
                    .withStudentNumber(VALID_STUDENT_NUMBER_AMY)
                    .withDietaryRequirements(VALID_DIETARY_AMY)
                    .withRole(VALID_ROLE_AMY)
                    .withTags() // Clear tags to match the command string
                    .build();

            assertParseSuccess(parser, NAME_DESC_AMY + yearDesc + STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_AMY
                    + PHONE_DESC_AMY + DIETARY_DESC_AMY + ROLE_DESC_AMY,
                    new AddCommand(expectedPerson));
        }
    }

    @Test
    public void parse_invalidYearValues_failure() {
        String[] invalidYears = {"0", "11", "12", "-1", "abc", "1.5", "01", "10.0"};

        for (String invalidYear : invalidYears) {
            String invalidYearDesc = " " + PREFIX_YEAR + invalidYear;
            assertParseFailure(parser, NAME_DESC_AMY + invalidYearDesc + STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_AMY
                    + PHONE_DESC_AMY + DIETARY_DESC_AMY + ROLE_DESC_AMY,
                    Year.MESSAGE_CONSTRAINTS);
        }
    }

    @Test
    public void parse_validStudentNumberFormats_success() {
        String[] validStudentNumbers = {
            "A1234567X", "B9876543Y", "C1111111Z", "D9999999A",
            "E5555555B", "F7777777C", "G3333333D", "H8888888E",
            "A1B2C3D4E", "Z9Y8X7W6V", "M5N4P3Q2R", "S1T2U3V4W"
        };

        for (String studentNumber : validStudentNumbers) {
            String studentNumberDesc = " " + PREFIX_STUDENT_NUMBER + studentNumber;
            Person expectedPerson = new PersonBuilder(AMY)
                    .withYear(VALID_YEAR_AMY)
                    .withStudentNumber(studentNumber)
                    .withDietaryRequirements(VALID_DIETARY_AMY)
                    .withRole(VALID_ROLE_AMY)
                    .withTags() // Clear tags to match the command string
                    .build();

            assertParseSuccess(parser, NAME_DESC_AMY + YEAR_DESC_AMY + studentNumberDesc + EMAIL_DESC_AMY
                    + PHONE_DESC_AMY + DIETARY_DESC_AMY + ROLE_DESC_AMY,
                    new AddCommand(expectedPerson));
        }
    }

    @Test
    public void parse_invalidStudentNumberFormats_failure() {
        String[] invalidStudentNumbers = {"", " ", "A", "1234567", "A123456", "A12345678", "A1234567", "A1234567X1"};

        for (String invalidStudentNumber : invalidStudentNumbers) {
            String invalidStudentNumberDesc = " " + PREFIX_STUDENT_NUMBER + invalidStudentNumber;
            assertParseFailure(parser, NAME_DESC_AMY + YEAR_DESC_AMY + invalidStudentNumberDesc + EMAIL_DESC_AMY
                    + PHONE_DESC_AMY + DIETARY_DESC_AMY + ROLE_DESC_AMY,
                    StudentNumber.MESSAGE_CONSTRAINTS);
        }
    }

    @Test
    public void parse_validDietaryRequirements_success() {
        String[] validDietaryOptions = {
            "No restrictions", "Vegetarian", "Vegan", "Halal", "Kosher",
            "Gluten-free", "Dairy-free", "Nut allergy", "Seafood allergy",
            "Multiple allergies", "Vegetarian (no eggs)", "Halal and gluten-free"
        };

        for (String dietary : validDietaryOptions) {
            String dietaryDesc = " " + PREFIX_DIETARY + dietary;
            Person expectedPerson = new PersonBuilder(AMY)
                    .withYear(VALID_YEAR_AMY)
                    .withStudentNumber(VALID_STUDENT_NUMBER_AMY)
                    .withDietaryRequirements(dietary)
                    .withRole(VALID_ROLE_AMY)
                    .withTags() // Clear tags to match the command string
                    .build();

            assertParseSuccess(parser, NAME_DESC_AMY + YEAR_DESC_AMY + STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_AMY
                    + PHONE_DESC_AMY + dietaryDesc + ROLE_DESC_AMY,
                    new AddCommand(expectedPerson));
        }
    }

    @Test
    public void parse_invalidDietaryRequirements_failure() {
        String[] invalidDietaryOptions = {"", " ", "   "};

        for (String invalidDietary : invalidDietaryOptions) {
            String invalidDietaryDesc = " " + PREFIX_DIETARY + invalidDietary;
            assertParseFailure(parser, NAME_DESC_AMY + YEAR_DESC_AMY + STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_AMY
                    + PHONE_DESC_AMY + invalidDietaryDesc + ROLE_DESC_AMY,
                    DietaryRequirements.MESSAGE_CONSTRAINTS);
        }
    }

    @Test
    public void parse_validRoleFormats_success() {
        String[] validRoles = {
            "President", "Vice President", "Secretary", "Treasurer", "Member",
            "Committee Member", "Event Coordinator", "Public Relations Officer",
            "President ", " Vice President", "Secretary ", " Treasurer"
        };

        for (String role : validRoles) {
            String roleDesc = " " + PREFIX_ROLE + role;
            Person expectedPerson = new PersonBuilder(AMY)
                    .withYear(VALID_YEAR_AMY)
                    .withStudentNumber(VALID_STUDENT_NUMBER_AMY)
                    .withDietaryRequirements(VALID_DIETARY_AMY)
                    .withRole(role.trim())
                    .withTags() // Clear tags to match the command string
                    .build();

            assertParseSuccess(parser, NAME_DESC_AMY + YEAR_DESC_AMY + STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_AMY
                    + PHONE_DESC_AMY + DIETARY_DESC_AMY + roleDesc,
                    new AddCommand(expectedPerson));
        }
    }

    @Test
    public void parse_invalidRoleFormats_failure() {
        String[] invalidRoles = {"", " "}; // Only test truly invalid values (empty/whitespace)

        for (String invalidRole : invalidRoles) {
            String invalidRoleDesc = " " + PREFIX_ROLE + invalidRole;
            assertParseFailure(parser, NAME_DESC_AMY + YEAR_DESC_AMY + STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_AMY
                    + PHONE_DESC_AMY + DIETARY_DESC_AMY + invalidRoleDesc,
                    Role.MESSAGE_CONSTRAINTS);
        }
    }
}
