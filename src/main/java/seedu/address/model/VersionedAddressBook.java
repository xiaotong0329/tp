package seedu.address.model;

import java.util.Stack;

import seedu.address.commons.util.ToStringBuilder;

/**
 * A versioned address book that maintains a history of states for undo functionality.
 */
public class VersionedAddressBook extends AddressBook {
    private final Stack<ReadOnlyAddressBook> addressBookStateHistory;
    private final Stack<ReadOnlyAddressBook> addressBookRedoHistory;

    /**
     * Creates a VersionedAddressBook with the given initial state.
     */
    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);
        this.addressBookStateHistory = new Stack<>();
        this.addressBookRedoHistory = new Stack<>();
    }

    /**
     * Creates a VersionedAddressBook with an empty initial state.
     */
    public VersionedAddressBook() {
        super();
        this.addressBookStateHistory = new Stack<>();
        this.addressBookRedoHistory = new Stack<>();
    }

    /**
     * Saves the current state of the address book to the history.
     * This should be called before making any modifications.
     */
    public void commit() {
        addressBookStateHistory.push(new AddressBook(this));
        // Clear redo history when a new state is committed
        addressBookRedoHistory.clear();
    }

    /**
     * Removes the last committed state from the history.
     * This is used when a command fails after commit.
     */
    public void rollbackLastCommit() {
        if (!addressBookStateHistory.isEmpty()) {
            addressBookStateHistory.pop();
        }
    }

    /**
     * Undoes the last change by restoring the previous state.
     * @return true if undo was successful, false if there are no states to undo
     */
    public boolean undo() {
        if (addressBookStateHistory.isEmpty()) {
            return false;
        }

        // Save current state to redo history
        addressBookRedoHistory.push(new AddressBook(this));

        // Restore previous state
        ReadOnlyAddressBook previousState = addressBookStateHistory.pop();
        resetData(previousState);

        return true;
    }

    /**
     * Redoes the last undone change.
     * @return true if redo was successful, false if there are no states to redo
     */
    public boolean redo() {
        if (addressBookRedoHistory.isEmpty()) {
            return false;
        }

        // Save current state to undo history
        addressBookStateHistory.push(new AddressBook(this));

        // Restore next state
        ReadOnlyAddressBook nextState = addressBookRedoHistory.pop();
        resetData(nextState);

        return true;
    }

    /**
     * Returns true if there are states available to undo.
     */
    public boolean canUndo() {
        return !addressBookStateHistory.isEmpty();
    }

    /**
     * Returns true if there are states available to redo.
     */
    public boolean canRedo() {
        return !addressBookRedoHistory.isEmpty();
    }

    /**
     * Returns the number of states available to undo.
     */
    public int getUndoCount() {
        return addressBookStateHistory.size();
    }

    /**
     * Returns the number of states available to redo.
     */
    public int getRedoCount() {
        return addressBookRedoHistory.size();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VersionedAddressBook)) {
            return false;
        }

        VersionedAddressBook otherVersionedAddressBook = (VersionedAddressBook) other;
        return super.equals(otherVersionedAddressBook)
                && addressBookStateHistory.equals(otherVersionedAddressBook.addressBookStateHistory)
                && addressBookRedoHistory.equals(otherVersionedAddressBook.addressBookRedoHistory);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("addressBook", super.toString())
                .add("undoHistorySize", addressBookStateHistory.size())
                .add("redoHistorySize", addressBookRedoHistory.size())
                .toString();
    }
}
