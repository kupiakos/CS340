package server.db.postgres;

import server.db.IGameDAO;
import server.models.GameModel;
import shared.models.ICommandAction;

import java.sql.Connection;
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
        return null;
    }

    @Override
    public List<GameModel> findAll() {
        return null;
    }

    @Override
    public boolean insert(GameModel obj) {
        return false;
    }

    @Override
    public boolean update(GameModel obj) {
        return false;
    }

    @Override
    public boolean delete(GameModel obj) {
        return false;
    }

    @Override
    public List<ICommandAction> findAllCommands() {
        return null;
    }

    @Override
    public boolean insertCommand(ICommandAction command, int gameId) {
        return false;
    }

    @Override
    public boolean flushCommands() {
        return false;
    }
}
