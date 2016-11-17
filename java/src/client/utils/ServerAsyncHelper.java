package client.utils;

import client.base.IAction;
import client.game.IGameManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shared.definitions.functions.ThrowingConsumer;
import shared.definitions.functions.ThrowingFunction;
import shared.definitions.functions.ThrowingSupplier;
import shared.models.game.ClientModel;

import java.util.function.Consumer;

public class ServerAsyncHelper {
    private IGameManager gameManager;

    public ServerAsyncHelper(IGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public <T, R> Future<T, R> runMethod(ThrowingFunction<T, R> runFunc, T arg) {
        return new Future<>(runFunc, arg);
    }

    public <T> VoidFuture<T> runMethod(ThrowingConsumer<T> runFunc, T arg) {
        return new VoidFuture<>(runFunc, arg);
    }

    public <R> NoArgFuture<R> runMethod(ThrowingSupplier<R> runFunc) {
        return new NoArgFuture<>(runFunc);
    }

    public <T> ClientModelFuture<T> runModelMethod(@NotNull ThrowingFunction<T, ClientModel> runFunc, T arg) {
        return new ClientModelFuture<>(runFunc, arg);
    }

    public abstract class BaseFuture<RunType, SuccessType> extends Thread {
        protected RunType runFunc;
        protected SuccessType successFunc;
        protected Consumer<Exception> failFunc;

        public BaseFuture(@NotNull RunType runFunc) {
            this.runFunc = runFunc;
        }

        @NotNull
        public BaseFuture<RunType, SuccessType> onSuccess(@Nullable SuccessType successFunc) {
            this.successFunc = successFunc;
            return this;
        }

        @NotNull
        public BaseFuture<RunType, SuccessType> onError(@Nullable Consumer<Exception> failFunc) {
            this.failFunc = failFunc;
            return this;
        }
    }

    public class NoArgFuture<R> extends BaseFuture<ThrowingSupplier<R>, Consumer<R>> {
        boolean hasResult = false;
        R result;
        Exception exception;

        public NoArgFuture(@NotNull ThrowingSupplier<R> runFunc) {
            super(runFunc);
        }

        @Override
        public void run() {
            try {
                result = runFunc.get();
                hasResult = true;
                if (successFunc != null) {
                    successFunc.accept(result);
                }
            } catch (Exception e) {
                exception = e;
                hasResult = true;
                if (failFunc != null) {
                    failFunc.accept(e);
                }
            }
        }

        public R get() throws Exception {
            if (isAlive()) {
                join();
            }
            if (exception != null) {
                throw exception;
            }
            if (hasResult) {
                return result;
            }
            throw new IllegalThreadStateException();
        }
    }

    public class VoidFuture<T> extends BaseFuture<ThrowingConsumer<T>, IAction> {
        Exception exception;
        private T arg;

        public VoidFuture(@NotNull ThrowingConsumer<T> runFunc, T arg) {
            super(runFunc);
            this.arg = arg;
        }

        @Override
        public void run() {
            try {
                runFunc.execute(arg);
                if (successFunc != null) {
                    successFunc.execute();
                }
            } catch (Exception e) {
                if (failFunc != null) {
                    failFunc.accept(e);
                }
            }
        }

        public void get() throws Exception {
            if (isAlive()) {
                join();
            }
            if (exception != null) {
                throw exception;
            }
        }
    }

    public class Future<T, R> extends BaseFuture<ThrowingFunction<T, R>, Consumer<R>> {
        protected T arg;
        boolean hasResult = false;
        R result;
        Exception exception;

        public Future(@NotNull ThrowingFunction<T, R> runFunc, T arg) {
            super(runFunc);
            this.arg = arg;
        }

        @Override
        public void run() {
            try {
                result = runFunc.apply(arg);
                hasResult = true;
                if (successFunc != null) {
                    successFunc.accept(result);
                }
            } catch (Exception e) {
                exception = e;
                hasResult = true;
                if (failFunc != null) {
                    failFunc.accept(e);
                }
            }
        }

        public R get() throws Exception {
            if (isAlive()) {
                join();
            }
            if (exception != null) {
                throw exception;
            }
            if (hasResult) {
                return result;
            }
            throw new IllegalThreadStateException();
        }
    }

    public class ClientModelFuture<T> extends Future<T, ClientModel> {

        public ClientModelFuture(@NotNull ThrowingFunction<T, ClientModel> runFunc, T arg) {
            super(runFunc, arg);
            onSuccess(model -> gameManager.updateGameManager(model));
        }

        @NotNull
        public ClientModelFuture<T> onSuccess(@Nullable IAction successFunc) {
            onSuccess(model -> {
                if (successFunc != null) {
                    successFunc.execute();
                }
                gameManager.updateGameManager(model);
            });
            return this;
        }

        @NotNull
        public ClientModelFuture<T> onSuccessAfter(@Nullable IAction successFunc) {
            onSuccess(model -> {
                gameManager.updateGameManager(model);
                if (successFunc != null) {
                    successFunc.execute();
                }
            });
            return this;
        }

        // Prevent problems with ordering of .onSuccess, .onError
        @NotNull
        public ClientModelFuture<T> onError(@Nullable Consumer<Exception> failFunc) {
            super.onError(failFunc);
            return this;
        }
    }
}
