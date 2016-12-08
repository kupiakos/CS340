package server.db.postgres;

import server.db.IDAO;
import server.db.IDAOObject;
import server.db.IUserDAO;
import server.models.User;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by elija on 12/2/2016.
 */
public abstract class PostgresDAO<T extends IDAOObject> implements IDAO<T> {
    private Connection db = null;

    public PostgresDAO(Connection db) {
        this.db = db;
    }

    @Override
    public T findById(int id) {
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM " + getTableName() + " WHERE ID=?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            T result = getValue(rs);
            stmt.close();
            rs.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        List<T> result = new ArrayList<>();
        try {
            PreparedStatement stmt = db.prepareStatement("SELECT * FROM " + getTableName() + ";");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(getValue(rs));
            }
            stmt.close();
            rs.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public boolean insert(T obj) {
        try {
            Map<String, Object> values = getColumns(obj);
            String[] q = new String[values.size()];
            Arrays.fill(q, "?");
            String sql = "INSERT INTO " + getTableName() +
                    " VALUES (" + String.join(",", (CharSequence[]) q) + ");";

            PreparedStatement stmt = db.prepareStatement(sql);
            int col = 1;
            for (Map.Entry<String, Object> v : values.entrySet()) {
                Object val = v.getValue();
                if (val instanceof String)
                    stmt.setString(col++, (String) val);
                else if (val instanceof Integer)
                    stmt.setInt(col++, (Integer) val);
//                stmt.setObject(col++, v.getValue());
            }
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(T obj) {
        try {
            Map<String, Object> values = getColumns(obj);

            List<String> q = values.keySet().stream()
                    .map(k -> String.format("%s = ?", k))
                    .collect(Collectors.toList());
            String sql = "UPDATE " + getTableName() + " SET " + String.join(",", q) +
                    " WHERE ID = ?;";

            PreparedStatement stmt = db.prepareStatement(sql);
            int col = 1;

            for (Object v : values.values()) {
                stmt.setObject(col++, v);
            }
            stmt.setInt(col, obj.getId());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(T obj) {
        try {
            PreparedStatement stmt = db.prepareStatement("DELETE FROM " + getTableName() + " WHERE ID = ?;");
            stmt.setInt(1, obj.getId());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    protected abstract Class<T> getTypeClass();
    protected abstract String getTableName();
    protected abstract T getValue(ResultSet rs) throws SQLException;
    protected abstract Map<String, Object> getColumns(T obj);

}
