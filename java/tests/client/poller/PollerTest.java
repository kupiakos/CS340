package client.poller;

import client.game.GameManager;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Checks the workings of the Poller - can it start, stop and set the clientModel with new data
 *
 * @author audakel on 9/21/16.
 */
public class PollerTest {
    private Poller p;
    private GameManager gm;

    @Before
    public void setUp() throws Exception {
        p = new Poller();
        gm = GameManager.getGame();
    }

    /**
     * Makes sure the new clientModel is empty, starts the poller and waits a bit, then checks to see
     * if a new model is in it with version -1
     *
     * @throws Exception
     */
    @Test
    public void startPoller() throws Exception {
        assertNull(gm.getClientModel());
        p.startPoller();
        TimeUnit.SECONDS.sleep(5);
        assertEquals(gm.getClientModel().getVersion(), -1);
    }

    /**
     * Makes sure that something is in the clientModel, then clears it out and stops the poller, then checks
     * to make sure it is still empty
     *
     * @throws Exception
     */
    @Test
    public void stopPoller() throws Exception {
        assertNotNull(gm.getClientModel());
        p.stopPoller();
        gm.setClientModel(null);
        assertNull(gm.getClientModel());
    }

    /**
     * Sets the clientModel to null, then fills it with a new clientModel and checks specific info
     *
     * @throws Exception
     */
    @Test
    public void setClientModel() throws Exception {
        gm.setClientModel(null);
        assertNull(gm.getClientModel());
        //TODO:: grab specific info from dummy server and check for it
    }

}