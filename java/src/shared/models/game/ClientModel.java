package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import shared.definitions.PlayerIndex;
import java.util.List;
import java.util.ArrayList;

/**
 * The top level client model
 */
@Generated("net.kupiakos")
public class ClientModel {

    @SerializedName("chat")
    @Expose
    private MessageList chat;

    @SerializedName("winner")
    @Expose
    private PlayerIndex winner;

    @SerializedName("turnTracker")
    @Expose
    private TurnTracker turnTracker;

    @SerializedName("map")
    @Expose
    private Map map;

    @SerializedName("bank")
    @Expose
    private ResourceList bank;

    @SerializedName("tradeOffer")
    @Expose
    private TradeOffer tradeOffer;

    @SerializedName("players")
    @Expose
    private List<Player> players = new ArrayList<Player>();

    @SerializedName("version")
    @Expose
    private int version;

    @SerializedName("log")
    @Expose
    private MessageList log;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public ClientModel() {
    }

    /**
      * @param chat All the chat messages.
      * @param winner This is -1 when nobody's won yet. When they have, it's their order index [0-3]
      * @param turnTracker This tracks who's turn it is and what action's being done.
      * @param map The map
      * @param bank The cards available to be distributed to the players.
      * @param tradeOffer The current trade offer, if there is one.
      * @param players The players
      * @param version The version of the model. This is incremented whenever anyone makes a move.
      * @param log All the log messages.
     */
    public ClientModel(MessageList chat, PlayerIndex winner, TurnTracker turnTracker, Map map, ResourceList bank, TradeOffer tradeOffer, List<Player> players, int version, MessageList log) {
            this.chat = chat;
            this.winner = winner;
            this.turnTracker = turnTracker;
            this.map = map;
            this.bank = bank;
            this.tradeOffer = tradeOffer;
            this.players = players;
            this.version = version;
            this.log = log;
    }

    /**
     * @return All the chat messages.
     */
    public MessageList getChat() { return chat; }

    /**
     * @param chat All the chat messages.
     */
    public void setChat(MessageList chat) { this.chat = chat; }

    public ClientModel withChat(MessageList chat) {
        setChat(chat);
        return this;
    }
    /**
     * @return This is -1 when nobody's won yet. When they have, it's their order index [0-3]
     */
    public PlayerIndex getWinner() { return winner; }

    /**
     * @param winner This is -1 when nobody's won yet. When they have, it's their order index [0-3]
     */
    public void setWinner(PlayerIndex winner) { this.winner = winner; }

    public ClientModel withWinner(PlayerIndex winner) {
        setWinner(winner);
        return this;
    }
    /**
     * @return This tracks who's turn it is and what action's being done.
     */
    public TurnTracker getTurnTracker() { return turnTracker; }

    /**
     * @param turnTracker This tracks who's turn it is and what action's being done.
     */
    public void setTurnTracker(TurnTracker turnTracker) { this.turnTracker = turnTracker; }

    public ClientModel withTurnTracker(TurnTracker turnTracker) {
        setTurnTracker(turnTracker);
        return this;
    }
    /**
     * Gets the current {@link Map} of the game.
     * @return the current {@link Map} of the game
     */
    public Map getMap() { return map; }

    /**
     * Sets the {@link Map} of the game.
     * @param map the current {@link Map} of the game
     */
    public void setMap(Map map) { this.map = map; }

    /**
     * Sets the {@link Map} of the game.
     * @param map the current {@link Map} of the game
     * @return the current {@link Map} of the game
     */
    public ClientModel withMap(Map map) {
        setMap(map);
        return this;
    }
    /**
     * @return The cards available to be distributed to the players.
     */
    public ResourceList getBank() { return bank; }

    /**
     * @param bank The cards available to be distributed to the players.
     */
    public void setBank(ResourceList bank) { this.bank = bank; }

    public ClientModel withBank(ResourceList bank) {
        setBank(bank);
        return this;
    }
    /**
     * @return The current trade offer, if there is one.
     */
    public TradeOffer getTradeOffer() { return tradeOffer; }

    /**
     * @param tradeOffer The current trade offer, if there is one.
     */
    public void setTradeOffer(TradeOffer tradeOffer) { this.tradeOffer = tradeOffer; }

    public ClientModel withTradeOffer(TradeOffer tradeOffer) {
        setTradeOffer(tradeOffer);
        return this;
    }
    /**
     * Gets the {@link Player}s of the game
     * @return The {@link Player}s currently playing this game
     */
    public List<Player> getPlayers() { return players; }

    /**
     * Sets the {@link Player}s of the game
     * @param players The {@link Player}s currently playing this game
     */
    public void setPlayers(List<Player> players) { this.players = players; }

    public ClientModel withPlayers(List<Player> players) {
        setPlayers(players);
        return this;
    }
    /**
     * @return The version of the model. This is incremented whenever anyone makes a move.
     */
    public int getVersion() { return version; }

    /**
     * @param version The version of the model. This is incremented whenever anyone makes a move.
     */
    public void setVersion(int version) { this.version = version; }

    public ClientModel withVersion(int version) {
        setVersion(version);
        return this;
    }
    /**
     * @return All the log messages.
     */
    public MessageList getLog() { return log; }

    /**
     * @param log All the log messages.
     */
    public void setLog(MessageList log) { this.log = log; }

    public ClientModel withLog(MessageList log) {
        setLog(log);
        return this;
    }

    @Override
    public String toString() {
        return "ClientModel [" +
            "chat=" + chat +
            ", winner=" + winner +
            ", turnTracker=" + turnTracker +
            ", map=" + map +
            ", bank=" + bank +
            ", tradeOffer=" + tradeOffer +
            ", players=" + players +
            ", version=" + version +
            ", log=" + log +
            "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ClientModel) {
            return equals((ClientModel)other);
        }
        return false;
    }

    public boolean equals(ClientModel other) {
        return (
            chat == other.chat &&
            winner == other.winner &&
            turnTracker == other.turnTracker &&
            map == other.map &&
            bank == other.bank &&
            tradeOffer == other.tradeOffer &&
            players == other.players &&
            version == other.version &&
            log == other.log
        );
    }
}
