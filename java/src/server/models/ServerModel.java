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
     * Called when a new {@link User} registers.  This method inserts a
     * new {@link User} into {@link ServerModel#idToUser}
     *
     * @pre {@link #getUser(int)} returns a valid {@link User} for some {@code id}
     * @post A new {@link User} is placed in {@link #idToUser}
     */
    public void registerUser() {

    }

    /**
     * Called when a new game is created.  This method creates a new {@link GameModel}
     * and inserts it into {@link ServerModel#gameIDToModel}
     *
     * @return the new {@link GameModel#id} created
     * @pre {@link #getGameModel(int)} returns a valid {@link GameModel} for some {@code id}
     * @post A new {@link GameModel} is placed in {@link #gameIDToModel}
     */
    public int startNewGame() {
        return 0;
    }

    /**
     * Returns a {@link User} object or null
     *
     * @param ID An integer that is used to determine which {@link User} to return
     * @return a {@link User} if {@code ID} is contained in {@link #idToUser},
     * null otherwise
     * @pre None
     * @post A {@link User} object or null is returned
     */
    public User getUser(int ID) {
        return idToUser.get(ID);
    }

    /**
     * Returns a {@link GameModel} object or null
     *
     * @param ID An integer that is used to determine which {@link GameModel} to return
     * @return a {@link GameModel} if {@code ID} is contained in {@link #gameIDToModel},
     * null otherwise
     * @pre None
     * @post A value {@link GameModel} object or null is returned
     */
    public GameModel getGameModel(int ID) {
        return gameIDToModel.get(ID);
    }
}
