package shared.definitions.functions;

public interface ThrowingFunction<T, R> {
    R apply(T arg) throws Exception;
}
