package caojun.com.logintest;

import org.junit.Test;

import caojun.com.logintest.build.Car;
import caojun.com.logintest.build.ConcreteBuild;
import caojun.com.logintest.build.Director;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testBuild(){
        Car construct = new Director(new ConcreteBuild()).construct();
        System.out.println(construct);
    }
}