package shared.facades;

import shared.models.game.ClientModel;
import shared.models.game.Player;

public class ChatFacade {

    private ClientModel model;

    /**
     * Create a new {@code ChatFacade} and set its model.
     *
     * @param model the model to set
     */
    public ChatFacade(ClientModel model) {
        this.model = model;
    }

    /**
     * Sets the current {@link ClientModel}.
     *
     * @param model the {@link ClientModel} to set
     * @pre The given model is not null
     * @post This will set its current model
     */
    public void setModel(ClientModel model) {
        this.model = model;
    }

    /**
     * Checks if a {@link Player} can send chat messages.
     *
     * @return whether the player can send any chat messages
     */
    public static boolean canSendChat(Player player) {
        return false;
    }

    /**
     * Sends a chat message to a {@link Player}.
     */
    public static void sendChat(Player player) {
//        if (canSendChat(player)) {
//        }
    }
}
