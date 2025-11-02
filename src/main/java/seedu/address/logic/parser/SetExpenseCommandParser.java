package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetExpenseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Money;

/**
 * Parses input arguments and creates a new SetExpenseCommand object
 */
public class SetExpenseCommandParser implements Parser<SetExpenseCommand> {

    public static final String MESSAGE_INVALID_AMOUNT_FORMAT =
            "Amount must be a positive number with at most 2 decimal places (e.g., 100.50)";
    public static final String MESSAGE_NEGATIVE_AMOUNT =
            "Amount cannot be negative. Please enter a positive number.";

    // Pattern to match numbers with at most 2 decimal places
    private static final Pattern VALID_AMOUNT_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");

    @Override
    public SetExpenseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);
        if (argMultimap.getPreamble().isEmpty() || !arePrefixesPresent(argMultimap, PREFIX_AMOUNT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetExpenseCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT);

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        String amountString = argMultimap.getValue(PREFIX_AMOUNT).get().trim();

        // Check for negative numbers
        if (amountString.startsWith("-")) {
            throw new ParseException(MESSAGE_NEGATIVE_AMOUNT);
        }

        // Validate format (at most 2 decimal places, no negative sign)
        if (!VALID_AMOUNT_PATTERN.matcher(amountString).matches()) {
            throw new ParseException(MESSAGE_INVALID_AMOUNT_FORMAT);
        }

        // Additional check: verify decimal places count
        if (amountString.contains(".")) {
            String decimalPart = amountString.split("\\.")[1];
            if (decimalPart.length() > 2) {
                throw new ParseException(MESSAGE_INVALID_AMOUNT_FORMAT);
            }
        }

        // Try to parse as BigDecimal to catch any other invalid formats
        try {
            BigDecimal bd = new BigDecimal(amountString);
            if (bd.compareTo(BigDecimal.ZERO) < 0) {
                throw new ParseException(MESSAGE_NEGATIVE_AMOUNT);
            }
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_AMOUNT_FORMAT);
        }

        Money amount = Money.parse(amountString);
        return new SetExpenseCommand(index, amount);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return java.util.stream.Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}


