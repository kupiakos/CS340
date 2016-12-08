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
//        configs.put("password", "");
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
//        assertTrue(pp.getGameDAO().insert(new GameModel(1, new GameInfo(new ArrayList(), "title", 1), new ClientModel())));
//        assertEquals("title", pp.getGameDAO().findById(1).getGameInfo().getTitle());
//        assertTrue(pp.getGameDAO().update(new GameModel(1, new GameInfo(new ArrayList(), "newTitle", 1), new ClientModel())));
//        assertEquals("newTitle", pp.getGameDAO().findById(1).getGameInfo().getTitle());
//        assertTrue(pp.getGameDAO().delete(new GameModel(1, new GameInfo(), new ClientModel())));
//        assertTrue(pp.getGameDAO().insert(new GameModel(2, new GameInfo(), new ClientModel())));
//        assertTrue(pp.getGameDAO().insert(new GameModel(3, new GameInfo(), new ClientModel())));
//        assertTrue(pp.getGameDAO().insert(new GameModel(4, new GameInfo(), new ClientModel())));
//        assertTrue(pp.getGameDAO().findAll().size() == 3);
    }

}
