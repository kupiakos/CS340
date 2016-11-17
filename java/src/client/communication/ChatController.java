package client.communication;

import client.base.Controller;
import client.base.IView;
import org.jetbrains.annotations.NotNull;
import shared.definitions.CatanColor;
import shared.facades.ChatFacade;
import shared.models.game.ClientModel;
import shared.models.game.MessageList;
import shared.models.game.Player;
import shared.models.moves.SendChatAction;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Controls all the interaction between the fascade, server, and UI for chatting
 *
 * @author audakel on 9/24/16.
 */
public class ChatController extends Controller implements IChatController {
    private static final Logger LOGGER = Logger.getLogger("ChatController");

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
        SendChatAction chat = new SendChatAction(message, getPlayer().getPlayerIndex());
        ChatFacade cf = getFacade().getChat();

        if (cf.canSendChat(getPlayer(), message)) {
            LOGGER.info("sent chat: " + chat);
            getAsync().runModelMethod(server::sendChat, chat)
                    .onError(Throwable::printStackTrace)
                    .start();
        }
    }

    @Override
    public void updateFromModel(ClientModel model) {
        MessageList chats = model.getChat();

        Map<String, CatanColor> colors = model.getPlayers().stream()
                .collect(Collectors.toMap(Player::getName, Player::getColor));

        List<LogEntry> entries = chats.getLines().stream()
                .map(e -> new LogEntry(
                        colors.get(e.getSource()),
                        e.getMessage()))
                .collect(Collectors.toList());

        // Update the view with the new chats
        getView().setEntries(entries);
    }
}
