package client.utils;

import static org.junit.Assert.fail;

public class ExceptionHelpers {
    public static <ExcType extends Exception> void assertThrows(ThrowingAction t, Class<ExcType> excType) {
        try {
            t.execute();
            fail("Expected exception of type " +
                    excType.getSimpleName() +
                    " to be thrown, but no exception was thrown.");
        } catch (Exception exc) {
            if (!excType.isInstance(exc)) {
                fail("Expected exception of type " +
                        excType.getSimpleName() +
                        " to be thrown, but got an exception of type " +
                        exc.getClass().getSimpleName() +
                        " instead."
                );
            }
        }
    }

    public interface ThrowingAction {
        void execute() throws Exception;
    }
}
