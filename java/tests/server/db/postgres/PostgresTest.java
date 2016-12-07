package server.db.postgres;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by elija on 12/3/2016.
 */
public class PostgresTest {
    PostgresProvider pp;

    @Before
    public void setup() {
        //pp = new PostgresProvider("postgres", "password");
    }

    @Test
    public void testUserDAO() {
        /*assertTrue(pp.startTransaction());
        assertTrue(pp.finishTransaction());
        assertTrue(pp.clearDB());
        assertTrue(pp.getUserDAO().insert(new User(1, "James", "password")));
        assertTrue(pp.getUserDAO().update(new User(1, "Bob", "password")));
        assertTrue(pp.getUserDAO().findById(1) != null);
        assertTrue(pp.getUserDAO().delete(new User(1, "whatever", "whocares")));
        assertTrue(pp.getUserDAO().insert(new User(1, "James", "password")));
        assertTrue(pp.getUserDAO().insert(new User(2, "Bob", "password")));
        assertTrue(pp.getUserDAO().insert(new User(3, "Kevin", "password")));
        assertTrue(pp.getUserDAO().findAll().size() == 3);*/
    }

}
