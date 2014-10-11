import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;

import junit.framework.TestCase;

/**
 * test the tree class
 * @author jbruzek sucram20
 *
 */
public class TreeTest extends TestCase {

	private Tree tree;

	/**
	 * set up a tree before the tests start
	 */
	@Before
	public void setUp() {
		tree = new Tree();
	}

	public void printSomething() {
		System.out.println("something");
	}

	public void testIsLeaf()
	{
	    assertTrue(tree.getRoot().isLeaf());
	    for (int i = 1; i <= 3; i++) {
            MemHandle m = new MemHandle(i);
            MemHandle v = new MemHandle(i + 5);
            tree.insert(tree.getRoot(), null, new KVPair(m, v));
        }
	    assertFalse(tree.getRoot().isLeaf());
	    assertEquals(1, tree.getRoot().numRecs());
	    tree.print(tree.getRoot(), tree.depth());

	}

	/**
	 * test the print and printTree methods
	 */
	public void testPrint() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        for (int i = 1; i <= 5; i++) {
        	MemHandle m = new MemHandle(i);
        	MemHandle v = new MemHandle(i + 5);
        	tree.insert(tree.getRoot(), null, new KVPair(m, v));
        }

        tree.print(tree.getRoot(), tree.depth());

        try {
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
        String whatWasPrinted = new String(baos.toByteArray());
        String[] linesOfOutput = whatWasPrinted.split(
                System.getProperty("line.separator"));
        assertEquals(8, linesOfOutput.length);
        assertEquals("3 9", linesOfOutput[0]);
        assertEquals("  2 7", linesOfOutput[1]);
        assertEquals("    1 6", linesOfOutput[2]);
        assertEquals("    2 7", linesOfOutput[3]);
        assertEquals("  4 9", linesOfOutput[4]);
        assertEquals("    3 8", linesOfOutput[5]);
        assertEquals("    4 9", linesOfOutput[6]);
        assertEquals("    5 10", linesOfOutput[7]);
	}
}
