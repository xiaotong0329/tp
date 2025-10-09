package seedu.address.logic.parser;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     *
     * @param args full user input following the "import" command word
     * @throws ParseException if user input cannot be parsed
     */
    @Override
    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String filePath = null;

        // Optional syntax: import /from <file>
        if (trimmedArgs.contains("/from")) {
            filePath = trimmedArgs.replace("/from", "").trim();
            if (filePath.isEmpty()) {
                filePath = null; // Default import file name will be used
            }
        }

        // No validation errors: CsvManager handles default cases internally
        return new ImportCommand(filePath);
    }
}

