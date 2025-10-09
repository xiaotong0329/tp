package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;

/**
 * Unit tests for ImportCommandParser.
 */
public class ImportCommandParserTest {

    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgsWithFromKeyword_returnsImportCommand() throws Exception {
        ImportCommand expectedCommand = new ImportCommand("members.csv");
        assertParseSuccess(parser, " /from members.csv", expectedCommand);
    }

    @Test
    public void parse_validArgsWithoutPath_returnsDefaultImportCommand() throws Exception {
        ImportCommand expectedCommand = new ImportCommand(null);
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_validArgsWithEmptyFrom_returnsDefaultImportCommand() throws Exception {
        ImportCommand expectedCommand = new ImportCommand(null);
        assertParseSuccess(parser, " /from ", expectedCommand);
    }
}
