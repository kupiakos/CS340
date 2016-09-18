package client.facade;

/**
 * Created by audakel on 9/16/16.
 */
public class Chat extends Facade{

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
