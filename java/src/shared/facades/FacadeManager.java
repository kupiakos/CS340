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
    private RobberFacade robber;
    private TradingFacade trading;
    private ResourcesFacade resources;
    private DevCardFacade devCards;

    /**
     * Init the Facade manager with the starting clientModel
     *
     * @param cm current ClientModel
     * @pre {@code cm} is a valid {@link ClientModel}.
     * @post This provides valid init on {@code ClientModel}.
     */
    public FacadeManager(ClientModel cm) {
        clientModel = cm;
        turn = new TurnFacade(this);
        chat = new ChatFacade(this);
        map = new MapFacade(this);
        building = new BuildingFacade(this);
        robber = new RobberFacade(this);
        trading = new TradingFacade(this);
        resources = new ResourcesFacade(this);
        devCards = new DevCardFacade(this);
    }

    /**
     * Updates all facades with a new model
     *
     * @pre {@code cm} is a valid {@link ClientModel}.
     * @post Will update all the facades with the new ClientModel
     */
    public void update(ClientModel cm) {
        setClientModel(cm);
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

    public void setTurn(TurnFacade turn) {
        this.turn = turn;
    }

    public ChatFacade getChat() {
        return chat;
    }

    public void setChat(ChatFacade chat) {
        this.chat = chat;
    }

    public BuildingFacade getBuilding() {
        return building;
    }

    public void setBuilding(BuildingFacade building) {
        this.building = building;
    }

    public MapFacade getMap() {
        return map;
    }

    public void setMap(MapFacade map) {
        this.map = map;
    }

    public RobberFacade getRobber() {
        return robber;
    }

    public void setRobber(RobberFacade robber) {
        this.robber = robber;
    }

    public TradingFacade getTrading() {
        return trading;
    }

    public void setTrading(TradingFacade trading) {
        this.trading = trading;
    }

    public ResourcesFacade getResources() {
        return resources;
    }

    public void setResources(ResourcesFacade resources) {

        this.resources = resources;
    }

    public DevCardFacade getDevCards() {
        return devCards;
    }

    public void setDevCards(DevCardFacade devCards) {
        this.devCards = devCards;
    }
}
