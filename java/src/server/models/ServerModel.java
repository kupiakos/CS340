package server.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.db.IPersistenceProvider;
import shared.models.ICommandAction;
import shared.models.game.ClientModel;
import shared.models.games.GameInfo;
import shared.models.games.PlayerInfo;

import java.util.*;

public class ServerModel {

    // Possibly consider a Map<String, User> instead
    private Map<Integer, User> users;
    private Map<Integer, GameModel> gameModels;
    private transient Set<UserSession> sessions;

    public ServerModel() {
        users = new HashMap<>();
        gameModels = new HashMap<>();
        sessions = new HashSet<>();
    }

    /**
     * Register a new user with a given username and password.
     *
     * @return the ID of the new user
     */
    public int registerUser(@NotNull String username, @NotNull String password) throws IllegalArgumentException {
        if (users.values().stream().anyMatch(u -> username.equals(u.getUsername()))) {
            throw new IllegalArgumentException("The user " + username + " already exists!");
        }
        int id = users.keySet().stream().max(Integer::compareTo).orElseGet(() -> 0) + 1;
        User user = new User(id, username, password);
        assert !users.containsKey(id);
        users.put(id, user);
        return id;
    }

    @Nullable
    public User getUser(@NotNull String username) {
        return users.values().stream().filter(u -> username.equals(u.getUsername())).findFirst().orElse(null);
    }

    /**
     * Determine whether a username and password combination are already registered
     *
     * @param username the username, not null
     * @param password the password, not null
     * @return true if the user is authenticated, else false
     */
    public boolean authenticateUser(@NotNull String username, @NotNull String password) {
        User user = getUser(username);
        return user != null && user.getPassword().equals(password);
    }

    public UserSession newSession(@NotNull String username) {
        User user = getUser(username);
        if (user == null) {
            return null;
        }
        UserSession session;
        do {
            session = UserSession.newSession(user.getId());
        } while (sessions.contains(session));
        sessions.add(session);
        return session;
    }

    public boolean validateSession(@NotNull UserSession session) {
        return sessions.contains(session);
    }

    /**
     * Called when a new game is created.  This method creates a new {@link GameModel}.
     *
     * @return the new {@link GameModel#id} created
     * @pre {@link #getGameModel(int)} returns a valid {@link GameModel} for some {@code id}
     * @post A new {@link GameModel} is placed in {@link #gameModels}
     */
    public int startNewGame(boolean randomTiles, boolean randomPorts, boolean randomNumbers, String name) {
        List<PlayerInfo> playerInfos = new ArrayList<>();
        int id = gameModels.keySet().stream().max(Integer::compareTo).orElseGet(() -> 0) + 1;

        GameInfo gameInfo = new GameInfo(playerInfos, name, id);

        ClientModel clientModel = new ClientModel(randomTiles, randomPorts, randomNumbers);

        GameModel gameModel = new GameModel(id, gameInfo, clientModel);
        gameModels.put(id, gameModel);
        return id;
    }

    /**
     * Returns a {@link User} object or null
     *
     * @param ID An integer that is used to determine which {@link User} to return
     * @return a {@link User} if {@code ID} is contained in {@link #users},
     * null otherwise
     * @pre None
     * @post A {@link User} object or null is returned
     */
    @Nullable
    public User getUser(int ID) {
        return users.get(ID);
    }

    /**
     * Returns a {@link GameModel} object or null
     *
     * @param ID An integer that is used to determine which {@link GameModel} to return
     * @return a {@link GameModel} if {@code ID} is contained in {@link #gameModels},
     * null otherwise
     * @pre None
     * @post A value {@link GameModel} object or null is returned
     */
    @Nullable
    public GameModel getGameModel(int ID) {
        return gameModels.get(ID);
    }

    @Nullable
    public List<GameInfo> getGameInfo() {
        List<GameInfo> gameInfoList = new ArrayList<>();
        for (GameModel g : gameModels.values()) {
            gameInfoList.add(g.getGameInfo());
        }
        return gameInfoList;
    }

    /**
     * Called whenever the server model is initialized so server can retrieve any preexisting users and games.
     *
     * @param p
     */
    public void updateFromDatabase(IPersistenceProvider p) {
        for (User user : p.getUserDAO().findAll()) {
            this.users.put(user.getId(), user);
        }
        for (GameModel game : p.getGameDAO().findAll()) {
            this.gameModels.put(game.getId(), game);
        }
        for (ICommandAction command : p.getGameDAO().findAllCommands()) {
            //TODO perform each command for the correct game.
        }
    }

    /**
     * Called when server has processed n commands, this method updates all the game models in the database.
     *
     * @param p
     */
    public void updateGamesInDatabase(IPersistenceProvider p) {
        for (GameModel game : gameModels.values()) {
            p.getGameDAO().update(game);
        }
    }
}
