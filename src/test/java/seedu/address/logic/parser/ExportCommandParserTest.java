package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;

/**
 * Unit tests for ExportCommandParser.
 */
public class ExportCommandParserTest {

    private final ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgsWithToKeyword_returnsExportCommand() throws Exception {
        ExportCommand expectedCommand = new ExportCommand("members.csv");
        assertParseSuccess(parser, " /to members.csv", expectedCommand);
    }

    @Test
    public void parse_validArgsWithoutPath_returnsDefaultExportCommand() throws Exception {
        ExportCommand expectedCommand = new ExportCommand(null);
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_validArgsWithEmptyTo_returnsDefaultExportCommand() throws Exception {
        ExportCommand expectedCommand = new ExportCommand(null);
        assertParseSuccess(parser, " /to ", expectedCommand);
    }
}

