package name.karmanov;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class UtilTest {

    @Test
    void out() {
        String expectedString = "teststring";
        //-- change output
        ByteArrayOutputStream baOs = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baOs);
        PrintStream old = System.out;
        System.setOut(ps);
        //-- test output
        Util.out(expectedString);
        expectedString += Util.N;
        //-- restore output
        System.out.flush();
        System.setOut(old);
        //-- assert results
        assertEquals(expectedString, baOs.toString());
    }
}