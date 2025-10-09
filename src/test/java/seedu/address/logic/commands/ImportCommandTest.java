package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Tests for ImportCommand.
 */
public class ImportCommandTest {

    private final Model model = new ModelManager();

    @Test
    public void execute_validImportFile_success() throws Exception {
        Path tempFile = Path.of("temp_members.csv");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write("Name,Year,StudentNumber,Email,Phone,DietaryRequirements,Role,Tags\n");
            writer.write("John Doe,1,A1234567X,john@example.com,98765432,None,Member,leadership\n");
        }

        ImportCommand command = new ImportCommand("temp_members.csv");
        command.execute(model);

        // At least one person imported successfully
        assertTrue(model.getFilteredPersonList().size() > 0);

        Files.deleteIfExists(tempFile);
    }

    @Test
    public void execute_missingFile_throwsCommandException() {
        ImportCommand command = new ImportCommand("nonexistent.csv");
        assertThrows(CommandException.class, () -> command.execute(model));
    }
}

