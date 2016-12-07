package server.db.postgres;

import com.sun.tools.internal.ws.processor.model.Model;
import server.db.IGameDAO;
import server.models.GameModel;
import server.models.User;
import server.serialization.ActionDeserializer;
import shared.models.ICommandAction;
import shared.serialization.ModelSerializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elija on 12/2/2016.
 */
public class PostgresGameDAO implements IGameDAO {
    private Connection db = null;

    public PostgresGameDAO(Connection db) {
        this.db = db;
    }

    @Override
    public GameModel findById(int id) {
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM GAMES WHERE ID=" + id + ";");
            if (!rs.next()) {
                return null;
            }
            GameModel result = ModelSerializer.getInstance().fromJson(rs.getString(2), GameModel.class);
            stmt.close();
            rs.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<GameModel> findAll() {
        List<GameModel> result = new ArrayList<>();
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM GAMES");
            while (rs.next()) {
                result.add(ModelSerializer.getInstance().fromJson(rs.getString(2), GameModel.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean insert(GameModel obj) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PREPARE insertGameModel (int, text) AS" +
            "INSERT INTO GAMES VALUES ($1, $2);" +
            "EXECUTE insertGameModel(obj.getId(), ModelSerializer.getInstance().toJson(obj, GameModel.class));");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(GameModel obj) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PREPARE updateGameModel (int, text) AS" +
            "UPDATE GAMES SET MODEL = $1 WHERE ID = $2;" +
            "EXECUTE updateGameModel(" + obj.getId() + ", " + ModelSerializer.getInstance().toJson(obj, GameModel.class) + ");");
//            stmt.execute("UPDATE GAMES SET MODEL = '" + ModelSerializer.getInstance().toJson(obj, GameModel.class) + "' " +
//                    "WHERE ID =" + obj.getId() + ";");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(GameModel obj) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("DELETE FROM GAMES WHERE ID=" + obj.getId() + ";");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ICommandAction> findAllCommands() {
        List<ICommandAction> result = new ArrayList<>();
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM COMMANDS");
            while (rs.next()) {
                result.add(ActionDeserializer.getInstance().deserializeAction(rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean insertCommand(ICommandAction command, int gameId) {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("PREPARE insertCommand (int, text) AS" +
                    "INSERT INTO COMMANDS (ID, COMMAND) VALUES ($1, $2);" +
                    "EXECUTE insertCommand(gameId, ModelSerializer.getInstance().toJson(command, command.getClass()));");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean flushCommands() {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("DELETE FROM COMMANDS;");
            stmt.execute("ALTER SEQUENCE COMMANDS_COMMAND_ORDER_SEQ RESTART WITH 1;");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
