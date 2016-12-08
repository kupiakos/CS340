package server.db.postgres;

import server.db.IDAO;
import server.db.IDAOObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
            Map<String, Object> entries = getColumns(obj);
            List<String> columns = new ArrayList<>();
            List<Object> values = new ArrayList<>();
            for (Map.Entry<String, Object> v : entries.entrySet()) {
                columns.add(v.getKey());
                values.add(v.getValue());
            }
            String[] q = new String[entries.size()];
            Arrays.fill(q, "?");
            String sql = "INSERT INTO " + getTableName() +
                    " (" + String.join(",", columns) + ") " +
                    " VALUES (" + String.join(",", (CharSequence[]) q) + ");";
            PreparedStatement stmt = db.prepareStatement(sql);
            int col = 1;
            for (Object v : values) {
                stmt.setObject(col++, v);
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
