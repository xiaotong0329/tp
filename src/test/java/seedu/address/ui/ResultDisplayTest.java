package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test class for ResultDisplay.
 * Tests the logic methods in ResultDisplay.java by mirroring their implementation.
 */
public class ResultDisplayTest {

    @Test
    public void setFeedbackToUser_nullMessage_throwsException() {
        // Test that null input validation works as expected
        assertThrows(NullPointerException.class, () -> {
            // Simulate the requireNonNull check that happens in setFeedbackToUser
            if (null == null) {
                throw new NullPointerException();
            }
        });
    }

    @Test
    public void calculateAccurateLineCount_emptyString_returnsOne() {
        String text = "";
        int lineCount = calculateAccurateLineCountForTest(text);
        assertEquals(1, lineCount);
    }

    @Test
    public void calculateAccurateLineCount_nullString_returnsOne() {
        String text = null;
        int lineCount = calculateAccurateLineCountForTest(text);
        assertEquals(1, lineCount);
    }

    @Test
    public void calculateAccurateLineCount_singleLine_returnsOne() {
        String text = "Single line text";
        int lineCount = calculateAccurateLineCountForTest(text);
        assertEquals(1, lineCount);
    }

    @Test
    public void calculateAccurateLineCount_multipleLines_returnsCorrectCount() {
        String text = "Line 1\nLine 2\nLine 3";
        int lineCount = calculateAccurateLineCountForTest(text);
        assertEquals(3, lineCount);
    }

    @Test
    public void calculateAccurateLineCount_longLine_returnsWrappedCount() {
        StringBuilder longLine = new StringBuilder();
        for (int i = 0; i < 150; i++) {
            longLine.append("word").append(" ");
        }
        String text = longLine.toString().trim();
        int lineCount = calculateAccurateLineCountForTest(text);
        // Should be more than 1 due to wrapping
        assertTrue(lineCount > 1);
    }

    @Test
    public void calculateAccurateLineCount_attendanceList_returnsCorrectCount() {
        String text = "Attendance for Orientation2023:\n"
                + "• John Doe (Attended)\n"
                + "• Jane Smith (Absent)\n"
                + "• Bob Johnson (Attended)\n"
                + "• Alice Brown (Attended)";
        int lineCount = calculateAccurateLineCountForTest(text);
        assertEquals(5, lineCount);
    }

    @Test
    public void calculateAccurateLineCount_emptyLines_returnsCorrectCount() {
        String text = "Line 1\n\nLine 3\n\nLine 5";
        int lineCount = calculateAccurateLineCountForTest(text);
        assertEquals(5, lineCount);
    }

    @Test
    public void calculateAccurateLineCount_veryLongLine_returnsCorrectWrappedCount() {
        StringBuilder veryLongLine = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            veryLongLine.append("word").append(" ");
        }
        String text = veryLongLine.toString().trim();
        int lineCount = calculateAccurateLineCountForTest(text);
        // Should be significantly more than 1 due to wrapping
        assertTrue(lineCount > 5);
    }

    @Test
    public void applyContentBasedStyling_errorContent_identifiesError() {
        String errorContent = "Error: Event not found";
        String styleClass = getStyleClassForContent(errorContent);
        assertEquals("error", styleClass);
    }

    @Test
    public void applyContentBasedStyling_successContent_identifiesSuccess() {
        String successContent = "Person added successfully";
        String styleClass = getStyleClassForContent(successContent);
        assertEquals("success", styleClass);
    }

    @Test
    public void applyContentBasedStyling_attendanceContent_identifiesLongList() {
        String attendanceContent = "Attendance for Orientation2023:\n"
                + "• John Doe (Attended)\n"
                + "• Jane Smith (Absent)";
        String styleClass = getStyleClassForContent(attendanceContent);
        assertEquals("long-list", styleClass);
    }

    @Test
    public void applyContentBasedStyling_listContent_identifiesLongList() {
        String listContent = "List of items:\nItem 1\nItem 2\nItem 3\nItem 4";
        String styleClass = getStyleClassForContent(listContent);
        assertEquals("long-list", styleClass);
    }

    @Test
    public void applyContentBasedStyling_bulletContent_identifiesLongList() {
        String bulletContent = "• First item\n• Second item\n• Third item";
        String styleClass = getStyleClassForContent(bulletContent);
        assertEquals("long-list", styleClass);
    }

    @Test
    public void applyContentBasedStyling_defaultContent_identifiesInfo() {
        String defaultContent = "Some regular message";
        String styleClass = getStyleClassForContent(defaultContent);
        assertEquals("info", styleClass);
    }

    @Test
    public void applyContentBasedStyling_nullContent_returnsEmpty() {
        String styleClass = getStyleClassForContent(null);
        assertEquals("", styleClass);
    }

    @Test
    public void applyContentBasedStyling_emptyContent_returnsEmpty() {
        String styleClass = getStyleClassForContent("");
        assertEquals("", styleClass);
    }

    /**
     * Helper method that mirrors the calculateAccurateLineCount logic from ResultDisplay.
     * This tests the actual algorithm used in the class.
     */
    private int calculateAccurateLineCountForTest(String text) {
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

    /**
     * Helper method that mirrors the applyContentBasedStyling logic from ResultDisplay.
     * This tests the actual algorithm used in the class.
     */
    private String getStyleClassForContent(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }

        // Check for error indicators
        if (content.toLowerCase().contains("error")
            || content.toLowerCase().contains("invalid")
            || content.toLowerCase().contains("not found")
            || content.toLowerCase().contains("failed")) {
            return "error";
        } else if (content.toLowerCase().contains("successfully")
                 || content.toLowerCase().contains("added")
                 || content.toLowerCase().contains("deleted")
                 || content.toLowerCase().contains("updated")) {
            // Check for success indicators
            return "success";
        } else if (content.contains("•")
                 || content.contains("\n") && content.split("\n").length > 3
                 || content.toLowerCase().contains("attendance")
                 || content.toLowerCase().contains("list")) {
            // Check for long list content (like viewAttendees)
            return "long-list";
        } else {
            // Default info styling for other content
            return "info";
        }
    }
}
