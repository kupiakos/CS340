package client.utils;

import client.base.IAction;
import client.game.IGameManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shared.models.game.ClientModel;

import java.util.function.Consumer;

public class MockServerAsyncHelper extends ServerAsyncHelper {

    public MockServerAsyncHelper(IGameManager gameManager) {
        super(gameManager);
    }

    public <T, R> ServerAsyncHelper.Runner<T, R> runMethod(ServerAsyncHelper.ThrowingFunction<T, R> runFunc, T arg) {
        return new MockRunner<T, R>(runFunc, arg);
    }

    public <T> ServerAsyncHelper.VoidRunner<T> runMethod(ServerAsyncHelper.ThrowingConsumer<T> runFunc, T arg) {
        return new MockVoidRunner<T>(runFunc, arg);
    }

    public <T> ServerAsyncHelper.ClientModelRunner<T> runModelMethod(
            @NotNull ServerAsyncHelper.ThrowingFunction<T, ClientModel> runFunc,
            T arg) {
        return new MockClientModelRunner<T>(runFunc, arg);
    }

    public abstract class MockBaseRunner<RunType, SuccessType> extends ServerAsyncHelper.BaseRunner<RunType, SuccessType> {
        public MockBaseRunner(@NotNull RunType runFunc) {
            super(runFunc);
        }

        @Override
        public void start() {
            // We shouldn't be starting any extra threads during tests
        }
    }

    public class MockVoidRunner<T> extends ServerAsyncHelper.VoidRunner<T> {
        private boolean hasResult;
        private T arg;

        public MockVoidRunner(@NotNull ServerAsyncHelper.ThrowingConsumer<T> runFunc, T arg) {
            super(runFunc, arg);
        }

        @Override
        public void start() {
            // We shouldn't be starting any extra threads during tests
        }
    }

    public class MockRunner<T, R> extends ServerAsyncHelper.Runner<T, R> {
        public MockRunner(@NotNull ServerAsyncHelper.ThrowingFunction<T, R> runFunc, T arg) {
            super(runFunc, arg);
        }

        @Override
        public void start() {
            // We shouldn't be starting any extra threads during tests
        }
    }

    public class MockClientModelRunner<T> extends ServerAsyncHelper.ClientModelRunner<T> {

        public MockClientModelRunner(@NotNull ServerAsyncHelper.ThrowingFunction<T, ClientModel> runFunc, T arg) {
            super(runFunc, arg);
        }

        @NotNull
        public ServerAsyncHelper.ClientModelRunner<T> onSuccess(@Nullable IAction successFunc) {
            return this;
        }

        @NotNull
        public ServerAsyncHelper.ClientModelRunner<T> onError(@Nullable Consumer<Exception> failFunc) {
            super.onError(failFunc);
            return this;
        }

        @Override
        public void start() {
            // We shouldn't be starting any extra threads during tests
        }
    }

}
