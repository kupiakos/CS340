package server.models;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ServerModel {

    private Map<Integer, User> idToUser;
    private Map<Integer, GameModel> gameIDToModel;

    public ServerModel() {
        idToUser = new HashMap<>();
        gameIDToModel = new HashMap<>();
    }

    /**
     * Called when a new {@link User} registers.  This method inserts a new {@link User} into {@link ServerModel#idToUser}
     *
     * @pre The Register button is clicked
     * the credentials ({@link User#username} and {@link User#password}) are valid
     * @post A new {@link User} is placed in {@link ServerModel#idToUser}
     */
    public void registerUser() {

    }

    /**
     * Called when a new game is created.  This method creates a new {@link GameModel} and inserts it into {@link ServerModel#gameIDToModel}
     *
     * @pre The Create Game button is clicked before a game is started
     * each {@link shared.models.game.Player} are valid with their game color
     * the game name is valid
     * @post A new {@link GameModel} is placed in {@link ServerModel#gameIDToModel}
     */
    public void startNewGame() {

    }

    /**
     * Returns a {@link User} based on the {@code ID} given
     *
     * @param ID An integer that is used to determine which {@link User} to return
     * @return a {@link User} if {@code ID} is contained in {@link ServerModel#idToUser}; {@code null} otherwise
     * @pre The {@code ID} must not be {@code null}
     * @post A {@link User} object or {@code null} is returned
     */
    public User getUser(int ID) {
        return idToUser.get(ID);
    }

    /**
     * Returns a {@link GameModel} based on the {@code ID} given
     *
     * @param ID An integer that is used to determine which {@link GameModel} to return
     * @return a {@link GameModel} if {@code ID} is contained in {@link ServerModel#gameIDToModel}; {@code null} otherwise
     * @pre The {@code ID} must not be {@code null}
     * @post A value {@link GameModel} object or {@code null} is returned
     */
    public GameModel getGameModel(int ID) {
        return gameIDToModel.get(ID);
    }
}
