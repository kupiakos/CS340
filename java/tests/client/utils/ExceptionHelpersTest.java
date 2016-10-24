package client.utils;

import org.junit.Test;

import static client.utils.ExceptionHelpers.assertThrows;

public class ExceptionHelpersTest {

    @Test(expected = AssertionError.class)
    public void failsBecauseNoException() throws Exception {
        assertThrows(() -> {
        }, Exception.class);
    }

    @Test(expected = AssertionError.class)
    public void failsBecauseWrongException() throws Exception {
        assertThrows(() -> {
            throw new IllegalStateException();
        }, IllegalArgumentException.class);
    }

    @Test
    public void correctlyThrows() throws Exception {
        assertThrows(() -> {
            throw new IllegalArgumentException();
        }, IllegalArgumentException.class);
        assertThrows(() -> {
            throw new IllegalArgumentException();
        }, Exception.class);
    }

}