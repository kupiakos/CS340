package client.communication;

import client.base.Controller;
import client.base.IView;
import org.jetbrains.annotations.NotNull;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.facades.ChatFacade;
import shared.models.game.ClientModel;
import shared.models.game.MessageEntry;
import shared.models.game.MessageList;
import shared.models.game.Player;
import shared.models.moves.SendChatAction;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Controls all the interaction between the fascade, server, and UI for chatting
 *
 * @author audakel on 9/24/16.
 */
public class ChatController extends Controller implements IChatController {
    private static final Logger LOGGER = Logger.getLogger(ChatController.class.getSimpleName());

    /**
     * Required constructor, registers on the observable list
     *
     * @param view chat view
     */
    public ChatController(IView view) {
        super(view);
        observeClientModel();
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
        PlayerIndex player = getModel().getTurnTracker().getCurrentTurn();
        SendChatAction chat = new SendChatAction(message, player);
        ChatFacade cf = getFacade().getChat();

        if (cf.canSendChat(chat)) {
            LOGGER.info("sent chat: " + chat);
            cf.sendChat(chat);
            getAsync().runModelMethod(server::sendChat, chat)
                    .onError(Throwable::printStackTrace)
                    .start();
        }
    }

    @Override
    public void updateFromModel(ClientModel model) {
        MessageList chats = model.getChat();
        ArrayList<LogEntry> entries = new ArrayList<>();

        if (chats != null) {
            LOGGER.fine("Updating chats...");

            for (MessageEntry message : chats.getLines()) {
                String msg = message.getMessage();
                String source = message.getSource();

                CatanColor messageColor = null;
                for (Player player : model.getPlayers()) {
                    // Set the chat color from who sent the message
                    if (player.getName().equals(source)) {
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
