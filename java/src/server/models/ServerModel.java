package server.models;

import java.util.Map;

public class ServerModel {

    private Map<Integer, GameModel> gameIdToModel;  //game id to game model
    private Map<Integer, User> idToUser;  //id to users

    public ServerModel() {
    }

    /**
     * registers a User
     */
    public void registerUser() {

    }

    /**
     * gets the User that corresponds to the id parameter
     *
     * @param id a User's id
     * @return the User that corresponds to the id parameter
     */
    public User getUser(int id) {
        return idToUser.get(id);
    }

    /**
     * gets the GameModel that corresponds to the id parameter
     *
     * @param id a Game Model's id
     * @return the Game Model that corresponds to the id parameter
     */
    public GameModel getGameModel(int id) {
        return gameIdToModel.get(id);
    }

}
