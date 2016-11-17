package shared.facades;

import org.jetbrains.annotations.NotNull;
import shared.models.game.ClientModel;
import shared.models.game.MessageEntry;
import shared.models.game.Player;

/**
 * Provides operations for users to chat with each other in a game.
 */
public class ChatFacade extends AbstractFacade {

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param manager the manager to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public ChatFacade(@NotNull FacadeManager manager) {
        super(manager);
    }

    /**
     * Checks if a {@link Player} can send chat messages.
     *
     * @return whether the player can send any chat messages
     */
    public boolean canSendChat(@NotNull Player p, @NotNull String message) {
        return !message.isEmpty();
    }

    /**
     * Sends a chat message to a {@link Player} by updating the message list in the model
     */
    public void sendChat(@NotNull Player p, String message) {
        if (canSendChat(p, message)) {
            getModel().getChat().addMessage(new MessageEntry(p.getName(), message));
        }
    }
}
