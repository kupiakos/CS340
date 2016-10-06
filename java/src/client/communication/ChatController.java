package client.communication;

import client.base.Controller;
import client.base.IView;
import client.game.GameManager;
import org.jetbrains.annotations.NotNull;
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
     * Required constructor, registers on the observable list
     *
     * @param view chat view
     */
    public ChatController(IView view) {
        super(view);
        update(GameManager.getGame(), null);
        GameManager.getGame().addObserver(this);
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
    public void sendMessage(@NotNull String message) {
        if (message == null) throw new IllegalArgumentException();

        PlayerIndex player = GameManager.getGame().getClientModel().getTurnTracker().getCurrentTurn();
        SendChatAction chat = new SendChatAction(message, player);
        ChatFacade cf = GameManager.getGame().getFacade().getChat();
        IServer s = GameManager.getGame().getServer();

        if (cf.canSendChat(chat)) {
            new ChatFacade(GameManager.getGame().getFacade()).sendChat(chat);
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
                String msg = message.getMessage();
                String source = message.getSource();

                CatanColor messageColor = null;
                for (Player player : GameManager.getGame().getFacade().getClientModel().getPlayers()) {
                    // Set the chat color from who sent the message
                    if (player.getName().equals(source)){
                        messageColor = player.getColor();
                    }
                }
                // Add new entries with the message and color
                entries.add(new LogEntry(messageColor, msg));
            }
        }
        // Update the view with the new chats
        getView().setEntries(entries);
    }
}
