package seedu.address.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

/**
 * A set of assertion methods useful for writing tests.
 */
public class Assert {

    /**
     * Asserts that the {@code executable} throws the {@code expectedType} Exception.
     * This is a wrapper function that invokes the assertion in {@code Assertions}
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, Executable executable) {
        Assertions.assertThrows(expectedType, executable);
    }

    /**
     * Asserts that the {@code executable} throws the {@code expectedType} Exception with the {@code expectedMessage}.
     * This is a wrapper function that invokes the assertion in {@code Assertions}
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, String expectedMessage,
            Executable executable) {
        Throwable thrownException = Assertions.assertThrows(expectedType, executable);
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    /**
     * Asserts that the {@code executable} throws the {@code expectedType} Exception and the
     * exception's message matches the {@code messagePredicate}.
     * This is a wrapper function that invokes the assertion in {@code Assertions}
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, Predicate<String> messagePredicate,
            Executable executable) {
        Throwable thrownException = Assertions.assertThrows(expectedType, executable);
        assertTrue(messagePredicate.test(thrownException.getMessage()));
    }
}