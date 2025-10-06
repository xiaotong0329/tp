package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.DietaryRequirements;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.StudentNumber;
import seedu.address.model.person.Year;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String year;
    private final String studentNumber;
    private final String email;
    private final String phone;
    private final String dietaryRequirements;
    private final String role;
    private final String remark;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("year") String year,
            @JsonProperty("studentNumber") String studentNumber, @JsonProperty("email") String email,
            @JsonProperty("phone") String phone, @JsonProperty("dietaryRequirements") String dietaryRequirements,
            @JsonProperty("role") String role, @JsonProperty("remark") String remark,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.year = year;
        this.studentNumber = studentNumber;
        this.email = email;
        this.phone = phone;
        this.dietaryRequirements = dietaryRequirements;
        this.role = role;
        this.remark = remark;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        year = source.getYear().toString();
        studentNumber = source.getStudentNumber().value;
        email = source.getEmail().value;
        phone = source.getPhone().value;
        dietaryRequirements = source.getDietaryRequirements().value;
        role = source.getRole().value;
        remark = source.getRemark().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (year == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName()));
        }
        if (!Year.isValidYear(year)) {
            throw new IllegalValueException(Year.MESSAGE_CONSTRAINTS);
        }
        final Year modelYear = new Year(year);

        if (studentNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentNumber.class.getSimpleName()));
        }
        if (!StudentNumber.isValidStudentNumber(studentNumber)) {
            throw new IllegalValueException(StudentNumber.MESSAGE_CONSTRAINTS);
        }
        final StudentNumber modelStudentNumber = new StudentNumber(studentNumber);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (dietaryRequirements == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DietaryRequirements.class.getSimpleName()));
        }
        if (!DietaryRequirements.isValidDietaryRequirements(dietaryRequirements)) {
            throw new IllegalValueException(DietaryRequirements.MESSAGE_CONSTRAINTS);
        }
        final DietaryRequirements modelDietaryRequirements = new DietaryRequirements(dietaryRequirements);

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = new Role(role);

        final Remark modelRemark = new Remark(remark == null ? "" : remark);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelYear, modelStudentNumber, modelEmail, modelPhone, modelDietaryRequirements, modelRole, modelRemark, modelTags);
    }

}
