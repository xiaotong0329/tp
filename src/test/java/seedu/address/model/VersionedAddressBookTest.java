package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

public class VersionedAddressBookTest {

    @Test
    public void commit_initialState_hasNoUndoHistory() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

        assertFalse(versionedAddressBook.canUndo());
        assertEquals(0, versionedAddressBook.getUndoCount());
    }

    @Test
    public void commit_afterAddingPerson_canUndo() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

        versionedAddressBook.commit();
        versionedAddressBook.addPerson(ALICE);

        assertTrue(versionedAddressBook.canUndo());
        assertEquals(1, versionedAddressBook.getUndoCount());
    }

    @Test
    public void undo_afterCommit_success() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

        versionedAddressBook.commit();
        versionedAddressBook.addPerson(ALICE);

        boolean result = versionedAddressBook.undo();

        assertTrue(result);
        assertFalse(versionedAddressBook.hasPerson(ALICE));
        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void undo_withNoHistory_returnsFalse() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

        boolean result = versionedAddressBook.undo();

        assertFalse(result);
    }

    @Test
    public void redo_afterUndo_success() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

        versionedAddressBook.commit();
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.undo();

        boolean result = versionedAddressBook.redo();

        assertTrue(result);
        assertTrue(versionedAddressBook.hasPerson(ALICE));
        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void redo_withNoRedoHistory_returnsFalse() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

        boolean result = versionedAddressBook.redo();

        assertFalse(result);
    }

    @Test
    public void commit_clearsRedoHistory() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

        // Add person, undo, then commit
        versionedAddressBook.commit();
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.undo();

        assertTrue(versionedAddressBook.canRedo());

        // Commit should clear redo history
        versionedAddressBook.commit();
        versionedAddressBook.addPerson(BOB);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void multipleUndosAndRedos_workCorrectly() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

        // Add ALICE
        versionedAddressBook.commit();
        versionedAddressBook.addPerson(ALICE);

        // Add BOB
        versionedAddressBook.commit();
        versionedAddressBook.addPerson(BOB);

        // Undo twice
        assertTrue(versionedAddressBook.undo());
        assertTrue(versionedAddressBook.undo());
        assertFalse(versionedAddressBook.hasPerson(ALICE));
        assertFalse(versionedAddressBook.hasPerson(BOB));

        // Redo twice
        assertTrue(versionedAddressBook.redo());
        assertTrue(versionedAddressBook.hasPerson(ALICE));
        assertFalse(versionedAddressBook.hasPerson(BOB));

        assertTrue(versionedAddressBook.redo());
        assertTrue(versionedAddressBook.hasPerson(ALICE));
        assertTrue(versionedAddressBook.hasPerson(BOB));
    }
}
