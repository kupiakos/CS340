package server.db.postgres;

import server.db.IGameDAO;
import server.db.IPersistenceProvider;
import server.db.IUserDAO;
import server.plugin.IPlugin;
import server.plugin.PersistencePlugin;
import server.plugin.PluginConfig;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.Map;

/**
 * Created by elija on 12/2/2016.
 */
public class PostgresProvider extends PersistencePlugin implements IPersistenceProvider {

    private PostgresUserDAO userDAO;
    private PostgresGameDAO gameDAO;
    private Connection db;

    public PostgresProvider(Map<String, String> config) {
        super(config);
        try {
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection("jdbc:postgresql://localhost/template1", config.get("username"), config.get("password"));
            Statement stmt = db.createStatement();
            //stmt.execute("DROP DATABASE catandb");
            ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = 'catandb';");
            if (!rs.next()) {
                rs = stmt.executeQuery("SELECT 1 FROM pg_roles WHERE rolname='player';");
                if (!rs.next()) {
                    stmt.execute("CREATE USER PLAYER WITH PASSWORD 'catan';");
                }
                stmt.execute("CREATE DATABASE CATANDB;");
                stmt.execute("GRANT ALL PRIVILEGES ON DATABASE CATANDB TO PLAYER;");
                stmt.execute("ALTER USER PLAYER WITH SUPERUSER;");
            }
            rs.close();
            stmt.close();
            db = DriverManager.getConnection("jdbc:postgresql://localhost/catandb", "player", "catan");


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        userDAO = new PostgresUserDAO(db);
        gameDAO = new PostgresGameDAO(db);
    }

    @Override
    public IPlugin start() {
        createDB();
        return this;
    }

    @Override
    public boolean createDB() {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("DO $do$ " +
                    "BEGIN " +
                    " CREATE TABLE IF NOT EXISTS USERINFO(ID INT PRIMARY KEY NOT NULL, " +
                    "USERNAME TEXT NOT NULL, " +
                    "PASSWORD TEXT NOT NULL); " +
                    "END; " +
                    "$do$");
            stmt.execute("DO $do$ " +
                    "BEGIN " +
                    " CREATE TABLE IF NOT EXISTS GAMES(ID INT PRIMARY KEY NOT NULL, " +
                    "MODEL TEXT NOT NULL); " +
                    "END; " +
                    "$do$");
            stmt.execute("DO $do$ " +
                    "BEGIN " +
                    " CREATE TABLE IF NOT EXISTS COMMANDS(ID INT NOT NULL, " +
                    "COMMAND_ORDER SERIAL PRIMARY KEY, " +
                    "COMMAND TEXT NOT NULL); " +
                    "END; " +
                    "$do$");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean startTransaction() {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("BEGIN TRANSACTION");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean finishTransaction() {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("END TRANSACTION");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean clearDB() {
        try {
            Statement stmt = db.createStatement();
            stmt.execute("DELETE FROM USERINFO");
            stmt.execute("DELETE FROM GAMES");
            stmt.execute("DELETE FROM COMMANDS");
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public IUserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public IGameDAO getGameDAO() {
        return gameDAO;
    }

    @Override
    public PluginConfig.PluginType getType() {
        return PluginConfig.PluginType.PERSISTENCE;
    }

    @Override
    public String getName() {
        return "postgres";
    }

    @Override
    public URLClassLoader getURLClassLoader() {
        URL url = null;
        try {
            url = new URL("postgres" + File.separator);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URL[] urls = new URL[]{url};
        return new URLClassLoader(urls);
    }
}
