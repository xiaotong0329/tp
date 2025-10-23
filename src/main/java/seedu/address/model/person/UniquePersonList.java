package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.UniqueList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList extends UniqueList<Person> {

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        setElement(target, editedPerson);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        setElements(persons);
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        setAllFromOther(replacement);
    }

    @Override
    protected boolean isSameElement(Person person1, Person person2) {
        return person1.isSamePerson(person2);
    }

    @Override
    protected RuntimeException createDuplicateException() {
        return new DuplicatePersonException();
    }

    @Override
    protected RuntimeException createNotFoundException() {
        return new PersonNotFoundException();
    }
}
