package shared.definitions.functions;

public interface ThrowingConsumer<T> {
    void execute(T arg) throws Exception;
}
