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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        //-- test output
        Util.out(expectedString);
        expectedString += Util.N;
        //-- Put things back
        System.out.flush();
        System.setOut(old);
        //-- test results
        assertEquals(expectedString, baos.toString());
    }
}