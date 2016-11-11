package shared.models;

/**
 * Created by kevin on 11/11/16.
 */
public interface ICommandAction {
    /**
     * execute will be called on the server's side.  It will make the necessary method calls to complete the action.
     */
    void execute();
}
