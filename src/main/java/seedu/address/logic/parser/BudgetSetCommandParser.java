package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.time.LocalDate;

import seedu.address.logic.commands.BudgetSetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Money;

/**
 * Parses input arguments and creates a new BudgetSetCommand object
 */
public class BudgetSetCommandParser implements Parser<BudgetSetCommand> {

    @Override
    public BudgetSetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_FROM, PREFIX_TO);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_FROM, PREFIX_TO)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetSetCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT, PREFIX_FROM, PREFIX_TO);

        Money amount = Money.parse(argMultimap.getValue(PREFIX_AMOUNT).get());
        LocalDate from = ParserUtil.parseDate(argMultimap.getValue(PREFIX_FROM).get());
        LocalDate to = ParserUtil.parseDate(argMultimap.getValue(PREFIX_TO).get());
        if (to.isBefore(from)) {
            throw new ParseException("End date must be on or after start date");
        }
        return new BudgetSetCommand(amount, from, to);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return java.util.stream.Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}


