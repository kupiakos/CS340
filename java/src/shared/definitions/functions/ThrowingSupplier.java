package shared.definitions.functions;

public interface ThrowingSupplier<R> {
    R get() throws Exception;
}
