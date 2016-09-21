package shared.facades;

import shared.models.game.ClientModel;

/**
 * This manager creates a simple way to update and create all facades at once.
 */
public class FacadeManager {
    /**
     * A reference to the game info in the client model, will update frequently
     */
    private ClientModel clientModel;

    /**
     * Handles all the api code for a players turn
     */
    private TurnFacade turn;

    /**
     * Checks to see if player can chat, than provides api for chatting
     */
    private ChatFacade chat;

    /**
     * Provides an abstracted interface over the model for the game map
     */
    private MapFacade map;

    /**
     * Provides an abstracted interface over the model for building
     */
    private BuildingFacade building;

    // TODO:: add more facades here

    /**
     * Init the Facade manager with the starting clientModel
     *
     * @param cm current ClientModel
     * @pre {@code cm} is a valid {@link ClientModel}.
     * @post This provides valid init on {@code ClientModel}.
     */
    public FacadeManager(ClientModel cm) {
        clientModel = cm;
        turn = new TurnFacade(cm);
        chat = new ChatFacade(cm);
        map = new MapFacade(cm);
        building = new BuildingFacade(cm);
        // TODO:: add more facades here
    }


    /**
     * Updates all facades with a new model
     *
     * @pre {@code cm} is a valid {@link ClientModel}.
     * @post Will update all the facades with the new ClientModel
     */
    public void update(ClientModel cm) {
        setClientModel(cm);
        turn.setModel(cm);
        chat.setModel(cm);
        map.setModel(cm);
        building.setModel(cm);
        // TODO:: add more facades here
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    private void setClientModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public TurnFacade getTurn() {
        return turn;
    }

    public ChatFacade getChat() {
        return chat;
    }

    public BuildingFacade getBuilding() {
        return building;
    }

    public MapFacade getMap() {
        return map;
    }

}
