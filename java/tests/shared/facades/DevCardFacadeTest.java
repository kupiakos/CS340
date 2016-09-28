package shared.facades;

import client.game.GameManager;
import org.junit.Test;
import shared.models.game.ClientModel;
import shared.models.game.Player;
import shared.models.game.ResourceSet;
import shared.serialization.ModelExample;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Philip on 9/26/2016.
 */
public class DevCardFacadeTest {

    private ClientModel model;
    private Player currentPlayer;
    private List<Player> listOfPlayers;
    private DevCardFacade facade;


    @org.junit.Before
    public void setup() {
        GameManager game = GameManager.getGame();
        model = ModelExample.fullJsonModel();
        game.setClientModel(model);
        facade = game.getFacade().getDevCards();
        listOfPlayers = model.getPlayers();
        currentPlayer = listOfPlayers.get(0);

        Player p = listOfPlayers.get(0);
        ResourceSet resourceSet = new ResourceSet(1, 0, 1, 0, 1);
        p.setResources(resourceSet);
    }

    @Test
    public void canBuyDevCard() throws Exception {

    }

    @Test
    public void buyDevCard() throws Exception {

    }

    @Test
    public void canUseDevCard() throws Exception {

    }

    @Test
    public void useDevCard() throws Exception {

    }

    @Test
    public void useSoldierCard() throws Exception {

    }

    @Test
    public void canUseVictoryPointCards() throws Exception {

    }

    @Test
    public void useVictoryPointCards() throws Exception {

    }

    @Test
    public void canUseRoadBuildingCard() throws Exception {

    }

    @Test
    public void useRoadBuildingCard() throws Exception {

    }

    @Test
    public void canUseYearOfPlentyCard() throws Exception {

    }

    @Test
    public void useYearOfPlentyCard() throws Exception {

    }

    @Test
    public void useMonopolyCard() throws Exception {

    }

}