package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class ClientModel {

    @SerializedName("bank")
    @Expose
    private Bank bank;
    @SerializedName("chat")
    @Expose
    private Chat chat;
    @SerializedName("log")
    @Expose
    private Log log;
    @SerializedName("map")
    @Expose
    private Map map;
    @SerializedName("players")
    @Expose
    private List<Player> players = new ArrayList<Player>();
    @SerializedName("tradeOffer")
    @Expose
    private TradeOffer tradeOffer;
    @SerializedName("turnTracker")
    @Expose
    private TurnTracker turnTracker;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("winner")
    @Expose
    private String winner;

    /**
     * No args constructor for use in serialization
     */
    public ClientModel() {
    }

    /**
     * @param tradeOffer
     * @param turnTracker
     * @param players
     * @param map
     * @param winner
     * @param bank
     * @param chat
     * @param log
     * @param version
     */
    public ClientModel(Bank bank, Chat chat, Log log, Map map, List<Player> players, TradeOffer tradeOffer, TurnTracker turnTracker, String version, String winner) {
        this.bank = bank;
        this.chat = chat;
        this.log = log;
        this.map = map;
        this.players = players;
        this.tradeOffer = tradeOffer;
        this.turnTracker = turnTracker;
        this.version = version;
        this.winner = winner;
    }

    /**
     * @return The bank
     */
    public Bank getBank() {
        return bank;
    }

    /**
     * @param bank The bank
     */
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public ClientModel withBank(Bank bank) {
        this.bank = bank;
        return this;
    }

    /**
     * @return The chat
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * @param chat The chat
     */
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public ClientModel withChat(Chat chat) {
        this.chat = chat;
        return this;
    }

    /**
     * @return The log
     */
    public Log getLog() {
        return log;
    }

    /**
     * @param log The log
     */
    public void setLog(Log log) {
        this.log = log;
    }

    public ClientModel withLog(Log log) {
        this.log = log;
        return this;
    }

    /**
     * @return The map
     */
    public Map getMap() {
        return map;
    }

    /**
     * @param map The map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    public ClientModel withMap(Map map) {
        this.map = map;
        return this;
    }

    /**
     * @return The players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param players The players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public ClientModel withPlayers(List<Player> players) {
        this.players = players;
        return this;
    }

    /**
     * @return The tradeOffer
     */
    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }

    /**
     * @param tradeOffer The tradeOffer
     */
    public void setTradeOffer(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
    }

    public ClientModel withTradeOffer(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
        return this;
    }

    /**
     * @return The turnTracker
     */
    public TurnTracker getTurnTracker() {
        return turnTracker;
    }

    /**
     * @param turnTracker The turnTracker
     */
    public void setTurnTracker(TurnTracker turnTracker) {
        this.turnTracker = turnTracker;
    }

    public ClientModel withTurnTracker(TurnTracker turnTracker) {
        this.turnTracker = turnTracker;
        return this;
    }

    /**
     * @return The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    public ClientModel withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * @return The winner
     */
    public String getWinner() {
        return winner;
    }

    /**
     * @param winner The winner
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    public ClientModel withWinner(String winner) {
        this.winner = winner;
        return this;
    }

}
