package client.utils;

import client.base.IAction;
import client.game.IGameManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shared.models.game.ClientModel;

import java.util.function.Consumer;

public class MockServerAsyncHelper extends ServerAsyncHelper {

    public MockServerAsyncHelper() {
        super(null);
    }

    public MockServerAsyncHelper(IGameManager gameManager) {
        super(gameManager);
    }

    public <T, R> ServerAsyncHelper.Future<T, R> runMethod(ServerAsyncHelper.ThrowingFunction<T, R> runFunc, T arg) {
        return new MockFuture<>(runFunc, arg);
    }

    public <T> ServerAsyncHelper.VoidFuture<T> runMethod(ServerAsyncHelper.ThrowingConsumer<T> runFunc, T arg) {
        return new MockVoidFuture<>(runFunc, arg);
    }

    public <R> NoArgFuture<R> runMethod(ThrowingSupplier<R> runFunc) {
        return new MockNoArgFuture<>(runFunc);
    }

    public <T> ServerAsyncHelper.ClientModelFuture<T> runModelMethod(
            @NotNull ServerAsyncHelper.ThrowingFunction<T, ClientModel> runFunc,
            T arg) {
        return new MockClientModelFuture<>(runFunc, arg);
    }

    public abstract class MockBaseFuture<RunType, SuccessType> extends ServerAsyncHelper.BaseFuture<RunType, SuccessType> {
        public MockBaseFuture(@NotNull RunType runFunc) {
            super(runFunc);
        }

        @Override
        public void start() {
            // We shouldn't be starting any extra threads during tests
        }
    }

    public class MockNoArgFuture<R> extends NoArgFuture<R> {
        public MockNoArgFuture(@NotNull ThrowingSupplier<R> runFunc) {
            super(runFunc);
        }

        @Override
        public void start() {
            // We shouldn't be starting any extra threads during tests
        }

        @Override
        public R get() {
            return null;
        }
    }

    public class MockVoidFuture<T> extends ServerAsyncHelper.VoidFuture<T> {
        private boolean hasResult;
        private T arg;

        public MockVoidFuture(@NotNull ServerAsyncHelper.ThrowingConsumer<T> runFunc, T arg) {
            super(runFunc, arg);
        }

        @Override
        public void start() {
            // We shouldn't be starting any extra threads during tests
        }

        @Override
        public void get() {
        }
    }

    public class MockFuture<T, R> extends ServerAsyncHelper.Future<T, R> {
        public MockFuture(@NotNull ServerAsyncHelper.ThrowingFunction<T, R> runFunc, T arg) {
            super(runFunc, arg);
        }

        @Override
        public void start() {
            // We shouldn't be starting any extra threads during tests
        }

        @Override
        public R get() {
            return null;
        }
    }

    public class MockClientModelFuture<T> extends ServerAsyncHelper.ClientModelFuture<T> {

        public MockClientModelFuture(@NotNull ServerAsyncHelper.ThrowingFunction<T, ClientModel> runFunc, T arg) {
            super(runFunc, arg);
        }

        @NotNull
        public ServerAsyncHelper.ClientModelFuture<T> onSuccess(@Nullable IAction successFunc) {
            return this;
        }

        @NotNull
        public ServerAsyncHelper.ClientModelFuture<T> onError(@Nullable Consumer<Exception> failFunc) {
            super.onError(failFunc);
            return this;
        }

        @Override
        public void start() {
            // We shouldn't be starting any extra threads during tests
        }

        @Override
        public ClientModel get() {
            return null;
        }
    }

}
