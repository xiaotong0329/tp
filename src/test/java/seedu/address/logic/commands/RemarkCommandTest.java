package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Remark remark = new Remark("Likes to swim");
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);

        Person editedPerson = new Person(
                person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), remark, person.getTags());

        String expectedMessage = String.format(
                RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(person, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() {
        Person person = model.getFilteredPersonList().get(Index.fromOneBased(1).getZeroBased());
        Remark remark = new Remark(""); // empty = delete
        RemarkCommand remarkCommand = new RemarkCommand(Index.fromOneBased(1), remark);

        Person editedPerson = new Person(
                person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), remark, person.getTags());

        String expectedMessage = String.format(
                RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(person, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }
}

