package server.db.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import server.db.IGameDAO;
import server.models.GameModel;
import server.serialization.ActionDeserializer;
import shared.models.ICommandAction;
import shared.serialization.ModelSerializer;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by kevin on 12/7/16.
 */
public class MongoGameDAO extends MongoDAO<GameModel> implements IGameDAO {

    private MongoDatabase db;
    private MongoCollection<Document> games;
    private MongoCollection<Document> commands;
    private int counter;

    public MongoGameDAO(MongoDatabase db) {
        super(db.getCollection("games"));
        games = db.getCollection("games");
        commands = db.getCollection("commands");
        counter = 1;
    }

    @Override
    public List<ICommandAction> findAllCommands() {
        List<ICommandAction> listOfCommands = new ArrayList<>();
        for (int x = 1; x <= counter; x++) {
            ICommandAction obj = ActionDeserializer.getInstance().deserializeAction(commands.find(eq("_id", x)).first().toJson());
            listOfCommands.add(x-1, obj);
        }
        return listOfCommands;
    }

    @Override
    public boolean insertCommand(ICommandAction command, int gameId) {
        long currnetTotal = commands.count();
        String data = ModelSerializer.getInstance().toJson(command, command.getClass());
        Document doc = Document.parse(data);
        doc.put("_id", counter);
        commands.insertOne(doc);
        if (currnetTotal < commands.count()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean flushCommands() {
        commands.deleteMany(and(gte("_id", 1), lte("_id", counter)));
        counter = 1;
        if (commands.count() == (long) 0) {
            return true;
        }
        return false;
    }

    @Override
    protected Class<GameModel> getTypeClass() {
        return GameModel.class;
    }
}
