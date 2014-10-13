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

	// ----------------------------------------------------------
	/**
	 * Just cuz
	 */
	public void printSomething() {
		System.out.println("something");
	}

	// ----------------------------------------------------------
	/**
	 * tests insert for multiple inserts
	 */
	public void testInsert()
	{
	    int j = 3;
	    assertTrue(tree.getRoot().isLeaf());
	    for (int i = 2; i <= 100; i*= 3 , j *= 3) {
            MemHandle m = new MemHandle(i);
            MemHandle v = new MemHandle(i + 5);
            tree.insert(new KVPair(m, v));
            MemHandle h = new MemHandle(j);
            MemHandle x = new MemHandle(j + 3);
            tree.insert(new KVPair(h, x));
        }
	    MemHandle m = new MemHandle(5);
        MemHandle v = new MemHandle(20);
	    tree.insert(new KVPair(m, v));

	    MemHandle h = new MemHandle(3);
        MemHandle x = new MemHandle(25);
        tree.insert(new KVPair(h, x));

	    assertFalse(tree.getRoot().isLeaf());
	    assertFalse(tree.getRoot().getChild(0).isLeaf());
	    assertFalse(tree.getRoot().getChild(1).isLeaf());
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
        	tree.insert(new KVPair(m, v));
        }

        tree.printTree();

        try {
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
        String whatWasPrinted = new String(baos.toByteArray());
        String[] linesOfOutput = whatWasPrinted.split(
                System.getProperty("line.separator"));
        assertEquals(7, linesOfOutput.length);
        assertEquals("3 8", linesOfOutput[0]);
        assertEquals("  2 7", linesOfOutput[1]);
        assertEquals("    1 6", linesOfOutput[2]);
        assertEquals("    2 7", linesOfOutput[3]);
        assertEquals("  4 9", linesOfOutput[4]);
        assertEquals("    3 8", linesOfOutput[5]);
        assertEquals("    4 9 5 10", linesOfOutput[6]);
	}

	/**
	 * tests searching for a key in the tree
	 */
	public void testSearchTree()
	{
        int j= 3;
	    for (int i = 2; i <= 100; i*= 3 , j *= 3) {
            MemHandle m = new MemHandle(i);
            MemHandle v = new MemHandle(i + 5);
            tree.insert(new KVPair(m, v));
            MemHandle h = new MemHandle(j);
            MemHandle x = new MemHandle(j + 3);
            tree.insert(new KVPair(h, x));
        }
	    for (int i = 2; i <= 100; i*= 3) {
            MemHandle m = new MemHandle(i);
            MemHandle v = new MemHandle(i + 5);
            assertTrue(tree.search(tree.getRoot(), new KVPair(m, v)));
        }
	    MemHandle a = new MemHandle(0);
        MemHandle b = new MemHandle(7);
        KVPair pair = new KVPair(a, b);
        assertFalse(tree.search(tree.getRoot(), pair));

	}
}
