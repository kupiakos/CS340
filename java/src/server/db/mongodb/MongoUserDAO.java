package server.db.mongodb;

import com.mongodb.client.MongoDatabase;
import server.db.IUserDAO;
import server.models.User;

/**
 * Created by kevin on 12/7/16.
 */
public class MongoUserDAO extends MongoDAO<User> implements IUserDAO {

    public MongoUserDAO(MongoDatabase db) {
        super(db.getCollection("users"));
    }

    @Override
    protected Class<User> getTypeClass() {
        return User.class;
    }
}
