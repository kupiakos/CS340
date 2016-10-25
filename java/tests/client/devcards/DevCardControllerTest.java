package client.devcards;

import client.base.IAction;
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
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.facades.DevCardFacade;
import shared.facades.ResourcesFacade;
import shared.models.game.ClientModel;
import shared.models.game.DevCardSet;
import shared.models.game.Player;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Philip on 10/24/2016.
 */
public class DevCardControllerTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Spy
    private MockServerAsyncHelper async;

    private MockProxy mockProxy;

    @Mock
    private DevCardFacade devCardFacade;
    @Mock
    private ResourcesFacade resourcesFacade;

    @Mock
    private IPlayDevCardView playView;

    @Mock
    private IBuyDevCardView buyView;

    @Mock
    private IAction soldierAction;

    @Mock
    private IAction roadAction;

    private IGameManager gm;
    private DevCardController dcc;
    private ClientModel model;
    private Player currentPlayer;
    private Player noDevCardsPlayer;

    @Before
    public void setUp() throws Exception {
        model = MockCM.fullJsonModel();
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FOURTH);
        gm = new MockGM();
        gm.setAsync(async);
        gm.setClientModel(model);
        mockProxy = new MockProxy();
        gm.setServer(mockProxy);
        gm.getFacade().setDevCards(devCardFacade);
        gm.getFacade().setResources(resourcesFacade);
        dcc = new DevCardController(playView, buyView, soldierAction, roadAction, gm, mockProxy);
        currentPlayer = model.getPlayer(PlayerIndex.FIRST);
        noDevCardsPlayer = model.getPlayer(PlayerIndex.FOURTH);
        for (ResourceType type : ResourceType.values()) {
            currentPlayer.getResources().setOfType(type, 1);
            noDevCardsPlayer.getResources().setOfType(type, 0);
        }
        currentPlayer.setOldDevCards(new DevCardSet(1, 1, 1, 1, 1));
        noDevCardsPlayer.setOldDevCards(new DevCardSet(0, 0, 0, 0, 0));
    }

    @Test
    public void buyCard() throws Exception {
        //Player 4; has no resources
        when(devCardFacade.canBuyDevCard(noDevCardsPlayer)).thenReturn(false);
        dcc.buyCard();
        verify(devCardFacade, never()).buyDevCard(noDevCardsPlayer);

        //Player 1; has resources
        model.getTurnTracker().setCurrentTurn(PlayerIndex.FIRST);
        dcc = new DevCardController(playView, buyView, soldierAction, roadAction, gm, mockProxy);
        when(devCardFacade.canBuyDevCard(currentPlayer)).thenReturn(true);
        dcc.buyCard();
        verify(devCardFacade, atMost(1)).buyDevCard(currentPlayer);
    }

    @Test
    public void playMonopolyCard() throws Exception {
        //Player 4; has no devCards
        ResourceType type = ResourceType.ORE;
        when(devCardFacade.canUseMonopolyCard(noDevCardsPlayer, type)).thenReturn(false);
        dcc.playMonopolyCard(type);
        verify(devCardFacade, never()).useMonopolyCard(noDevCardsPlayer, type);
        //Player 1; has devCards
        when(devCardFacade.canUseMonopolyCard(currentPlayer, type)).thenReturn(true);
        dcc.playMonopolyCard(type);
    }

    @Test
    public void playMonumentCard() throws Exception {

    }

    @Test
    public void playRoadBuildCard() throws Exception {

    }

    @Test
    public void playSoldierCard() throws Exception {

    }

    @Test
    public void playYearOfPlentyCard() throws Exception {

    }

}