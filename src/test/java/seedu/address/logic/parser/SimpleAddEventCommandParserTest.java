package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventId;
import seedu.address.testutil.EventBuilder;

public class SimpleAddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_simpleCommand_success() throws Exception {
        String userInput = "ev/event1 d/2023-12-25 desc/Christmas Event";

        Event expectedEvent = new EventBuilder()
                .withEventId("event1")
                .withDate("2023-12-25")
                .withDescription("Christmas Event")
                .build();
        AddEventCommand result = parser.parse(userInput);
        assertEquals(new AddEventCommand(expectedEvent), result);
    }
}
