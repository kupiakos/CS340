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
        pp = new PostgresProvider();
    }

    @Test
    public void test() {
        pp.startTransaction();
        pp.finishTransaction();
        pp.clearDB();
    }

}
