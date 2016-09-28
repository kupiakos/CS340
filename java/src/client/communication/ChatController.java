package client.communication;

import client.base.Controller;
import client.base.IView;
import client.game.GameManager;
import shared.IServer;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.facades.ChatFacade;
import shared.models.game.MessageEntry;
import shared.models.game.MessageList;
import shared.models.game.Player;
import shared.models.moves.SendChatAction;

import javax.naming.CommunicationException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Controls all the interaction between the fascade, server, and UI for chatting
 *
 * @author audakel on 9/24/16.
 */
public class ChatController extends Controller implements IChatController, Observer {
    /**
     * Required constructor
     *
     * @param view chat view
     */
    public ChatController(IView view) {
        super(view);
    }

    @Override
    public IChatView getView() {
        return (IChatView) super.getView();
    }


    /**
     * Checks with facade if chat can be sent, then updates client model then calls server with the update
     * <p>
     * {@inheritDoc}
     *
     * @param message
     */
    @Override
    public void sendMessage(String message) {
        // TODO:: Fix this shiz
        PlayerIndex player = PlayerIndex.FIRST;
        SendChatAction chat = new SendChatAction();
        ChatFacade cf = GameManager.getGame().getFacade().getChat();
        IServer s = GameManager.getGame().getServer();

        if (cf.canSendChat(new SendChatAction(message, player))) {
            cf.sendChat(chat);
            try {
                s.sendChat(chat);
            } catch (CommunicationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks the game manager for chats, if there are non null chats, it cycles through
     * and updates the players with the new messages
     * <p>
     * {@inheritDoc}
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        MessageList chats = GameManager.getGame().getClientModel().getChat();
        ArrayList<LogEntry> entries = new ArrayList<>();

        if (chats != null) {
            for (MessageEntry message : chats.getLines()) {
                String m = message.getMessage();
                String s = message.getSource();

                CatanColor messageColor = null;
                for (Player player : GameManager.getGame().getFacade().getClientModel().getPlayers()) {
                    // TODO:: Set the chat color from who sent the message
                }
                // TODO:: Add new entries with the message and color
            }
        }
        // TODO:: Update the view with the new chats

    }
}
