package eu.wecan.streamdemo.optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class IntStreamTest {

    @Test
    void testIterate() {
        assertEquals(55, IntStream.iterate(1, x -> x + 1).limit(10).sum());
    }

    @Test
    void testGenerate() {
        assertEquals(100, IntStream.generate(() -> 10).limit(10).sum());
    }

    @Test
    void testRange() {
        assertEquals(45, IntStream.range(1, 10).sum());
    }

    @Test
    void testRangeClosed() {
        assertEquals(55, IntStream.rangeClosed(1, 10).sum());
    }

    @Test
    void testBoxed() {
        assertEquals(
        Stream.of(1, 2, 3, 4).collect(Collectors.toList()),
        IntStream.range(1, 5).boxed().collect(Collectors.toList()));
    }

    @Test
    void testReduce() {
        assertEquals(
        24,
        IntStream.range(1, 5).reduce((a, b) -> a * b).getAsInt());
    }
}
