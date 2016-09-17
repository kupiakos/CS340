package shared.facades;

import shared.models.game.Player;

public class ChatFacade {

    /**
     * Checks if a {@link Player} can send chat messages.
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
