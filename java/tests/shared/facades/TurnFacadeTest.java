package shared.facades;

import client.game.GameManager;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;
import shared.models.game.Player;
import shared.serialization.ModelExample;

import static client.game.GameManager.*;
import static org.junit.Assert.*;
import static shared.definitions.PlayerIndex.*;
import static shared.definitions.PlayerIndex.SECOND;
import static shared.definitions.TurnStatus.PLAYING;
import static shared.definitions.TurnStatus.ROLLING;


/**
 * @author audakel on 9/29/16.
 */
public class TurnFacadeTest {
    private TurnFacade tf;
    private Player p;
    private int len;

    /**
     * Inits the game and sets up a player and turnFacade
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        getGame().setClientModel(ModelExample.fullJsonModel());
        p = ModelExample.fullJsonModel().getPlayer(FIRST);
    }

    private TurnFacade tf(){
        return getGame().getFacade().getTurn();

    }


    @Test
    public void endTurn() throws Exception {


    }

    /**
     * Checks if you can end turn before putting down 2 roads && settlements
     *
     * @throws Exception
     */
    @Test
    public void canEndTurn() throws Exception {
        assertEquals(tf().getPhase(), TurnStatus.SECOND_ROUND);
        getGame().getClientModel().getPlayer(p.getPlayerIndex()).setRoads(1);
        getGame().getClientModel().getPlayer(p.getPlayerIndex()).setSettlements(1);
        assertFalse(tf().canEndTurn(p.getPlayerIndex()));
        getGame().getClientModel().getPlayer(p.getPlayerIndex()).setRoads(2);
        getGame().getClientModel().getPlayer(p.getPlayerIndex()).setSettlements(2);
        assertTrue(tf().canEndTurn(p.getPlayerIndex()));
        tf().endTurn(p.getPlayerIndex());
        assertEquals(tf().getPhase(), ROLLING);
        assertEquals(getGame().getClientModel().getTurnTracker().getCurrentTurn(), FIRST);
    }

    @Test
    public void isPlayersTurn() throws Exception {
        getGame().getClientModel().getTurnTracker().setCurrentTurn(FOURTH);
        assertEquals(getGame().getClientModel().getTurnTracker().getCurrentTurn(), FOURTH);

    }

    @Test
    public void getPhase() throws Exception {

    }

    @Test
    public void setPhase() throws Exception {

    }

    @Test
    public void getPlayers() throws Exception {
        assertEquals(getGame().getClientModel().getPlayerCount(), 4);
    }

    @Test
    public void startGame() throws Exception {

    }

    @Test
    public void advanceTurnStatus() throws Exception {
        getGame().getClientModel().getTurnTracker().setStatus(ROLLING);
        getGame().getClientModel().getTurnTracker().setCurrentTurn(SECOND);
        assertFalse(tf().advanceTurnStatus());

//        getGame().getClientModel().getTurnTracker().setStatus(PLAYING);
//        assertTrue(tf().advanceTurnStatus());
//        assertEquals(tf().getPhase(), ROLLING);
//        assertEquals(getGame().getClientModel().getTurnTracker().getCurrentTurn(), THIRD);
    }

    @Test
    public void startBuildPhase() throws Exception {

    }

    @Test
    public void startRobbing() throws Exception {

    }

    @Test
    public void stopRobbing() throws Exception {

    }

    @Test
    public void stopRolling() throws Exception {

    }

    @Test
    public void isEndGame() throws Exception {
        getGame().getClientModel().getPlayer(FIRST).setVictoryPoints(9);
        tf().endGame();
        assertFalse(tf().isEndGame());
        getGame().getClientModel().getPlayer(FIRST).setVictoryPoints(11);
        tf().endGame();
        assertTrue(tf().isEndGame());

    }

    @Test
    public void isSetup() throws Exception {

    }

    @Test
    public void endGame() throws Exception {

    }

}