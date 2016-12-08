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
        long currnetTotal = collection.count();
        String model = ModelSerializer.getInstance().toJson(obj, getTypeClass());
        Document doc = Document.parse(model);
        doc.put("_id", obj.getId());
        collection.insertOne(doc);
        if (currnetTotal < collection.count()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(T obj) {
        long currnetTotal = collection.count();
        String model = ModelSerializer.getInstance().toJson(obj, getTypeClass());
        Document doc = Document.parse(model);
        doc.put("_id", obj.getId());
        if (collection.find(eq("_id", obj.getId())).first().equals(doc)) {
            collection.deleteOne(eq("_id", obj.getId()));
            collection.insertOne(doc);
            if (currnetTotal == collection.count()) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(T obj) {
        long currnetTotal = collection.count();
        String model = ModelSerializer.getInstance().toJson(obj, getTypeClass());
        Document doc = Document.parse(model);
        doc.put("_id", obj.getId());
        if (collection.find(eq("_id", obj.getId())).first().equals(doc)) {
            collection.deleteOne(eq("_id", obj.getId()));
            if (currnetTotal > collection.count()) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
