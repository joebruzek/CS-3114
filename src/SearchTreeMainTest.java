import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;


/**
 *  This class is made specifically for testing the command function
 *  of SearchTree
 *
 *  @author sucram20. jbruzek
 *  @version Oct 16, 2014
 */
public class SearchTreeMainTest
{
//    private final ByteArrayOutputStream outContent
//        = new ByteArrayOutputStream();

    // ----------------------------------------------------------
    /**
     * Tests the command function of the SearchTree
     */
    @Test
    public void test()
    {

        //System.setOut(new PrintStream(outContent));
        String[] str = new String[3];
        str[0] = "10";
        str[1] = "32";
        str[2] = "src/P2_Input1_Sample.txt";
        SearchTree.main(str);
        //assertFalse(outContent.toString().equals(""));
    }

}
