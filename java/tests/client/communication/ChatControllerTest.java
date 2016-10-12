package client.communication;

import client.game.GameManager;
import client.game.IGameManager;
import client.server.MockCM;
import client.server.MockProxy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import shared.facades.FacadeManager;
import shared.models.game.ClientModel;
import shared.models.game.MessageEntry;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author audakel on 10/5/16.
 */
public class ChatControllerTest {
    private IGameManager gm;
    private ChatController cc;
    private int len;
    private FacadeManager facades;


    @Before
    public void setUp() throws Exception {
        ClientModel model = MockCM.fullJsonModel();
        gm = GameManager.getGame();
        gm.setClientModel(model);
        gm.setServer(new MockProxy());
        facades = gm.getFacade();
        len = facades.getClientModel().getChat().getLines().size();
        cc = new ChatController(new ChatView());
        cc.setServer(gm.getServer());
    }

    @Test(expected=NullPointerException.class)
    public void sendEmptyMessage(){
        cc.sendMessage(null);
    }

    @Test
    public void sendMessage() {
        String m = "Good message";
        assertEquals(len, facades.getClientModel().getChat().getLines().size());
        cc.setGameManager(gm);
        cc.sendMessage(m);
        // TODO: Multithreaded testing
//        List<MessageEntry> clist = gm.getClientModel().getChat().getLines();
//        assertNotEquals(len, clist.size());
//        MessageEntry c5 = clist.get(clist.size() - 1);
//        assertEquals(m, c5.getMessage());
    }

    @Test
    public void update() throws Exception {
//        cc.sendMessage("uno");
//        cc.sendMessage("dos");
//        cc.sendMessage("tres");
//        cc.update(new Observable(), null);


    }

}