package client.communication;

import client.game.GameManager;
import client.server.MockCM;
import org.junit.Before;
import org.junit.Test;
import shared.models.game.MessageEntry;
import shared.models.moves.SendChatAction;

import java.util.ArrayList;
import java.util.Observable;

import static org.junit.Assert.*;

/**
 * @author audakel on 10/5/16.
 */
public class ChatControllerTest {
    private GameManager gm;
    private ChatController cc;
    private int len;


    @Before
    public void setUp() throws Exception {
        gm = GameManager.getGame();
        gm.setClientModel(MockCM.fullJsonModel());
        len = GameManager.getGame().getClientModel().getChat().getLines().size();
        cc = new ChatController(new ChatView());
    }

    @Test(expected=IllegalArgumentException.class)
    public void sendEmptyMessage(){
        cc.sendMessage(null);
    }

    @Test
    public void sendMessage() {
        String m = "Good message";
        assertTrue(len == GameManager.getGame().getClientModel().getChat().getLines().size());
        cc.sendMessage(m);
        ArrayList<MessageEntry> clist = (ArrayList<MessageEntry>) GameManager.getGame().getClientModel().getChat().getLines();
        assertFalse(len == clist.size());
        MessageEntry c5 = clist.get(clist.size() - 1);
        assertTrue(c5.getMessage().equals(m));
    }

    @Test
    public void update() throws Exception {
//        cc.sendMessage("uno");
//        cc.sendMessage("dos");
//        cc.sendMessage("tres");
//        cc.update(new Observable(), null);


    }

}