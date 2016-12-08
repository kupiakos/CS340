package server.db.postgres;

import server.db.IGameDAO;
import server.models.GameModel;
import server.serialization.ActionDeserializer;
import shared.models.GameAction;
import shared.models.ICommandAction;
import shared.serialization.ModelSerializer;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elija on 12/2/2016.
 */
public class PostgresGameDAO extends PostgresDAO<GameModel> implements IGameDAO {
    private Connection db = null;

    public PostgresGameDAO(Connection db) {
        super(db);
        this.db = db;
    }

    @Override
    protected Class<GameModel> getTypeClass() {
        return GameModel.class;
    }

    @Override
    protected String getTableName() {
        return "GAMES";
    }

    @Override
    protected GameModel getValue(ResultSet rs) throws SQLException {
        return ModelSerializer.getInstance().fromJson(rs.getString(2), GameModel.class);
    }

    @Override
    protected Map<String, Object> getColumns(GameModel obj) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("ID", obj.getId());
        result.put("MODEL", ModelSerializer.getInstance().toJson(obj, GameModel.class));
        return result;
    }

    @Override
    public List<ICommandAction> findAllCommands() {
        List<ICommandAction> result = new ArrayList<>();
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM COMMANDS");
            while (rs.next()) {
                ICommandAction action = ActionDeserializer.getInstance().deserializeAction(rs.getString(3));
                if (action instanceof GameAction) {
                    ((GameAction) action).setGameId(rs.getInt(1));
                }
                result.add(action);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean insertCommand(ICommandAction command, int gameId) {
        try {
            PreparedStatement stmt = db.prepareStatement("INSERT INTO COMMANDS (ID, COMMAND) VALUES (?, ?);");
            stmt.setInt(1, gameId);
            stmt.setString(2, ModelSerializer.getInstance().toJson(command, command.getClass()));
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean clearCommands() {
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
