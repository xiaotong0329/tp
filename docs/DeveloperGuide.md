---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# ClubHub Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://junit.org/junit5/)
* UI components and architecture patterns adapted from the original AddressBook project.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of ClubHub.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2526S1-CS2103T-T10-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person`, `Event`, and `Task` objects residing in the `Model`.
* provides separate panels for members, events, tasks, and budget tracking.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores all `Event` objects in a `UniqueEventList` for event management.
* stores all `Task` objects in a `UniqueTaskList` for task tracking.
* stores `Attendance` records for tracking event participation.
* stores a `Budget` object for financial tracking and expense management.
* stores the currently 'selected' objects (e.g., results of a search query) as separate _filtered_ lists which are exposed to outsiders as unmodifiable `ObservableList` objects that can be 'observed' e.g. the UI can be bound to these lists so that the UI automatically updates when the data in the lists change.
* stores a `UserPref` object that represents the user's preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* can save and load `Person`, `Event`, `Task`, `Attendance`, and `Budget` data.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Undo/redo feature

#### Implementation

The undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### Event Management

#### Implementation

The event management system allows users to create, view, and delete events. Each event has:
- **EventId**: Unique identifier for the event
- **Date**: When the event occurs
- **Description**: Details about the event
- **Expense**: Budget allocated for the event

Key classes:
- `Event`: Represents an event with its properties
- `AddEventCommand`: Creates new events
- `DeleteEventCommand`: Removes events
- `UniqueEventList`: Manages event collection

### Task Management

#### Implementation

The task management system allows users to create, mark, and delete tasks. Each task has:
- **Title**: Description of the task
- **Deadline**: Optional due date
- **Status**: Whether the task is completed

Key classes:
- `Task`: Represents a task with its properties
- `AddTaskCommand`: Creates new tasks
- `MarkTaskCommand`/`UnmarkTaskCommand`: Updates task status
- `DeleteTaskCommand`: Removes tasks
- `UniqueTaskList`: Manages task collection

### Attendance Tracking

#### Implementation

The attendance system tracks which members attended specific events:
- **Attendance**: Links members to events with attendance status
- **MarkAttendanceCommand**: Records member attendance
- **AddAttendanceCommand**: Adds members to event attendance list
- **ShowAttendanceCommand**: Displays attendance for an event

Key classes:
- `Attendance`: Represents attendance record
- `UniqueAttendanceList`: Manages attendance collection

### Budget Management

#### Implementation

The budget system tracks financial information:
- **Budget**: Overall budget with start/end dates and amount
- **SetExpenseCommand**: Sets expense for specific events
- **BudgetSetCommand**: Sets overall budget
- **BudgetReportCommand**: Shows budget summary

Key classes:
- `Budget`: Represents budget information
- `Money`: Represents monetary values
- `SetExpenseCommand`: Sets event-specific expenses

### Import/Export Feature

#### Implementation

The import/export system allows data exchange:
- **ImportCommand**: Loads member data from CSV files
- **ExportCommand**: Saves member data to CSV files
- **CsvUtil**: Handles CSV file operations

Key classes:
- `ImportCommand`: Imports data from CSV
- `ExportCommand`: Exports data to CSV
- `CsvUtil`: Utility for CSV operations

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

## Target User Profile

**Primary User:**
CCA secretary in NUS, who needs to:
- Manage 50+ contacts of members with different roles in a structured way.
- Keep track of members’ preferences and dietary restrictions.
- Record event attendees and mark attendance.
- Delegate and track tasks.

**Pain Points:**
- **Fragmented systems:** Currently uses spreadsheets, group chats, and manual checklists, which are error-prone and time-consuming.
- **Repetitive tasks:** Re-entering the same member details across multiple files and attendance sheets.
- **Lack of structure:** Hard to filter/search members quickly (e.g., “all Year 1 members who are vegetarian”).


## Value Proposition

A streamlined address book that organises member details, tracks unique preferences, and simplifies event attendance — helping secretaries stay efficient, accurate, and focused on building stronger communities.


### User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

#### **Member Management**

| Priority | As a …​             | I want to …​                                                                | So that I can…​                                                        |
|----------|---------------------|-----------------------------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | secretary           | add a member with name, year, role, student number, phone number, email, and dietary requirements | keep member details organized and complete                            |
| `* * *`  | secretary           | add optional tags to members (e.g., "volunteer", "leadership")             | categorize members for better organization                             |
| `* * *`  | secretary           | edit a member's details                                                     | correct mistakes without re-entering everything                        |
| `* * *`  | secretary           | delete a member from the system                                             | remove members who are no longer part of the club                      |
| `* * *`  | secretary           | find members by searching across all fields (name, year, role, etc.)        | quickly locate specific members                                        |
| `* * *`  | secretary           | list all members at once                                                    | get an overview of all club members                                   |
| `* * *`  | secretary           | clear all member data                                                       | reset the system when starting fresh                                  |

#### **Event Management**

| Priority | As a …​             | I want to …​                                                                | So that I can…​                                                        |
|----------|---------------------|-----------------------------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | secretary           | create an event with a unique ID, date, and description                    | prepare an attendance list and track club activities                   |
| `* * *`  | secretary           | delete an event                                                             | remove outdated or duplicate records                                  |
| `* * *`  | secretary           | set a budget/expense for specific events                                    | track event costs and manage finances                                 |
| `* * *`  | secretary           | view all events in a list                                                   | see all upcoming and past events                                      |

#### **Attendance Tracking**

| Priority | As a …​             | I want to …​                                                                | So that I can…​                                                        |
|----------|---------------------|-----------------------------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | secretary           | add members to an event's attendance list                                  | prepare attendance records before the event                           |
| `* * *`  | secretary           | mark members as attended for an event                                       | record who actually showed up                                         |
| `* * *`  | secretary           | mark members as absent for an event                                         | track who was supposed to come but didn't                             |
| `* * *`  | secretary           | remove members from an event's attendance list                             | correct attendance records                                            |
| `* * *`  | secretary           | view all attendees for a specific event                                     | see who is registered for an event                                    |
| `* * *`  | secretary           | see attendance summary for an event (attended vs absent)                   | get a quick overview of event participation                           |

#### **Task Management**

| Priority | As a …​             | I want to …​                                                                | So that I can…​                                                        |
|----------|---------------------|-----------------------------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | secretary           | add tasks with optional deadlines                                           | assign responsibilities and track progress                             |
| `* * *`  | secretary           | mark tasks as completed                                                     | track what has been finished                                          |
| `* * *`  | secretary           | unmark completed tasks as pending                                           | correct task status if needed                                         |
| `* * *`  | secretary           | delete tasks                                                                | remove tasks that are no longer relevant                              |
| `* * *`  | secretary           | view all tasks in a list                                                    | see what needs to be done                                             |

#### **Budget Management**

| Priority | As a …​             | I want to …​                                                                | So that I can…​                                                        |
|----------|---------------------|-----------------------------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | treasurer           | set a global budget with start and end dates                               | track overall club finances for a period                              |
| `* * *`  | treasurer           | set expenses for individual events                                          | track costs per event                                                  |
| `* * *`  | treasurer           | view a budget report                                                        | see financial overview and spending patterns                           |
| `* * *`  | treasurer           | reset the global budget                                                    | start fresh for a new period                                          |

#### **Data Management**

| Priority | As a …​             | I want to …​                                                                | So that I can…​                                                        |
|----------|---------------------|-----------------------------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | secretary           | export member data to CSV files                                             | backup data and share with other exco members                         |
| `* * *`  | secretary           | import member data from CSV files                                           | quickly add multiple members at once                                  |
| `* * *`  | secretary           | undo my last action                                                         | recover from mistakes quickly                                         |
| `* * *`  | secretary           | redo a previously undone action                                             | restore changes I accidentally undid                                  |

#### **System Features**

| Priority | As a …​             | I want to …​                                                                | So that I can…​                                                        |
|----------|---------------------|-----------------------------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | secretary           | get help with available commands                                            | learn how to use the system effectively                               |
| `* * *`  | secretary           | exit the application safely                                                 | close the app without losing data                                     |
| `* *`    | secretary           | see statistics about attendance (e.g. attendance per member)                | identify active vs inactive members                                   |
| `* *`    | secretary           | filter members by year of study                                             | easily find all Year 1 members                                        |
| `* *`    | secretary           | search for members by specific roles (e.g., Treasurer, President)           | quickly contact the right person                                      |
| `* *`    | secretary           | record a member's dietary restrictions                                      | plan meals without mistakes                                           |
| `*`      | secretary           | filter members' common free time                                            | arrange events efficiently                                            |
| `*`      | busy secretary      | give access to the contact list to other exco members                       | let them manage contacts if I am not free                             |



# ClubHub Developer Guide

## **Use Cases**

For all use cases below, the **System** is `ClubHub` and the **Actor** is the **Secretary**, unless specified otherwise.

---

### **Use case: UC01 – Add Member**

**System:** ClubHub
**Actor:** Secretary

**MSS (Main Success Scenario):**
1. Secretary chooses to add a member.
2. ClubHub requests name, year, role, dietary requirements, student number, phone number(above compulsory) and telegram handle(optional).
3. Secretary enters the requested details.
4. ClubHub validates inputs and creates the member record.
5. ClubHub displays confirmation and the updated member list.
   Use case ends.

**Extensions:**
- 3a. Missing/invalid fields.
    - 3a1. ClubHub shows specific error and usage hint.
    - 3a2. Secretary re-enters data.
    - Use case resumes from step 4.
- *a. At any time, Secretary cancels.
    - *a1. ClubHub asks to confirm cancellation.
    - *a2. Secretary confirms.
    - Use case ends.

---

### **Use case: UC02 – Search Members by Field**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary chooses to search members.
2. ClubHub requests search criteria (e.g., **field** and **query**).
3. Secretary enters the field and query (e.g., Field: `Year`, Query: `3`).
4. ClubHub searches members whose specified field matches the query.
5. ClubHub displays a list of matching members with key details (e.g., Name, Year, Role, Dietary Restriction).
6. (Optional) Secretary selects a member to view full details.
   Use case ends.

**Extensions:**
- 2a. Secretary omits field or query.
    - 2a1. ClubHub prompts for the missing input(s).
    - Use case resumes from step 3.
- 3a. Invalid field name provided.
    - 3a1. ClubHub lists supported fields.
    - 3a2. Secretary re-enters a valid field.
    - Use case resumes from step 3.
- 4a. No members match the criteria.
    - 4a1. ClubHub displays "No member found." and suggests refining the query.
    - Use case ends.
- 5a. Result set is too large.
    - 5a1. ClubHub paginates results or prompts for filters.
    - Use case resumes from step 3 or continues browsing.
- *a. At any time, Secretary cancels.
    - *a1. ClubHub asks to confirm cancellation.
    - *a2. Secretary confirms.
    - Use case ends.

---

### **Use case: UC03 – Create Event**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary chooses to create an event.
2. ClubHub requests event ID, date, and description.
3. Secretary enters details.
4. ClubHub validates and creates the event.
5. ClubHub displays confirmation.
   Use case ends.

**Extensions:**
- 3a. Duplicate/non-conforming EventID or invalid date/description.
    - 3a1. ClubHub shows validation error(s).
    - 3a2. Secretary re-enters data.
    - Use case resumes from step 4.

---

### **Use case: UC04 – Record Event Attendance**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary searches for an event by ID or date.
2. ClubHub displays the event details.
3. Secretary chooses to record attendance.
4. ClubHub displays the list of members.
5. Secretary marks members as attended.
6. ClubHub saves the attendance and confirms.
   Use case ends.

**Extensions:**
- 1a. Event not found.
    - 1a1. ClubHub displays “Event not found.”
    - Use case ends.
- 5a. Secretary marks the same member again.
    - 5a1. ClubHub ignores duplicate and continues.

---

### **Use case: UC05 – Update Member Details**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary searches for the member by name and year.
2. ClubHub displays the member’s current details.
3. Secretary chooses to edit details.
4. ClubHub requests updated information.
5. Secretary enters new details (e.g., role changed).
6. ClubHub validates and updates the record.
7. ClubHub confirms the update.
   Use case ends.

**Extensions:**
- 1a. No member matches the search.
    - 1a1. ClubHub displays “No member found.”
    - Use case ends.
- 6a. Invalid details entered.
    - 6a1. ClubHub requests correction.
    - 6a2. Secretary re-enters details.
    - Use case resumes from step 7.

---

### **Use case: UC06 – Assign and Track Tasks**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary searches for a member.
2. ClubHub displays the member’s profile.
3. Secretary chooses to assign a task.
4. ClubHub requests task details.
5. Secretary enters task description and deadline.
6. ClubHub saves the task under the member’s record.
7. Secretary later views the list of pending tasks.
8. When task is done, Secretary marks it completed.
9. ClubHub updates the task status.
   Use case ends.

**Extensions:**
- 1a. Member not found.
    - 1a1. ClubHub displays “No member found.”
    - Use case ends.
- 5a. Task details are missing/invalid.
    - 5a1. ClubHub prompts for correction.
    - 5a2. Secretary re-enters details.
    - Use case resumes from step 6.

---

### **Use case: UC07 – Undo Last Action**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary issues the undo command.
2. ClubHub restores the system to its state before the last action.
3. ClubHub displays confirmation.
   Use case ends.

**Extensions:**
- 1a. No action available to undo.
    - 1a1. ClubHub displays “No action to undo.”
    - Use case ends.

---

### **Use case: UC08 – View Attendance and Follow-Up**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary chooses to view attendance for an event.
2. ClubHub displays the list of members who attended.
3. Secretary identifies absentees for follow-up actions (e.g., reminders).
   Use case ends.

**Extensions:**
- 1a. Event not found.
    - 1a1. ClubHub displays “Event not found.”
    - Use case ends.
- 2a. No attendance recorded yet.
    - 2a1. ClubHub shows “No attendance recorded yet.”
    - Use case ends.

---

### **Use case: UC09 – Delete Event**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary chooses to delete an event.
2. ClubHub requests event ID and confirmation.
3. Secretary provides the ID and confirms deletion.
4. ClubHub deletes the event and shows success message.
   Use case ends.

**Extensions:**
- 2a. Event not found.
    - 2a1. ClubHub displays “Event not found.”
    - Use case ends.
- *a. Secretary cancels at confirmation.
    - *a1. ClubHub cancels deletion.
    - Use case ends.

---

### **Use case: UC10 – View Pending Tasks**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary chooses to view pending tasks.
2. ClubHub retrieves and lists all tasks not marked as completed, grouped by member.
3. Secretary reviews tasks.
   Use case ends.

**Extensions:**
- 2a. No pending tasks.
    - 2a1. ClubHub displays “No pending tasks.”
    - Use case ends.

---

### **Use case: UC11 – View Attendance Statistics**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary chooses to view attendance statistics.
2. ClubHub calculates attendance percentage for each member across all events.
3. ClubHub displays summary statistics (e.g., member name, number of events attended, attendance rate).
   Use case ends.

**Extensions:**
- 2a. No attendance data available.
    - 2a1. ClubHub displays “No attendance data found.”
    - Use case ends.

---

### **Use case: UC12 – Archive Past Events**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary chooses to archive events.
2. ClubHub lists past events (e.g., before today’s date).
3. Secretary selects one or more events to archive.
4. ClubHub moves the events into the archive section.
5. ClubHub confirms success.
   Use case ends.

**Extensions:**
- 2a. No past events available.
    - 2a1. ClubHub displays “No past events to archive.”
    - Use case ends.

---

### **Use case: UC13 – Filter Members’ Common Free Time**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary chooses to check common free time for members.
2. ClubHub requests the list of members to include in the check.
3. Secretary selects members (e.g., Exco team).
4. ClubHub cross-references schedules/free-time data.
5. ClubHub displays the common available time slots.
   Use case ends.

**Extensions:**
- 2a. No schedules available for some members.
    - 2a1. ClubHub notifies which members lack schedules.
    - 2a2. Secretary may continue with available data.
- 5a. No common free time found.
    - 5a1. ClubHub suggests splitting into smaller groups.
    - Use case ends.

---

### **Use case: UC14 – Share Contact List Access**

**System:** ClubHub
**Actor:** Secretary, Other Exco Members

**MSS:**
1. Secretary chooses to share the contact list.
2. ClubHub requests the role or specific member(s) to grant access.
3. Secretary specifies the exco members.
4. ClubHub grants read/write access to those members.
5. ClubHub confirms access granted.
   Use case ends.

**Extensions:**
- 3a. Member not in exco.
    - 3a1. ClubHub rejects request.
    - Use case resumes from step 2.
- *a. Secretary revokes access later.
    - *a1. ClubHub updates permissions.
    - Use case ends.

---

### **Use case: UC15 – Bulk Import/Export Member Details**

**System:** ClubHub
**Actor:** Secretary

**MSS:**
1. Secretary chooses to import or export member details.
2. For import:
    - ClubHub requests a CSV/Excel file.
    - Secretary uploads the file.
    - ClubHub validates the file format and contents.
    - ClubHub imports member data and shows summary of new/updated records.
3. For export:
    - ClubHub generates a CSV/Excel file with current member details.
    - Secretary downloads the file.
      Use case ends.

**Extensions:**
- 2a. Import file invalid (wrong format, missing fields).
    - 2a1. ClubHub shows error message with line numbers.
    - 2a2. Secretary corrects file and retries.
- 2b. Duplicate entries in import file.
    - 2b1. ClubHub prompts whether to update or skip.
    - Use case resumes from step 2.

### Non-Functional Requirements

- **Performance**: Actions (add/search/edit) should respond within 1 second for up to 200 members.
- **Usability**: Must be usable via CLI with clear error messages and undo support.
- **Portability**: Runs locally on Windows, macOS, and Linux.
- **Single-user**: Only one secretary per copy of the app.
- **No remote server**: All data stored locally.
- **Reliability**: Data should not be lost when the program closes.
- **Incremental development**: Feature additions should not break previous functionality.
- **Security**: User data should not be exposed externally.


### Glossary

**Actor**
An entity (usually a user or external system) that interacts with ClubHub. In most use cases, the actor is the **Secretary**, though other Exco members may also interact with the system.

**Attendance**
A record of which members were present at an event. Used to generate statistics and track participation.

**ClubHub**
The system being developed to support secretarial duties, including member management, event organisation, task assignment, and record-keeping.

**Dietary Restriction**
Any limitation on food or drink that a member cannot consume (e.g., vegetarian, halal, no seafood). Stored in member records to aid event meal planning.

**Event**
A scheduled club activity created and tracked in ClubHub. Each event has an ID, date, and description, and may include attendance records.

**Exco (Executive Committee)**
The group of members responsible for running the club (e.g., President, Treasurer, Secretary). Exco members may have special access privileges.

**Field**
A specific attribute of a member record (e.g., Name, Year, Role, Dietary Restriction) used for searching and filtering.

**Member**
A registered individual in ClubHub with stored details such as name, year of study, role, student number, phone number, and optional telegram handle.

**Member Record**
The collection of data fields stored for a member in ClubHub.

**Pending Task**
A task assigned to a member that has not yet been marked as completed.

**Role**
The position a member holds in the club (e.g., President, Treasurer, Secretary, General Member).

**Secretary**
The main user of ClubHub. Responsible for managing members, creating events, tracking attendance, assigning tasks, and maintaining records.

**Task**
An assigned responsibility given to a member, tracked by ClubHub until marked completed.

**Telegram Handle**
An optional contact field that stores a member’s Telegram username for communication purposes.

**Undo**
A command that allows the Secretary to reverse the most recent action in ClubHub.

**Year of Study**
The academic year of a member (e.g., Year 1, Year 2, etc.). Used for filtering and organising members.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a member

1. Adding a member with all required fields

   1. Test case: `add n/John Doe y/3 s/A1234567X e/johnd@example.com p/98765432 d/Vegetarian r/President`<br>
      Expected: Member is added to the list. Success message shown.

   1. Test case: `add n/Jane Smith y/2 s/A7654321A e/janesmith@example.com p/12345678 d/Halal r/Member t/volunteer`<br>
      Expected: Member with tag is added. Success message shown.

### Managing events

1. Adding an event

   1. Test case: `addevent e/Orientation2025 dt/2025-08-15 desc/NUS Freshmen Orientation`<br>
      Expected: Event is added to the event list. Success message shown.

1. Deleting an event

   1. Test case: `deleteevent e/Orientation2025`<br>
      Expected: Event is removed from the list. Success message shown.

### Managing tasks

1. Adding a task

   1. Test case: `addtask Prepare presentation dl/2025-12-01`<br>
      Expected: Task is added to the task list. Success message shown.

1. Marking a task as done

   1. Test case: `marktask 1`<br>
      Expected: Task status changes to done. Success message shown.

### Managing attendance

1. Adding members to attendance list

   1. Test case: `addattendance e/Orientation2025 m/John Doe`<br>
      Expected: Member is added to event attendance list. Success message shown.

1. Marking attendance

   1. Test case: `markattendance e/Orientation2025 m/John Doe`<br>
      Expected: Member is marked as attended. Success message shown.

### Budget management

1. Setting event expense

   1. Test case: `setexpense 1 a/100`<br>
      Expected: Event expense is set to $100. Success message shown.

1. Setting overall budget

   1. Test case: `budget set a/5000 from/2025-01-01 to/2025-12-31`<br>
      Expected: Budget is set for the specified period. Success message shown.

### Import/Export

1. Exporting members

   1. Test case: `export /to members.csv`<br>
      Expected: CSV file is created with member data. Success message shown.

1. Importing members

   1. Test case: `import /from members.csv`<br>
      Expected: Members are imported from CSV file. Success message shown.

### Undo/Redo

1. Undoing a command

   1. Test case: `add n/Test User y/1 s/A1111111A e/test@example.com p/11111111 d/None r/Member` followed by `undo`<br>
      Expected: Member is removed. Success message shown.

1. Redoing a command

   1. Test case: After undo, execute `redo`<br>
      Expected: Member is added back. Success message shown.

### Saving data

1. Dealing with missing/corrupted data files

   1. Delete the `data/addressbook.json` file and restart the app.<br>
      Expected: App starts with sample data.

   1. Corrupt the `data/addressbook.json` file by editing it with invalid JSON and restart the app.<br>
      Expected: App starts with sample data and shows error message.
