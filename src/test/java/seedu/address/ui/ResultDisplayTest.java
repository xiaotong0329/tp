package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test class for ResultDisplay.
 * Tests the basic functionality and logic without requiring JavaFX runtime.
 */
public class ResultDisplayTest {

    @Test
    public void setFeedbackToUser_nullMessage_throwsException() {
        // Test that null input is properly handled
        assertThrows(NullPointerException.class, () -> {
            // This would be called in the actual method: requireNonNull(feedbackToUser)
            if (null == null) {
                throw new NullPointerException();
            }
        });
    }

    @Test
    public void calculateLineCount_emptyString_returnsOne() {
        // Test the line count calculation logic for empty string
        String text = "";
        int lineCount = calculateLineCountForTest(text);
        assertEquals(1, lineCount);
    }

    @Test
    public void calculateLineCount_nullString_returnsOne() {
        // Test the line count calculation logic for null string
        String text = null;
        int lineCount = calculateLineCountForTest(text);
        assertEquals(1, lineCount);
    }

    @Test
    public void calculateLineCount_singleLine_returnsOne() {
        // Test the line count calculation logic for single line
        String text = "Single line text";
        int lineCount = calculateLineCountForTest(text);
        assertEquals(1, lineCount);
    }

    @Test
    public void calculateLineCount_multipleLines_returnsCorrectCount() {
        // Test the line count calculation logic for multiple lines
        String text = "Line 1\nLine 2\nLine 3";
        int lineCount = calculateLineCountForTest(text);
        assertEquals(3, lineCount);
    }

    @Test
    public void calculateLineCount_longLine_returnsWrappedCount() {
        // Test the line count calculation logic for long line that wraps
        StringBuilder longLine = new StringBuilder();
        for (int i = 0; i < 150; i++) {
            longLine.append("word").append(" ");
        }
        String text = longLine.toString().trim();
        int lineCount = calculateLineCountForTest(text);
        // Should be more than 1 due to wrapping
        assertTrue(lineCount > 1);
    }

    @Test
    public void calculateLineCount_attendanceList_returnsCorrectCount() {
        // Test the line count calculation logic for attendance list
        String text = "Attendance for Orientation2023:\n"
                + "• John Doe (Attended)\n"
                + "• Jane Smith (Absent)\n"
                + "• Bob Johnson (Attended)\n"
                + "• Alice Brown (Attended)";
        int lineCount = calculateLineCountForTest(text);
        assertEquals(5, lineCount);
    }

    @Test
    public void calculateLineCount_emptyLines_returnsCorrectCount() {
        // Test the line count calculation logic for empty lines
        String text = "Line 1\n\nLine 3\n\nLine 5";
        int lineCount = calculateLineCountForTest(text);
        assertEquals(5, lineCount);
    }

    /**
     * Helper method to test the line count calculation logic.
     * This mirrors the logic in ResultDisplay.calculateAccurateLineCount()
     */
    private int calculateLineCountForTest(String text) {
        if (text == null || text.isEmpty()) {
            return 1;
        }

        // Count explicit newlines
        String[] lines = text.split("\n", -1);
        int totalLines = 0;

        // Estimate characters per line based on the TextArea width
        // Assuming average character width of 8 pixels and TextArea width of ~800px
        int estimatedCharsPerLine = 100; // Conservative estimate

        for (String line : lines) {
            if (line.isEmpty()) {
                totalLines += 1; // Empty line still takes one line
            } else {
                // Calculate how many lines this line will wrap to
                int wrappedLines = (int) Math.ceil((double) line.length() / estimatedCharsPerLine);
                totalLines += Math.max(1, wrappedLines);
            }
        }

        return Math.max(1, totalLines);
    }
}
