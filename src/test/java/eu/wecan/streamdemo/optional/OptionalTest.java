package eu.wecan.streamdemo.optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class OptionalTest {

    private static final String OTHER_VALUE = "OTHER VALUE";
    private static final String VALUE = "VALUE";
    private String buffer;

    @Test
    void testIsPresentTrue() {
        assertTrue(Optional.of(VALUE).isPresent());
    }

    @Test
    void testIsEmptyTrue() {
        assertTrue(Optional.empty().isEmpty());
    }

    @Test
    void testOptionalOfNullThrowsException() {
        assertThrows(NullPointerException.class, () -> Optional.of(null));
    }

    @Test
    void testOptionalOfNullableNullIsEmpty() {
        assertEquals(Optional.empty(), Optional.ofNullable(null));
    }

    @Test
    void testGet() {
        assertEquals(VALUE, Optional.of(VALUE).get());
    }

    @Test
    void testGetOnEmptyThrowsException() {
        assertThrows(NoSuchElementException.class, () -> Optional.empty().get());
    }

    @Test
    void testOr() {
        assertEquals(Optional.of(VALUE), Optional.empty().or(() -> Optional.of(VALUE)));
        assertEquals(Optional.of(OTHER_VALUE), Optional.of(OTHER_VALUE).or(() -> Optional.of(VALUE)));
    }

    @Test
    void testOrElse() {
        assertEquals(OTHER_VALUE, Optional.empty().orElse(OTHER_VALUE));
    }

    @Test
    void testOrElseGet() {
        assertEquals(OTHER_VALUE, Optional.empty().orElseGet(this::provideOtherValue));
    }

    @Test
    void testMap() {
        assertEquals("value", Optional.of(VALUE).map(String::toLowerCase).get());
    }

    @Test
    void testFlatMap() {
        assertEquals(Optional.of(VALUE), Optional.of(VALUE).flatMap(this::findValue));
        assertEquals(Optional.empty(), Optional.of(OTHER_VALUE).flatMap(this::findValue));
    }

    @Test
    void testFilter() {
        assertEquals(Optional.of(VALUE), Optional.of(VALUE).filter(VALUE::equals));
        assertEquals(Optional.empty(), Optional.of(OTHER_VALUE).filter(VALUE::equals));
    }

    @Test
    void testIfPresent() {
        buffer = null;
        Optional.of(VALUE).ifPresent(this::writeToBuffer);
        assertEquals(VALUE, buffer);
    }

    @Test
    void testIfPresentOrElse() {
        buffer = null;
        Optional.<String>empty().ifPresentOrElse(this::writeToBuffer, () -> buffer = "NOPE");
        assertEquals("NOPE", buffer);
    }

    private String provideOtherValue() {
        return OTHER_VALUE;
    }

    private Optional<String> findValue(final String value) {
        return VALUE.equals(value) ? Optional.of(VALUE) : Optional.empty();
    }

    private void writeToBuffer(final String text) {
        this.buffer = text;
    }

}
