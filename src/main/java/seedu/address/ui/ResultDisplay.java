package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * A ui for the result display that shows command feedback to the user.
 * Dynamically adjusts height based on content length.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";
    private static final int MIN_HEIGHT = 100;
    private static final int MAX_HEIGHT = 400;
    private static final int LINE_HEIGHT = 18;
    private static final int PADDING = 40; // Top and bottom padding + borders

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);

    @FXML
    private TextArea resultDisplay;

    /**
     * Creates a new ResultDisplay.
     */
    public ResultDisplay() {
        super(FXML);
        setupTextArea();
    }

    /**
     * Sets up the TextArea with proper configuration for dynamic height.
     */
    private void setupTextArea() {
        resultDisplay.setWrapText(true);
        resultDisplay.setEditable(false);
        resultDisplay.setPrefRowCount(1);

        // Listen to text changes to adjust height
        resultDisplay.textProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> adjustHeightToContent());
        });

        // Also listen to layout changes
        resultDisplay.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> adjustHeightToContent());
        });
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
        Platform.runLater(() -> {
            adjustHeightToContent();
            applyContentBasedStyling(feedbackToUser);
        });
    }

    /**
     * Dynamically adjusts the height of the result display based on content.
     * Uses a more accurate method to calculate required height.
     */
    private void adjustHeightToContent() {
        String text = resultDisplay.getText();
        if (text == null || text.isEmpty()) {
            getRoot().setPrefHeight(MIN_HEIGHT);
            return;
        }

        // Calculate the number of lines needed more accurately
        int lineCount = calculateAccurateLineCount(text);

        // Calculate required height
        int requiredHeight = (lineCount * LINE_HEIGHT) + PADDING;

        // Clamp between min and max height
        int finalHeight = Math.max(MIN_HEIGHT, Math.min(MAX_HEIGHT, requiredHeight));

        // Set the height on both the root and the TextArea
        getRoot().setPrefHeight(finalHeight);
        getRoot().setMinHeight(finalHeight);
        getRoot().setMaxHeight(finalHeight);

        // Also set the TextArea's preferred row count
        resultDisplay.setPrefRowCount(lineCount);

        // Log for debugging
        logger.info("Adjusting height: text length=" + text.length()
                   + ", lineCount=" + lineCount
                   + ", requiredHeight=" + requiredHeight
                   + ", finalHeight=" + finalHeight);

        // Force a layout pass
        getRoot().requestLayout();
    }

    /**
     * Calculates the number of lines needed to display the text more accurately.
     * Takes into account text wrapping, newline characters, and actual text width.
     */
    private int calculateAccurateLineCount(String text) {
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
     * Applies appropriate CSS styling based on the content type.
     * Different styles for success messages, errors, long lists, etc.
     */
    private void applyContentBasedStyling(String content) {
        // Clear existing style classes
        getRoot().getStyleClass().removeAll("success", "error", "long-list", "info");

        if (content == null || content.isEmpty()) {
            return;
        }

        // Check for error indicators
        if (content.toLowerCase().contains("error")
            || content.toLowerCase().contains("invalid")
            || content.toLowerCase().contains("not found")
            || content.toLowerCase().contains("failed")) {
            getRoot().getStyleClass().add("error");
        } else if (content.toLowerCase().contains("successfully")
                 || content.toLowerCase().contains("added")
                 || content.toLowerCase().contains("deleted")
                 || content.toLowerCase().contains("updated")) {
            // Check for success indicators
            getRoot().getStyleClass().add("success");
        } else if (content.contains("•")
                 || content.contains("\n") && content.split("\n").length > 3
                 || content.toLowerCase().contains("attendance")
                 || content.toLowerCase().contains("list")) {
            // Check for long list content (like viewAttendance)
            getRoot().getStyleClass().add("long-list");
        } else {
            // Default info styling for other content
            getRoot().getStyleClass().add("info");
        }
    }

}
