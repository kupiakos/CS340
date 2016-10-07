package shared.exceptions;

/**
 * The player has failed to join a game
 */
public class JoinGameException extends IllegalArgumentException {
    public JoinGameException(String message) {
        super(message);
    }
}
