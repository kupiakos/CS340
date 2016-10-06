package shared.facades;

import client.game.GameManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.PlayerIndex;
import shared.models.game.MessageEntry;
import shared.models.game.Player;
import shared.models.moves.SendChatAction;
import shared.serialization.ModelExample;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author audakel on 9/29/16.
 */
public class ChatFacadeTest {
    private ChatFacade cf;
    private Player p;
    private int len;

    /**
     * Inits the game and sets up a player and ChatFacade
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        GameManager.getGame().setClientModel(ModelExample.fullJsonModel());
        cf = GameManager.getGame().getFacade().getChat();
        p = ModelExample.fullJsonModel().getPlayer(PlayerIndex.FIRST);
        len = GameManager.getGame().getClientModel().getChat().getLines().size();
    }

    /**
     * Makes 2 bad chats that can not be sent, and one good one that is sent.
     *
     * @throws Exception
     */
    @Test
    public void canSendChat() throws Exception {
        SendChatAction c = new SendChatAction("", p.getPlayerIndex());
        SendChatAction c2 = new SendChatAction("why hello", null);
        SendChatAction c3 = new SendChatAction("Good day sir", p.getPlayerIndex());

        assertFalse(cf.canSendChat(c));
        assertFalse(cf.canSendChat(c2));
        assertTrue(cf.canSendChat(c3));
    }

    /**
     * Creates 2 bad messages that are missing either a PlayerIndex or a Message.
     * Sending these should not let the messages be added to the chat list.
     * Then we create a good one and send it, which we then recover from the message list to ensure it got there.
     *
     * @throws Exception
     */
    @Test
    public void sendChat() throws Exception {
//        GameManager.getGame().getClientModel().getChat().setLines(new ArrayList<MessageEntry>());
        SendChatAction c = new SendChatAction("", p.getPlayerIndex());
        SendChatAction c2 = new SendChatAction("why hello", null);
        SendChatAction c3 = new SendChatAction("Good day sir", p.getPlayerIndex());

        cf.sendChat(c);
        cf.sendChat(c2);
        assertTrue(len == cf.getModel().getChat().getLines().size());
        assertTrue(cf.canSendChat(c3));
        cf.sendChat(c3);
        ArrayList<MessageEntry> clist = (ArrayList<MessageEntry>) cf.getModel().getChat().getLines();
        assertTrue(1 == clist.size());
        MessageEntry c4 = clist.get(clist.size() - 1);
        assertTrue(c4.getMessage().equals(c3.getContent()));
    }

}