package server.db.mongodb;

import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import server.db.IGameDAO;
import server.models.GameModel;
import shared.models.ICommandAction;
import shared.serialization.ModelSerializer;

import java.util.List;

/**
 * Created by kevin on 12/7/16.
 */
public class MongoGameDAO extends MongoDAO<GameModel> implements IGameDAO {

    private MongoDatabase db;
    private MongoCollection<Document> games;
    private MongoCollection<Document> commands;

    public MongoGameDAO(MongoDatabase db) {
        super(db.getCollection("games"));
        games = db.getCollection("games");
        commands = db.getCollection("commands");
    }

    @Override
    public List<ICommandAction> findAllCommands() {
        return null;
    }

    @Override
    public boolean insertCommand(ICommandAction command, int gameId) {
        String data = ModelSerializer.getInstance().toJson(command, command.getClass());
        DBObject object = (DBObject) JSON.parse(data);
        object.put("gameId", gameId);
        return true;
    }

    @Override
    public boolean flushCommands() {
        return false;
    }

    @Override
    protected Class<GameModel> getTypeClass() {
        return GameModel.class;
    }
}
