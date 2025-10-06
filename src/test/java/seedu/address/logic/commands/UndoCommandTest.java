package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UndoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_undoAfterAddCommand_success() {
        // Create a new model with empty address book
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        
        // Add a person first
        emptyModel.addPerson(ALICE);
        emptyModel.commit(); // This would normally be done by LogicManager
        
        // Now undo should work
        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        
        assertCommandSuccess(undoCommand, emptyModel, expectedMessage, emptyModel);
    }

    @Test
    public void execute_undoWithNoHistory_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = UndoCommand.MESSAGE_NO_UNDO;
        
        assertCommandFailure(undoCommand, model, expectedMessage);
    }

    @Test
    public void execute_multipleUndos_success() {
        // Create a new model with empty address book
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        
        // Add two persons
        emptyModel.addPerson(ALICE);
        emptyModel.commit();
        emptyModel.addPerson(BOB);
        emptyModel.commit();
        
        // First undo should work
        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        
        assertCommandSuccess(undoCommand, emptyModel, expectedMessage, emptyModel);
        
        // Second undo should also work
        assertCommandSuccess(undoCommand, emptyModel, expectedMessage, emptyModel);
    }

    @Test
    public void equals() {
        UndoCommand undoCommand = new UndoCommand();
        UndoCommand anotherUndoCommand = new UndoCommand();

        // same object -> returns true
        assertTrue(undoCommand.equals(undoCommand));

        // same class -> returns true
        assertTrue(undoCommand.equals(anotherUndoCommand));

        // different types -> returns false
        assertFalse(undoCommand.equals(1));

        // null -> returns false
        assertFalse(undoCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        UndoCommand undoCommand = new UndoCommand();
        String expected = "UndoCommand";
        assertEquals(expected, undoCommand.toString());
    }
}
