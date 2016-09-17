package shared.facade;

public class ChatFacade {

    /**
     * checks if player can send chat
     * @return
     */
    public static boolean canSendChat() {
        return false;
    }

    /**
     * Checks canSendChat, if true will send chat, if false returns null
     */
    public static void sendChat() {
        if (canSendChat()) {
        }
    }
}
