package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;

import seedu.address.commons.util.CsvManager;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Imports member details from a CSV file into the address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Imports members from a CSV file.\n"
        + "Format: " + COMMAND_WORD + " [/from FILEPATH]\n"
        + "Example: " + COMMAND_WORD + " /from members.csv";

    public static final String MESSAGE_SUCCESS = "Import complete: %1$d members added.";
    public static final String MESSAGE_FAILURE = "Failed to import members: %1$s";

    private final String filePath;

    /**
     * Creates an ImportCommand with an optional file path.
     */
    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            List<Person> imported = CsvManager.importPersons(filePath);
            int addedCount = 0;

            for (Person person : imported) {
                if (!model.hasPerson(person)) {
                    model.addPerson(person);
                    addedCount++;
                }
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, addedCount));

        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return (filePath == null && otherImportCommand.filePath == null)
            || (filePath != null && filePath.equals(otherImportCommand.filePath));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("filePath", filePath)
            .toString();
    }
}


