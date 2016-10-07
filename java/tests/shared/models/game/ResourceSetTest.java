package shared.models.game;

import org.junit.Assert;
import org.junit.Test;

public class ResourceSetTest {
    @Test
    public void toNegative() throws Exception {

    }

    @Test
    public void combine() throws Exception {

    }

    @Test
    public void subtract() throws Exception {

    }

    @Test
    public void isSubset() throws Exception {

    }

    @Test
    public void isNegative() throws Exception {

    }

    @Test
    public void combineStatic() throws Exception {

    }

    @Test
    public void subtract1() throws Exception {

    }

    @Test
    public void toNegativeStatic() throws Exception {

    }

    @Test
    public void equals() throws Exception {
        Assert.assertEquals(new ResourceSet(1, 2, 3, 4, 5), new ResourceSet(1, 2, 3, 4, 5));
        Assert.assertNotEquals(new ResourceSet(1, 2, 3, 4, 5), new ResourceSet(5, 4, 3, 2, 1));
        Assert.assertEquals(new ResourceSet(), new ResourceSet());
    }

}