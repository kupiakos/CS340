package server.db.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import server.db.IDAO;
import server.db.IDAOObject;
import shared.serialization.ModelSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;
import static shared.utils.ClassUtils.getStackTrace;

/**
 * Created by kevin on 12/7/16.
 */
public abstract class MongoDAO<T extends IDAOObject> implements IDAO<T> {
    private static final Logger LOGGER = Logger.getLogger("MongoDAO");
    private MongoCollection<Document> collection;

    protected MongoDAO(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    protected abstract Class<T> getTypeClass();

    @Override
    public T findById(int id) {
        try {
            Document obj = collection.find(eq("id", id)).first();
            if (obj == null)
                return null;
            return ModelSerializer.getInstance().fromJson(obj.toJson(), getTypeClass());
        } catch (MongoException e) {
            LOGGER.warning(getStackTrace(e));
            return null;
        }
    }

    @Override
    public List<T> findAll() {
        try {
            MongoCursor<Document> cursor = collection.find().iterator();
            List<T> result = new ArrayList<>();
            while (cursor.hasNext()) {
                result.add(ModelSerializer.getInstance().fromJson(cursor.next().toJson(), getTypeClass()));
            }
            return result;
        } catch (MongoException e) {
            LOGGER.warning(getStackTrace(e));
            return null;
        }
    }

    @Override
    public boolean insert(T obj) {
        try {
            String model = ModelSerializer.getInstance().toJson(obj, getTypeClass());
            Document doc = Document.parse(model);
            doc.put("id", obj.getId());
            collection.insertOne(doc);
            return true;
        } catch (MongoException e) {
            LOGGER.warning(getStackTrace(e));
            return false;
        }
    }

    @Override
    public boolean update(T obj) {
        try {
            String model = ModelSerializer.getInstance().toJson(obj, getTypeClass());
            Document doc = Document.parse(model);
            doc.put("id", obj.getId());
            collection.updateOne(eq("id", obj.getId()), new BasicDBObject("$set", doc));
            return true;
        } catch (MongoException e) {
            LOGGER.warning(getStackTrace(e));
            return false;
        }
    }

    @Override
    public boolean delete(T obj) {
        try {
            String model = ModelSerializer.getInstance().toJson(obj, getTypeClass());
            Document doc = Document.parse(model);
            doc.put("id", obj.getId());
            collection.deleteOne(eq("id", obj.getId()));
            return true;
        } catch (MongoException e) {
            LOGGER.warning(getStackTrace(e));
            return false;
        }
    }
}
