package server.db.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import server.db.IDAO;
import server.db.IDAOObject;
import shared.serialization.ModelSerializer;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by kevin on 12/7/16.
 */
public abstract class MongoDAO<T extends IDAOObject> implements IDAO<T> {
    private MongoCollection<Document> collection;

    protected MongoDAO(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    protected abstract Class<T> getTypeClass();

    @Override
    public T findById(int id) {
        Document obj = collection.find(eq("id", id)).first();
        return ModelSerializer.getInstance().fromJson(obj.toJson(), getTypeClass());
    }

    @Override
    public List<T> findAll() {
        MongoCursor<Document> cursor = collection.find().iterator();
        List<T> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(ModelSerializer.getInstance().fromJson(cursor.next().toJson(), getTypeClass()));
        }
        return result;
    }

    @Override
    public boolean insert(T obj) {
        return false;
    }

    @Override
    public boolean update(T obj) {
        return false;
    }

    @Override
    public boolean delete(T obj) {
        return false;
    }
}
