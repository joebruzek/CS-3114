import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;

/**
 * test the FileReader class
 * 
 * @author jbruzek
 * @author sucram20
 * @version 2014.10.14
 */
public class FileReaderTest extends TestCase {
	
	private FileReader fr;

	/**
	 * set up the tests
	 */
	public void setUp() {
		File file = new File("src/P1sampleInput.txt");
		fr = new FileReader(file);
	}

	/**
	 * test the get Commands method
	 */
	public void testGetCommands() {
		ArrayList<String> commands = fr.getCommands();

		assertEquals(24, commands.size());
	}

	/**
	 * test the get Command method
	 */
	public void testGetCommand() {
		String command = fr.getCommand(5);

		assertEquals("insert Ma Rainey<SEP>Ma Rainey's Black Bottom", command);
	}
}
