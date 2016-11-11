package shared.models.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.models.GameAction;

import javax.annotation.Generated;
import java.util.Objects;

/**
 * A game creation request.
 */
@Generated("net.kupiakos")
public class CreateGameRequest extends GameAction {

    @SerializedName("randomTiles")
    @Expose
    private boolean randomTiles;

    @SerializedName("randomPorts")
    @Expose
    private boolean randomPorts;

    @SerializedName("randomNumbers")
    @Expose
    private boolean randomNumbers;

    @SerializedName("name")
    @Expose
    private String name;


    // CUSTOM CODE
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public CreateGameRequest() {
    }

    /**
     * @param randomTiles   whether the tiles should be randomly placed
     * @param randomPorts   whether the port should be randomly placed
     * @param randomNumbers whether the numbers should be randomly placed
     * @param name          The name of the game
     */
    public CreateGameRequest(boolean randomTiles, boolean randomPorts, boolean randomNumbers, String name) {
        this.randomTiles = randomTiles;
        this.randomPorts = randomPorts;
        this.randomNumbers = randomNumbers;
        this.name = name;
    }

    /**
     * @return whether the tiles should be randomly placed
     */
    public boolean isRandomTiles() {
        return randomTiles;
    }

    /**
     * @param randomTiles whether the tiles should be randomly placed
     */
    public void setRandomTiles(boolean randomTiles) {
        this.randomTiles = randomTiles;
    }

    public CreateGameRequest withRandomTiles(boolean randomTiles) {
        setRandomTiles(randomTiles);
        return this;
    }

    /**
     * @return whether the port should be randomly placed
     */
    public boolean isRandomPorts() {
        return randomPorts;
    }

    /**
     * @param randomPorts whether the port should be randomly placed
     */
    public void setRandomPorts(boolean randomPorts) {
        this.randomPorts = randomPorts;
    }

    public CreateGameRequest withRandomPorts(boolean randomPorts) {
        setRandomPorts(randomPorts);
        return this;
    }

    /**
     * @return whether the numbers should be randomly placed
     */
    public boolean isRandomNumbers() {
        return randomNumbers;
    }

    /**
     * @param randomNumbers whether the numbers should be randomly placed
     */
    public void setRandomNumbers(boolean randomNumbers) {
        this.randomNumbers = randomNumbers;
    }

    public CreateGameRequest withRandomNumbers(boolean randomNumbers) {
        setRandomNumbers(randomNumbers);
        return this;
    }

    /**
     * @return The name of the game
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name of the game
     */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    public CreateGameRequest withName(@NotNull String name) {
        setName(name);
        return this;
    }

    @Override
    public String toString() {
        return "CreateGameRequest [" +
                "randomTiles=" + randomTiles +
                ", randomPorts=" + randomPorts +
                ", randomNumbers=" + randomNumbers +
                ", name=" + name +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof CreateGameRequest) {
            return equals((CreateGameRequest) other);
        }
        return false;
    }

    public boolean equals(CreateGameRequest other) {
        return (
                randomTiles == other.randomTiles &&
                        randomPorts == other.randomPorts &&
                        randomNumbers == other.randomNumbers &&
                        Objects.equals(name, other.name)
        );
    }

    /**
     * Run on the server.  Creates a new game on the server with the parameters specified in this action.
     */
    @Override
    public void execute() {

    }
}
