package shared.facades;

import org.junit.Before;
import org.junit.Test;
import shared.definitions.Constants;
import shared.definitions.TurnStatus;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.serialization.ModelExample;

import static org.junit.Assert.*;
import static shared.definitions.PlayerIndex.*;
import static shared.definitions.TurnStatus.ROLLING;


/**
 * @author audakel on 9/29/16.
 */
public class TurnFacadeTest {
    private TurnFacade tf;
    private Player p;
    private int len;
    private ClientModel model;

    /**
     * Inits the game and sets up a player and turnFacade
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        model = ModelExample.fullJsonModel();
        FacadeManager facades = new FacadeManager(model);
        tf = facades.getTurn();
        p = model.getPlayer(FIRST);
    }

    @Test
    public void endTurn() throws Exception {

    }

    /**
     * Checks if you can end turn before putting down 2 roads and settlements
     *
     * @throws Exception
     */
    @Test
    public void canEndTurn() throws Exception {
        assertEquals(tf.getPhase(), TurnStatus.SECOND_ROUND);
        p.setRoads(Constants.START_ROADS - 1);
        p.setSettlements(Constants.START_SETTLEMENTS - 1);
        assertFalse(tf.canEndTurn(p));
        p.setSettlements(Constants.START_SETTLEMENTS - 2);
        assertFalse(tf.canEndTurn(p));
        p.setRoads(Constants.START_ROADS - 2);
        assertTrue(tf.canEndTurn(p));
        tf.endTurn(p);
        assertEquals(tf.getPhase(), ROLLING);
        assertEquals(model.getTurnTracker().getCurrentTurn(), FIRST);
    }

    @Test
    public void isPlayersTurn() throws Exception {
        model.getTurnTracker().setCurrentTurn(FOURTH);
        assertEquals(model.getTurnTracker().getCurrentTurn(), FOURTH);

    }

    @Test
    public void getPhase() throws Exception {

    }

    @Test
    public void setPhase() throws Exception {

    }

    @Test
    public void getPlayers() throws Exception {
        assertEquals(model.getPlayerCount(), 4);
    }

    @Test
    public void startGame() throws Exception {

    }

    @Test
    public void advanceTurnStatus() throws Exception {
        model.getTurnTracker().setStatus(ROLLING);
        model.getTurnTracker().setCurrentTurn(SECOND);
        tf.endTurn(p);

//        model.getTurnTracker().setStatus(PLAYING);
//        assertTrue(tf.advanceTurnStatus());
//        assertEquals(tf.getPhase(), ROLLING);
//        assertEquals(model.getTurnTracker().getCurrentTurn(), THIRD);
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
        model.getPlayer(FIRST).setVictoryPoints(9);
        tf.endGame();
        assertFalse(tf.isEndGame());
        model.getPlayer(FIRST).setVictoryPoints(11);
        tf.endGame();
        assertTrue(tf.isEndGame());

    }

    @Test
    public void isSetup() throws Exception {

    }

    @Test
    public void endGame() throws Exception {

    }

}