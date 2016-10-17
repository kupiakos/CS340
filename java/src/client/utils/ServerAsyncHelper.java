package client.utils;

import client.base.IAction;
import client.game.IGameManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shared.models.game.ClientModel;

import java.util.function.Consumer;

public class ServerAsyncHelper {
    private IGameManager gameManager;

    public ServerAsyncHelper(IGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public <T, R> Runner<T, R> runMethod(ThrowingFunction<T, R> runFunc, T arg) {
        return new Runner<T, R>(runFunc, arg);
    }

    public <T> VoidRunner<T> runMethod(ThrowingConsumer<T> runFunc, T arg) {
        return new VoidRunner<T>(runFunc, arg);
    }

    public <T> ClientModelRunner<T> runModelMethod(@NotNull ThrowingFunction<T, ClientModel> runFunc, T arg) {
        return new ClientModelRunner<T>(runFunc, arg);
    }

    public interface ThrowingFunction<T, R> {
        R apply(T arg) throws Exception;
    }

    public interface ThrowingConsumer<T> {
        void execute(T arg) throws Exception;
    }

    public abstract class BaseRunner<RunType, SuccessType> extends Thread {
        protected RunType runFunc;
        protected SuccessType successFunc;
        protected Consumer<Exception> failFunc;

        public BaseRunner(@NotNull RunType runFunc) {
            this.runFunc = runFunc;
        }

        @NotNull
        public BaseRunner<RunType, SuccessType> onSuccess(@Nullable SuccessType successFunc) {
            this.successFunc = successFunc;
            return this;
        }

        @NotNull
        public BaseRunner<RunType, SuccessType> onError(@Nullable Consumer<Exception> failFunc) {
            this.failFunc = failFunc;
            return this;
        }
    }

    public class VoidRunner<T> extends BaseRunner<ThrowingConsumer<T>, IAction> {
        private boolean hasResult;
        private T arg;

        public VoidRunner(@NotNull ThrowingConsumer<T> runFunc, T arg) {
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
    }

    public class Runner<T, R> extends BaseRunner<ThrowingFunction<T, R>, Consumer<R>> {
        protected T arg;
        private boolean hasResult;

        public Runner(@NotNull ThrowingFunction<T, R> runFunc, T arg) {
            super(runFunc);
            this.arg = arg;
        }

        @Override
        public void run() {
            try {
                R result = runFunc.apply(arg);
                if (successFunc != null) {
                    successFunc.accept(result);
                }
            } catch (Exception e) {
                if (failFunc != null) {
                    failFunc.accept(e);
                }
            }
        }
    }

    public class ClientModelRunner<T> extends Runner<T, ClientModel> {

        public ClientModelRunner(@NotNull ThrowingFunction<T, ClientModel> runFunc, T arg) {
            super(runFunc, arg);
            onSuccess(model -> gameManager.setClientModel(model));
        }

        @NotNull
        public ClientModelRunner<T> onSuccess(@Nullable IAction successFunc) {
            onError(e -> System.out.println(e.getMessage() + " Hello world!"));
            onSuccess(model -> {
                gameManager.setClientModel(model);
                if (successFunc != null) {
                    successFunc.execute();
                }
            });
            return this;
        }

        // Prevent problems with ordering of .onSuccess, .onError
        @NotNull
        public ClientModelRunner<T> onError(@Nullable Consumer<Exception> failFunc) {
            super.onError(failFunc);
            return this;
        }
    }
}
