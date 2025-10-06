# Undo/Redo Feature Demonstration

## Overview
The undo/redo feature has been successfully implemented in ClubHub! Users can now undo their previous commands to reverse changes made to the address book, and redo previously undone commands.

## How to Use

1. **Start the application**: Run `./gradlew run` to launch ClubHub
2. **Make some changes**: Add, edit, or delete persons/events
3. **Undo changes**: Type `undo` in the command box to reverse the last command
4. **Redo changes**: Type `redo` in the command box to re-apply a previously undone command

## Supported Commands for Undo

The following commands can be undone:
- `add` - Adding a new person
- `edit` - Editing an existing person
- `delete` - Deleting a person
- `clear` - Clearing the entire address book
- `remark` - Adding/editing remarks
- `addevent` - Adding a new event
- `deleteevent` - Deleting an event

## Example Usage

```
1. add n/John Doe p/98765432 e/john@example.com a/123 Main St
   → Person added successfully

2. add n/Jane Smith p/12345678 e/jane@example.com a/456 Oak Ave
   → Person added successfully

3. undo
   → Previous command has been undone (Jane Smith removed)

4. redo
   → Previous undo has been redone (Jane Smith added back)

5. edit 1 p/99999999
   → Person edited successfully

6. undo
   → Previous command has been undone (Jane's phone number reverted)

7. undo
   → Previous command has been undone (Jane Smith removed again)

8. redo
   → Previous undo has been redone (Jane Smith added back)

9. redo
   → Previous undo has been redone (Jane's phone number updated)
```

## Technical Implementation

The undo/redo feature is implemented using:
- **VersionedAddressBook**: Tracks state history using stacks for both undo and redo
- **Model Interface**: Extended with undo/redo operations
- **UndoCommand**: New command to trigger undo functionality
- **RedoCommand**: New command to trigger redo functionality
- **LogicManager**: Automatically saves state before executing commands

## Error Handling

- If there are no commands to undo, the system will display: "There are no commands to undo."
- If there are no commands to redo, the system will display: "There are no commands to redo."
- Commands that fail (e.g., invalid index) will not affect the undo/redo history
- The undo/redo feature works seamlessly with the existing command system

## Notes

- Only commands that modify the address book data are undoable/redoable
- View commands (like `list`, `find`, `help`) do not create undo/redo history
- The undo/redo history is cleared when the application is restarted
- Multiple undos/redos are supported - you can undo/redo several commands in sequence
- **Important**: Making a new command after undoing will clear the redo history
