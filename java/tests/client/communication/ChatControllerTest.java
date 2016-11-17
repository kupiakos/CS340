package client.communication;

import client.game.IGameManager;
import client.game.MockGM;
import client.server.MockCM;
import client.server.MockProxy;
import client.utils.MockServerAsyncHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.facades.ChatFacade;
import shared.models.game.ClientModel;
import shared.models.game.MessageEntry;
import shared.models.game.MessageList;
import shared.models.game.Player;
import shared.models.moves.SendChatAction;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author audakel on 10/5/16.
 */
public class ChatControllerTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Spy
    private MockServerAsyncHelper async;

    private MockProxy mockProxy;

    @Mock
    private ChatFacade chatFacade;

    @Mock
    private IChatView chatView;

    private IGameManager gm;
    private ChatController cc;

    private ClientModel model;


    @Before
    public void setUp() throws Exception {
        model = MockCM.fullJsonModel();
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        gm = new MockGM();
        gm.setAsync(async);
        gm.setClientModel(model);
        gm.setServer(mockProxy);
        mockProxy = new MockProxy();
        gm.setServer(mockProxy);
        gm.getFacade().setChat(chatFacade);
        gm.setThisPlayerIndex(PlayerIndex.FIRST);
        cc = new ChatController(chatView);
        cc.setServer(gm.getServer());
        cc.setGameManager(gm);
    }

    // TODO: Identify cause of difference in IntelliJ vs. Ant.
    // Suspected: different behavior with @NotNull in debug vs release mode
//     @Test(expected=NullPointerException.class)
//     public void sendEmptyMessage(){
//         cc.sendMessage(null);
//     }

    @Test
    public void sendMessage() {
        String m = "Good message";
        when(chatFacade.canSendChat(any(Player.class), anyString())).thenReturn(true);
        cc.sendMessage(m);
        verify(async).runModelMethod(
                // any() is used as you can't properly compare lambdas
                any(),
                eq(new SendChatAction(m, PlayerIndex.FIRST)));
    }

    @Test
    public void sendBadMessage() {
        // We're not testing the ChatFacade code, we're unit testing sendMessage.
        String m = "Bad Message";
        when(chatFacade.canSendChat(any(Player.class), anyString())).thenReturn(false);
        cc.sendMessage(m);
        verifyZeroInteractions(async);
    }

    @Test
    public void update() throws Exception {
        List<LogEntry> entries = Arrays.asList(
                new LogEntry(CatanColor.PURPLE, "hello"),
                new LogEntry(CatanColor.GREEN, "world")
        );
        model.setChat(new MessageList(Arrays.asList(
                new MessageEntry("alyssa", "hello"),
                new MessageEntry("Quinn", "world")
        )));
        cc.updateFromModel(model);
        verify(chatView).setEntries(entries);
    }

}
