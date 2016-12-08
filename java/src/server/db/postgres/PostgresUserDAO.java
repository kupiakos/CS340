package server.db.postgres;

import server.db.IUserDAO;
import server.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by elija on 12/2/2016.
 */
public class PostgresUserDAO extends PostgresDAO<User> implements IUserDAO {
    private Connection db = null;

    public PostgresUserDAO(Connection db) {
        super(db);
        this.db = db;
    }

    @Override
    protected Class<User> getTypeClass() {
        return User.class;
    }

    @Override
    protected String getTableName() {
        return "USERINFO";
    }

    @Override
    protected User getValue(ResultSet rs) throws SQLException {
        return new User(rs.getInt(1), rs.getString(2), rs.getString(3));
    }

    @Override
    protected Map<String, Object> getColumns(User obj) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("ID", obj.getId());
        result.put("USERNAME", obj.getUsername());
        result.put("PASSWORD", obj.getPassword());
        return result;
    }


}
