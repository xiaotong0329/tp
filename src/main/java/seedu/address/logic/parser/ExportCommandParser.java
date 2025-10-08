package seedu.address.logic.parser;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     *
     * @param args full user input following the "export" command word
     * @throws ParseException if user input cannot be parsed
     */
    @Override
    public ExportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String filePath = null;

        // Optional syntax: export /to <file>
        if (trimmedArgs.contains("/to")) {
            filePath = trimmedArgs.replace("/to", "").trim();
            if (filePath.isEmpty()) {
                filePath = null; // Let CsvManager handle default file creation
            }
        }

        // No validation errors: CsvManager handles default cases internally
        return new ExportCommand(filePath);
    }
}

