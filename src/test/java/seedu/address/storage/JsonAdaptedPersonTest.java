package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.DietaryRequirements;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;



public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_YEAR = "11";
    private static final String INVALID_STUDENT_NUMBER = "";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DIETARY = " ";
    private static final String INVALID_ROLE = "Role@";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_YEAR = BENSON.getYear().toString();
    private static final String VALID_STUDENT_NUMBER = BENSON.getStudentNumber().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_DIETARY = BENSON.getDietaryRequirements().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(INVALID_NAME, VALID_YEAR, VALID_STUDENT_NUMBER, VALID_EMAIL, VALID_PHONE, VALID_DIETARY, VALID_ROLE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_YEAR, VALID_STUDENT_NUMBER, VALID_EMAIL, VALID_PHONE, VALID_DIETARY, VALID_ROLE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_YEAR, VALID_STUDENT_NUMBER, VALID_EMAIL, INVALID_PHONE, VALID_DIETARY, VALID_ROLE, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_STUDENT_NUMBER, VALID_EMAIL, VALID_PHONE, VALID_DIETARY, VALID_ROLE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_YEAR, VALID_STUDENT_NUMBER, INVALID_EMAIL, VALID_PHONE, VALID_DIETARY, VALID_ROLE, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_YEAR, VALID_STUDENT_NUMBER, null, VALID_PHONE, VALID_DIETARY, VALID_ROLE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_YEAR, VALID_STUDENT_NUMBER, VALID_EMAIL, VALID_PHONE, INVALID_DIETARY, VALID_ROLE, VALID_TAGS);
        String expectedMessage = DietaryRequirements.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_YEAR, VALID_STUDENT_NUMBER, VALID_EMAIL, VALID_PHONE, null, VALID_ROLE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DietaryRequirements.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_YEAR, VALID_STUDENT_NUMBER, VALID_EMAIL, VALID_PHONE, VALID_DIETARY, VALID_ROLE, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
