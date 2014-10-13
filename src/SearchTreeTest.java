import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import junit.framework.TestCase;

/**
 * test the SearchTree class
 * @author jbruzek, sucram20
 *
 */
public class SearchTreeTest extends TestCase {

	private ArrayList<String> commands;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	/**
	 * set up before tests
	 */
	public void setUp() {
		System.setOut(new PrintStream(outContent));
		commands = new ArrayList<String>();
		commands.add("insert this<SEP>that");
		commands.add("remove artist this");
		commands.add("remove song that");
		commands.add("remove artist not there");
		commands.add("remove song not there");
		commands.add("print blocks");
		commands.add("print artist");
		commands.add("print song");
		commands.add("jsdhbjsdavjdf jbkvsa");
	}

	/**
	 * test main
	 */
	public void testMain() {
		String[] str = new String[3];
		str[0] = "10";
		str[1] = "32";
		str[2] = "src/test1.txt";
		SearchTree.main(str);

		assertTrue(!outContent.toString().equals(""));
	}

	/**
	 * test the get bytes method
	 */
    public void testGetBytes() {
    	String test = "Length: 10";

    	byte[] bytes = SearchTree.getBytes(test);
    	assertEquals(12, bytes.length);
    	assertEquals((byte) 10, bytes[0]);
    	assertEquals((byte) 0, bytes[1]);
    }

    /**
     * test toPair method
     */
    public void testToPair() {
    	String str = "First thing<SEP>Second thing";
    	Pair<String, String> p = SearchTree.toPair(str);

    	assertEquals("First thing", p.getFirst());
    	assertEquals("Second thing", p.getSecond());
    }

    /**
     * test the toByteArray method
     *
     * implicitely test the getLength method
     */
    public void testToByteArray() {
    	String str = "Length: 10<SEP>Length: 15-----";
    	Pair<String, String> p = SearchTree.toPair(str);

    	Pair<byte[], byte[]> pair = SearchTree.toByteArray(p);

    	assertEquals(10, SearchTree.getLength(pair.getFirst()));
    	assertEquals(15, SearchTree.getLength(pair.getSecond()));
    }

	/**
     * test the execute commands method
     */
    public void testExecuteCommands() {
    	MemoryManager mm = new MemoryManager(1000);
    	HashTable a = new HashTable(10, "Artists");
    	HashTable b = new HashTable(10, "Songs");
    	Tree t = new Tree();
    	SearchTree.executeCommands(commands, mm, a, b, t);

    	assertEquals(1, mm.getBlocks().size());
    	assertEquals(10, a.getSize());
    	assertEquals(10, b.getSize());
    }
}
