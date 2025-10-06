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

public class RedoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_redoAfterUndo_success() {
        // Create a new model with empty address book
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        
        // Add a person first
        emptyModel.addPerson(ALICE);
        emptyModel.commit();
        
        // Undo the add
        emptyModel.undo();
        
        // Now redo should work
        RedoCommand redoCommand = new RedoCommand();
        String expectedMessage = RedoCommand.MESSAGE_SUCCESS;
        
        assertCommandSuccess(redoCommand, emptyModel, expectedMessage, emptyModel);
    }

    @Test
    public void execute_redoWithNoRedoHistory_throwsCommandException() {
        RedoCommand redoCommand = new RedoCommand();
        String expectedMessage = RedoCommand.MESSAGE_NO_REDO;
        
        assertCommandFailure(redoCommand, model, expectedMessage);
    }

    @Test
    public void execute_multipleRedos_success() {
        // Create a new model with empty address book
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        
        // Add two persons
        emptyModel.addPerson(ALICE);
        emptyModel.commit();
        emptyModel.addPerson(BOB);
        emptyModel.commit();
        
        // Undo twice
        emptyModel.undo();
        emptyModel.undo();
        
        // First redo should work
        RedoCommand redoCommand = new RedoCommand();
        String expectedMessage = RedoCommand.MESSAGE_SUCCESS;
        
        assertCommandSuccess(redoCommand, emptyModel, expectedMessage, emptyModel);
        
        // Second redo should also work
        assertCommandSuccess(redoCommand, emptyModel, expectedMessage, emptyModel);
    }

    @Test
    public void execute_redoAfterNewCommand_throwsCommandException() {
        // Create a new model with empty address book
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        
        // Add a person
        emptyModel.addPerson(ALICE);
        emptyModel.commit();
        
        // Undo
        emptyModel.undo();
        
        // Add another person (this should clear redo history)
        emptyModel.addPerson(BOB);
        emptyModel.commit();
        
        // Redo should fail because redo history was cleared
        RedoCommand redoCommand = new RedoCommand();
        String expectedMessage = RedoCommand.MESSAGE_NO_REDO;
        
        assertCommandFailure(redoCommand, emptyModel, expectedMessage);
    }

    @Test
    public void equals() {
        RedoCommand redoCommand = new RedoCommand();
        RedoCommand anotherRedoCommand = new RedoCommand();

        // same object -> returns true
        assertTrue(redoCommand.equals(redoCommand));

        // same class -> returns true
        assertTrue(redoCommand.equals(anotherRedoCommand));

        // different types -> returns false
        assertFalse(redoCommand.equals(1));

        // null -> returns false
        assertFalse(redoCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        RedoCommand redoCommand = new RedoCommand();
        String expected = "RedoCommand";
        assertEquals(expected, redoCommand.toString());
    }
}
