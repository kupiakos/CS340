package server.db.postgres;

import server.db.IUserDAO;
import server.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elija on 12/2/2016.
 */
public class PostgresUserDAO implements IUserDAO {
    private Connection db = null;

    public PostgresUserDAO(Connection db) {
        this.db = db;
    }

    @Override
    public User findById(int id) {
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERINFO WHERE ID=" + id + ";");
            if (!rs.next()) {
                return null;
            }
            User result = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            stmt.close();
            rs.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERINFO");
            while (rs.next()) {
                result.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean insert(User obj) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("INSERT INTO USERINFO VALUES (" + obj.getId() + ", '" + obj.getUsername() + "', '" + obj.getPassword() + "');");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User obj) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("UPDATE USERINFO " +
                    "SET USERNAME = '" + obj.getUsername() + "', PASSWORD ='" + obj.getPassword() + "' " +
                    "WHERE ID =" + obj.getId() + ";");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(User obj) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("DELETE FROM USERINFO WHERE ID=" + obj.getId() + ";");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
