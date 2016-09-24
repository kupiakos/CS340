package shared.facades;

import client.game.GameManager;
import com.sun.istack.internal.NotNull;
import shared.definitions.PlayerIndex;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.moves.SendChatAction;

import javax.naming.CommunicationException;

/**
 * Provides operations for users to chat with each other in a game.
 */
public class ChatFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param model the model to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public ChatFacade(@NotNull ClientModel model) {
        super(model);
    }

    /**
     * Checks if a {@link Player} can send chat messages.
     *
     * @return whether the player can send any chat messages
     */
    public static boolean canSendChat(@NotNull PlayerIndex p) {
        return false;
    }

    /**
     * Sends a chat message to a {@link Player}.
     */
    public static void sendChat(@NotNull SendChatAction c) {
        if (canSendChat(c.getPlayerIndex())) {
            try {
                GameManager.getGame().getServer().sendChat(c);
            } catch (CommunicationException e) {
                e.printStackTrace();
            }
            //  TODO:: handleResponse ?
        }
    }
}
