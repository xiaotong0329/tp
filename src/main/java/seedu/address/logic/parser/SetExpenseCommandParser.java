package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetExpenseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Money;

/**
 * Parses input arguments and creates a new SetExpenseCommand object
 */
public class SetExpenseCommandParser implements Parser<SetExpenseCommand> {

    @Override
    public SetExpenseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);
        if (argMultimap.getPreamble().isEmpty() || !arePrefixesPresent(argMultimap, PREFIX_AMOUNT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetExpenseCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT);

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Money amount = Money.parse(argMultimap.getValue(PREFIX_AMOUNT).get());
        return new SetExpenseCommand(index, amount);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return java.util.stream.Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}


