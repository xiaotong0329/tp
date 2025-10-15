package seedu.address.logic.parser;

import seedu.address.logic.commands.BudgetReportCommand;
import seedu.address.logic.commands.BudgetResetCommand;
import seedu.address.logic.commands.BudgetSetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the umbrella budget command with subcommands: set/reset/report.
 */
public class BudgetCommandParser implements Parser<seedu.address.logic.commands.Command> {
    @Override
    public seedu.address.logic.commands.Command parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException("Specify subcommand: set, reset, or report");
        }
        if (trimmed.startsWith("set ") || trimmed.equals("set")) {
            String subArgs = trimmed.length() > 3 ? trimmed.substring(3) : "";
            return new BudgetSetCommandParser().parse(subArgs);
        } else if (trimmed.equals("reset")) {
            return new BudgetResetCommand();
        } else if (trimmed.equals("report")) {
            return new BudgetReportCommand();
        }
        throw new ParseException("Unknown budget subcommand: " + trimmed);
    }
}


