package server.db.postgres;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by elija on 12/3/2016.
 */
public class PostgresTest {
    PostgresProvider pp;
    Map<String, String> config;

    @Before
    public void setup() {
//        Map configs = new HashMap();
//        configs.put("name", "postgres");
//        configs.put("username", "postgres");
//        configs.put("password", "password");
//        configs.put("port", "5432");
//
//        pp = new PostgresProvider(configs);
//        pp.createDB();
    }

    @Test
    public void testUserDAO() {
//        assertTrue(pp.startTransaction());
//        assertTrue(pp.finishTransaction());
//        assertTrue(pp.clearDB());
//        assertTrue(pp.getUserDAO().insert(new User(1, "James", "password")));
//        assertTrue(pp.getUserDAO().update(new User(1, "Bob", "password")));
//        assertTrue(pp.getUserDAO().findById(1) != null);
//        assertTrue(pp.getUserDAO().delete(new User(1, "whatever", "whocares")));
//        assertTrue(pp.getUserDAO().insert(new User(1, "James", "password")));
//        assertTrue(pp.getUserDAO().insert(new User(2, "Bob", "password")));
//        assertTrue(pp.getUserDAO().insert(new User(3, "Kevin", "password")));
//        assertTrue(pp.getUserDAO().findAll().size() == 3);
    }

    @Test
    public void testGameDAO() {
//        List<PlayerInfo> players = new ArrayList<PlayerInfo>();
//        assertTrue(pp.getGameDAO().insert(new GameModel(1, new GameInfo(players, "title", 1), new ClientModel())));
//        assertTrue(pp.getGameDAO().update(new GameModel());
//        assertTrue(pp.getGameDAO().findById(1) != null);
//        assertTrue(pp.getGameDAO().delete();
//        assertTrue(pp.getGameDAO().insert();
//        assertTrue(pp.getGameDAO().insert();
//        assertTrue(pp.getGameDAO().insert();
//        assertTrue(pp.getGameDAO().findAll().size() == 3);
    }

}
