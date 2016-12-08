package server.db.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import server.db.IGameDAO;
import server.models.GameModel;
import server.serialization.ActionDeserializer;
import shared.models.GameAction;
import shared.models.ICommandAction;
import shared.serialization.ModelSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static com.mongodb.client.model.Sorts.ascending;
import static shared.utils.ClassUtils.getStackTrace;

/**
 * Created by kevin on 12/7/16.
 */
public class MongoGameDAO extends MongoDAO<GameModel> implements IGameDAO {

    private static final Logger LOGGER = Logger.getLogger("MongoGameDAO");
    private MongoCollection<Document> commands;

    public MongoGameDAO(MongoDatabase db) {
        super(db.getCollection("games"));
        commands = db.getCollection("commands");
    }

    @Override
    public List<ICommandAction> findAllCommands() {
        try {
            List<ICommandAction> listOfCommands = new ArrayList<>();
            commands.find().sort(ascending("_id")).forEach((Consumer<? super Document>) i -> {
                ICommandAction action = ActionDeserializer.getInstance().deserializeAction(i.toJson());
                if (action instanceof GameAction) {
                    ((GameAction) action).setGameId(i.getInteger("gameId"));
                }
                listOfCommands.add(action);
            });
            return listOfCommands;
        } catch (MongoException e) {
            LOGGER.warning(getStackTrace(e));
            return null;
        }
    }

    @Override
    public boolean insertCommand(ICommandAction command, int gameId) {
        try {
            String data = ModelSerializer.getInstance().toJson(command, command.getClass());
            Document doc = Document.parse(data);
            doc.put("gameId", gameId);
            commands.insertOne(doc);
            return true;
        } catch (MongoException e) {
            LOGGER.warning(getStackTrace(e));
            return false;
        }
    }

    @Override
    public boolean clearCommands() {
        try {
            commands.deleteMany(new BasicDBObject());
            return (commands.count() == (long) 0);
        } catch (MongoException e) {
            LOGGER.warning(getStackTrace(e));
            return false;
        }
    }

    @Override
    protected Class<GameModel> getTypeClass() {
        return GameModel.class;
    }
}
